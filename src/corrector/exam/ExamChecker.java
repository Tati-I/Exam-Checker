package corrector.exam;

import java.util.ArrayList;
import java.util.List;

public class ExamChecker {
    private List<Character> correctAnswers;
    private List<Character> studentAnswers;
    private int correctCount;
    private int incorrectCount;
    private List<Integer> incorrectIndices;
    private boolean isChecked;
    private int maxScore; // العلامة الكاملة

    public ExamChecker() {
        correctAnswers = new ArrayList<>();
        studentAnswers = new ArrayList<>();
        isChecked = false;
        maxScore = 100;
    }

    public ExamChecker(List<Character> correctAnswers) {
        this.correctAnswers = correctAnswers;
        studentAnswers = new ArrayList<>();
        isChecked = false;
        maxScore = 100;
    }

    public ExamChecker(List<Character> correctAnswers, List<Character> studentAnswers) {
        this.correctAnswers = correctAnswers;
        this.studentAnswers = studentAnswers;
        isChecked = false;
        maxScore = 100;
    }

    public void setCorrectAnswers(List<Character> correctAnswers) {
        this.correctAnswers = correctAnswers;
        isChecked = false; // إعادة تعيين حالة التحقق
    }

    public void setStudentAnswers(List<Character> studentAnswers) {
        this.studentAnswers = studentAnswers;
        isChecked = false; // إعادة تعيين حالة التحقق
    }

    public void setMaxScore(int score) {
        this.maxScore = score;
    }

    public int getCorrectCount() {
        checkAnswers();
        return correctCount;
    }

    public int getIncorrectCount() {
        checkAnswers();
        return incorrectCount;
    }

    private void checkAnswers() {
        if (isChecked) return;

        correctCount = 0;
        incorrectCount = 0;
        incorrectIndices = new ArrayList<>();

        int compareLength = Math.min(correctAnswers.size(), studentAnswers.size());

        for (int i = 0; i < compareLength; i++) {
            if (correctAnswers.get(i).equals(studentAnswers.get(i))) {
                correctCount++;
            } else {
                incorrectCount++;
                incorrectIndices.add(i);
            }
        }

        if (studentAnswers.size() < correctAnswers.size()) {
            incorrectCount = correctAnswers.size() - studentAnswers.size();
        }

        isChecked = true; // تم التحقق
    }

    public double calculateScore() {
        checkAnswers();
        return ((double) correctCount / correctAnswers.size()) * maxScore;
    }

    public double getScore(){
        checkAnswers();
        return calculateScore();
    }

    @Override
    public String toString() {
        checkAnswers();
        StringBuilder result = new StringBuilder();
        result.append("=== Exam Results ===\n");
        result.append("Correct Answers: ").append(correctCount).append("\n");
        result.append("Incorrect Answers: ").append(incorrectCount).append("\n");
        result.append("Final Score: ").append(String.format("%.2f", calculateScore())).append(" / ").append(maxScore+".00").append("\n");

        if (!incorrectIndices.isEmpty()) {
            result.append("Incorrect Answer Positions: ");
            for (int index : incorrectIndices) {
                result.append(index + 1).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
