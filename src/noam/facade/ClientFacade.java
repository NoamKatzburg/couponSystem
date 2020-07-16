package noam.facade;

import java.sql.SQLException;

import noam.dao.CompaniesDAO;
import noam.dao.CouponsDAO;
import noam.dao.CustomersDAO;

public abstract class ClientFacade {

	protected CompaniesDAO companiesDAO;
	
	protected CustomersDAO customersDAO;
	
	protected CouponsDAO couponsDAO;
	
	
	
//	public ClientFacade(CompaniesDAO companiesDAO, CustomersDAO customersDAO, CouponsDAO couponsDAO) {
////		this.companiesDAO = new CompaniesDBDAO();
////		this.customersDAO = new CustomersDBDAO();
////		this.couponsDAO = new CouponsDBDAO();
//	}



	public abstract boolean login(String email, String password)  throws SQLException;

}
