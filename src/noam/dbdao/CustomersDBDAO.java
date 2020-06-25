package noam.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import noam.beans.Customer;
import noam.dao.CustomersDAO;
import noam.db.ConnectionPool;

public class CustomersDBDAO implements CustomersDAO {

	private Connection connection;

	public boolean isCustomerExist(String email, String password) {
		connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM `coupon_system`.`customers` WHERE  `email`= ? AND `password`=?";

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
		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "INSERT INTO `coupon_system`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?);\r\n"
					+ "";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	public void updateCustomer(Customer customer, int id) {
		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "UPDATE `coupon_system`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.setInt(5, id);
			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	public void deleteCustomer(int customerID) {
		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "DELETE FROM `coupon_system`.`customers` WHERE (`id` = ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);

			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	public ArrayList<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();

		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM `coupon_system`.`customers`;";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String firstName = resultSet.getString(2);
				String lastName = resultSet.getString(3);
				String email = resultSet.getString(4);
				String password = resultSet.getString(5);

				customers.add(new Customer(id, firstName, lastName, email, password));
			}

		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return (ArrayList<Customer>) customers;
	}

	public Customer getOneCustomer(int customerID) {
		Customer c1 = null;
		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM `coupon_system`.`customers` WHERE (`id`= ?);";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String firstName = resultSet.getString(2);
				String lastName = resultSet.getString(3);
				String email = resultSet.getString(4);
				String password = resultSet.getString(5);

				c1 = new Customer(id, firstName, lastName, email, password);
			}

		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return c1;

	}

}
