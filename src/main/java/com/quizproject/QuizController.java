package com.quizproject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
public class QuizController {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public QuizController(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/")
    public String home() { return "index"; }

    @GetMapping("/select")
    public String selectQuiz(Model model) {
        model.addAttribute("quizzes", quizRepository.findAll());
        return "select_quiz";
    }

    @GetMapping("/take/{id}")
    public String takeQuiz(@PathVariable Long id, Model model) {
        Quiz quiz = quizRepository.findById(id).orElse(null);
        if (quiz == null) return "redirect:/select";

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", quiz.getQuestions());
        return "quiz";
    }
    // --- Create Quiz & Save the New Quiz---
    @GetMapping("/create-quiz")
    public String showCreateQuizForm() {
        return "create_quiz";
    }
    @PostMapping("/create-quiz")
    public String createQuiz(@RequestParam String title,
                             @RequestParam String description,
                             @RequestParam String icon) {
        
        Quiz newQuiz = new Quiz(title, description, icon);
        quizRepository.save(newQuiz);
        return "redirect:/add"; 
    }

    @GetMapping("/add")
    public String showAddQuestionForm(Model model) {
        model.addAttribute("quizzes", quizRepository.findAll());
        return "add_question";
    }

    @PostMapping("/add")
    public String addQuestion(
            @RequestParam Long quizId,
            @RequestParam String type,
            @RequestParam String prompt,
            @RequestParam String correctAnswer,
            @RequestParam(required = false) String optionsStr) {

        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz == null) return "redirect:/add";

        BaseQuestion newQuestion;
        if ("CHOICE".equals(type)) {

            List<String> options = List.of(optionsStr.split("\\s*,\\s*"));
            int correctIndex = Integer.parseInt(correctAnswer);
            newQuestion = new MultipleChoiceQuestion(prompt, correctIndex, options);
        } else {
            newQuestion = new TextQuestion(prompt, correctAnswer);
        }

        quiz.addQuestion(newQuestion);
        questionRepository.save(newQuestion); // Saves to DB
        
        return "redirect:/take/" + quizId;
    }

    @PostMapping("/submit")
    public String submitQuiz(@RequestParam Map<String, String> allParams, Model model) {
        int score = 0;
        int total = 0;
        List<ResultDetail> results = new ArrayList<>();

        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            if (entry.getKey().startsWith("question_")) {
                Long qId = Long.parseLong(entry.getKey().replace("question_", ""));
                BaseQuestion q = questionRepository.findById(qId).orElse(null);
                
                if (q != null) {
                    total++;
                    boolean isCorrect = q.validateAnswer(new QuizAnswer<>(entry.getValue()));
                    if (isCorrect) score++;

                    List<String> opts = (q instanceof MultipleChoiceQuestion) ? ((MultipleChoiceQuestion) q).getOptions() : null;
                    results.add(new ResultDetail(q.getPrompt(), entry.getValue(), q.getCorrectAnswer().getValue().toString(), isCorrect, opts));
                }
            }
        }
        model.addAttribute("score", score);
        model.addAttribute("total", total);
        model.addAttribute("results", results);
        return "result";
    }

    public static class ResultDetail {
        public String prompt, userAnswer, correctAnswer;
        public boolean isCorrect;
        public List<String> options;
        public ResultDetail(String p, String u, String c, boolean ok, List<String> o) {
            this.prompt=p; userAnswer=u; correctAnswer=c; isCorrect=ok; options=o;
        }
    }
}