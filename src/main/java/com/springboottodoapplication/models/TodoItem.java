package com.springboottodoapplication.models;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "todo_item")
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private int customerId;

    @NotBlank(message = "Description is required")
    private String description;

    private boolean complete;

    private Instant createdDate;

    private Instant modifiedDate;

    private TodoItem() {}

    public TodoItem(String description, int customerId) {
        this.description = description;
        this.customerId = customerId;
        this.complete = false;
        this.createdDate = Instant.now();
        this.modifiedDate = Instant.now();
    }
    
    @Override
    public String toString() {
        return String.format("TodoItem{id=%d, customerId=%d, description='%s', complete='%s', createdDate='%s', modifiedDate='%s'}",
        id, customerId, description, complete, createdDate, modifiedDate);
    }
   
 
}
