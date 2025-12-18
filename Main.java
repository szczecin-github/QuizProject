import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        IQuizStorage storage = new JsonFileStorage();
        String filename = "quiz_data.json";

        // 1. Create a Quiz in memory
        IQuiz myQuiz = new ConsoleQuiz();
        myQuiz.addQuestion(new TextQuestion("What is the capital of France?", "Paris"));
        myQuiz.addQuestion(new MultipleChoiceQuestion("Which is the largest planet? 1.Earth 2.Mars 3.Jupiter (Enter index)", 3));

        // 2. Save it to disk
        try {
            storage.saveQuiz(myQuiz, filename);
            System.out.println("Quiz saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving quiz: " + e.getMessage());
        }

        System.out.println("-------------------------");

        // 3. Load it back and run it
        try {
            System.out.println("Loading quiz from file...");
            IQuiz loadedQuiz = storage.loadQuiz(filename);
            loadedQuiz.start();
        } catch (IOException e) {
            System.out.println("Error loading quiz: " + e.getMessage());
        }
    }
}