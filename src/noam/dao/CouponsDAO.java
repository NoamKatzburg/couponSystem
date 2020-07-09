package noam.dao;

import java.util.ArrayList;
import java.util.List;

import noam.beans.Coupon;
import noam.exceptions.NoSuchCouponException;
import noam.exceptions.OutOfStockException;

public interface CouponsDAO {

	void addCoupon(Coupon coupon);

	void updateCoupon(Coupon coupon, int id);

	void deleteCoupon(int couponID);

	List<Coupon> getAllCoupons();

	List<Coupon> getAllCouponsByCompanyId(int companyId);

	Coupon getOneCoupon(int couponID);

	void addCouponPurchase(int customerID, int couponID) throws OutOfStockException;
	
	//List<Coupon> getCouponPurchaseByCustomerId(int id);

	void deleteCouponPurchase(int customerID, int couponID) throws NoSuchCouponException;

	void deleteCouponPurchaseById(int couponID) throws NoSuchCouponException;
	
	void deleteAllCouponPurchasesByCustomerId(int custId);

	boolean doesCouponExist(int couponID);
}
