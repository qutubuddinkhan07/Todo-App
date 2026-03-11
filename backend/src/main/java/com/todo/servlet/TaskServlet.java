package com.todo.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.config.JacksonConfig;
import com.todo.dao.TaskDAO;
import com.todo.model.Task;

@WebServlet("/api/tasks/*")
public class TaskServlet extends HttpServlet {
	private TaskDAO taskDAO;
	private ObjectMapper objectMapper;

	@Override
	public void init() throws ServletException {
		taskDAO = new TaskDAO();

		// using the configured ObjectMapper from JacksonConfig instead of creating a
		// new one
		objectMapper = JacksonConfig.getObjectMapper();
	}

	// GET - Retrieve tasks
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String pathInfo = request.getPathInfo();

		try {
			if (pathInfo == null || pathInfo.equals("/")) {
				// Get all tasks
				List<Task> tasks = taskDAO.getAllTasks();
				out.print(objectMapper.writeValueAsString(tasks));
			} else {
				// Get specific task
				String[] pathParts = pathInfo.split("/");
				if (pathInfo.length() > 1) {
					Long id = Long.parseLong(pathParts[1]);
					Task task = taskDAO.getTasksById(id);
					if (task != null) {
						out.print(objectMapper.writeValueAsString(task));
					} else {
						response.setStatus(HttpServletResponse.SC_NOT_FOUND);
						out.print("{\"error\": \"Task not found\"}");
					}
				}
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}

	// POST - Create new task
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		try {
			// Read JSON from request
			BufferedReader reader = request.getReader();
			Task task = objectMapper.readValue(reader, Task.class);

			// Create task
			Task createdTask = taskDAO.createTask(task);

			if (createdTask != null) {
				response.setStatus(HttpServletResponse.SC_CREATED);
				out.print(objectMapper.writeValueAsString(createdTask));
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				out.print("{\"error\": \"Failed to create task\"}");
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}

	// PUT - Update task
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		String pathInfo = request.getPathInfo();

		try {
			if (pathInfo != null && pathInfo.split("/").length > 1) {
				Long id = Long.parseLong(pathInfo.split("/")[1]);

				// Check if it's a toggle request
				if (pathInfo.contains("/toggle")) {
					Task updatedTask = taskDAO.toggleTaskStatus(id);
					if (updatedTask != null) {
						out.print(objectMapper.writeValueAsString(updatedTask));
					} else {
						response.setStatus(HttpServletResponse.SC_NOT_FOUND);
						out.print("{\"error\": \"Task not found\"}");
					}
				} else {
					// regular update
					BufferedReader reader = request.getReader();
					Task task = objectMapper.readValue(reader, Task.class);
					task.setId(id);

					Task updatedTask = taskDAO.updateTask(task);
					if (updatedTask != null) {
						out.print(objectMapper.writeValueAsString(updatedTask));
					} else {
						response.setStatus(HttpServletResponse.SC_NOT_FOUND);
						out.print("{\"error\": \"Task not found\"}");
					}
				}
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				out.print("{\"error\": \"Task ID is required\"}");
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}

	// DELETE - Delete task
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		String pathInfo = request.getPathInfo();

		try {
			if (pathInfo != null && pathInfo.split("/").length > 1) {
				Long id = Long.parseLong(pathInfo.split("/")[1]);

				boolean deleted = taskDAO.deleteTask(id);

				if (deleted) {
					out.print("{\"message\": \"Task deleted successfully\"}");
				} else {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					out.print("{\"error\": \"Task not found\"}");
				}
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}
}
