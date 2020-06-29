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

	
}
