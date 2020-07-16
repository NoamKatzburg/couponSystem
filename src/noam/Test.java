package noam;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import noam.beans.Category;
import noam.beans.Company;
import noam.beans.Coupon;
import noam.beans.Customer;
import noam.dao.CategoriesDAO;
import noam.db.Database;
import noam.dbdao.CategoriesDBDAO;
import noam.exceptions.CannotChangeException;
import noam.exceptions.EmailExistsException;
import noam.exceptions.NoSuchCouponException;
import noam.exceptions.OutOfStockException;
import noam.facade.AdminFacade;
import noam.facade.CompanyFacade;
import noam.facade.CustomerFacade;
import noam.utils.MyUtils;

public class Test {

	@SuppressWarnings("deprecation")
	public static void testAll() throws ClassNotFoundException, SQLException, InterruptedException,
			OutOfStockException, NoSuchCouponException, CannotChangeException, EmailExistsException {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Database.initDB();
		CategoriesDAO categoriesDBDAO = new CategoriesDBDAO();
		categoriesDBDAO.initCategoryTable();

		Company com1 = new Company();
		com1.setName("company1");
		com1.setEmail("com@g.com");
		com1.setPassword("com1");

		Company com2 = new Company();
		com2.setName("company2");
		com2.setEmail("com2@g.com");
		com2.setPassword("com2");

		Company com3 = new Company();
		com3.setName("company3");
		com3.setEmail("com3@g.com");
		com3.setPassword("com3");


		Customer cust1 = new Customer();
		cust1.setFirstName("customer1");
		cust1.setLastName("customer");
		cust1.setEmail("cust1@g.com");
		cust1.setPassword("1234");

		Customer cust2 = new Customer();
		cust2.setFirstName("customer2");
		cust2.setLastName("customer");
		cust2.setEmail("cust2@g.com");
		cust2.setPassword("1234");

		Customer cust3 = new Customer();
		cust3.setFirstName("customer3");
		cust3.setLastName("customer");
		cust3.setEmail("cust3@g.com");
		cust3.setPassword("1234");

		Customer cust4 = new Customer();
		cust4.setFirstName("customer4");
		cust4.setLastName("customer");
		cust4.setEmail("cust4@g.com");
		cust4.setPassword("1234");


		Coupon coupon1 = new Coupon();
		coupon1.setCompanyID(1);
		coupon1.setCategory(Category.Electricty);
		coupon1.setTitle("new");
		coupon1.setDescription("bolts and more");
		coupon1.setStartDate(new Date(2021, 9, 3));
		coupon1.setEndDate(new Date(2021, 9, 17));
		coupon1.setAmount(21);
		coupon1.setPrice(22.2);
		coupon1.setImage("img");

		Coupon coupon2 = new Coupon();
		coupon2.setCompanyID(2);
		coupon2.setCategory(Category.Food);
		coupon2.setTitle("1+1");
		coupon2.setDescription("eat more");
		coupon2.setStartDate(new Date(2021, 9, 17));
		coupon2.setEndDate(new Date(2021, 9, 17));
		coupon2.setAmount(100);
		coupon2.setPrice(51.5);
		coupon2.setImage("img");

		Coupon coupon3 = new Coupon();
		coupon3.setCompanyID(3);
		coupon3.setCategory(Category.Restaurant);
		coupon3.setTitle("come to us");
		coupon3.setDescription("free meal");
		coupon3.setStartDate(new Date(2019, 9, 17));
		coupon3.setEndDate(new Date(2019, 9, 17));
		coupon3.setAmount(33);
		coupon3.setPrice(221.5);
		coupon3.setImage("img");

		Coupon coupon4 = new Coupon();
		coupon4.setCompanyID(1);
		coupon4.setCategory(Category.Restaurant);
		coupon4.setTitle("foood");
		coupon4.setDescription("hello");
		coupon4.setStartDate(new Date(2020, 9, 3));
		coupon4.setEndDate(new Date(2020, 9, 17));
		coupon4.setAmount(1213);
		coupon4.setPrice(66.9);
		coupon4.setImage("img");

		// testCompanyClass(companiesDBDAO, c1, c2, c3);
		// testCustomerClass(customersDBDAO, cust1, cust2, cust3, cust4);
		// testCouponsClass(cuDbdao, cu1, cu2, cu3);

		AdminFacade aFacade = new AdminFacade();
		MyUtils.classSeparator("*Admin*");

		MyUtils.printTestLine("Testing Admin login");
		System.out.println("If it works will return true: " + aFacade.login("admin@admin.com", "admin"));

		MyUtils.printTestLine("Testing add company");
		aFacade.addCompany(com1);
		aFacade.addCompany(com2);
		aFacade.addCompany(com3);
		List<Company> companies = aFacade.getAllCompanies();
		MyUtils.printCompaniesTable(companies);

		MyUtils.printTestLine("Testing update company");
		com1.setEmail("com1@g.com");
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

		// checked and works
//		MyUtils.printTestLine("Testing delete company");
//		aFacade.deleteCompany(3);
//		System.out.println("deleted " + com3.getName());
//		companies = aFacade.getAllCompanies();
//		MyUtils.printCompaniesTable(companies);
//		
//		
//		MyUtils.printTestLine("Testing delete customer");
//		aFacade.deleteCustomer(1);
//		System.out.println("deleted " + cust1.getFirstName() + " " + cust1.getLastName());
//		customers = aFacade.getAllCustomers();
//		MyUtils.printCustomersTable(customers);

		////////////////////////////////////////////////////////////////////////////////////////
		CompanyFacade companyFacade = new CompanyFacade();
		MyUtils.classSeparator("Company");

		MyUtils.printTestLine("Testing Company Login");
		System.out.println("if works should return true: " + companyFacade.login("com1@g.com", "com1")); // company 1

		MyUtils.printTestLine("Testing add coupon");
		companyFacade.addCoupon(coupon1);
		companyFacade.login("com2@g.com", "com2"); // company 2
		companyFacade.addCoupon(coupon2);
		companyFacade.login("com3@g.com", "com3"); // company 3
		companyFacade.addCoupon(coupon3);
		companyFacade.login("com1@g.com", "com1");
		companyFacade.addCoupon(coupon4);
		List<Coupon> coupons = aFacade.getAllCoupons();
		MyUtils.printCouponsTable(coupons);

		MyUtils.printTestLine("Testing update coupon");
		coupon2.setAmount(500);
		companyFacade.login("com2@g.com", "com2");
		companyFacade.updateCoupon(coupon2, 2);
		coupons = aFacade.getAllCoupons();
		MyUtils.printCouponsTable(coupons);

		MyUtils.printTestLine("Testing get company coupons");
		companyFacade.login("com1@g.com", "com1");
		coupons = companyFacade.getCompanyCoupons();
		MyUtils.printCouponsTable(coupons);

		MyUtils.printTestLine("get  Company Coupons By Category (Restaurant)");
		coupons = companyFacade.getCompanyCouponsByCategory(Category.Restaurant);
		MyUtils.printCouponsTable(coupons);

		MyUtils.printTestLine("testing get company coupons by price (less or equal to 50)");
		coupons = companyFacade.getCompanyCouponsByPrice(50.0);
		MyUtils.printCouponsTable(coupons);

		MyUtils.printTestLine("Getting company details");
		System.out.println(companyFacade.getCompanyDetails());

		
		// checked and works
//		MyUtils.printTestLine("Testing delete coupon");
//		companyFacade.deleteCoupon(1);
//		coupons = aFacade.getAllCoupons();
//		MyUtils.printCouponsTable(coupons);

////////////////////////////////////////////////////////////////////////////////////////
		
		CustomerFacade customerFacade = new CustomerFacade();

		MyUtils.classSeparator("Customer");

		MyUtils.printTestLine("Testing Customer login");
		System.out.println("If it works will return true: " + customerFacade.login("cust1@g.com", "987654"));

		MyUtils.printTestLine("testing Purchase coupon");
		customerFacade.purchaseCoupon(coupon1, 1);
		customerFacade.login("cust2@g.com", "1234");
		customerFacade.purchaseCoupon(coupon2, 2);
		customerFacade.purchaseCoupon(coupon2, 2); // tried to buy twice
		customerFacade.login("cust3@g.com", "1234");
		customerFacade.purchaseCoupon(coupon3, 3); // is expired
		customerFacade.purchaseCoupon(coupon4, 4);
		customerFacade.purchaseCoupon(coupon1, 1);
		customerFacade.login("cust4@g.com", "1234");
		customerFacade.purchaseCoupon(coupon4, 4);
		System.out.println();
		MyUtils.printCouponsVsCustomersTable();
		coupons = aFacade.getAllCoupons();
		MyUtils.printCouponsTable(coupons);

		MyUtils.printTestLine("testing get customer coupons (customer 3)");
		customerFacade.login("cust3@g.com", "1234");
		MyUtils.printCouponsTable(customerFacade.getCustomerCoupons());
		
		MyUtils.printTestLine("Testing get Customer Coupons By Category");
		coupons = customerFacade.getCustomerCouponsByCategory(Category.Restaurant);
		MyUtils.printCouponsTable(coupons);
		
		MyUtils.printTestLine("Testing get Customer Coupons By price");
		coupons = customerFacade.getCustomerCouponsByPrice(50.0);
		MyUtils.printCouponsTable(coupons);
		
		MyUtils.printTestLine("Testing get Customer details");
		System.out.println(customerFacade.getCustomerDetails());
	}

}
