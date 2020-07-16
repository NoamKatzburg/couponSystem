package noam.beans;

public class CustomerVsCoupon {

	private int customerId;
	private int couponId;

	public CustomerVsCoupon(int customerId, int couponId) {
		this.customerId = customerId;
		this.couponId = couponId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	@Override
	public String toString() {
		return "CustomerVsCoupon [customerId=" + customerId + ", couponId=" + couponId + "]";
	}

}
