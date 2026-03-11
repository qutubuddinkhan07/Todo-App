import React from "react";
import { FaCheck, FaEdit, FaTrash } from "react-icons/fa";
import "./TaskItem.css";
import { toast } from "react-toastify";

const TaskItem = ({ task, onToggle, onDelete, onEdit }) => {
  const handleToggle = () => {
    onToggle(task.id);
  };

  const handleDelete = () => {
    if (window.confirm("Are you sure you want to delte this task?")) {
      onDelete(task.id);
    }
  };

  const handleEdit = () => {
    if (task.completed) {
      toast.error("Please mark the task as incomplete to edit it");
      return;
    }
    onEdit(task);
  };

  const formDate = (dateString) => {
    if (!dateString) return "No date";

    const date = new Date(dateString);
    return date.toLocaleDateString("en-US", {
      month: "short",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  return (
    <div className={`task-item ${task.completed ? "completed" : ""}`}>
      <div className="task-content">
        <div className="task-header">
          <h3 className="task-title">{task.title}</h3>
          {task.createdAt ? (
            <span className="task-date">{formDate(task.createdAt)}</span>
          ) : (
            <span className="task-date">{formDate(task.updatedAt)}</span>
          )}
        </div>

        {task.description && (
          <p className="task-description">{task.description}</p>
        )}
      </div>

      <div className="task-actions">
        <button
          className={`action-btn toggle-btn ${task.completed ? "completed" : ""}`}
          onClick={handleToggle}
          title={task.completed ? "Mark as incomplete" : "Mark as complete"}
        >
          <FaCheck />
        </button>

        <button
          className={`action-btn edit-btn ${task.completed ? "disabled" : ""}`}
          onClick={handleEdit}
          title={task.completed ? "Cannot edit completed tasks" : "Edit task"}
          style={{ opacity: task.completed ? 0.5 : 1 }}
        >
          <FaEdit />
        </button>

        <button
          className="action-btn delete-btn"
          onClick={handleDelete}
          title="Delete task"
        >
          <FaTrash />
        </button>
      </div>
    </div>
  );
};

export default TaskItem;
