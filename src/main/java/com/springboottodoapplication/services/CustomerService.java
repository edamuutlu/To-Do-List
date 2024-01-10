package com.springboottodoapplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboottodoapplication.models.Customer;
import com.springboottodoapplication.repositories.ICustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	ICustomerRepository customerRepository;
	
	public void save(Customer customer) {
		customerRepository.save(customer);
	}

}
