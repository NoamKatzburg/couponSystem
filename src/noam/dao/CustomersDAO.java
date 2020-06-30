package noam.dao;

import java.util.ArrayList;

import noam.beans.Customer;


public interface CustomersDAO {
	
	boolean isCustomerExist(String email, String password);

	void addCustomer(Customer customer);

	void updateCustomer(Customer customer, int id);

	void deleteCustomer(int customerID);

	ArrayList<Customer> getAllCustomers();

	Customer getOneCustomer(int customerID);
	
	Customer getOneCustomerByEmail(String email);

}
