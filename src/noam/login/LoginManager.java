package noam.login;

import java.sql.SQLException;

import noam.facade.AdminFacade;
import noam.facade.ClientFacade;
import noam.facade.CompanyFacade;
import noam.facade.CustomerFacade;

public class LoginManager {

	private static LoginManager instance = null;

	private LoginManager() {
	}

	public static LoginManager getInstance() {
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

	public ClientFacade login(String email, String password, ClientType clientType) throws SQLException {
		switch (clientType) {
		case ADMINISTRATOR:
			AdminFacade aFacade = new AdminFacade();
			if (aFacade.login(email, password)) {
				return aFacade;
			}
			return null;
		case COMPANY:
			CompanyFacade comFacade = new CompanyFacade();
			if (comFacade.login(email, password)) {
				return comFacade;
			}
			return null;
		case CUSTOMER:
			CustomerFacade custFacade = new CustomerFacade();
			if (custFacade.login(email, password)) {
				return custFacade;
			}
			return null;
		default:
			return null;
		}
	}

}
