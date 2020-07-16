package noam.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import noam.beans.Category;
import noam.beans.Company;
import noam.beans.Coupon;
import noam.beans.Customer;
import noam.beans.CustomerVsCoupon;
import noam.db.ConnectionPool;

public class MyUtils {

	private static Connection connection;

//	public static java.sql.Date convertUtilToSql(java.util.Date uDate) {
//		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
//		return sDate;
//	}

//	public static java.util.Date convertSqlToUtil(java.sql.Date sDate) {
//		java.util.Date uDate = new java.util.Date(sDate.getTime());
//		return uDate;
//	}

	@SuppressWarnings("deprecation")
	public static java.sql.Date convertUtilToSql(java.util.Date date) {
		return new java.sql.Date(date.getYear() - 1900, date.getMonth() - 1, date.getDate() + 1);
	}

	@SuppressWarnings("deprecation")
	public static java.util.Date convertSqlToUtil(java.sql.Date date) {
		return new java.util.Date(date.getYear() - 1900, date.getMonth() - 1, date.getDate() + 1);
	}

	@SuppressWarnings("deprecation")
	public static java.util.Date fixDate(java.util.Date date) {
		return new java.util.Date(date.getYear() - 1900, date.getMonth() - 1, date.getDate());
	}

	public static java.util.Date calcDate(int DD, int MM, int YYYY) {

		ZoneId defaultZoneId = ZoneId.systemDefault();

		// creating the instance of LocalDate using the day, month, year info
		LocalDate localDate = LocalDate.of(YYYY, MM, DD);

		// local date + atStartOfDay() + default time zone + toInstant() = Date
		return java.util.Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
	}

	public static Category convertIntToCategory(int categoryId) {
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

	public static int convertStringToInt(String categoryName) {
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

	public static void classSeparator(String className) {
		System.out.println();
		System.out.println();
		System.out.println("**************************************************************");
		System.out.println("**************************************************************");
		System.out.println("***************************" + className + "***************************");
		System.out.println("**************************************************************");
		System.out.println("**************************************************************");
		System.out.println();
		System.out.println();

	}

	public static void printTestLine(String s) {
		System.out.println();
		System.out.println(String.format("-------------------%s-------------------", s));
		System.out.println();
	}

	public static void separatorLine() {
		System.out.println("===========================================================================");
	}

	public static void printCompaniesTable(List<Company> com) {
		System.out.printf("%10s %10s %20s %10s %10s", "id", "name", "email", "password", "coupons");
		System.out.println();
		MyUtils.separatorLine();
		for (int i = 0; i < com.size(); i++) {
			System.out.printf("%10s %10s %20s %10s %10s", (com.get(i)).getId(), (com.get(i)).getName(),
					(com.get(i)).getEmail(), (com.get(i)).getPassword(), (com.get(i)).getCoupons());
			System.out.println();
		}

	}

	public static void printCustomersTable(List<Customer> customers) {
		System.out.printf("%10s %10s %10s %20s %10s %10s", "id", "first", "last", "email", "password", "coupons");
		System.out.println();
		MyUtils.separatorLine();
		for (int i = 0; i < customers.size(); i++) {
			System.out.printf("%10s %10s %10s %20s %10s %10s", (customers.get(i)).getId(),
					(customers.get(i)).getFirstName(), (customers.get(i)).getLastName(), (customers.get(i)).getEmail(),
					(customers.get(i)).getPassword(), (customers.get(i)).getCoupons());
			System.out.println();
		}
	}

	public static void printCouponsTable(List<Coupon> coupons) {
		System.out.printf("%10s %10s %20s %10s %20s %10s %10s %10s %10s %10s", "id", "companyID", "category", "title",
				"description", "startDate", "endDate", "amount", "price", "img");
		System.out.println();
		System.out.print("===============================================================");
		MyUtils.separatorLine();
		for (int i = 0; i < coupons.size(); i++) {
			System.out.printf("%10s %10s %20s %10s %20s %10s %10s %10s %10s %10s", (coupons.get(i)).getId(),
					(coupons.get(i)).getCompanyID(), (coupons.get(i)).getCategory(), (coupons.get(i)).getTitle(),
					(coupons.get(i)).getDescription(), (coupons.get(i)).getStartDate(), (coupons.get(i)).getEndDate(),
					(coupons.get(i)).getAmount(), (coupons.get(i)).getPrice(), (coupons.get(i)).getImage());
			System.out.println();
		}
	}

	public static void printCouponsVsCustomersTable() {
		List<CustomerVsCoupon> purchases = new ArrayList<>();
		connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sql = "SELECT * FROM coupon_system.customers_vs_coupons;";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int customerId = resultSet.getInt(1);
				int couponId = resultSet.getInt(2);

				purchases.add(new CustomerVsCoupon(customerId, couponId));
			}

		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		System.out.printf("%10s %10s", "customerId", "couponId");
		System.out.println();
		MyUtils.separatorLine();
		for (int i = 0; i < purchases.size(); i++) {
			System.out.printf("%10s %10s", (purchases.get(i)).getCustomerId(), (purchases.get(i)).getCouponId());
			System.out.println();

		}
		System.out.println();
	}
}
