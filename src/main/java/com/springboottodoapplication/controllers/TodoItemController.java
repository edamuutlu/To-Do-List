package com.springboottodoapplication.controllers;

import java.time.Instant;
import java.util.Optional;

import javax.validation.Valid;

import com.springboottodoapplication.models.Customer;
import com.springboottodoapplication.models.TodoItem;
import com.springboottodoapplication.repositories.ICustomerRepository;
import com.springboottodoapplication.repositories.ITodoItemRepository;
import com.springboottodoapplication.services.TodoItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoItemController {

    @Autowired
    private ITodoItemRepository todoItemRepository;
    
    @Autowired
    private TodoItemService todoItemService;
    
    @Autowired
    private ICustomerRepository customerRepository;

    @PostMapping("/todo")
    public String createTodoItem(@RequestParam String taskName, @RequestParam String description, 
                                 @RequestParam String username, Model model) {
        Customer customer = customerRepository.findByUsername(username);
        
        TodoItem todoItem = new TodoItem();
        todoItem.setTaskName(taskName);
        todoItem.setDescription(description);
        todoItem.setCreatedDate(Instant.now());
        todoItem.setModifiedDate(Instant.now());
        todoItem.setCustomerId(customer.getCustomerId());
        
        todoItemRepository.save(todoItem);
        return "redirect:/userPage/" + username;
    }


    @PostMapping("/todo/{id}")
    public String updateTodoItem(@PathVariable("id") long id, @Valid TodoItem todoItem, BindingResult result, Model model) {
    	
    	Optional<TodoItem> itemOptional = todoItemRepository.findById(id);
    	if (itemOptional.isPresent()) {
    	    TodoItem item = itemOptional.get();
    	    Customer customer = customerRepository.getCustomerById(item.getCustomerId());
    	    if (result.hasErrors()) {
    	    	return "redirect:/userPage/" + customer.getUsername();
            }
    	    todoItem.setId(id);
            todoItem.setCustomerId(item.getCustomerId());
            todoItem.setTaskName(todoItem.getTaskName());
            todoItem.setDescription(todoItem.getDescription());
            todoItem.setCreatedDate(item.getCreatedDate());
            todoItem.setModifiedDate(Instant.now());
            todoItemRepository.save(todoItem);
            return "redirect:/userPage/" + customer.getUsername();
    	    
    	} else {
    	    
    	}    	
    	
        return "/login"; //?
    }

}
