# QuizProject - Spring Boot Web Application

A modern, interactive Quiz Application built with Java Spring Boot. This project has evolved from a simple console app into a feature-rich web platform featuring a "Who Wants to Be a Millionaire" style interface, concurrent data loading, and detailed result analysis.

## 🚀 Features

### 🌟 Interactive Web UI
- **User-Friendly Interface:** Built with **Thymeleaf** and **Bootstrap 5**, featuring a clean, responsive design.
- **Dynamic Question Types:** Supports both standard **Text Input** questions and **Multiple Choice** questions with distinct UI elements.
- **"Millionaire" Style Buttons:** Custom CSS styling for multiple-choice options that light up and change color upon selection.

### 🧠 Smart Logic
- **Multiple Quizzes:** Users can choose between different topics (e.g., "History Trivia", "Space Explorer") from a dedicated selection screen.
- **Concurrent Loading:** Utilizes Java `CompletableFuture` to simulate loading data from multiple sources (e.g., History DB and Space DB) simultaneously for faster startup.
- **Review Mode:** After finishing a quiz, users receive a detailed breakdown of their performance, highlighting correct answers in **Green** and mistakes in **Red**.

### 🛠 Administrative Tools
- **Add Question Portal:** A dedicated page (`/add`) allowing users to create new questions dynamically and assign them to specific quizzes without writing code.
- **In-Memory Database:** Uses **H2 Database** to store questions, ensuring a clean slate every time the application restarts.

## 🛠️ Tech Stack
- **Backend:** Java 17, Spring Boot 3
- **Frontend:** Thymeleaf, HTML5, CSS3, Bootstrap 5
- **Database:** H2 (In-Memory)
- **Build Tool:** Maven

## 🏃‍♂️ How to Run

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/YourUsername/QuizProject.git](https://github.com/YourUsername/QuizProject.git)
   cd QuizProject
   ```
2. **Build and Run: You can run the application directly using Maven:**
  ```Bash
  mvn clean spring-boot:run
  ```
3. **Access the App: Open your browser and navigate to: 👉 http://localhost:8080**

## Usage Guide ##
- **Select a Quiz: Start by clicking "Start Quiz" and choosing a topic from the dashboard.**
- **Play: - Type answers for text questions.**
- **Click the blue option buttons for multiple-choice questions.**
- **Review: Check your score and see exactly where you went wrong on the Review Page.**
- **Create: Click "Add Quiz" on the home screen to create your own Quiz category.**
- **Create: Click "Add Question" on the home screen to insert your own custom questions into any active quiz.**
