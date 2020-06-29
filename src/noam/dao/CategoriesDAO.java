package noam.dao;

import noam.beans.Category;

public interface CategoriesDAO {

	void initCategoryTable();

	Category convertIntToCategory(int categoryId);

	int convertStringToInt(String categoryName);
}
