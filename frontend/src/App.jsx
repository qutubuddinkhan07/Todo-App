import React, { useEffect, useState } from "react";
import * as api from "./services/api";
import TaskForm from "./components/TaskForm";
import "./App.css";
import TaskList from "./components/TaskList";
import { ToastContainer } from "react-toastify";

const App = () => {
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [editingTask, setEditingTask] = useState(null);
  const [filter, setFilter] = useState("all");

  //Fetch all tasks on component mount
  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    setLoading(true);
    try {
      const data = await api.getAllTasks();
      setTasks(data);
    } catch (error) {
      console.error("Error fetching tasks:", error);
    } finally {
      setLoading(false);
    }
  };

  const addTask = async (taskData) => {
    try {
      const newTask = await api.createTask(taskData);
      setTasks([newTask, ...tasks]);
      return true;
    } catch (error) {
      console.error("Error adding task: ", error);
      return false;
    }
  };

  const updateTask = async (id, taskData) => {
    try {
      const updatedTask = await api.updateTask(id, taskData);
      setTasks(tasks.map((task) => (task.id === id ? updatedTask : task)));
      setEditingTask(null);
      return true;
    } catch (error) {
      console.error("Error updating task:", error);
      return false;
    }
  };

  const deleteTask = async (id) => {
    try {
      await api.deleteTask(id);
      setTasks(tasks.filter((task) => task.id !== id));
      return true;
    } catch (error) {
      console.error("Error deleting task:", error);
      return false;
    }
  };

  const toggleTaskStatus = async (id) => {
    try {
      const updatedTask = await api.toggleTaskStatus(id);
      setTasks(tasks.map((task) => (task.id === id ? updatedTask : task)));
      return true;
    } catch (error) {
      console.error("Error toggling task:", error);
      return false;
    }
  };

  const getFilteredTasks = () => {
    switch (filter) {
      case "active":
        return tasks.filter((task) => !task.completed);
      case "completed":
        return tasks.filter((task) => task.completed);
      default:
        return tasks;
    }
  };

  return (
    <div className="App">
      <div className="container">
        <header className="app-header">
          <h1>📝 Todo App</h1>
          <p>Organize your tasks efficiently</p>
        </header>

        <TaskForm
          onSubmit={addTask}
          editingTask={editingTask}
          onUpdate={updateTask}
          onCancelEdit={() => setEditingTask(null)}
        />

        <div className="filter-section">
          <button
            className={`filter-btn ${filter === "all" ? "active" : ""}`}
            onClick={() => setFilter("all")}
          >
            All ({tasks.length})
          </button>

          <button
            className={`filter-btn ${filter === "active" ? "active" : ""}`}
            onClick={() => setFilter("active")}
          >
            Active ({tasks.filter((t) => !t.completed).length})
          </button>

          <button
            className={`filter-btn ${filter === "completed" ? "active" : ""}`}
            onClick={() => setFilter("completed")}
          >
            Completed ({tasks.filter((t) => t.completed).length})
          </button>
        </div>

        {/* Moved TaskList outside of filter-section div */}
        <TaskList
          tasks={getFilteredTasks()}
          loading={loading}
          onToggle={toggleTaskStatus}
          onDelete={deleteTask}
          onEdit={setEditingTask}
        />
      </div>

      <ToastContainer />
    </div>
  );
};

export default App;
