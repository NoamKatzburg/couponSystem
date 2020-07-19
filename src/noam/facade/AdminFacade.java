package noam.facade;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import noam.beans.Company;
import noam.beans.Coupon;
import noam.beans.Customer;
import noam.exceptions.CannotChangeException;
import noam.exceptions.EmailExistsException;
import noam.exceptions.NoSuchCouponException;

public class AdminFacade extends ClientFacade {

	public AdminFacade() {
		super();
	}

	public boolean login(String email, String password) {
		if (email == "admin@admin.com" && password == "admin") {
			System.out.println("ADMIN is now logged in");
			return true;
		}
		return false;
	}

	public void addCompany(Company company) throws SQLException {
		if (companiesDAO.getAllCompanies() == null) {
			companiesDAO.addCompany(company);
		} else {
			List<Company> companies = companiesDAO.getAllCompanies();
			for (Company comp : companies) {
				if (comp.getName() == company.getName()) {
					System.out.println("Name is already registered, company not added");
					return;
				}
				if (comp.getEmail() == company.getEmail()) {
					System.out.println("Email is already registered, company not added");
					return;
				}
			}
			companiesDAO.addCompany(company);
		}
	}

	public void updateCompany(Company company, int id) {
		companiesDAO.updateCompany(company, id);
	}

	public void deleteCompany(int companyId) throws NoSuchCouponException {
		List<Coupon> allCoupons = couponsDAO.getAllCoupons();
		System.out.println(allCoupons);
		for (Coupon coupon : allCoupons) {
			if (coupon.getCompanyID() == companyId) {
				couponsDAO.deleteCouponPurchaseById(coupon.getId());
			}
		}

		for (Coupon coupon : allCoupons) {
			couponsDAO.deleteCoupon(coupon.getId());
		}
		companiesDAO.deleteCompany(companyId);

	}

	public List<Company> getAllCompanies() throws CannotChangeException {
		List<Company> companies = companiesDAO.getAllCompanies();
		for (Company company : companies) {
			company.setCoupons(couponsDAO.getAllCouponsByCompanyId(company.getId()));
		}
		return companies;
	}

	public Company getOneCompany(int companyId) {
		Company c1 = companiesDAO.getOneCompany(companyId);
	//	c1.setCoupons(couponsDAO.getAllCouponsByCompanyId(companyId));
		return c1;

	}

	public void addCustomer(Customer customer) throws EmailExistsException {
//		customersDAO = new CustomersDBDAO();
		List<Customer> customers = customersDAO.getAllCustomers();
		for (Customer cust : customers) {
			if (customer.getEmail() == cust.getEmail()) {
				throw new EmailExistsException("This email is already registered");
			}
		}
		customersDAO.addCustomer(customer);
	}

	public void updateCustomer(Customer customer, int id) {
		customersDAO.updateCustomer(customer, id);
	}

	public void deleteCustomer(int customerId) {
		couponsDAO.deleteAllCouponPurchasesByCustomerId(customerId);
		customersDAO.deleteCustomer(customerId);
	}

	public List<Customer> getAllCustomers() {
		List<Customer> customers = customersDAO.getAllCustomers();
		for (Customer customer : customers) {
			customer.setCoupons(couponsDAO.getCouponsByCustomerId(customer.getId()));
		}
		return customers;
	}

	public Customer getOneCustomer(int customerId) {
		Customer c1 = customersDAO.getOneCustomer(customerId);
		c1.setCoupons(couponsDAO.getCouponsByCustomerId(c1.getId()));
		return c1;
	}

	public List<Coupon> getAllCoupons() {
//		couponsDAO = new CouponsDBDAO();
		return couponsDAO.getAllCoupons();
	}

}
