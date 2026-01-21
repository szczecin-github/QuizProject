package com.quizproject;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
class Quiz {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String icon; // e.g., "bi-globe" for history

    // One Quiz has Many Questions
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BaseQuestion> questions = new ArrayList<>();

    public Quiz() {}
    public Quiz(String title, String description, String icon) {
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    public void addQuestion(BaseQuestion q) {
        questions.add(q);
        q.setQuiz(this); // Link the question back to this quiz
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getIcon() { return icon; }
    public List<BaseQuestion> getQuestions() { return questions; }
}

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "question_type")
abstract class BaseQuestion implements IQuestion<Object> {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String prompt;
    private String correctAnswer;

    // Link back to the Quiz
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public BaseQuestion() {}
    public BaseQuestion(String prompt, String correctAnswer) {
        this.prompt = prompt;
        this.correctAnswer = correctAnswer;
    }

    public void setQuiz(Quiz quiz) { this.quiz = quiz; } // Setter
    
    public String getPrompt() { return prompt; }
    public String getCorrectAnswerString() { return correctAnswer; }
    public Long getId() { return id; }
}

@Entity
@DiscriminatorValue("TEXT")
class TextQuestion extends BaseQuestion {
    public TextQuestion() {}
    public TextQuestion(String prompt, String correctText) { super(prompt, correctText); }

    @Override
    public boolean validateAnswer(IAnswer<Object> userAnswer) {
        return String.valueOf(userAnswer.getValue()).equalsIgnoreCase(getCorrectAnswerString());
    }
    @Override
    public IAnswer<Object> getCorrectAnswer() { return new QuizAnswer<>(getCorrectAnswerString()); }
}

@Entity
@DiscriminatorValue("CHOICE")
class MultipleChoiceQuestion extends BaseQuestion {
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> options;

    public MultipleChoiceQuestion() {}
    public MultipleChoiceQuestion(String prompt, Integer correctIndex, List<String> options) {
        super(prompt, String.valueOf(correctIndex));
        this.options = options;
    }

    public List<String> getOptions() { return options; }

    @Override
    public boolean validateAnswer(IAnswer<Object> userAnswer) {
        try {
            int userVal = Integer.parseInt(String.valueOf(userAnswer.getValue()));
            int correctVal = Integer.parseInt(getCorrectAnswerString());
            return userVal == correctVal;
        } catch (NumberFormatException e) { return false; }
    }
    @Override
    public IAnswer<Object> getCorrectAnswer() {
        return new QuizAnswer<>(Integer.parseInt(getCorrectAnswerString()));
    }
}

class QuizAnswer<T> implements IAnswer<T> {
    private final T value;
    public QuizAnswer(T value) { this.value = value; }
    @Override public T getValue() { return value; }
    @Override public String toString() { return String.valueOf(value); }
}