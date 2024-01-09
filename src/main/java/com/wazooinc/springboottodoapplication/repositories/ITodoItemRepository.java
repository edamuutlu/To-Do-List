package com.wazooinc.springboottodoapplication.repositories;

import com.wazooinc.springboottodoapplication.models.TodoItem;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ITodoItemRepository extends CrudRepository<TodoItem, Long> {  
	
	@Query("FROM TodoItem WHERE customerId=?1")
	TodoItem getCustomerById(int customerId);
	
}
