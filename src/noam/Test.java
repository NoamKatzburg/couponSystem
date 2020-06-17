package noam;

import java.sql.SQLException;

import noam.db.Database;
import noam.dbdao.CompaniesDBDAO;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
	
		Class.forName("com.mysql.cj.jdbc.Driver");	
		
		Database.initDB();
		
		

	}

}
