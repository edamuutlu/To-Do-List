package com.wazooinc.springboottodoapplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.wazooinc.springboottodoapplication.models.Customer;

public interface ICustomerRepository extends CrudRepository<Customer, Long>{
	
	@Query("FROM Customer WHERE status=?1")
	List<Customer> findByStatus(int status);

	@Query("SELECT c FROM Customer c WHERE c.username = :username")
	Customer findByUsername(String username);
	
	@Query("FROM Customer WHERE customerId=?1")
	Customer getCustomerById(int customerId);
	
	@Query("FROM Customer WHERE status=?1 and tc=?2")
	List<Customer> findByStatusAndTc(int status, String tc);
	
	@Query("FROM Customer WHERE status=?1 and email=?2") // ?1 ve ?2 parametrelerin sırasını belirtmektedir
	List<Customer> findByStatusAndEmail(int status, String email);
	
	@Query("SELECT c FROM Customer c WHERE c.status = :status AND c.username = :username")
	List<Customer> findByStatusAndUsername(int status, String username);

}
