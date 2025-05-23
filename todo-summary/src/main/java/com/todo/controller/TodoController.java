package com.todo.controller;

import com.todo.model.Todo;
import com.todo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private OpenAIService aiService;

    @Autowired
    private SlackService slackService;

    @GetMapping("/todos")
    public List<Todo> getAll() {
        return todoService.getAllTodos();
    }

    @PostMapping("/todos")
    public Todo create(@RequestBody Todo todo) {
        return todoService.addTodo(todo);
    }

    @DeleteMapping("/todos/{id}")
    public void delete(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }

    @PostMapping("/summarize")
    public String summarize() {
        List<Todo> todos = todoService.getAllTodos();
        if (todos.isEmpty()) return "No todos available.";

        StringBuilder prompt = new StringBuilder("Summarize my pending tasks:\n");
        todos.forEach(todo -> prompt.append("- ").append(todo.getTitle()).append(": ").append(todo.getDescription()).append("\n"));

        String summary = aiService.getSummary(prompt.toString());
        boolean success = slackService.sendMessage(summary);

        return success ? "Summary sent to Slack!" : "Failed to send to Slack.";
    }
}
