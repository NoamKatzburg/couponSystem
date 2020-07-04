package noam;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import noam.beans.Category;
import noam.beans.Company;
import noam.beans.Coupon;
import noam.beans.Customer;
import noam.dao.CategoriesDAO;
import noam.dao.CompaniesDAO;
import noam.db.Database;
import noam.dbdao.CategoriesDBDAO;
import noam.dbdao.CompaniesDBDAO;
import noam.dbdao.CouponsDBDAO;
import noam.dbdao.CustomersDBDAO;
import noam.exceptions.CannotChangeNameException;
import noam.exceptions.NoSuchCouponException;
import noam.exceptions.OutOfStockException;
import noam.facade.AdminFacade;
import noam.facade.EmailExistsException;
import noam.utils.MyUtils;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException,
			OutOfStockException, NoSuchCouponException, CannotChangeNameException, EmailExistsException {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Database.initDB();
		CategoriesDAO categoriesDBDAO = new CategoriesDBDAO();
		categoriesDBDAO.initCategoryTable();

		CompaniesDAO companiesDBDAO = new CompaniesDBDAO();

		Company com1 = new Company();
		com1.setName("company1");
		com1.setEmail("com@pany.com");
		com1.setPassword("1234");

		Company com2 = new Company();
		com2.setName("company2");
		com2.setEmail("My@comp");
		com2.setPassword("helooo");

		Company com3 = new Company();
		com3.setName("company3");
		com3.setEmail("com@pany");
		com3.setPassword("12354");

		CustomersDBDAO customersDBDAO = new CustomersDBDAO();

		Customer cust1 = new Customer();
		cust1.setFirstName("noam");
		cust1.setLastName("katz");
		cust1.setEmail("katz@katz");
		cust1.setPassword("1234");

		Customer cust2 = new Customer();
		cust2.setFirstName("kobi");
		cust2.setLastName("shasha");
		cust2.setEmail("noakobim@katz");
		cust2.setPassword("1234");

		Customer cust3 = new Customer();
		cust3.setFirstName("benny");
		cust3.setLastName("katz");
		cust3.setEmail("noam@katz");
		cust3.setPassword("1234");

		Customer cust4 = new Customer();
		cust4.setFirstName("hallel");
		cust4.setLastName("katz");
		cust4.setEmail("hallel@katz");
		cust4.setPassword("1234");

		CouponsDBDAO cuDbdao = new CouponsDBDAO();

		Coupon coupon1 = new Coupon();
		coupon1.setCategory(Category.Electricty);
		coupon1.setTitle("new");
		coupon1.setDescription("bolts and more");
		coupon1.setStartDate(new Date(2020, 9, 3));
		coupon1.setEndDate(new Date(2020, 9, 17));
		coupon1.setAmount(21);
		coupon1.setPrice(22.2);
		coupon1.setImage("img");

		Coupon coupon2 = new Coupon();
		coupon2.setCategory(Category.Food);
		coupon2.setTitle("1+1");
		coupon2.setDescription("eat more");
		coupon2.setStartDate(new Date(2020, 9, 17));
		coupon2.setEndDate(new Date(2020, 9, 17));
		coupon2.setAmount(100);
		coupon2.setPrice(51.5);
		coupon2.setImage("img");

		Coupon coupon3 = new Coupon();
		coupon3.setCategory(Category.Restaurant);
		coupon3.setTitle("come to us");
		coupon3.setDescription("free meal");
		coupon3.setStartDate(new Date(2020, 9, 17));
		coupon3.setEndDate(new Date(2020, 9, 17));
		coupon3.setAmount(33);
		coupon3.setPrice(221.5);
		coupon3.setImage("img");

		// testCompanyClass(companiesDBDAO, c1, c2, c3);
		// testCustomerClass(customersDBDAO, cust1, cust2, cust3, cust4);
		// testCouponsClass(cuDbdao, cu1, cu2, cu3);
		AdminFacade aFacade = new AdminFacade();

		
		MyUtils.printTestLine("Testing login");
		System.out.println("If it works will return true: " + aFacade.login("admin@admin.com", "admin"));

		
		MyUtils.printTestLine("Testing add company");
		aFacade.addCompany(com1);
		aFacade.addCompany(com2);
		aFacade.addCompany(com3);
		List<Company> companies = aFacade.getAllCompanies();
		MyUtils.printCompaniesTable(companies);

		
		MyUtils.printTestLine("Testing update company");
		com1.setEmail("company@com");
		aFacade.updateCompany(com1, 1);
		companies = aFacade.getAllCompanies();
		MyUtils.printCompaniesTable(companies);

		
		MyUtils.printTestLine("Testing get one company");
		System.out.println(aFacade.getOneCompany(2));

		
		MyUtils.printTestLine("Testing add customer");
		aFacade.addCustomer(cust1);
		aFacade.addCustomer(cust2);
		aFacade.addCustomer(cust3);
		aFacade.addCustomer(cust4);
		List<Customer> customers = aFacade.getAllCustomers();
		MyUtils.printCustomersTable(customers);

		
		MyUtils.printTestLine("Testing update customer");
		cust1.setPassword("987654");
		try {
			cust2.setId(555);
		} catch (Exception e) {
			e.getMessage();
		}
		aFacade.updateCustomer(cust1, 1);
		aFacade.updateCustomer(cust2, 2);
		customers = aFacade.getAllCustomers();
		MyUtils.printCustomersTable(customers);
		

		MyUtils.printTestLine("Testing one customer");
		System.out.println(aFacade.getOneCustomer(2));

		
		MyUtils.printTestLine("Testing delete company");
		aFacade.deleteCompany(3);
		System.out.println("deleted " + com3.getName());
		companies = aFacade.getAllCompanies();
		MyUtils.printCompaniesTable(companies);
		
		
		MyUtils.printTestLine("Testing delete customer");
		aFacade.deleteCustomer(1);
		System.out.println("deleted " + cust1.getFirstName() + " " + cust1.getLastName());
		customers = aFacade.getAllCustomers();
		MyUtils.printCustomersTable(customers);

	}

	public static void testCompanyClass(CompaniesDBDAO cDbdao, Company c1, Company c2, Company c3)
			throws SQLException, CannotChangeNameException {
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
		// cDbdao.deleteCompany(2);

		// checking getAllCompanies
		System.out.println(cDbdao.getAllCompanies());

		// checking getonecompany
		System.out.println(cDbdao.getOneCompany(3));

		System.out.println("finished checking Copmanies \n *******************************************");
	}

	public static void testCouponsClass(CouponsDBDAO cuDbdao, Coupon cu1, Coupon cu2, Coupon cu3)
			throws OutOfStockException, NoSuchCouponException {

		// Checking add coupon
		cuDbdao.addCoupon(cu1);
		cuDbdao.addCoupon(cu2);
		cuDbdao.addCoupon(cu3);

		// checking update coupon
		cu1.setAmount(3000);
		cuDbdao.updateCoupon(cu1, 1);

		// checking delete coupon
		// cuDbdao.deleteCoupon(2);

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
		// cuDbdao.deleteCouponPurchase(1, 3);

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
		// cDbdao.deleteCustomer(2);

		// checking get all customers
		System.out.println(cDbdao.getAllCustomers());

		// checking one customer
		System.out.println(cDbdao.getOneCustomer(3));

		System.out.println("finished checking customers \n *******************************************");
	}

}
