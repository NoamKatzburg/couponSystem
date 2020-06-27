package noam.facade;

import java.util.ArrayList;

import noam.beans.Category;
import noam.beans.Coupon;
import noam.beans.Customer;

public class CustomerFacade extends ClientFacade {

	private int customerId;

	public boolean login(String email, String password) {
		// TODO Auto-generated method stub
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
