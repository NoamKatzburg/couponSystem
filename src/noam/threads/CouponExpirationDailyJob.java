package noam.threads;

import java.util.Date;
import java.util.List;

import noam.beans.Coupon;
import noam.dao.CouponsDAO;
import noam.exceptions.NoSuchCouponException;

public class CouponExpirationDailyJob implements Runnable {

	private CouponsDAO cDao;
	private boolean quit;
	private Date date;
	private long sleepTime = 24 * 60 * 60 * 1000;
	
	public CouponExpirationDailyJob() {
		
	}

	@Override
	public void run() {
		quit = false;
		while (quit == false) {
			date = new Date();
			List<Coupon> coupons = cDao.getAllCoupons();
			for (Coupon coupon : coupons) {
				if (date.after(coupon.getEndDate())) {
					// delete coupon purchase
					try {
						cDao.deleteCouponPurchaseById(coupon.getId());
					} catch (NoSuchCouponException e) {
						e.getMessage();
					}
					// delete coupon
					cDao.deleteCoupon(coupon.getId());
				}
			}

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.getMessage();
			}
		}

	}

	public void quit() {
		quit = true;
	}

}
