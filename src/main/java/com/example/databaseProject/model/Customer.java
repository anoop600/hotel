package com.example.databaseProject.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.example.databaseProject.utility.Utility;

@Entity
@Table(name = "Customers")
public class Customer {
	@Id
	@NotNull
	@Length(min = 4, max = 4)
	private String customerId;
	
	@Length(min = 3, max = 25)
	@NotNull
	private String customerName;
	
	@Length(min = 6, max = 35)
	@NotNull
	private String email;
	
	@Length(min = 5, max = 25)
	private String password;
	
	
	@Length(min = 10, max = 10)
	@NotNull
	private String mobileNumber;
	
	@Length(min = 3, max = 25)
	@NotNull
	private String city;
	
	@Length(min = 3, max = 25)
	@NotNull
	private String state;
	
	@NotNull
	private boolean loggedInWith;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId() {
		this.customerId = "C"+Utility.generateId("customer");
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isLoggedInWith() {
		return loggedInWith;
	}

	public void setLoggedInWith(boolean loggedInWith) {
		this.loggedInWith = loggedInWith;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", email=" + email
				+ ", password=" + password + ", mobileNumber=" + mobileNumber + ", city=" + city + ", state=" + state
				+ ", loggedInWith=" + loggedInWith + "]";
	}
	
	
	
}
