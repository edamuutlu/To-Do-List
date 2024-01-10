package com.springboottodoapplication.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboottodoapplication.models.TodoItem;
import com.springboottodoapplication.repositories.ITodoItemRepository;

@Service
@Transactional
public class TodoItemService {
    
    @Autowired
    private ITodoItemRepository todoItemRepository;
    
    public Optional<TodoItem> findById(long id) {
        return todoItemRepository.findById(id);
    }

    public void deleteByCustomerId(int customerId) {
        todoItemRepository.deleteByCustomerId(customerId);
    }
}

