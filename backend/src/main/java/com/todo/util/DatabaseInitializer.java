package com.todo.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.todo.model.Task;

public class DatabaseInitializer {
	public static void initializeDatabase() {
		// check if tables exist or if data needs to be seeded
		EntityManager em = JPAUtil.getEntityManager();

		try {
			// check if tasks table is empty
			Long count = em.createQuery("SELECT COUNT(t) FROM Task t", Long.class).getSingleResult();

			if (count == 0) {
				System.out.println("Database empty. Seeding initial data...");
				seedInitialData();
			}
		} catch (Exception e) {
			// Table might not exist yet - JPA will create it
			System.out.println("Tables may not exist yet: " + e.getMessage());
			;
		} finally {
			em.close();
		}
	}

	private static void seedInitialData() {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();

		List<Task> tasks = createTaskList();

		try {
			et.begin();

			for (Task task : tasks) {
				em.persist(task);
			}

			et.commit();
			System.out.println("Sample data seeded successfully!");
		} catch (Exception e) {
			if (et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public static List<Task> createTaskList() {
		return Arrays.asList(
				new Task(null, "Complete project proposal",
						"Write the final draft of the Q2 project proposal for client review", false,
						LocalDateTime.of(2026, 3, 1, 9, 30, 0), LocalDateTime.of(2026, 3, 1, 9, 30, 0)),

				new Task(null, "Learn React Hooks",
						"Study useEffect, useState, and custom hooks with practical examples", false,
						LocalDateTime.of(2026, 3, 2, 14, 15, 0), LocalDateTime.of(2026, 3, 2, 14, 15, 0)),

				new Task(null, "Morning workout", "30 minutes cardio and strength training", true,
						LocalDateTime.of(2026, 3, 3, 6, 0, 0), LocalDateTime.of(2026, 3, 3, 7, 30, 0)),

				new Task(null, "Buy groceries", "Milk, eggs, bread, vegetables, and fruits for the week", false,
						LocalDateTime.of(2026, 3, 3, 18, 45, 0), LocalDateTime.of(2026, 3, 3, 18, 45, 0)),

				new Task(null, "Team meeting", "Weekly sync with the development team to discuss sprint progress", true,
						LocalDateTime.of(2026, 3, 4, 10, 0, 0), LocalDateTime.of(2026, 3, 4, 11, 30, 0)),

				new Task(null, "Fix login bug", "Resolve the authentication issue reported by users on the login page",
						false, LocalDateTime.of(2026, 3, 4, 13, 20, 0), LocalDateTime.of(2026, 3, 4, 13, 20, 0)),

				new Task(null, "Read technical book",
						"Continue reading \"Clean Code\" by Robert C. Martin - Chapters 5-7", false,
						LocalDateTime.of(2026, 3, 5, 20, 0, 0), LocalDateTime.of(2026, 3, 5, 20, 0, 0)));
	}
}
