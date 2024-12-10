package corrector.exam;

import java.util.*;

public class ExamChecker {
    private List<Character> correctAnswers;
    private List<Character> studentAnswers;
    private List<Integer> incorrectIndices;
    private final List<Character> validOptions = List.of('A', 'B', 'C', 'D', 'E'); // تأكد من جعل الخيارات قابلة للتعديل
    private final Map<Integer, Double> questionWeights;
    private int correctCount;
    private int incorrectCount;
    private double maxScore;
    private boolean isChecked;

    public ExamChecker() {
        correctAnswers = new ArrayList<>();
        studentAnswers = new ArrayList<>();
        questionWeights = new HashMap<>();
        isChecked = false;
        maxScore = 100;
    }

    public ExamChecker(List<Character> correctAnswers) {
        this.correctAnswers = correctAnswers;
        studentAnswers = new ArrayList<>();
        questionWeights = new HashMap<>();
        isChecked = false;
        maxScore = 100;
    }

    public ExamChecker(List<Character> correctAnswers, List<Character> studentAnswers) {
        this.correctAnswers = correctAnswers;
        this.studentAnswers = studentAnswers;
        questionWeights = new HashMap<>();
        isChecked = false;
        maxScore = 100;
    }

    public void setQuestionWeight(int questionIndex, double weight) {
        questionWeights.put(questionIndex, weight);
        isChecked = false;
    }

    public void setCorrectAnswers(List<Character> correctAnswers) {
        this.correctAnswers = correctAnswers;
        questionWeights.clear();
        isChecked = false;
    }

    public void setStudentAnswers(List<Character> studentAnswers) {
        if (studentAnswers == null || studentAnswers.isEmpty()) {
            throw new IllegalArgumentException("يجب إدخال إجابات الطالب");
        }

        boolean validAnswers = new HashSet<>(validOptions).containsAll(studentAnswers);

        if (!validAnswers) {
            throw new IllegalArgumentException("الإجابات يجب أن تكون ضمن الخيارات التالية: A أو B أو C أو D أو E");
        }
        if (studentAnswers.size() > correctAnswers.size()) {
            throw new IllegalArgumentException("عدد إجابات الطالب أكبر من عدد الأسئلة المتاحة");
        }

        this.studentAnswers = studentAnswers;
        isChecked = false;
    }

    public void setMaxScore(int score) {
        this.maxScore = score;
        isChecked = false;
    }

    public int getCorrectCount() {
        checkAnswers();
        return correctCount;
    }

    public int getIncorrectCount() {
        checkAnswers();
        return incorrectCount;
    }

    private void verifyAnswersLogic() {
        correctCount = 0;
        incorrectCount = 0;
        incorrectIndices = new ArrayList<>();
        for (int i = 0; i < Math.min(correctAnswers.size(), studentAnswers.size()); i++) {
            if (correctAnswers.get(i).equals(studentAnswers.get(i))) {
                correctCount++;
            } else {
                incorrectCount++;
                incorrectIndices.add(i);
            }
        }
        incorrectCount += Math.max(0, correctAnswers.size() - studentAnswers.size());
        isChecked = true;
    }

    private void checkAnswers() {
        if (isChecked) return;
        verifyAnswersLogic();
    }

    public double calculateScore() {
        checkAnswers();

        if (questionWeights.isEmpty()) {
            return ((double) correctCount / correctAnswers.size()) * maxScore;
        }

        double totalWeight = 0.0;
        for (int i = 0; i < correctAnswers.size(); i++) {
            totalWeight += questionWeights.getOrDefault(i, 1.0);
        }

        double totalScore = 0.0;

        for (int i = 0; i < correctAnswers.size(); i++) {
            double questionWeight = questionWeights.getOrDefault(i, 1.0);
            double questionScore = (questionWeight / totalWeight) * maxScore;

            if (i < studentAnswers.size() && correctAnswers.get(i).equals(studentAnswers.get(i))) {
                totalScore += questionScore;
            }
        }

        return totalScore;
    }

    public double getScore() {
        checkAnswers();
        return Double.parseDouble(String.format("%.2f", calculateScore()));
    }

    @Override
    public String toString() {
        checkAnswers();
        StringBuilder result = new StringBuilder();
        result.append("=== Exam Results ===\n");
        result.append("Correct Answers: ").append(correctCount).append("\n");
        result.append("Incorrect Answers: ").append(incorrectCount).append("\n");
        result.append("Final Score: ").append(String.format("%.2f", calculateScore())).
                append(" / ").append(maxScore).append("\n");

        if (!incorrectIndices.isEmpty()) {
            result.append("Incorrect Answer Positions: ");
            for (int index : incorrectIndices) {
                result.append(index + 1).append(" ");
            }
            result.append("\n");
        }

        if (!questionWeights.isEmpty()) {
            result.append("Question Weights: ");
            questionWeights.forEach((index, weight) ->
                    result.append("Q").append(index + 1).append(": ").append(weight).append(" "));
            result.append("\n");
        }

        return result.toString();
    }
}
