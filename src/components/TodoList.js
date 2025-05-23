import React from 'react';

export default function TodoList({ todos, onDelete }) {
  return (
    <ul>
      {todos.map(todo => (
        <li key={todo.id}>
          <strong>{todo.title}</strong> — {todo.description}
          <button onClick={() => onDelete(todo.id)}>❌</button>
        </li>
      ))}
    </ul>
  );
}
