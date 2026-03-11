import React from "react";
import { ThreeDots } from "react-loader-spinner";
import TaskItem from "./TaskItem";
import "./TaskList.css";

const TaskList = ({ tasks, loading, onToggle, onDelete, onEdit }) => {
  if (loading) {
    return (
      <div className="loading-container">
        <ThreeDots
          height={80}
          width={80}
          radius={9}
          color="#667eea"
          ariaLabel="three-dots-loading"
        />
      </div>
    );
  }

  if (tasks.length === 0) {
    return (
      <div className="empty-state">
        <p>No tasks found. Create your first task!</p>
      </div>
    );
  }

  return (
    <div className="task-list">
      {tasks.map((task) => (
        <TaskItem
          key={task.id}
          task={task}
          onToggle={onToggle}
          onDelete={onDelete}
          onEdit={onEdit}
        />
      ))}
    </div>
  );
};

export default TaskList;
