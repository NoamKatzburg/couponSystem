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
	private final static String IS_CUST_EXIST = "SELECT * FROM `coupon_system`.`customers` WHERE `email`= ? AND `password`=?;";
	private final static String GET_CUST_ID_BY_EMAIL = "SELECT * FROM `coupon_system`.`customers` WHERE `email`= ?;";
	private final static String ADD_CUST = "INSERT INTO `coupon_system`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?);";
	private final static String UPDATE_CUST = "UPDATE `coupon_system`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);";
	private final static String DELETE_CUST = "DELETE FROM `coupon_system`.`customers` WHERE (`id` = ?);";
	private final static String GET_ALL_CUST = "SELECT * FROM `coupon_system`.`customers`;";
	private final static String GET_ONE_CUST ="SELECT * FROM `coupon_system`.`customers` WHERE (`id`= ?);";
	private final static String GET_ONE_CUST_BY_EMAIL ="SELECT * FROM `coupon_system`.`customers` WHERE (`email`= ?);";

	public boolean isCustomerExist(String email, String password) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = IS_CUST_EXIST;

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
			connection = null;
		}

		return false;

	}

	public int getCustomerIdByEmail(String email) {
		int id = 0;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = GET_CUST_ID_BY_EMAIL;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				id = resultSet.getInt(1);
//				String first = resultSet.getString(2);
//				String last = resultSet.getString(3);
//				String custEmail = resultSet.getString(4);
//				String password = resultSet.getString(5);
			}

		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return id;
	}

	public void addCustomer(Customer customer) {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = ADD_CUST;
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
			connection = null;
		}

	}

	public void updateCustomer(Customer customer, int id) {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = UPDATE_CUST;
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
			connection = null;
		}

	}

	public void deleteCustomer(int customerID) {

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql =DELETE_CUST ;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);

			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = GET_ALL_CUST;

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
			connection = null;
		}
		return customers;
	}

	public Customer getOneCustomer(int customerID) {
		Customer c1 = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = GET_ONE_CUST;

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
			connection = null;
		}
		return c1;

	}

	public Customer getOneCustomerByEmail(String custEmail) {
		Customer c1 = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = GET_ONE_CUST_BY_EMAIL;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, custEmail);
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
			connection = null;
		}
		return c1;

	}

}
