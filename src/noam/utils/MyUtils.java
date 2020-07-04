package noam.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import noam.beans.Company;
import noam.beans.Customer;

public class MyUtils {

//	public static java.sql.Date convertUtilToSql(java.util.Date uDate) {
//		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
//		return sDate;
//	}

	public static java.util.Date convertSqlToUtil(java.sql.Date sDate) {
		java.util.Date uDate = new java.util.Date(sDate.getTime());
		return uDate;
	}

	public static java.sql.Date convertUtilToSql(java.util.Date date) {
		return new java.sql.Date(date.getYear() - 1900, date.getMonth() - 1, date.getDate() + 1);
	}

	public static java.util.Date calcDate(int DD, int MM, int YYYY) {

		ZoneId defaultZoneId = ZoneId.systemDefault();

		// creating the instance of LocalDate using the day, month, year info
		LocalDate localDate = LocalDate.of(YYYY, MM, DD);

		// local date + atStartOfDay() + default time zone + toInstant() = Date
		return java.util.Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
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
}
