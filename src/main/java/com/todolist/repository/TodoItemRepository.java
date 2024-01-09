package com.todolist.repository;

import org.springframework.data.repository.CrudRepository;

import com.todolist.model.TodoItem;

public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {  
}
