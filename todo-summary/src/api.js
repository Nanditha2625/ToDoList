import axios from 'axios';

const API_BASE_URL = "http://localhost:8080";

export const getTodos = () => axios.get(`${API_BASE_URL}/todos`);
export const addTodo = (todo) => axios.post(`${API_BASE_URL}/todos`, todo);
export const deleteTodo = (id) => axios.delete(`${API_BASE_URL}/todos/${id}`);
export const summarizeTodos = () => axios.post(`${API_BASE_URL}/summarize`);
