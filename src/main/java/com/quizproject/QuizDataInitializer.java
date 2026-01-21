package com.quizproject;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class QuizDataInitializer {

    // Thread 1: Loads the "History" Quiz
    public CompletableFuture<Quiz> loadHistoryQuiz() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Loading History Data...");
            try { Thread.sleep(500); } catch (InterruptedException e) { }

            Quiz quiz = new Quiz("History Trivia", "Test your knowledge of the past!", "bi-hourglass-split");
            
            quiz.addQuestion(new TextQuestion("Which empire built the Colosseum?", "Roman"));
            quiz.addQuestion(new MultipleChoiceQuestion("Who was the first US President?", 1, 
                List.of("George Washington", "Thomas Jefferson", "Abraham Lincoln")));
            quiz.addQuestion(new TextQuestion("In which year did WWII end?", "1945"));

            return quiz;
        });
    }

    // Thread 2: Loads the "Space" Quiz
    public CompletableFuture<Quiz> loadSpaceQuiz() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Loading Space Data...");
            try { Thread.sleep(500); } catch (InterruptedException e) { }

            Quiz quiz = new Quiz("Space Explorer", "Journey through the stars!", "bi-rocket-takeoff");
            
            quiz.addQuestion(new MultipleChoiceQuestion("Which planet is known as the Red Planet?", 2, 
                List.of("Venus", "Mars", "Jupiter")));
            quiz.addQuestion(new TextQuestion("What is the name of our galaxy?", "Milky Way"));
            quiz.addQuestion(new MultipleChoiceQuestion("Who was the first man on the moon?", 1, 
                List.of("Neil Armstrong", "Buzz Aldrin", "Yuri Gagarin")));

            return quiz;
        });
    }
}