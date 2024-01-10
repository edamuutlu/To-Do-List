package com.springboottodoapplication.controllers;

import com.springboottodoapplication.models.Customer;
import com.springboottodoapplication.models.TodoItem;
import com.springboottodoapplication.repositories.ICustomerRepository;
import com.springboottodoapplication.repositories.ITodoItemRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TodoFormController {
    
    @Autowired
    private ITodoItemRepository todoItemRepository;
    
    @Autowired
    private ICustomerRepository customerRepository;


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        TodoItem todoItem = todoItemRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));
    
        model.addAttribute("todo", todoItem);
        return "update-todo-item";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") long id, Model model) {
        TodoItem todoItem = todoItemRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));
    
        todoItemRepository.delete(todoItem);
        
        Customer customer = customerRepository.getCustomerById(todoItem.getCustomerId());

        return "redirect:/userPage/" + customer.getUsername();
    }

    @RequestMapping(path = { "/isCompleteItem/{id}"}, method = RequestMethod.GET)
    public String isCompleteItem(@PathVariable("id") long itemId, Model model) {

        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(itemId);
             
        if (optionalTodoItem.isPresent()) {
            TodoItem todoItem = optionalTodoItem.get();
            
         // TodoItem'ın complete özelliğini kontrol ederek tersine çevirme
            if (todoItem.isComplete()) {
                todoItem.setComplete(false);
            } else {
                todoItem.setComplete(true);
            }
         
            
            todoItemRepository.save(todoItem);
            
            Customer customer = customerRepository.getCustomerById(todoItem.getCustomerId());

            return "redirect:/userPage/" + customer.getUsername();
            
        } else {
            // TodoItem bulunamadı durumu
            // Gerekli hata işlemleri veya geri dönüşler yapılabilir
        	return "/login";
        }
     
    } 
}
