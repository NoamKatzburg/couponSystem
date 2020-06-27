package noam.facade;

import java.util.ArrayList;

import noam.beans.Category;
import noam.beans.Company;
import noam.beans.Coupon;

public class CompanyFacade extends ClientFacade {
	
	private int companyId;

	public boolean login(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	public void addCoupon(Coupon coupon) {

	}

	public void updateCoupon(Coupon coupon) {

	}

	public void deleteCoupon(int couponId) {

	}

	public ArrayList<Coupon> getCompanyCoupons() {
		return null;

	}

	public ArrayList<Coupon> getCompanyCoupons(Category category) {
		return null;

	}

	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) {
		return null;

	}

	public Company getCompanyDetails() {
		return null;

	}

}
