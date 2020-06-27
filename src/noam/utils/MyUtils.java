package noam.utils;

import noam.beans.Category;

public class MyUtils {

	public static java.sql.Date convertUtilToSql(java.util.Date uDate) {
		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		return sDate;
	}

	public static java.util.Date convertSqlToUtil(java.sql.Date sDate) {
		java.util.Date uDate = new java.util.Date(sDate.getTime());
		return uDate;
	}

	public static  Category convertIntToCategory(int categoryId) {
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
}
