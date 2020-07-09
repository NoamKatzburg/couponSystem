package noam.lm;

import noam.facade.AdminFacade;
import noam.facade.ClientFacade;
import noam.facade.CompanyFacade;
import noam.facade.CustomerFacade;

public class LoginManager {

	private LoginManager instance = null;

	private LoginManager() {
	}

	public LoginManager getInstance() {
		if (instance == null) {
			synchronized (LoginManager.class) {
				if (instance == null) {
					instance = new LoginManager();
				}
			}
		}
		return instance;
	}

	@Override
	public String toString() {
		return "LoginManager [instance=" + instance + "]";
	}

	public ClientFacade login(String email, String password, ClientType clientType) {
		switch (clientType) {
		case ADMINISTRATOR:
			return new AdminFacade();
		case COMPANY:
			return new CompanyFacade();
		case CUSTOMER:
			return new CustomerFacade();
		default:
			return null;
		}
	}

}
