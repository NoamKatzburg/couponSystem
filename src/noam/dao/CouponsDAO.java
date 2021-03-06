package noam.dao;

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

	void addCouponPurchase(int customerID, int couponID)  throws OutOfStockException;

	boolean doesCouponPurchaseExist(int customerId, int couponId);

	List<Coupon> getCouponsByCustomerId(int customerId);

	//void deleteCouponPurchase(int customerID, int couponID) throws NoSuchCouponException; NOT IN USE

	void deleteCouponPurchaseById(int couponID) throws NoSuchCouponException;

	void deleteAllCouponPurchasesByCustomerId(int custId);

	boolean doesCouponExist(int couponID);
}
