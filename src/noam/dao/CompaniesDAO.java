package noam.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import noam.beans.Company;

public interface CompaniesDAO {

	boolean isCompanyExist(String email, String password) throws SQLException;

	void addCompany(Company company) throws InterruptedException , SQLException ;

	void updateCompany(Company company) throws SQLException;

	void deleteCompany(int companyID) throws SQLException;

	ArrayList<Company> getAllCompanies()  throws SQLException;

	Company getOneCompany(int companyID);

}
