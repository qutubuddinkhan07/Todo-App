import axios from "axios";
import { toast } from "react-toastify";

const API_BASE_URL =
  import.meta.env.VITE_API_URL || "http://localhost:8080/backend/api";

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

//Response interceptor for error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    const message = error.response?.data?.error || "An error occured";
    toast.error(message);
    return Promise.reject(error);
  },
);

export const getAllTasks = async () => {
  try {
    const response = await api.get("/");
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getTaskById = async (id) => {
  try {
    const response = await api.get(`/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const createTask = async (task) => {
  try {
    const response = await api.post(`/`, task);
    toast.success("Task created successfully!");
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const updateTask = async (id, task) => {
  try {
    const response = await api.put(`/${id}`, task);
    toast.success("Task updated successfully!");
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const deleteTask = async (id) => {
  try {
    const response = await api.delete(`/${id}`);
    toast.success("Task deleted successfully!");
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const toggleTaskStatus = async (id) => {
  try {
    const response = await api.put(`/${id}/toggle`);
    toast.success("Task status updated successfully!");
    return response.data;
  } catch (error) {
    throw error;
  }
};
