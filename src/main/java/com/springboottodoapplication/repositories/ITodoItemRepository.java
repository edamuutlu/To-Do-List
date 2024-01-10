package com.springboottodoapplication.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboottodoapplication.models.TodoItem;

@Repository
public interface ITodoItemRepository extends JpaRepository<TodoItem, Long> {  
	
	@Query("SELECT t FROM TodoItem t WHERE t.id = :id")
	Optional<TodoItem> findById(long id);
	
	@Query("FROM TodoItem WHERE customerId=?1")
	List <TodoItem> getCustomerById(int customerId);
	
	@Modifying
	@Query("DELETE FROM TodoItem t WHERE t.customerId = ?1")
	void deleteByCustomerId(int customerId);
	
	List<TodoItem> findByCustomerIdAndComplete(int customerId, boolean isComplete);

}
