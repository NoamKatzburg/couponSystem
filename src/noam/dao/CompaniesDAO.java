package noam.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import noam.beans.Company;

public interface CompaniesDAO {

	boolean isCompanyExist(String email, String password) throws SQLException;

	void addCompany(Company company);

	void updateCompany(Company company, int companyId);

	void deleteCompany(int companyID);

	ArrayList<Company> getAllCompanies();

	Company getOneCompany(int companyID);

}
