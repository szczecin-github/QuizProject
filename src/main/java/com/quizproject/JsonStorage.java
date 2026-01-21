package com.quizproject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class JsonFileStorage implements IQuizStorage {
    private final Gson gson;

    public JsonFileStorage() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void saveQuiz(IQuiz quiz, String filepath) throws IOException {
        QuizDTO dto = new QuizDTO();

        // Convert domain objects (Questions) to DTOs
        for (IQuestion<?> q : quiz.getQuestions()) {
            String type = "";
            if (q instanceof TextQuestion) type = "TEXT";
            else if (q instanceof MultipleChoiceQuestion) type = "MULTIPLE_CHOICE";
            
            dto.questions.add(new QuestionDTO(type, q.getPrompt(), q.getCorrectAnswer().getValue()));
        }

        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(dto, writer);
        }
    }

    @Override
    public IQuiz loadQuiz(String filepath) throws IOException {
        QuizDTO dto;
        try (FileReader reader = new FileReader(filepath)) {
            dto = gson.fromJson(reader, QuizDTO.class);
        }

        ConsoleQuiz loadedQuiz = new ConsoleQuiz();
        
        // Convert DTOs back to domain objects
        if (dto.questions != null) {
            for (QuestionDTO qDto : dto.questions) {
                switch (qDto.type) {
                    case "TEXT":
                        loadedQuiz.addQuestion(new TextQuestion(qDto.prompt, (String) qDto.correctAnswer));
                        break;
                    case "MULTIPLE_CHOICE":
                        // Gson reads generic numbers as Double, so we convert to Int
                        int correctIndex = ((Number) qDto.correctAnswer).intValue();
                        loadedQuiz.addQuestion(new MultipleChoiceQuestion(qDto.prompt, correctIndex));
                        break;
                }
            }
        }
        return loadedQuiz;
    }
}