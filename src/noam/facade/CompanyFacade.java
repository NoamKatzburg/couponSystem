package noam.facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import noam.beans.Category;
import noam.beans.Company;
import noam.beans.Coupon;
import noam.dbdao.CompaniesDBDAO;
import noam.dbdao.CouponsDBDAO;
import noam.exceptions.NoSuchCouponException;

public class CompanyFacade extends ClientFacade {

	private int companyId;

	public boolean login(String email, String password) throws SQLException {
		companiesDAO = new CompaniesDBDAO();
		if (companiesDAO.isCompanyExist(email, password)) {
			return true;
		}
		return false;
	}

	public void addCoupon(Coupon coupon) {
		couponsDAO = new CouponsDBDAO();
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
		couponsDAO = new CouponsDBDAO();
		couponsDAO.updateCoupon(coupon, id);
	}

	public void deleteCoupon(int couponId) throws NoSuchCouponException {
		couponsDAO = new CouponsDBDAO();
		couponsDAO.deleteCouponPurchaseById(couponId);
		couponsDAO.deleteCoupon(couponId);
	}

	public List<Coupon> getCompanyCouponsById(int companyId) {
		couponsDAO = new CouponsDBDAO();
		List<Coupon> coupons = couponsDAO.getAllCoupons();
		Iterator<Coupon> iter = coupons.iterator();

		while (iter.hasNext()) {
			Coupon coup = iter.next();

		    if (coup.getCompanyID() != companyId)
		        iter.remove();
		}
//		for (Coupon coupon : coupons) {
//			if (coupon.getCompanyID() != companyId) {
//				coupons.remove(coupon);
//			}
//		}
		return coupons;
	}

	public List<Coupon> getCompanyCouponsByCategory(Category category, int companyId) {
		couponsDAO = new CouponsDBDAO();
		List<Coupon> coupons = couponsDAO.getAllCoupons();
		Iterator<Coupon> iter = coupons.iterator();

		while (iter.hasNext()) {
			Coupon coup = iter.next();

		    if (coup.getCompanyID() != companyId && coup.getCategory() != category)
		        iter.remove();
		}
//		for (Coupon coupon : coupons) {
//			if (coupon.getCompanyID() != companyId && coupon.getCategory() != category) {
//				coupons.remove(coupon);
//			}
//		}
		return coupons;

	}

	public List<Coupon> getCompanyCouponsByPrice(double maxPrice, int companyId) {
		couponsDAO = new CouponsDBDAO();
		List<Coupon> coupons = couponsDAO.getAllCoupons();
		
		Iterator<Coupon> iter = coupons.iterator();

		while (iter.hasNext()) {
			Coupon coup = iter.next();

		    if (coup.getCompanyID() != companyId && coup.getPrice() <= maxPrice)
		        iter.remove();
		}
//		for (Coupon coupon : coupons) {
//			if (coupon.getCompanyID() != companyId && coupon.getPrice() <= maxPrice) {
//				coupons.remove(coupon);
//			}
//		}
		return coupons;

	}

	public Company getCompanyDetails(int companyId) {
		companiesDAO = new CompaniesDBDAO();
		return companiesDAO.getOneCompany(companyId);

	}

}
