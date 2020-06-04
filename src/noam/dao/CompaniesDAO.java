package noam.dao;

import noam.beans.Company;

public interface CompaniesDAO {
	
	boolean isCompanyExist(String email, String password);
	void addCompany(Company company);
	void updateCompany(Company company);
	
}
