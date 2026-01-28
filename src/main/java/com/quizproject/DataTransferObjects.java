package com.quizproject;
import java.util.ArrayList;
import java.util.List;

class QuizDTO {
    public List<QuestionDTO> questions = new ArrayList<>();
}

class QuestionDTO {
    public String type;
    public String prompt;
    public Object correctAnswer;  // Generic object holder for JSON

    public QuestionDTO() {}

    public QuestionDTO(String type, String prompt, Object correctAnswer) {
        this.type = type;
        this.prompt = prompt;
        this.correctAnswer = correctAnswer;
    }
}