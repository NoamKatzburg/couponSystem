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
	private static final String ADD_COUP = "INSERT INTO `coupon_system`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);\r\n";
	private static final String UPDATE_COUP = "UPDATE `coupon_system`.`coupons` SET `category_id` = ?, `title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?);\r\n";
	private static final String DELETE_COUP = "DELETE FROM `coupon_system`.`coupons` WHERE (`id` = ?);";
	private static final String GET_ALL_COUP = "SELECT * FROM `coupon_system`.`coupons`;";
	private static final String GET_ALL_COUP_BY_COM_ID = "SELECT * FROM `coupon_system`.`coupons` WHERE `company_id`= ?;";
	private static final String GET_ONE_COUP = "SELECT * FROM `coupon_system`.`coupons` WHERE (`id`= ?);";
	private static final String ADD_COUP_PURCHASE = "INSERT INTO `coupon_system`.`customers_vs_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?);";
	private static final String DOES_COUP_PURCHASE_EXIST = "SELECT * FROM coupon_system.customers_vs_coupons WHERE `customer_id` = ? AND `coupon_id` = ?;";
	private static final String GET_COUP_BY_CUST_ID = "SELECT * FROM coupon_system.customers_vs_coupons WHERE `customer_id` = ?;";
	private static final String DELETE_COUP_PURCHASE = "DELETE FROM `coupon_system`.`customers_vs_coupons` WHERE (`customer_id` = ?) and (`coupon_id` = ?);\r\n";
	private static final String DELETE_COUP_PURCHASE_BY_ID = "DELETE FROM `coupon_system`.`customers_vs_coupons` WHERE (`coupon_id` = ?);\r\n";
	private static final String DELETE_COUP_PURCHASES_BY_CUST_ID = "SELECT * FROM coupon_system.customers_vs_coupons WHERE `customer_id` = ?;";
	private static final String COES_COUP_EXIST = "SELECT * FROM coupon_system.coupons where `id`=?;";

	public void addCoupon(Coupon coupon) {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = ADD_COUP;
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
			connection = null;
		}
	}

	public void updateCoupon(Coupon coupon, int id) {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = UPDATE_COUP;
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
			connection = null;
		}

	}

	public void deleteCoupon(int couponID) {
		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = DELETE_COUP;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);

			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public List<Coupon> getAllCoupons() {
		List<Coupon> coupons = new ArrayList<Coupon>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = GET_ALL_COUP;
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
			connection = null;
		}
		return coupons;
	}

	public List<Coupon> getAllCouponsByCompanyId(int companyId) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = GET_ALL_COUP_BY_COM_ID;
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
			connection = null;
		}
		return coupons;
	}

	public Coupon getOneCoupon(int couponID) {
		Coupon c1 = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = GET_ONE_COUP;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, couponID);
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

				c1 = new Coupon(id, companyID, category, title, description, startDate, endDate, amount, price, image);
			}

		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return c1;
	}

	public void addCouponPurchase(int customerID, int couponID) throws OutOfStockException {

		Coupon c1 = getOneCoupon(couponID);
		if(c1.getAmount()>0) {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = ADD_COUP_PURCHASE;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.setInt(2, couponID);

			statement.executeUpdate();

			sql = "UPDATE `coupon_system`.`coupons` SET `amount` = ? WHERE (`id` = ?);";
			statement = connection.prepareStatement(sql);

			statement.setInt(1, c1.getAmount() - 1);
			statement.setInt(2, couponID);

			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		}else {
			throw new OutOfStockException("This coupon is out of stock");
		}

	}

	public boolean doesCouponPurchaseExist(int customerId, int couponId) {

		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = DOES_COUP_PURCHASE_EXIST;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerId);
			statement.setInt(2, couponId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				return true;
			}

		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return false;

	}

	public List<Coupon> getCouponsByCustomerId(int customerId) {
		List<Coupon> customerCoupons = new ArrayList<Coupon>();
		Coupon coupon = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = GET_COUP_BY_CUST_ID;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, customerId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
			//	int customerID = resultSet.getInt(1);
				int couponId = resultSet.getInt(2);

				coupon  = getOneCoupon(couponId);
				customerCoupons.add(coupon);
			}

		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
		return customerCoupons;
	}

	public void deleteCouponPurchase(int customerID, int couponID) throws NoSuchCouponException {
		Coupon c1 = null;
		if (doesCouponExist(couponID) == true) {

			c1 = getOneCoupon(couponID);
			try {
				connection = ConnectionPool.getInstance().getConnection();

				String sql = DELETE_COUP_PURCHASE;
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, customerID);
				statement.setInt(2, couponID);

				statement.executeUpdate();

				sql = "UPDATE `coupon_system`.`coupons` SET `amount` = ? WHERE (`id` = ?);";
				statement = connection.prepareStatement(sql);
				statement.setInt(1, c1.getAmount() + 1);
				statement.setInt(2, couponID);

				statement.executeUpdate();
				c1.setAmount(c1.getAmount() + 1);
			} catch (Exception e) {
				e.getMessage();
			} finally {
				ConnectionPool.getInstance().returnConnection(connection);
				connection = null;
			}
		} else {
			throw new NoSuchCouponException("This coupon does not exist");
		}

	}

	public void deleteCouponPurchaseById(int couponID) throws NoSuchCouponException {
		if (doesCouponExist(couponID) == true) {
			try {
				connection = ConnectionPool.getInstance().getConnection();

				String sql = DELETE_COUP_PURCHASE_BY_ID;
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, couponID);

				statement.executeUpdate();
			} catch (Exception e) {
				e.getMessage();
			} finally {
				ConnectionPool.getInstance().returnConnection(connection);
				connection = null;
			}
		} else {
			throw new NoSuchCouponException("This coupon does not exist");
		}

	}

	public void deleteAllCouponPurchasesByCustomerId(int custId) {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = DELETE_COUP_PURCHASES_BY_CUST_ID;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, custId);

			statement.executeUpdate();
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}
	}

	public boolean doesCouponExist(int couponID) {
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sql = COES_COUP_EXIST;

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
			connection = null;
		}

		return false;

	}

}
