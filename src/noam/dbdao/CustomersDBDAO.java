package noam.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import noam.beans.Company;
import noam.beans.Customer;
import noam.dao.CustomersDAO;
import noam.db.ConnectionPool;

public class CustomersDBDAO implements CustomersDAO {
	
	private Connection connection;

	public boolean isCustomerExist(String email, String password) {
		connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM `coupon_system`.`customers` WHERE  email= ? AND password=?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return true;
			}
			resultSet.close();
			
		} catch (InterruptedException | SQLException e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return false;
		
	}

	public void addCustomer(Customer customer) {
		// TODO Auto-generated method stub

	}

	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub

	}

	public void deleteCustomer(int customerID) {
		// TODO Auto-generated method stub

	}

	public ArrayList<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	public Customer getOneCustomer(int customerID) {
		// TODO Auto-generated method stub
		return null;
	}

}
