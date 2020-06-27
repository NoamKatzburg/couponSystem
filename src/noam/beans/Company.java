package noam.beans;

import java.util.ArrayList;

import noam.exceptions.CannotChangeIdException;
import noam.exceptions.CannotChangeNameException;

public class Company {

	private int id;
	private String name;
	private String email;
	private String password;
	private ArrayList<Coupon> coupons;

	public Company(int id, String name, String email, String password, ArrayList<Coupon> coupons) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}
	public Company(int id, String name, String email, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	public Company(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) throws CannotChangeIdException {
		if (id == 0) {
			this.id = id;
		}else {
			throw new CannotChangeIdException("You cannot change an id once set");
		}
    }

	public String getName() {
		return name;
	}

	public void setName(String name) throws CannotChangeNameException {
		if (name == null) {
			this.name = name;
		}else {
			throw new CannotChangeNameException("You cannot change a name once set");
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", coupons="
				+ coupons + "]";
	}

}
