package com.quizproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class Main {
    public static void main(String[] args) { SpringApplication.run(Main.class, args); }

    @Bean
    public CommandLineRunner demo(QuizRepository quizRepository, QuizDataInitializer initializer) {
        return (args) -> {
            System.out.println("--- Starting Concurrent Data Load ---");

            CompletableFuture<Quiz> task1 = initializer.loadHistoryQuiz();
            CompletableFuture<Quiz> task2 = initializer.loadSpaceQuiz();

            // Wait for both tasks and save
            CompletableFuture.allOf(task1, task2).join();
            
            quizRepository.save(task1.get());
            quizRepository.save(task2.get());

            System.out.println("--- Data Loaded! App Ready at http://localhost:8080 ---");
        };
    }
}