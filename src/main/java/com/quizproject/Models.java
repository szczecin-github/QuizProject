package com.quizproject;

import jakarta.persistence.*;

// 1. Mark as Entity for JPA
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "question_type")
// 2. We remove the generic <T> to make DB mapping easier
abstract class BaseQuestion implements IQuestion<Object> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prompt;
    
    // We store the answer as a String in the DB
    private String correctAnswer;

    // Default constructor needed for JPA
    public BaseQuestion() {}

    public BaseQuestion(String prompt, String correctAnswer) {
        this.prompt = prompt;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String getPrompt() { return prompt; }
    
    // Helper for JPA
    public String getCorrectAnswerString() { return correctAnswer; }

    public Long getId() { return id; }
}

@Entity
@DiscriminatorValue("TEXT")
class TextQuestion extends BaseQuestion {
    
    public TextQuestion() {}

    public TextQuestion(String prompt, String correctText) {
        super(prompt, correctText);
    }

    @Override
    public boolean validateAnswer(IAnswer<Object> userAnswer) {
        return String.valueOf(userAnswer.getValue()).equalsIgnoreCase(getCorrectAnswerString());
    }

    @Override
    public IAnswer<Object> getCorrectAnswer() {
        return new QuizAnswer<>(getCorrectAnswerString());
    }
}

@Entity
@DiscriminatorValue("CHOICE")
class MultipleChoiceQuestion extends BaseQuestion {
    
    public MultipleChoiceQuestion() {}

    public MultipleChoiceQuestion(String prompt, Integer correctIndex) {
        super(prompt, String.valueOf(correctIndex));
    }

    @Override
    public boolean validateAnswer(IAnswer<Object> userAnswer) {
        try {
            int userVal = Integer.parseInt(String.valueOf(userAnswer.getValue()));
            int correctVal = Integer.parseInt(getCorrectAnswerString());
            return userVal == correctVal;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public IAnswer<Object> getCorrectAnswer() {
        // Return as Integer to match original logic
        return new QuizAnswer<>(Integer.parseInt(getCorrectAnswerString()));
    }
}

// Keep QuizAnswer as is (it's not an Entity, just a wrapper)
class QuizAnswer<T> implements IAnswer<T> {
    private final T value;
    public QuizAnswer(T value) { this.value = value; }
    @Override public T getValue() { return value; }
    @Override public String toString() { return String.valueOf(value); }
}