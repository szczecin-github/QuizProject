package com.quizproject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ConsoleQuiz implements IQuiz {
    private List<IQuestion<?>> questions;
    private int score;

    public ConsoleQuiz() {
        this.questions = new ArrayList<>();
        this.score = 0;
    }

    @Override
    public void addQuestion(IQuestion<?> question) {
        this.questions.add(question);
    }
    
    @Override
    public List<IQuestion<?>> getQuestions() {
        return this.questions;
    }

    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Starting Quiz ---");

        for (IQuestion<?> q : questions) {
            System.out.println("\nQ: " + q.getPrompt());
            System.out.print("Your Answer: ");
            String input = scanner.nextLine();

            boolean isCorrect = processAnswer(q, input);
            
            if (isCorrect) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong. Correct was: " + q.getCorrectAnswer().getValue());
            }
        }
        System.out.println("\nFinal Score: " + score + "/" + questions.size());
    }

    // Helper to cast types safely based on the question type
    private <T> boolean processAnswer(IQuestion<T> question, String input) {
        try {
            Object parsedInput = input;
            
            // If question expects Integer, parse the string to Integer
            if (question.getCorrectAnswer().getValue() instanceof Integer) {
                parsedInput = Integer.parseInt(input);
            }
            
            IAnswer<T> userAnswer = new QuizAnswer<>((T) parsedInput);
            return question.validateAnswer(userAnswer);
            
        } catch (Exception e) {
            System.out.println("Invalid input format.");
            return false;
        }
    }
}