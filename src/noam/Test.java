package noam;

import java.sql.SQLException;
import java.util.Arrays;
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
import noam.facade.ClientFacade;
import noam.facade.CompanyFacade;
import noam.facade.CustomerFacade;
import noam.login.ClientType;
import noam.login.LoginManager;
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
		

		MyUtils.classSeparator("*Admin*");

		MyUtils.printTestLine("Testing Admin login");
		ClientFacade facade = LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);


		MyUtils.printTestLine("Testing add company");
		((AdminFacade) facade).addCompany(com1);
		((AdminFacade) facade).addCompany(com2);
		((AdminFacade) facade).addCompany(com3);
		List<Company> companies = ((AdminFacade) facade).getAllCompanies();
		MyUtils.printCompaniesTable(companies);

		MyUtils.printTestLine("Testing update company");
		com1.setEmail("com1@g.com");
		((AdminFacade) facade).updateCompany(com1, 1);
		companies = ((AdminFacade) facade).getAllCompanies();
		MyUtils.printCompaniesTable(companies);

		MyUtils.printTestLine("Testing get one company");
		System.out.println(((AdminFacade) facade).getOneCompany(2));

		MyUtils.printTestLine("Testing add customer");
		((AdminFacade) facade).addCustomer(cust1);
		((AdminFacade) facade).addCustomer(cust2);
		((AdminFacade) facade).addCustomer(cust3);
		((AdminFacade) facade).addCustomer(cust4);
		List<Customer> customers = ((AdminFacade) facade).getAllCustomers();
		MyUtils.printCustomersTable(customers);

		MyUtils.printTestLine("Testing update customer");
		cust1.setPassword("987654");
		try {
			cust2.setId(555);
		} catch (Exception e) {
			e.getMessage();
		}
		((AdminFacade) facade).updateCustomer(cust1, 1);
		((AdminFacade) facade).updateCustomer(cust2, 2);
		customers = ((AdminFacade) facade).getAllCustomers();
		MyUtils.printCustomersTable(customers);

		MyUtils.printTestLine("Testing one customer");
		System.out.println(((AdminFacade) facade).getOneCustomer(2));

		// checked and works
//		MyUtils.printTestLine("Testing delete company");
//		((AdminFacade) facade).deleteCompany(3);
//		System.out.println("deleted " + com3.getName());
//		companies = ((AdminFacade) facade).getAllCompanies();
//		MyUtils.printCompaniesTable(companies);
//		
//		
//		MyUtils.printTestLine("Testing delete customer");
//		((AdminFacade) facade).deleteCustomer(1);
//		System.out.println("deleted " + cust1.getFirstName() + " " + cust1.getLastName());
//		customers = ((AdminFacade) facade).getAllCustomers();
//		MyUtils.printCustomersTable(customers);

		////////////////////////////////////////////////////////////////////////////////////////
		MyUtils.classSeparator("Company");

		MyUtils.printTestLine("Testing Company Login");
		facade = LoginManager.getInstance().login("com1@g.com", "com1", ClientType.COMPANY);// company 1
		System.out.println(facade);

		MyUtils.printTestLine("Testing add coupon");
		((CompanyFacade) facade).addCoupon(coupon1);
		facade = LoginManager.getInstance().login("com2@g.com", "com2", ClientType.COMPANY);// company 2
		((CompanyFacade) facade).addCoupon(coupon2);
		facade = LoginManager.getInstance().login("com3@g.com", "com3", ClientType.COMPANY);// company 3
		((CompanyFacade) facade).addCoupon(coupon3);
		facade = LoginManager.getInstance().login("com1@g.com", "com1", ClientType.COMPANY);
		((CompanyFacade) facade).addCoupon(coupon4);
		facade = LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
		List<Coupon> coupons = ((AdminFacade) facade).getAllCoupons();
		MyUtils.printCouponsTable(coupons);

		MyUtils.printTestLine("Testing update coupon");
		coupon2.setAmount(500);
		facade = LoginManager.getInstance().login("com2@g.com", "com2", ClientType.COMPANY);// company 2
		((CompanyFacade) facade).updateCoupon(coupon2, 2);
		facade = LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
		coupons = ((AdminFacade) facade).getAllCoupons();
		MyUtils.printCouponsTable(coupons);

		MyUtils.printTestLine("Testing get company coupons");
		facade = LoginManager.getInstance().login("com1@g.com", "com1", ClientType.COMPANY);
		coupons = ((CompanyFacade) facade).getCompanyCoupons();
		MyUtils.printCouponsTable(coupons);

		MyUtils.printTestLine("get  Company Coupons By Category (Restaurant)");
		coupons = ((CompanyFacade) facade).getCompanyCouponsByCategory(Category.Restaurant);
		MyUtils.printCouponsTable(coupons);

		MyUtils.printTestLine("testing get company coupons by price (less or equal to 50)");
		coupons = ((CompanyFacade) facade).getCompanyCouponsByPrice(50.0);
		MyUtils.printCouponsTable(coupons);

		MyUtils.printTestLine("Getting company details");
		System.out.println(((CompanyFacade) facade).getCompanyDetails());

		
		// checked and works
