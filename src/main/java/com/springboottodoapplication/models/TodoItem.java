package com.springboottodoapplication.models;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import javax.persistence.Column;

@Entity
@Table(name = "todo_item")
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Integer customerId;

    @NotBlank(message = "Description is required")
    private String description;

    private boolean complete;

    private Instant createdDate;

    private Instant modifiedDate;
    
    @NotBlank(message = "Description is required")
    @Column(name="task_name")
    private String taskName;

    public TodoItem() {}

    public TodoItem(String description, int customerId, String taskName) {
        this.description = description;
        this.customerId = customerId;
        this.complete = false;
        this.createdDate = Instant.now();
        this.modifiedDate = Instant.now();
        this.taskName = taskName;
    }
    
    @Override
    public String toString() {
        return String.format("TodoItem{id=%d, customerId=%d, taskName='%s', description='%s', complete='%s', createdDate='%s', modifiedDate='%s'}",
        id, customerId, taskName, description, complete, createdDate, modifiedDate);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Instant getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Instant modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
   
    
}
