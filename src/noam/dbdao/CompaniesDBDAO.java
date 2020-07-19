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
	private static final String IS_COM_EXIST = "SELECT * FROM `coupon_system`.`companies` WHERE  `email`= ? AND `password`= ? ;";
	private static final String GET_COM_ID_BY_EMAIL = "SELECT * FROM `coupon_system`.`companies` WHERE  `email`= ?;";
	private static final String ADD_COM = "INSERT INTO `coupon_system`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?);\r\n";
	private static final String UPDATE_COM = "UPDATE `coupon_system`.`companies` SET `email` = ?, `password` = ? WHERE (`id` = ?);\r\n";
	private static final String DELETE_COM = "DELETE FROM `coupon_system`.`companies` WHERE (`id` = ?);";
	private static final String GET_ALL_COM = "SELECT * FROM `coupon_system`.`companies`;";
	private static final String GET_ONE_COM_BY_ID = "SELECT * FROM `COUPON_SYSTEM`.`COMPANIES` WHERE `id`=?;";
	
	
	public boolean isCompanyExist(String email, String password) throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = IS_COM_EXIST ;

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

	public int getCompanyIdByEmail(String email) {
		int id = 0;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = GET_COM_ID_BY_EMAIL ;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				id = resultSet.getInt(1);
//				String name = resultSet.getString(2);
//				String comEmail = resultSet.getString(3);
//				String password = resultSet.getString(4);
			}

		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return id;
	}

	public void addCompany(Company company) {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = ADD_COM;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	public void updateCompany(Company company, int companyId) {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = UPDATE_COM;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, company.getEmail());
			statement.setString(2, company.getPassword());
			statement.setInt(3, companyId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public void deleteCompany(int companyID) {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = DELETE_COM;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);

			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public List<Company> getAllCompanies() {
		List<Company> companies = new ArrayList<Company>();
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = GET_ALL_COM;

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				String password = resultSet.getString(4);

				companies.add(new Company(id, name, email, password));
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return  companies;
	}

	public Company getOneCompany(int companyID) {
		Company c1 = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = GET_ONE_COM_BY_ID;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
			ResultSet resultSet = statement.executeQuery();

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
			connection = null;
		}
		return c1;
	}

}
