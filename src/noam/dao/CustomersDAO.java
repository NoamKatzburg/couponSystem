package noam.dao;

import java.util.List;

import noam.beans.Customer;

public interface CustomersDAO {

	boolean isCustomerExist(String email, String password);

	int getCustomerIdByEmail(String email);

	void addCustomer(Customer customer);

	void updateCustomer(Customer customer, int id);

	void deleteCustomer(int customerID);

	List<Customer> getAllCustomers();

	Customer getOneCustomer(int customerID);

	//Customer getOneCustomerByEmail(String email); NOT IN USE

}
