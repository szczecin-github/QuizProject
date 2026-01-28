package com.quizproject;
// T represents the data type (String, Integer, etc.)
interface IAnswer<T> {
    T getValue();
}

interface IQuestion<T> {
    String getPrompt();
    boolean validateAnswer(IAnswer<T> answer);
    IAnswer<T> getCorrectAnswer();
}

interface IQuiz {
    void addQuestion(IQuestion<?> question);
    void start();
    java.util.List<IQuestion<?>> getQuestions();
}

interface IQuizStorage {
    void saveQuiz(IQuiz quiz, String filepath) throws java.io.IOException;
    IQuiz loadQuiz(String filepath) throws java.io.IOException;
}