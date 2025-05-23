package com.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.model.Todo;
import com.todo.repository.TodoRepository;
@Service
public class TodoService {

	@Autowired
	 private TodoRepository repository;

	    public List<Todo> getAllTodos() {
	        return repository.findAll();
	    }

	    public Todo addTodo(Todo todo) {
	        return repository.save(todo);
	    }

	    public void deleteTodo(Long id) {
	        repository.deleteById(id);
	    }
}
