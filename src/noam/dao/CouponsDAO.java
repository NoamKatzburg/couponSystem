package noam.dao;

import java.util.ArrayList;

import noam.beans.Coupon;
import noam.exceptions.NoSuchCouponException;
import noam.exceptions.OutOfStockException;

public interface CouponsDAO {

	void addCoupon(Coupon coupon);

	void updateCoupon(Coupon coupon, int id);

	void deleteCoupon(int couponID);

	ArrayList<Coupon> getAllCoupons();

	Coupon getFirstCouponByCompanyId(int companyId);

	Coupon getOneCoupon(int couponID);

	void addCouponPurchase(int customerID, int couponID) throws OutOfStockException;

	void deleteCouponPurchase(int customerID, int couponID) throws NoSuchCouponException;

	void deleteAllCouponPurchasesByCouponId(int couponID) throws NoSuchCouponException;
	
	void deleteAllCouponPurchasesByCustomerId(int custId);

	boolean doesCouponExist(int couponID);
}
