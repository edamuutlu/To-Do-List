package com.wazooinc.springboottodoapplication.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

	@Id
	@Column(name="customer_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;

	@Column(name="tc")
	@NotEmpty(message = "Enter your tc number.")
	@Digits(integer=11, fraction=0, message= "Only numeric input is allowed.")
	@Size(min = 11, max = 11, message = "TC number must be 11 digits.")
	private String tc;

	@Column(name="birth")
	@NotBlank(message = "Enter your birth.")
	//@Past(message = "Enter a past date")
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Enter a valid date in the format yyyy-mm-dd")
	private String birth;

	@Column(name="email")
	@NotBlank(message = "Enter your email.")
	@Email(message = "Enter a valid email address.")
	private String email;

	@Column(name="firstname")
	@NotBlank(message = "Enter your first name.")
	@Pattern(regexp = "[a-zA-Z\\s]+", message = "Only alphabetic characters and spaces are allowed")
	@Length(min = 2, message = "Name must be at least 2 characters")
	private String firstname;
	
	@Column(name="lastname")
	@NotBlank(message = "Enter your last name")
	@Pattern(regexp = "[a-zA-Z\\s]+", message = "Only alphabetic characters and spaces are allowed")
	@Length(min = 2, message = "Name must be at least 2 characters")
	private String lastname;
	
	@Column(name="username")
	@NotBlank(message = "Enter your username.")
	private String username;
	
	@Column(name="password")
	@NotBlank(message = "Enter your password.")
	private String password;
	
	@Column(name="phone_number")
	@NotBlank(message = "Enter your phoneNumber.")
	private String phoneNumber;
	
	@Column(name="gender")
	@NotBlank(message = "Enter your gender.")
	private String gender;
	
	@Column(name="status")
	private int status;

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(int customerId, String tc, String birth, String email, String firstname, String lastname,
			String username, String password, String phoneNumber, String gender, int status) {
		super();
		this.customerId = customerId;
		this.tc = tc;
		this.birth = birth;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.status = status;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getTc() {
		return tc;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", tc=" + tc + ", birth=" + birth + ", email=" + email
				+ ", firstname=" + firstname + ", lastname=" + lastname + ", username=" + username + ", password="
				+ password + ", phoneNumber=" + phoneNumber + ", gender=" + gender + ", status=" + status + "]";
	}

}
