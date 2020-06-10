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
import noam.beans.Customer;
import noam.dao.CouponsDAO;
import noam.db.ConnectionPool;
import noam.exceptions.noSuchCouponException;
import noam.exceptions.outOfStockException;
import noam.utils.MyUtils;

public class CouponsDBDAO implements CouponsDAO {

	private Connection connection;

	public void addCoupon(Coupon coupon) {
		connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "INSERT INTO `coupon_system`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?', ?, ?, ?, ?, ?);\r\n";
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

	public void updateCoupon(Coupon coupon) {
		connection = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "UPDATE `coupon_system`.`coupons` SET `company_id` = ?, `category_id` = ?, `title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?);\r\n";
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
			statement.setInt(10, coupon.getId());
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

	public ArrayList<Coupon> getAllCoupons() {
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
				Category category = Category.valueOf(resultSet.getString(3)); // what about case sensitivity?
				String title = resultSet.getString(4);
				String description = resultSet.getString(5);
				Date startDate = MyUtils.convertSqlToUtil(resultSet.getDate(6));
				Date endDate = MyUtils.convertSqlToUtil(resultSet.getDate(7));
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
		return (ArrayList<Coupon>) coupons;

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
				Category category = Category.valueOf(resultSet.getString(3)); // what about case sensitivity?
				String title = resultSet.getString(4);
				String description = resultSet.getString(5);
				Date startDate = MyUtils.convertSqlToUtil(resultSet.getDate(6));
				Date endDate = MyUtils.convertSqlToUtil(resultSet.getDate(7));
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

	public void addCouponPurchase(int customerID, int couponID) throws outOfStockException {
		connection = null;
		if (getOneCoupon(couponID).getAmount() > 0) {
			try {
				connection = ConnectionPool.getInstance().getConnection();

				String sql = "INSERT INTO `coupon_system`.`customers_vs_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?); UPDATE `coupon_system`.`coupons` SET `amount` = ? WHERE (`id` = ?);";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, customerID);
				statement.setInt(2, couponID);
				statement.setInt(3, getOneCoupon(couponID).getAmount() - 1);
				statement.setInt(4, couponID);

				statement.executeUpdate();
			} catch (Exception e) {
				e.getMessage();
			} finally {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		} else {
			throw new outOfStockException("This coupon is out of stock");
		}

	}

	public void deleteCouponPurchase(int customerID, int couponID) throws noSuchCouponException {
		if (doesCouponExist(couponID)==true) {
			connection = null;
			
			try {
				connection = ConnectionPool.getInstance().getConnection();

				String sql = "DELETE FROM `coupon_system`.`customers_vs_coupons` WHERE (`customer_id` = ?) and (`coupon_id` = ?); UPDATE `coupon_system`.`coupons` SET `amount` = ? WHERE (`id` = ?);";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, customerID);
				statement.setInt(2, couponID);
				statement.setInt(3, getOneCoupon(couponID).getAmount() + 1);
				statement.setInt(4, couponID);

				statement.executeUpdate();
			} catch (Exception e) {
				e.getMessage();
			} finally {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}
		else {
			throw new noSuchCouponException("This coupon does not exist");
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
