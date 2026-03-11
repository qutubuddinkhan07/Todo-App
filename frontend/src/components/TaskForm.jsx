import React, { useEffect, useState } from "react";
import "./TaskForm.css";

const TaskForm = ({ onSubmit, editingTask, onUpdate, onCancelEdit }) => {
  const [formData, setFormData] = useState({
    title: "",
    description: "",
  });

  useEffect(() => {
    if (editingTask) {
      setFormData({
        title: editingTask.title,
        description: editingTask.description || "",
      });
    } else {
      setFormData({ title: "", description: "" });
    }
  }, [editingTask]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.title.trim()) {
      alert("Title is required");
      return;
    }

    let success;
    if (editingTask) {
      success = await onUpdate(editingTask.id, formData);
    } else {
      success = await onSubmit(formData);
    }

    if (success) {
      setFormData({ title: "", description: "" });
    }
  };

  return (
    <form className="task-form" onSubmit={handleSubmit}>
      <h2>{editingTask ? "Edit Task" : "Add New Task"}</h2>

      <div className="form-group">
        <input
          type="text"
          name="title"
          value={formData.title}
          onChange={handleChange}
          placeholder="Task title"
          className="form-input"
          required
        />
      </div>

      <div className="form-group">
        <textarea
          name="description"
          value={formData.description}
          onChange={handleChange}
          placeholder="Task description (optional)"
          className="form-textarea"
          rows={3}
        ></textarea>
      </div>

      <div className="form-actions">
        <button type="submit" className="btn-submit">
          {editingTask ? "Update Task" : "Add Task"}
        </button>

        {editingTask && (
          <button type="button" onClick={onCancelEdit} className="btn-cancel">
            Cancel
          </button>
        )}
      </div>
    </form>
  );
};

export default TaskForm;
