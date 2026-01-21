package com.quizproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner demo(QuestionRepository repository) {
        return (args) -> {
            System.out.println("--- Spring Boot Quiz App Started ---");

            // 1. Create Questions
            TextQuestion q1 = new TextQuestion("What is the capital of France?", "Paris");
            MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("Which is the largest planet? 1.Earth 2.Mars 3.Jupiter", 3);

            // 2. Save to Database (CRUD: Create)
            repository.save(q1);
            repository.save(q2);
            System.out.println("Questions saved to Database!");

            // 3. Read from Database (CRUD: Read)
            System.out.println("\nFetching questions from Database:");
            for (BaseQuestion q : repository.findAll()) {
                System.out.println("ID: " + q.getId() + " | Prompt: " + q.getPrompt());
            }
            
            System.out.println("------------------------------------");
        };
    }
}