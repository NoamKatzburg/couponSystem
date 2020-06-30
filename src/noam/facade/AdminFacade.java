package noam.facade;

import java.sql.SQLException;
import java.util.ArrayList;

import noam.beans.Company;
import noam.beans.Coupon;
import noam.beans.Customer;
import noam.exceptions.NoSuchCouponException;

public class AdminFacade extends ClientFacade {

	public boolean login(String email, String password) {
		if (email == "admin@admin.com" && password == "admin") {
			return true;
		}
		return false;
	}

	public void addCompany(Company company) throws SQLException {
		if (companiesDAO.getOneCompany(company.getName(), company.getEmail()) == null) {
			companiesDAO.addCompany(company);
		}
	}

	public void updateCompany(Company company) {
		companiesDAO.updateCompany(company, company.getId());
	}

	public void deleteCompany(Company company) throws NoSuchCouponException {
		Coupon c1 = null;
		c1 = couponsDAO.getFirstCouponByCompanyId(company.getId()); // redundant?
		couponsDAO.deleteAllCouponPurchasesByCouponId(c1.getId());
		couponsDAO.deleteCoupon(c1.getId());
		companiesDAO.deleteCompany(company.getId());
	}

	public ArrayList<Company> getAllCompanies() {
		return companiesDAO.getAllCompanies();

	}

	public Company getOneCompany(int companyId) {
		return companiesDAO.getOneCompany(companyId);

	}

	public void addCustomer(Customer customer) throws EmailExistsException {
		if (customersDAO.getOneCustomerByEmail(customer.getEmail())==null) {
			customersDAO.addCustomer(customer);
		}else {
			throw new EmailExistsException("This email is already registered");
		}
	}

	public void updateCustomer(Customer customer) {
		customersDAO.updateCustomer(customer, customer.getId());
	}

	public void deleteCustomer(int customerId) {
		couponsDAO.deleteAllCouponPurchasesByCustomerId(customerId);
		customersDAO.deleteCustomer(customerId);
	}

	public ArrayList<Customer> getAllCustomers() {
		return customersDAO.getAllCustomers();

	}

	public Customer getOneCustomer(int customerId) {
		return customersDAO.getOneCustomer(customerId);

	}

}