//		MyUtils.printTestLine("Testing delete coupon");
//		((CompanyFacade) facade).deleteCoupon(1);
//		facade = LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
//		coupons = ((AdminFacade) facade).getAllCoupons();
//		MyUtils.printCouponsTable(coupons);

//////////////////////////////////////////////////////////////////////////////////////////


		MyUtils.classSeparator("Customer");

		MyUtils.printTestLine("Testing Customer login");
		facade = LoginManager.getInstance().login("cust1@g.com", "987654", ClientType.CUSTOMER);

		MyUtils.printTestLine("testing Purchase coupon");
		((CustomerFacade) facade).purchaseCoupon(coupon1, 1);
		facade = LoginManager.getInstance().login("cust2@g.com", "1234", ClientType.CUSTOMER);
		((CustomerFacade) facade).purchaseCoupon(coupon2, 2);
		((CustomerFacade) facade).purchaseCoupon(coupon2, 2); // tried to buy twice, should fail
		facade = LoginManager.getInstance().login("cust3@g.com", "1234", ClientType.CUSTOMER);
		((CustomerFacade) facade).purchaseCoupon(coupon3, 3); // is expired, should fail
		((CustomerFacade) facade).purchaseCoupon(coupon4, 4);
		((CustomerFacade) facade).purchaseCoupon(coupon1, 1);
		facade = LoginManager.getInstance().login("cust4@g.com", "1234", ClientType.CUSTOMER);
		((CustomerFacade) facade).purchaseCoupon(coupon4, 4);
		System.out.println();
		MyUtils.printCouponsVsCustomersTable();
		facade = LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
		coupons = ((AdminFacade) facade).getAllCoupons();
		MyUtils.printCouponsTable(coupons);

		MyUtils.printTestLine("testing get customer coupons (customer 3)");
		facade = LoginManager.getInstance().login("cust3@g.com", "1234", ClientType.CUSTOMER);
		MyUtils.printCouponsTable(((CustomerFacade) facade).getCustomerCoupons());
		
		MyUtils.printTestLine("Testing get Customer Coupons By Category");
		coupons = ((CustomerFacade) facade).getCustomerCouponsByCategory(Category.Restaurant);
		MyUtils.printCouponsTable(coupons);
		
		MyUtils.printTestLine("Testing get Customer Coupons By price");
		coupons = ((CustomerFacade) facade).getCustomerCouponsByPrice(50.0);
		MyUtils.printCouponsTable(coupons);
		
		MyUtils.printTestLine("Testing get Customer details");
		System.out.println(((CustomerFacade) facade).getCustomerDetails());
		
	}

}
