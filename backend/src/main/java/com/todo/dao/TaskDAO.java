package com.todo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.mysql.cj.xdevapi.SessionFactory;
import com.todo.model.Task;
import com.todo.util.JPAUtil;

public class TaskDAO {
	private SessionFactory sessionFactory;

	public TaskDAO() {
		try {
			// Read from environmene variables (set on Render)
			String dbHost = System.getenv("DB_HOST");
			String dbPort = System.getenv("DB_PORT");
			String dbName = System.getenv("DB_NAME");
			String dbUser = System.getenv("DB_USER");
			String dbPassword = System.getenv("DB_PASSWORD");

			// If environment variables exist, override JPA properties
			if (dbHost != null && !dbHost.isEmpty()) {
				Map<String, String> properties = new HashMap<>();

				// Build JDBC URL
				String jdbcUrl = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName
						+ "?useSSL=true&requireSSL=true&serverTimezone=UTC";

				// Override JPA properties
				properties.put("javax.persistence.jdbc.url", jdbcUrl);
				properties.put("javax.persistence.jdbc.user", dbUser);
				properties.put("javax.persistence.jdbc.password", dbPassword);

				// Update JPAUtil with these properties
				JPAUtil.overrideProperties(properties);
				System.out.println("Using environment variables for database connection");
				System.out.println("Connected to: " + dbHost + ":" + dbPort + "/" + dbName);
			} else {
				System.out.println("Using default persistence.xml configuration");
			}
		} catch (Exception e) {
			System.err.println("Error configuring database connection: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// create
	public Task createTask(Task task) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			em.persist(task);
			et.commit();
			return task;
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}

	// Read - Get all tasks
	public List<Task> getAllTasks() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<Task> query = em.createQuery("SELECT t FROM Task t ORDER BY t.createdAt DESC", Task.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}

	// Read - Get tasks by ID
	public Task getTasksById(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.find(Task.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}

	// Update
	public Task updateTask(Task task) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			Task updatedTask = em.merge(task);
			et.commit();
			return updatedTask;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}

	// Delete
	public boolean deleteTask(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			Task task = em.find(Task.class, id);
			if (task != null) {
				em.remove(task);
				et.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
	}

	// Mark task as complete/incomplete
	public Task toggleTaskStatus(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			Task task = em.find(Task.class, id);
			if (task != null) {
				task.setCompleted(!task.isCompleted());
				Task updatedTask = em.merge(task);
				et.commit();
				return updatedTask;
			}
			return null;
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}

	// Alternative: using try-with-resource approach (Java 9+)
	// If using Java 9 or above, this helper method can be used
	private <T> T executeInTransaction(EntityManager em, TransactionCallback<T> callback) {
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			T result = callback.execute(em);
			transaction.commit();
			return result;
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			throw e;
		}
	}

	@FunctionalInterface
	private interface TransactionCallback<T> {
		T execute(EntityManager em);
	}
}
