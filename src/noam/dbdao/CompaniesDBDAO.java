package noam.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import noam.beans.Company;
import noam.dao.CompaniesDAO;
import noam.db.ConnectionPool;

public class CompaniesDBDAO implements CompaniesDAO {

	private Connection connection;

	public boolean isCompanyExist(String email, String password) throws SQLException {
		connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM `coupon_system`.`companies` WHERE  `email`= ? AND `password`=?;";

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

	public void addCompany(Company company) {
		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "INSERT INTO `coupon_system`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?);\r\n";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	public void updateCompany(Company company)  {

		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "UPDATE `coupon_system`.`companies` SET `name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);\r\n";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.setInt(4, company.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	public void deleteCompany(int companyID)  {
		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "DELETE FROM `coupon_system`.`companies` WHERE (`id` = ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);

			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	public ArrayList<Company> getAllCompanies() {
		List<Company> companies = new ArrayList<Company>();

		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM `coupon_system`.`companies`;";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				String password = resultSet.getString(4);

				companies.add(new Company(id, name, email, password));
			}
			// what to do with the array of coupons?

		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return (ArrayList<Company>) companies;
	}

	public Company getOneCompany(int companyID) {
		Company c1 = null;

		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM `COUPON_SYSTEM`.`COMPANIES` WHERE `id`=?;";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				String password = resultSet.getString(4);

				c1 = new Company(id, name, email, password);
			}

		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return c1;
	}

}
