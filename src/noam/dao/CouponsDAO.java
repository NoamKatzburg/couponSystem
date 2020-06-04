package noam.dao;

import java.util.ArrayList;
import noam.beans.Coupon;

public interface CouponsDAO {

	void addCoupon(Coupon coupon);

	void updateCoupon(Coupon coupon);

	void deleteCoupon(int couponID);

	ArrayList<Coupon> getAllCoupons();

	Coupon getOneCoupon(int CouponID);

	void addCouponPurchase(int customerID, int couponID);

	void deleteCouponPurchase(int customerID, int couponID);
}
