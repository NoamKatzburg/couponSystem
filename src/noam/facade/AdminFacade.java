package noam.facade;

import java.sql.SQLException;
import java.util.ArrayList;

import noam.beans.Company;
import noam.beans.Customer;

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

	public void deleteCompany(Company company) {
		
	}

	public ArrayList<Company> getAllCompanies() {
		return null;

	}

	public Company getOneCompany(int companyId) {
		return null;

	}

	public void addCustomer(Customer customer) {

	}

	public void updateCustomer(Customer customer) {

	}

	public void deleteCustomer(int CustomerId) {

	}

	public ArrayList<Customer> getAllCustomers() {
		return null;

	}

	public Customer getOneCustomer(int customerId) {
		return null;

	}

}
