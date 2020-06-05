import java.sql.SQLException;

import noam.db.Database;
import noam.dbdao.CompaniesDBDAO;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
	
		Class.forName("com.mysql.cj.jdbc.Driver");	
		
		Database.initDB();
		
		CompaniesDBDAO cDbdao = new CompaniesDBDAO();
		System.out.println(cDbdao.isCompanyExist("hello@cola", "12345"));

	}

}
