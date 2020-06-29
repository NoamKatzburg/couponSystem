package noam.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import noam.beans.Category;
import noam.dao.CategoriesDAO;
import noam.db.ConnectionPool;

public class CategoriesDBDAO implements CategoriesDAO {

	private Connection connection;

	public void initCategoryTable() {

		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "INSERT INTO `coupon_system`.`categories` (`name`) VALUES ('Food');";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "INSERT INTO `coupon_system`.`categories` (`name`) VALUES ('Electricty');";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "INSERT INTO `coupon_system`.`categories` (`name`) VALUES ('Restaurant');";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "INSERT INTO `coupon_system`.`categories` (`name`) VALUES ('Vacation');";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	public Category convertIntToCategory(int categoryId) {
		switch (categoryId) {
		case 1:
			return Category.Food;
		case 2:
			return Category.Electricty;
		case 3:
			return Category.Restaurant;
		case 4:
			return Category.Vacation;
		default:
			break;
		}
		return null;
	}

	public int convertStringToInt(String categoryName) {
		switch (categoryName) {
		case "Food":
			return 0;
		case "Electricty":
			return 1;
		case "Restaurant":
			return 2;
		case "Vacation":
			return 3;
		default:
			break;
		}

		return 0;
	}
}
