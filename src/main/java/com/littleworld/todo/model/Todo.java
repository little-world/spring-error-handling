package com.littleworld.todo.model;


import jakarta.validation.constraints.NotBlank;

public class Todo {
  Long id;

  @NotBlank
  String task;

  public Todo() {}

  public Todo(Long id, String task) {
    this.id = id;
    this.task = task;  }
  public Long getId() { return id; }
  public void setId(Long id)  { this.id = id; }
  public String getTask() { return task; }
  public void setTask(String task)  { this.task = task; }  
  @Override 
  public String toString() {
    return "[Todo: [" + "id: " + id + ", " + "task: " + task + "]"; 
  }
}

