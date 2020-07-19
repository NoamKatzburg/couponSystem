package noam.facade;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import noam.beans.Category;
import noam.beans.Coupon;
import noam.beans.Customer;
import noam.dbdao.CouponsDBDAO;
import noam.exceptions.OutOfStockException;
import noam.utils.MyUtils;

public class CustomerFacade extends ClientFacade {

	private int customerId;

	public CustomerFacade() {
		super();
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public boolean login(String email, String password) {
		if (customersDAO.isCustomerExist(email, password)) {
			setCustomerId(customersDAO.getCustomerIdByEmail(email));
			System.out.println("customer id is now: " + customerId);
			return true;
		}

		return false;
	}

	public void purchaseCoupon(Coupon coupon, int couponId) throws OutOfStockException {
		if (couponsDAO.doesCouponPurchaseExist(customerId, couponId)) {
			System.out.println("you have already purchased this coupon, coupon: " + couponsDAO.getOneCoupon(couponId));
			return;
		}
		if (coupon.getAmount() < 1) {
			System.out.println("This coupon is out of stock");
			return;
		}
		if (MyUtils.fixDate(coupon.getEndDate()).before(new Date())) {
			System.out.println("this coupon has expired, Coupon: " + couponsDAO.getOneCoupon(couponId));
			return;
		}
		couponsDAO.addCouponPurchase(customerId, couponId);
	}

	public List<Coupon> getCustomerCoupons() {
		return couponsDAO.getCouponsByCustomerId(customerId);
	}

	public List<Coupon> getCustomerCouponsByCategory(Category category) {
		List<Coupon> coupons = getCustomerCoupons();
		Iterator<Coupon> iter = coupons.iterator();

		while (iter.hasNext()) {
			Coupon coup = iter.next();

			if (!coup.getCategory().equals(category)) {
				iter.remove();
			}
		}
		return coupons;
	}

	public List<Coupon> getCustomerCouponsByPrice(double maxPrice) {
		List<Coupon> coupons = getCustomerCoupons();

		Iterator<Coupon> iter = coupons.iterator();

		while (iter.hasNext()) {
			Coupon coup = iter.next();

			if (coup.getPrice() >= maxPrice) {
				iter.remove();
			}
		}
		return coupons;
	}

	public Customer getCustomerDetails() {
		Customer c = customersDAO.getOneCustomer(customerId);
		c.setCoupons(getCustomerCoupons());
		return c;

	}

}
