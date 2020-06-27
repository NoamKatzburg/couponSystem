package noam;

import java.sql.SQLException;
import java.util.Date;

import noam.beans.Category;
import noam.beans.Company;
import noam.beans.Coupon;
import noam.beans.Customer;
import noam.db.Database;
import noam.dbdao.CompaniesDBDAO;
import noam.dbdao.CouponsDBDAO;
import noam.dbdao.CustomersDBDAO;
import noam.exceptions.noSuchCouponException;
import noam.exceptions.outOfStockException;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException,
			outOfStockException, noSuchCouponException {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Database.initDB();

		CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();

		Company c1 = new Company("comp", "com@pany.com", "1234");
		Company c2 = new Company("myComp", "My@comp", "helooo");
		Company c3 = new Company("comp", "com@pany", "12354");

		testCompanyClass(companiesDBDAO, c1, c2, c3);

		CustomersDBDAO customersDBDAO = new CustomersDBDAO();

		Customer cust1 = new Customer("noam", "katz", "noam@katz", "1234");
		Customer cust2 = new Customer("kobi", "shasha", "noakobim@katz", "1234");
		Customer cust3 = new Customer("benny", "katz", "noam@katz", "1234");
		Customer cust4 = new Customer("hallel", "katz", "hallel@katz", "1234");

		testCustomerClass(customersDBDAO, cust1, cust2, cust3, cust4);

		CouponsDBDAO cuDbdao = new CouponsDBDAO();

		Coupon cu1 = new Coupon(1, Category.Electricty, "new", "bolts and more", new Date(120, 9, 03),
				new Date(120, 9, 17), 21, 22, "img");
		Coupon cu2 = new Coupon(1, Category.Food, "1+1", "eat more", new Date(120, 10, 10), new Date(120, 10, 20), 100,
				5, "img");
		Coupon cu3 = new Coupon(3, Category.Restaurant, "come to us", "free meal", new Date(120, 10, 01),
				new Date(120, 10, 20), 5, 123, "img");

		testCouponsClass(cuDbdao, cu1, cu2, cu3);

	}

	public static void testCompanyClass(CompaniesDBDAO cDbdao, Company c1, Company c2, Company c3) throws SQLException {
		// Checking add company
		cDbdao.addCompany(c1);
		cDbdao.addCompany(c2);
		cDbdao.addCompany(c3);

		// Checking update company
		c1.setName("comp2");
		cDbdao.updateCompany(c1, 1);

		// checking is company exist
		System.out.println("if this works, should return true: " + cDbdao.isCompanyExist("com@pany", "12354"));

		// checking delete company
		cDbdao.deleteCompany(2);

		// checking getAllCompanies
		System.out.println(cDbdao.getAllCompanies());

		// checking getonecompany
		System.out.println(cDbdao.getOneCompany(3));

		System.out.println("finished checking Copmanies \n *******************************************");
	}

	public static void testCouponsClass(CouponsDBDAO cuDbdao, Coupon cu1, Coupon cu2, Coupon cu3)
			throws outOfStockException, noSuchCouponException {

		// Checking add coupon
		cuDbdao.addCoupon(cu1);
		cuDbdao.addCoupon(cu2);
		cuDbdao.addCoupon(cu3);

		// checking update coupon
		cu1.setAmount(3000);
		cuDbdao.updateCoupon(cu1, 1);

		// checking delete coupon
		cuDbdao.deleteCoupon(2);

		// checking getAllCoupons
		System.out.println(cuDbdao.getAllCoupons());

		// checking get one coupon 
		System.out.println("onr coupon " + cuDbdao.getOneCoupon(3));

		/*
		 * check coupon purchase can only do after i add customers 
		 */
		 cuDbdao.addCouponPurchase(1, 3);

		/*
		 * check delete coupon purchase can only do after i add customers 
		 */
		cuDbdao.deleteCouponPurchase(1, 3);

		// checking does coupon exist

		System.out.println("if works should return true: " + cuDbdao.doesCouponExist(3));

		System.out.println("finished checking Coupons \n *******************************************");

	}

	public static void testCustomerClass(CustomersDBDAO cDbdao, Customer c1, Customer c2, Customer c3, Customer c4) {

		// checking add customers
		cDbdao.addCustomer(c1);
		cDbdao.addCustomer(c2);
		cDbdao.addCustomer(c3);
		cDbdao.addCustomer(c4);

		// checking is customer exist

		System.out.println("if this works should br true: " + cDbdao.isCustomerExist("noam@katz", "1234"));

		// checking update customer
		c3.setPassword("111111");
		cDbdao.updateCustomer(c3, 3);

		// checking delete customer
		cDbdao.deleteCustomer(2);

		// checking get all customers
		System.out.println(cDbdao.getAllCustomers());

		// checking one customer
		System.out.println(cDbdao.getOneCustomer(3));

		System.out.println("finished checking customers \n *******************************************");

	}

}
