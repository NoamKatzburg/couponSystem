package noam.facade;

import java.util.ArrayList;
import java.util.List;

import noam.beans.Category;
import noam.beans.Coupon;
import noam.beans.Customer;
import noam.dbdao.CompaniesDBDAO;
import noam.dbdao.CouponsDBDAO;
import noam.dbdao.CustomersDBDAO;

public class CustomerFacade extends ClientFacade {

	private int customerId;

	public boolean login(String email, String password) {
		customersDAO = new CustomersDBDAO();
		if (customersDAO.isCustomerExist(email, password)) {
			return true;
		}
		return false;

	}

	public void purchaseCoupon(Coupon coupon) {

	}

	public ArrayList<Coupon> getCustomerCoupons() {
		return null;
	}

	public ArrayList<Coupon> getCustomerCoupons(Category category) {
		return null;

	}

	public ArrayList<Coupon> getCustomerCoupons(double maxPrice) {
		return null;

	}

	public Customer getCustomerDetails() {
		return null;

	}

}
