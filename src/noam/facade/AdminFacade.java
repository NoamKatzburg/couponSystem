package noam.facade;

import java.sql.SQLException;
import java.util.List;

import noam.beans.Company;
import noam.beans.Coupon;
import noam.beans.Customer;
import noam.dbdao.CompaniesDBDAO;
import noam.dbdao.CouponsDBDAO;
import noam.dbdao.CustomersDBDAO;
import noam.exceptions.EmailExistsException;
import noam.exceptions.NoSuchCouponException;

public class AdminFacade extends ClientFacade {
	
	

	public AdminFacade() {
		
	}

	public boolean login(String email, String password) {
		if (email == "admin@admin.com" && password == "admin") {
			return true;
		}
		return false;
	}

	public void addCompany(Company company) throws SQLException {
		companiesDAO = new CompaniesDBDAO();
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
		companiesDAO = new CompaniesDBDAO();
		companiesDAO.updateCompany(company, id);
	}

	public void deleteCompany(int companyId) throws NoSuchCouponException {
		couponsDAO = new CouponsDBDAO();
		companiesDAO = new CompaniesDBDAO();
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

	public List<Company> getAllCompanies() {
		return companiesDAO.getAllCompanies();

	}

	public Company getOneCompany(int companyId) {
		return companiesDAO.getOneCompany(companyId);

	}

	public void addCustomer(Customer customer) throws EmailExistsException {
		customersDAO = new CustomersDBDAO();
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
		return customersDAO.getAllCustomers();
	}

	public Customer getOneCustomer(int customerId) {
		return customersDAO.getOneCustomer(customerId);

	}

	public List<Coupon> getAllCoupons() {
		couponsDAO = new CouponsDBDAO();
		return couponsDAO.getAllCoupons();
	}

}
