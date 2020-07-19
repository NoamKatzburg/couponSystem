package noam.facade;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import noam.beans.Category;
import noam.beans.Company;
import noam.beans.Coupon;
import noam.exceptions.NoSuchCouponException;

public class CompanyFacade extends ClientFacade {

	private int companyId;

	public CompanyFacade() {
		super();
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public boolean login(String email, String password) throws SQLException {
		if (companiesDAO.isCompanyExist(email, password)) {
			setCompanyId(companiesDAO.getCompanyIdByEmail(email));
			System.out.println("company id is now: " + companyId);
			return true;
		}
		return false;
	}

	public void addCoupon(Coupon coupon) {
//		couponsDAO = new CouponsDBDAO();
		List<Coupon> coupons = couponsDAO.getAllCoupons();
		for (Coupon coup : coupons) {
			if (coup.getCompanyID() == coupon.getCompanyID() && coup.getTitle() == coupon.getTitle()) {
				System.out.println("This company already has a coupon by this title. please rename");
				return;
			}
		}
		couponsDAO.addCoupon(coupon);
	}

	public void updateCoupon(Coupon coupon, int id) {
//		couponsDAO = new CouponsDBDAO();
		couponsDAO.updateCoupon(coupon, id);
	}

	public void deleteCoupon(int couponId) throws NoSuchCouponException {
		couponsDAO.deleteCouponPurchaseById(couponId);
		couponsDAO.deleteCoupon(couponId);
	}

	public List<Coupon> getCompanyCoupons() {
		List<Coupon> coupons = couponsDAO.getAllCoupons();
		Iterator<Coupon> iter = coupons.iterator();

		while (iter.hasNext()) {
			Coupon coup = iter.next();

			if (coup.getCompanyID() != companyId) {
				iter.remove();
			}
		}
		return coupons;
	}

	public List<Coupon> getCompanyCouponsByCategory(Category category) {
		List<Coupon> coupons = getCompanyCoupons();
		Iterator<Coupon> iter = coupons.iterator();

		while (iter.hasNext()) {
			Coupon coup = iter.next();

			if (!coup.getCategory().equals(category)) {
				iter.remove();
			}
		}
		return coupons;

	}

	public List<Coupon> getCompanyCouponsByPrice(double maxPrice) {
//		couponsDAO = new CouponsDBDAO();
		List<Coupon> coupons = getCompanyCoupons();

		Iterator<Coupon> iter = coupons.iterator();

		while (iter.hasNext()) {
			Coupon coup = iter.next();

			if (coup.getPrice() >= maxPrice) {
				iter.remove();
			}
		}
		return coupons;

	}

	public Company getCompanyDetails() {
		Company company = companiesDAO.getOneCompany(companyId);
		company.setCoupons(getCompanyCoupons());
		return company ;

	}

	@Override
	public String toString() {
		return "CompanyFacade [companyId=" + companyId + "]";
	}
	
	

}
