package com.springboottodoapplication.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboottodoapplication.models.TodoItem;

public interface ITodoItemRepository extends CrudRepository<TodoItem, Long> {  
	
	@Query("FROM TodoItem WHERE customerId=?1")
	TodoItem getCustomerById(int customerId);
	
}
