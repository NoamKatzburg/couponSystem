package noam.facade;

import noam.dao.CompaniesDAO;
import noam.dao.CouponsDAO;
import noam.dao.CustomersDAO;

public abstract class ClientFacade {

	protected CompaniesDAO companiesDAO;
	
	protected CustomersDAO customersDAO;
	
	protected CouponsDAO couponsDAO;
	
	public abstract boolean login(String email, String password);
}
