import React, { useEffect, useState } from 'react';
import TodoForm from './components/TodoForm';
import TodoList from './components/TodoList';
import Notification from './components/Notification';
import { getTodos, addTodo, deleteTodo, summarizeTodos } from './api';

function App() {
  const [todos, setTodos] = useState([]);
  const [notification, setNotification] = useState({ message: '', type: '' });

  const loadTodos = () => {
    getTodos().then(res => setTodos(res.data));
  };

  useEffect(() => {
    loadTodos();
  }, []);

  const handleAdd = (todo) => {
    addTodo(todo).then(() => {
      loadTodos();
      showNotification("To-do added!", "success");
    });
  };

  const handleDelete = (id) => {
    deleteTodo(id).then(() => {
      loadTodos();
      showNotification("To-do deleted.", "info");
    });
  };

  const handleSummarize = () => {
    summarizeTodos().then(res => {
      showNotification("Summary sent to Slack!", "success");
    }).catch(() => {
      showNotification("Failed to send summary.", "error");
    });
  };

  const showNotification = (message, type) => {
    setNotification({ message, type });
    setTimeout(() => setNotification({ message: '', type: '' }), 3000);
  };

  return (
    <div className="App">
      <h1>Todo Summary Assistant</h1>
      <TodoForm onAdd={handleAdd} />
      <TodoList todos={todos} onDelete={handleDelete} />
      <button onClick={handleSummarize}>Summarize & Send to Slack</button>
      <Notification message={notification.message} type={notification.type} />
    </div>
  );
}

export default App;
