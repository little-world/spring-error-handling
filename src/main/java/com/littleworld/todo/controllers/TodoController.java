package com.littleworld.todo.controllers;

import com.littleworld.todo.util.FieldErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.littleworld.todo.model.*;
import com.littleworld.todo.services.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodoController {
  
  @Autowired  private TodoService todoService;

  @PostMapping("/todo")
  ResponseEntity<?> saveWithResponseEntity(@RequestBody Todo todo) {
    if (todo.getId() == 0 && todo.getTask() != null )
      return new ResponseEntity<>(todoService.save(todo), HttpStatus.OK);
    else return new ResponseEntity<>("can not save", HttpStatus.BAD_REQUEST);
  }

  @PostMapping("/todo2")
  Todo saveWithValid(@Valid @RequestBody Todo todo)  {
     return todoService.save(todo);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    return fieldErrors.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
  }

  @PutMapping("/todo")
  public Todo updateTodo(@RequestBody Todo todo) throws Exception {
    if (todoService.findById(todo.getId()).isPresent())
      return todoService.save(todo);
    else
      throw new Exception("nothing to update");
  }

   //staat ook in TodoExceptionHandler: @ControllerAdvice
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(Exception.class)
  public String exceptionHandler(Exception ex) {
    return "exception: " + ex.getMessage();
  }

}

