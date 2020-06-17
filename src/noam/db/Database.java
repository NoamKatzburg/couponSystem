package noam.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
		createSchema();
		createTableCompanies();
		createTableCategories();
		createTableCustomers();
		createTableCoupons();
		createTableCustomerVsCoupons();
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
					+ "  `name` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`id`));"
					+ "INSERT INTO `coupon_system`.`categories` (`name`) VALUES ('Food'); INSERT INTO `coupon_system`.`categories` (`name`) VALUES ('Electricty');INSERT INTO `coupon_system`.`categories` (`name`) VALUES ('Restaurant');INSERT INTO `coupon_system`.`categories` (`name`) VALUES ('Vacation');";

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

	public static void createData() {
		compData();
		custData();
		coupData();
	}

	public static void compData() {
		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "INSERT INTO `coupon_system`.`companies` (`name`, `email`, `password`) VALUES ('market', 'market@food.com', '1234');\r\n"
					+ "INSERT INTO `coupon_system`.`companies` (`name`, `email`, `password`) VALUES ('electric', 'elec@tricuty.com', '1234');\r\n"
					+ "INSERT INTO `coupon_system`.`companies` (`name`, `email`, `password`) VALUES ('McDonalds', 'mc@donalds.com', '1234');\r\n"
					+ "INSERT INTO `coupon_system`.`companies` (`name`, `email`, `password`) VALUES ('spa', 'spa@spa.com', '1234');\r\n"
					+ "";
			Statement statement = connection.createStatement();
			statement.executeQuery(sql);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			System.out.println("created companies data");
		}
	}

	public static void coupData() {
		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "INSERT INTO `coupon_system`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES ('1', '1', 'food', 'free food', '2000-10-10', '2000-10-10', '50', '44', 'img1');\r\n"
					+ "INSERT INTO `coupon_system`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES ('2', '2', 'electricity', 'free electricity', '2000-10-10', '2000-10-10', '43', '55', 'img2');\r\n"
					+ "INSERT INTO `coupon_system`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES ('3', '3', 'restaurant', 'free burger', '2000-10-10', '2000-10-10', '21', '43', 'img3');\r\n"
					+ "INSERT INTO `coupon_system`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES ('4', '4', 'vacation', 'free mani', '2000-10-10', '2000-10-10', '4', '22', 'img4');\r\n"
					+ "";
			Statement statement = connection.createStatement();
			statement.executeQuery(sql);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			System.out.println("created coupon data");
		}
	}
	
	public static void custData() {
		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "INSERT INTO `coupon_system`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES ('avi', 'cohen', 'avi@cohen.com', '1234');\r\n" + 
					"INSERT INTO `coupon_system`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES ('ben', 'katz', 'ben@katz.com', '1234');\r\n" + 
					"INSERT INTO `coupon_system`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES ('zohar', 'tor', 'zo@to.com', '1234');\r\n" + 
					"";
			Statement statement = connection.createStatement();
			statement.executeQuery(sql);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
			System.out.println("created coupon data");
		}
	}
	
	
}
