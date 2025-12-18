// Concrete Answer Wrapper
class QuizAnswer<T> implements IAnswer<T> {
    private final T value;

    public QuizAnswer(T value) {
        this.value = value;
    }

    @Override
    public T getValue() { return value; }
    
    @Override
    public String toString() { return String.valueOf(value); }
}

// Abstract Base Question
abstract class BaseQuestion<T> implements IQuestion<T> {
    private final String prompt;
    private final IAnswer<T> correctAnswer;

    public BaseQuestion(String prompt, T correctAnswerValue) {
        this.prompt = prompt;
        this.correctAnswer = new QuizAnswer<>(correctAnswerValue);
    }

    @Override
    public String getPrompt() { return prompt; }

    @Override
    public IAnswer<T> getCorrectAnswer() { return correctAnswer; }

    @Override
    public boolean validateAnswer(IAnswer<T> userAnswer) {
        return userAnswer.getValue().equals(correctAnswer.getValue());
    }
}

// Subclass for Text (String) Questions
class TextQuestion extends BaseQuestion<String> {
    public TextQuestion(String prompt, String correctText) {
        super(prompt, correctText);
    }

    @Override
    public boolean validateAnswer(IAnswer<String> userAnswer) {
        // Case-insensitive check for text
        return userAnswer.getValue().equalsIgnoreCase(getCorrectAnswer().getValue());
    }
}

// Subclass for Multiple Choice (Integer) Questions
class MultipleChoiceQuestion extends BaseQuestion<Integer> {
    public MultipleChoiceQuestion(String prompt, Integer correctIndex) {
        super(prompt, correctIndex);
    }
}