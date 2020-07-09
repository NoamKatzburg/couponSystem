package noam.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import noam.beans.Category;
import noam.beans.Coupon;
import noam.dao.CouponsDAO;
import noam.db.ConnectionPool;
import noam.exceptions.NoSuchCouponException;
import noam.exceptions.OutOfStockException;
import noam.utils.MyUtils;

public class CouponsDBDAO implements CouponsDAO {

	private Connection connection;
	private CategoriesDBDAO categoriesDBDAO = new CategoriesDBDAO();

	public void addCoupon(Coupon coupon) {
		connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "INSERT INTO `coupon_system`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);\r\n";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, coupon.getCompanyID());
			statement.setInt(2, coupon.getCategory().ordinal() + 1);
			statement.setString(3, coupon.getTitle());
			statement.setString(4, coupon.getDescription());
			statement.setDate(5, MyUtils.convertUtilToSql(coupon.getStartDate()));
			statement.setDate(6, MyUtils.convertUtilToSql(coupon.getEndDate()));
			statement.setInt(7, coupon.getAmount());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());
			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	public void updateCoupon(Coupon coupon, int id) {
		connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "UPDATE `coupon_system`.`coupons` SET `category_id` = ?, `title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?);\r\n";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, coupon.getCategory().ordinal() + 1);
			statement.setString(2, coupon.getTitle());
			statement.setString(3, coupon.getDescription());
			statement.setDate(4, MyUtils.convertUtilToSql(coupon.getStartDate()));
			statement.setDate(5, MyUtils.convertUtilToSql(coupon.getEndDate()));
			statement.setInt(6, coupon.getAmount());
			statement.setDouble(7, coupon.getPrice());
			statement.setString(8, coupon.getImage());
			statement.setInt(9, id);
			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	public void deleteCoupon(int couponID) {
		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "DELETE FROM `coupon_system`.`coupons` WHERE (`id` = ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);

			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	public List<Coupon> getAllCoupons() {
		List<Coupon> coupons = new ArrayList<Coupon>();

		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM `coupon_system`.`coupons`;";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				int companyID = resultSet.getInt(2);
				Category category = categoriesDBDAO.convertIntToCategory(resultSet.getInt(3));
				String title = resultSet.getString(4);
				String description = resultSet.getString(5);
				Date startDate = resultSet.getDate(6);
				Date endDate = resultSet.getDate(7);
				int amount = resultSet.getInt(8);
				double price = resultSet.getDouble(9);
				String image = resultSet.getString(10);
				coupons.add(new Coupon(id, companyID, category, title, description, startDate, endDate, amount, price,
						image));
			}

		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return  coupons;
	}

	public List<Coupon> getAllCouponsByCompanyId(int companyId) {
		List<Coupon> coupons = new ArrayList<Coupon>();

		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM `coupon_system`.`coupons` WHERE `company_id`= ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				int companyID = resultSet.getInt(2);
				Category category = categoriesDBDAO.convertIntToCategory(resultSet.getInt(3));
				String title = resultSet.getString(4);
				String description = resultSet.getString(5);
				Date startDate = resultSet.getDate(6);
				Date endDate = resultSet.getDate(7);
				int amount = resultSet.getInt(8);
				double price = resultSet.getDouble(9);
				String image = resultSet.getString(10);
				coupons.add(new Coupon(id, companyID, category, title, description, startDate, endDate, amount, price,
						image));
			}

		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return  coupons;
	}

	public Coupon getOneCoupon(int couponID) {
		Coupon c1 = null;
		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM `coupon_system`.`coupons` WHERE (`id`= ?);";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				int companyID = resultSet.getInt(2);
				Category category = categoriesDBDAO.convertIntToCategory(resultSet.getInt(3)); // what about case
																								// sensitivity?
				String title = resultSet.getString(4);
				String description = resultSet.getString(5);
				Date startDate = resultSet.getDate(6);
				Date endDate = resultSet.getDate(7);
				int amount = resultSet.getInt(8);
				double price = resultSet.getDouble(9);
				String image = resultSet.getString(10);

				c1 = new Coupon(id, companyID, category, title, description, startDate, endDate, amount, price, image);
			}

		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return c1;
	}

	public void addCouponPurchase(int customerID, int couponID) throws OutOfStockException {
		connection = null;
		Coupon c1 = getOneCoupon(couponID);

		if (c1.getAmount() > 0) {
			try {
				connection = ConnectionPool.getInstance().getConnection();

				String sql = "INSERT INTO `coupon_system`.`customers_vs_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?);";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, customerID);
				statement.setInt(2, couponID);

				statement.executeUpdate();

//				sql = "UPDATE `coupon_system`.`coupons` SET `amount` = ? WHERE (`id` = ?);";
//				statement = connection.prepareStatement(sql);
//
//				statement.setInt(1, c1.getAmount() - 1);
//				statement.setInt(2, couponID);

				statement.executeUpdate();
			} catch (Exception e) {
				e.getMessage();
			} finally {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		} else {
			throw new OutOfStockException("This coupon is out of stock");
		}

	}
	
	

	public void deleteCouponPurchase(int customerID, int couponID) throws NoSuchCouponException {
		if (doesCouponExist(couponID) == true) {
			connection = null;

			try {
				connection = ConnectionPool.getInstance().getConnection();

				String sql = "DELETE FROM `coupon_system`.`customers_vs_coupons` WHERE (`customer_id` = ?) and (`coupon_id` = ?);\r\n";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, customerID);
				statement.setInt(2, couponID);

				statement.executeUpdate();

//				sql = "UPDATE `coupon_system`.`coupons` SET `amount` = ? WHERE (`id` = ?);";
//				statement = connection.prepareStatement(sql);
//				statement.setInt(1, c1.getAmount() + 1);
//				statement.setInt(2, couponID);
//
//				statement.executeUpdate();
//				c1.setAmount(c1.getAmount() + 1);
			} catch (Exception e) {
				e.getMessage();
			} finally {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		} else {
			throw new NoSuchCouponException("This coupon does not exist");
		}

	}

	public void deleteCouponPurchaseById(int couponID) throws NoSuchCouponException {
		if (doesCouponExist(couponID) == true) {
			connection = null;

			try {
				connection = ConnectionPool.getInstance().getConnection();

				String sql = "DELETE FROM `coupon_system`.`customers_vs_coupons` WHERE (`coupon_id` = ?);\r\n";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, couponID);

				statement.executeUpdate();

//				sql = "UPDATE `coupon_system`.`coupons` SET `amount` = ? WHERE (`id` = ?);";
//				statement = connection.prepareStatement(sql);
//				statement.setInt(1, c1.getAmount() + 1);
//				statement.setInt(2, couponID);
//
//				statement.executeUpdate();
//				c1.setAmount(c1.getAmount() + 1);
			} catch (Exception e) {
				e.getMessage();
			} finally {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		} else {
			throw new NoSuchCouponException("This coupon does not exist");
		}

	}
	
	public void deleteAllCouponPurchasesByCustomerId(int custId) {

				connection = null;

			try {
				connection = ConnectionPool.getInstance().getConnection();

				String sql = "SELECT * FROM coupon_system.customers_vs_coupons WHERE `customer_id` = ?;";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, custId);

				statement.executeUpdate();

//				sql = "UPDATE `coupon_system`.`coupons` SET `amount` = ? WHERE (`id` = ?);";
//				statement = connection.prepareStatement(sql);
//				statement.setInt(1, c1.getAmount() + 1);
//				statement.setInt(2, couponID);
//
//				statement.executeUpdate();
//				c1.setAmount(c1.getAmount() + 1);
			} catch (Exception e) {
				e.getMessage();
			} finally {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		} 
	

	public boolean doesCouponExist(int couponID) {
		connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT * FROM coupon_system.coupons where `id`=?;";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
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

}
