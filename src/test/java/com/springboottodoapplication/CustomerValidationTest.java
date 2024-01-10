package com.springboottodoapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.springboottodoapplication.models.Customer;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
@Import(LocalValidatorFactoryBean.class)
public class CustomerValidationTest {

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Mock
	private Customer customer;

	@BeforeEach
	public void setUp() {
		customer = new Customer();
		customer.setCustomerId(1);
		customer.setTc("12345678901");
		customer.setBirth("1990-01-01");
		customer.setEmail("test@example.com");
		customer.setUsername("John");
		customer.setPassword("John");
		customer.setFirstname("John");
		customer.setLastname("Doe");
		customer.setPhoneNumber("5005005050");
		customer.setGender("M");
		customer.setStatus(1);
	}

	public boolean isUnderage() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate birthDate = LocalDate.parse(customer.getBirth(), formatter);
		LocalDate today = LocalDate.now();
		LocalDate maxBirthDate = today.minusYears(18);
		return birthDate.isBefore(maxBirthDate);
	}

	@Test
	public void testBirthInputMaxDate() {
		// Doğru tarih formatında max değeri elde ettik mi?
		assertTrue(customer.getBirth().matches("\\d{4}-\\d{2}-\\d{2}"));

		// 18 yaşın üstünde mü?
		assertTrue(isUnderage());
	}

	@Test
	public void whenTcNumberIsNull_ValidationFails() {
		customer.setTc(null);

		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		// @NotEmpty kuralına uymayan bir değer girildiğinden dolayı bir doğrulama
		// hatası oluşur. Ancak, diğer iki kural olan @Digits ve @Size için bir değer
		// girilmediği için bu kuralara uygunluğu kontrol edilmez.

		assertEquals("tc", violations.iterator().next().getPropertyPath().toString());
	}

	@Test
	public void whenTcNumberIsNotNumeric_ValidationFails() {
		customer.setTc("123abc");

		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		assertFalse(violations.isEmpty());
		assertEquals(2, violations.size()); // 2 tane ihlal olduğu anlamına gelmektedir
		assertEquals("tc", violations.iterator().next().getPropertyPath().toString());
	}

	@Test
	public void whenTcNumberHasInvalidLength_ValidationFails() {
		customer.setTc("123456789");

		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("tc", violations.iterator().next().getPropertyPath().toString());
	}

	@Test
	public void whenBirthDateIsBlank_ValidationFails() {
		customer.setBirth("");

		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		assertFalse(violations.isEmpty());
		assertEquals(2, violations.size());
		assertEquals("birth", violations.iterator().next().getPropertyPath().toString());
	}

	@Test
	public void whenBirthDateHasInvalidFormat_ValidationFails() {
		customer.setBirth("1990/01/01");

		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("birth", violations.iterator().next().getPropertyPath().toString());
	}

	@Test
	public void whenEmailIsInvalidFormat_ValidationFails() {
		customer.setEmail("test");

		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		assertEquals(1, violations.size());
	}

	@Test
	public void whenFirstnameIsBlank_ValidationFails() {
		customer.setFirstname("");

		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		assertFalse(violations.isEmpty());
		assertEquals(3, violations.size());
		assertEquals("firstname", violations.iterator().next().getPropertyPath().toString());
	}

	@Test
	public void whenFirstnameHasInvalidCharacters_ValidationFails() {
		customer.setFirstname("John123");

		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("firstname", violations.iterator().next().getPropertyPath().toString());
	}

	@Test
	public void whenFirstnameIsTooShort_ValidationFails() {
		customer.setFirstname("A");

		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("firstname", violations.iterator().next().getPropertyPath().toString());
	}

	@Test
	public void whenLastnameIsBlank_ValidationFails() {
		customer.setLastname("");

		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		assertFalse(violations.isEmpty());
		assertEquals(3, violations.size());
		assertEquals("lastname", violations.iterator().next().getPropertyPath().toString());
	}

	@Test
	public void whenLastnameHasInvalidCharacters_ValidationFails() {
		customer.setLastname("Doe123");

		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("lastname", violations.iterator().next().getPropertyPath().toString());
	}

	@Test
	public void whenLastnameIsTooShort_ValidationFails() {
		customer.setLastname("A");

		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("lastname", violations.iterator().next().getPropertyPath().toString());
	}

	@Test
	public void whenValidCustomer_ValidationSucceeds() {
		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		assertTrue(violations.isEmpty());
	}
}
