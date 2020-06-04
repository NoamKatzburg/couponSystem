package noam.dao;

import java.util.ArrayList;
import noam.beans.Company;

public interface CompaniesDAO {

	boolean isCompanyExist(String email, String password);

	void addCompany(Company company);

	void updateCompany(Company company);

	void deleteCompany(int companyID);

	ArrayList<Company> getAllCompanies();

	Company getOneCompany(int companyID);

}
