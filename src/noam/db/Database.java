package noam.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
	private static Connection connection;
	private static final String url = "jdbc:mysql://localhost:3306/coupon_system?createDatabaseIfNotExist=TRUE&useTimezone=TRUE&serverTimezone=UTC";
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

	public static void initDB() throws SQLException, InterruptedException {
		dropSchema();
		createSchema();
		createTableCompanies();
		createTableCategories();
		createTableCustomers();
		createTableCoupons();
		createTableCustomerVsCoupons();
	}

	public static void dropSchema() throws SQLException, InterruptedException {

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "DROP DATABASE `coupon_system`;";

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

	public static void createSchema() throws SQLException, InterruptedException {

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "CREATE SCHEMA `coupon_system`;";

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

	public static void createTableCompanies() throws SQLException, InterruptedException {

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "CREATE TABLE `coupon_system`.`companies` (\r\n" + "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `name` VARCHAR(45) NOT NULL,\r\n" + "  `email` VARCHAR(45) NOT NULL,\r\n"
					+ "  `password` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`id`));";

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

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "CREATE TABLE `coupon_system`.`customers` (\r\n" + "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `first_name` VARCHAR(45) NOT NULL,\r\n" + "  `last_name` VARCHAR(45) NOT NULL,\r\n"
					+ "  `email` VARCHAR(45) NOT NULL,\r\n" + "  `password` VARCHAR(45) NOT NULL,\r\n"
					+ "  PRIMARY KEY (`id`));";

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

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "CREATE TABLE `coupon_system`.`categories` (\r\n" + "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `name` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`id`));";

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

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "CREATE TABLE `coupon_system`.`coupons` (\r\n" + "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
					+ "  `company_id` INT NOT NULL,\r\n" + "  `category_id` INT NOT NULL,\r\n"
					+ "  `title` VARCHAR(45) NOT NULL,\r\n" + "  `description` VARCHAR(45) NOT NULL,\r\n"
					+ "  `start_date` DATE NOT NULL,\r\n" + "  `end_date` DATE NOT NULL,\r\n"
					+ "  `amount` INT NOT NULL,\r\n" + "  `price` DOUBLE NOT NULL,\r\n"
					+ "  `image` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`id`),\r\n"
					+ "  INDEX `company_id_idx` (`company_id` ASC) VISIBLE,\r\n"
					+ "  INDEX `cantegory_id_idx` (`category_id` ASC) VISIBLE,\r\n" + "  CONSTRAINT `company_id`\r\n"
					+ "    FOREIGN KEY (`company_id`)\r\n" + "    REFERENCES `coupon_system`.`companies` (`id`)\r\n"
					+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `cantegory_id`\r\n"
					+ "    FOREIGN KEY (`category_id`)\r\n" + "    REFERENCES `coupon_system`.`categories` (`id`)\r\n"
					+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION);";

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

		try {
			// STEP 2
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "CREATE TABLE `coupon_system`.`customers_vs_coupons` (\r\n"
					+ "  `customer_id` INT NOT NULL,\r\n" + "  `coupon_id` INT NOT NULL,\r\n"
					+ "  PRIMARY KEY (`customer_id`, `coupon_id`),\r\n"
					+ "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\r\n" + "  CONSTRAINT `customer_id`\r\n"
					+ "    FOREIGN KEY (`customer_id`)\r\n" + "    REFERENCES `coupon_system`.`customers` (`id`)\r\n"
					+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `coupon_id`\r\n"
					+ "    FOREIGN KEY (`coupon_id`)\r\n" + "    REFERENCES `coupon_system`.`coupons` (`id`)\r\n"
					+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION);";

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
