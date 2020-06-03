package noam.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private static Connection connection;
	private static final String url = "jdbc:mysql://localhost:3306/123?createDatabaseIfNotExist=TRUE&useTimezone=TRUE&serverTimezone=UTC";
	private static final String username = "root";
	private static final String password = "1234";

	public static String getUrl() {
		return url;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

	public static void createTableCompanies() throws SQLException, InterruptedException {

		// Connection connection = null;

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "CREATE TABLE `coupon_system`.`companies` ( `id` INT NOT NULL AUTO_INCREMENT, `name` VARCHAR(45) NOT NULL, `email` VARCHAR(45) NOT NULL, `password` VARCHAR(45) NOT NULL, PRIMARY KEY (`id`));";

			// STEP 3
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public static void createTableCustomers() throws SQLException, InterruptedException {

		// Connection connection = null;

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "CREATE TABLE `coupon_system`.`customers`( `id` INT NOT NULL AUTO_INCREMENT, `first_name` VARCHAR(45) NOT NULL, `last_name` VARCHAR(45) NOT NULL, `password` VARCHAR(45) NOT NULL, `email` VARCHAR(45) NOT NULL, PRIMARY KEY (`id`));";

			// STEP 3
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public static void createTableCategories() throws SQLException, InterruptedException {

		// Connection connection = null;

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "CREATE TABLE `coupon_system`.`categories` (`id` INT NOT NULL AUTO_INCREMENT, `name` VARCHAR(45) NOT NULL, PRIMARY KEY (`id`));";

			// STEP 3
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public static void createTableCoupons() throws SQLException, InterruptedException {

		// Connection connection = null;

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "CREATE TABLE `coupon_system`.`coupons` (`id` INT NOT NULL AUTO_INCREMENT, `company_id` INT NOT NULL, `category_id` INT NOT NULL, `title` VARCHAR(45) NOT NULL, `description` VARCHAR(45) NOT NULL, `start_date` DATE NOT NULL, `end_date` DATE NOT NULL, `amount` INT NOT NULL, `price` DOUBLE NOT NULL, `image` VARCHAR(45) NOT NULL, PRIMARY KEY (`id`), INDEX `company_id_idx` (`company_id` ASC) VISIBLE, INDEX `cantegory_id_idx` (`category_id` ASC) VISIBLE, CONSTRAINT `company_id` FOREIGN KEY (`company_id`) REFERENCES `coupon_system`.`companies` (`id`) ON DELETE NO ACTION  ON UPDATE NO ACTION, CONSTRAINT `cantegory_id` FOREIGN KEY (`category_id`) REFERENCES `coupon_system`.`categories` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION);";

			// STEP 3
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

	public static void createTableCustomerVsCoupons() throws SQLException, InterruptedException {

		// Connection connection = null;

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "CREATE TABLE `coupon_system`.`customers_vs_coupons` ( `customer_id` INT NOT NULL, `coupon_id` INT NOT NULL, PRIMARY KEY (`customer_id`, `coupon_id`), INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE, CONSTRAINT `customer_id` FOREIGN KEY (`customer_id`) REFERENCES `coupon_system`.`customers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION, CONSTRAINT `coupon_id` FOREIGN KEY (`coupon_id`) REFERENCES `coupon_system`.`coupons` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION)";

			// STEP 3
			PreparedStatement statment = connection.prepareStatement(sql);
			statment.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// STEP 5
			ConnectionPool.getInstance().returnConnection(connection);
			connection = null;
		}

	}

}
