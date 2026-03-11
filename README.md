# Task Manager Application

A full-stack task management application built with React (frontend) and Java Servlets (backend). Users can create, read, update, delete, and filter tasks with a clean and responsive UI.

## ✨ Features

- **Create Tasks** - Add new tasks with title and description
- **Read Tasks** - View all tasks with their status and timestamps
- **Update Tasks** - Edit existing task details
- **Delete Tasks** - Remove tasks you no longer need
- **Toggle Status** - Mark tasks as complete/incomplete with a single click
- **Filter Tasks** - View all, active, or completed tasks
- **Responsive Design** - Works seamlessly on desktop and mobile devices
- **Real-time Updates** - Instant UI updates without page refresh

## 🛠️ Tech Stack

### Frontend

- **React** - UI library
- **Axios** - HTTP client for API requests
- **React Icons** - Icon library
- **React Toastify** - Toast notifications
- **React Loader Spinner** - Loading animations
- **CSS3** - Styling with modern features

### Screeenshots
<img width="1919" height="1079" alt="Screenshot 2026-03-12 022400" src="https://github.com/user-attachments/assets/1322edb7-6bb4-407f-8eab-3692c0d980c9" />
<img width="1919" height="1079" alt="Screenshot 2026-03-12 022413" src="https://github.com/user-attachments/assets/1c885748-3247-4e79-9949-98e27930ccd6" />
<img width="1919" height="1079" alt="Screenshot 2026-03-12 022428" src="https://github.com/user-attachments/assets/16287a25-183e-4ca7-9bf1-7e9fd675f27b" />
<img width="1919" height="1079" alt="Screenshot 2026-03-12 022434" src="https://github.com/user-attachments/assets/f890d68d-7ec0-4037-a3e1-83743517a7b0" />
<img width="1919" height="1079" alt="Screenshot 2026-03-12 022441" src="https://github.com/user-attachments/assets/e0a4de8d-c7c0-443e-b598-20c46c5db12f" />

### Backend

- **Java Servlets** - REST API endpoints
- **Hibernate** - ORM for database operations
- **MySQL** - Database
- **Jackson** - JSON processing with JSR-310 support for Java 8 dates
- **Maven** - Dependency management

## 📋 Prerequisites

Before running this project, make sure you have installed:

- **Node.js** (v14 or higher)
- **Java** (JDK 11 or higher)
- **MySQL** (v8 or higher)
- **Apache Tomcat** (v9 or higher)
- **Maven** (for backend dependency management)

## 🚀 Installation and Setup

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/task-manager.git
cd task-manager
```

### 2. Backend Setup

Configure Database

```sql
CREATE DATABASE todoapp;
USE todoapp;

CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

Update Database Configuration
Edit <code>src/main/resources/hibernate.cfg.xml</code> with your database credentials:

```xml
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/todoapp</property>
<property name="hibernate.connection.username">your_username</property>
<property name="hibernate.connection.password">your_password</property>
```

Build and Deploy Backend

```bash
cd backend
mvn clean package
```

Deploy the generated WAR file to your Tomcat server

### 3. Frontend Setup

```bash
cd frontend
npm install
```

## 🌐 API Endpoints

<table>
<tr>
<th>Method</th>
<th>Endpoint</th>
<th>Description</th>
</tr>
<tr>
<td>GET</td>
<td>/api/tasks/</td>
<td>Get all tasks</td>
</tr>
<tr>
<td>GET</td>
<td>/api/tasks/{id}</td>
<td>Get task by ID</td>
</tr>
<tr>
<td>POST</td>
<td>/api/tasks/</td>
<td>Create new task</td>
</tr>
<tr>
<td>PUT</td>
<td>/api/tasks/{id}</td>
<td>Update task</td>
</tr>
<tr>
<td>DELETE</td>
<td>/api/tasks/{id}</td>
<td>Delete task</td>
</tr>
<tr>
<td>PUT</td>
<td>/api/tasks/{id}/toggle</td>
<td>Toggle task status</td>
</tr>
</table>

## 📁 Project Structure

```text
task-manager/
├── frontend/                 # React frontend
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   │   ├── TaskForm.jsx
│   │   │   ├── TaskItem.jsx
│   │   │   └── TaskList.jsx
│   │   ├── services/
│   │   │   └── api.js
│   │   ├── App.jsx
│   │   ├── App.css
│   │   └── index.js
│   ├── package.json
│   └── README.md
│
├── backend/                  # Java backend
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── todo/
│   │       │           ├── config/
│   │       │           │   └── JacksonConfig.java
│   │       │           ├── dao/
│   │       │           │   └── TaskDAO.java
│   │       │           ├── model/
│   │       │           │   └── Task.java
│   │       │           └── servlet/
│   │       │               └── TaskServlet.java
│   │       └── resources/
│   │           └── hibernate.cfg.xml
│   ├── pom.xml
│   └── README.md
│
└── README.md                 # Main README
```

## 🎯 Usage

1. Add a Task - Fill in the title and description, click "Add Task"
2. Complete a Task - Click the checkmark button on any task
3. Edit a Task - Click the edit button (only available for incomplete tasks)
4. Delete a Task - Click the trash button and confirm
5. Filter Tasks - Use the filter buttons to view all, active, or completed tasks

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (git checkout -b feature/AmazingFeature)
3. Commit your changes (git commit -m 'Add some AmazingFeature')
4. Push to the branch (git push origin feature/AmazingFeature)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👏 Acknowledgments

- React Icons for the beautiful icons
- React Toastify for the elegant notifications
- All contributors who help improve this project

## 📧 Contact

Qutubuddin Khan - qutubuddinkhan8261@gmail.com

Project Link: https://github.com/yourusername/task-manager
