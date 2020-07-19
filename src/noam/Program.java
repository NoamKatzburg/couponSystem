package noam;

import java.sql.SQLException;

import noam.exceptions.CannotChangeException;
import noam.exceptions.EmailExistsException;
import noam.exceptions.NoSuchCouponException;
import noam.exceptions.OutOfStockException;

public class Program {
public static void main(String[] args) {
	
		try {
			Test.testAll();
		} catch (ClassNotFoundException | SQLException | InterruptedException | OutOfStockException
				| NoSuchCouponException | CannotChangeException | EmailExistsException e) {

			e.getMessage();
		}
		
		System.out.println("end");
	


}
}
