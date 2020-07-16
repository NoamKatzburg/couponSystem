package noam.dao;

import java.sql.SQLException;
import java.util.List;

import noam.beans.Company;

public interface CompaniesDAO {

	boolean isCompanyExist(String email, String password) throws SQLException;//
	
	int getCompanyIdByEmail(String email);//

	void addCompany(Company company);//

	void updateCompany(Company company, int companyId);//

	void deleteCompany(int companyID);//

	List<Company> getAllCompanies();//

	Company getOneCompany(int companyID);//

}
