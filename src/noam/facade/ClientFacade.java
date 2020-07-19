package noam.facade;

import java.sql.SQLException;

import noam.dao.CompaniesDAO;
import noam.dao.CouponsDAO;
import noam.dao.CustomersDAO;
import noam.dbdao.CompaniesDBDAO;
import noam.dbdao.CouponsDBDAO;
import noam.dbdao.CustomersDBDAO;

public abstract class ClientFacade {

	protected CompaniesDAO companiesDAO;
	protected CustomersDAO customersDAO;
	protected CouponsDAO couponsDAO;
	
	public ClientFacade() {
		companiesDAO = new CompaniesDBDAO();
		customersDAO = new CustomersDBDAO();
		couponsDAO = new CouponsDBDAO();
	}


	public abstract boolean login(String email, String password)  throws SQLException;

}
