package corrector.exam;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * نظام تصحيح الامتحانات متعدد الميزات
 *
 * يوفر هذا النظام وظائف متقدمة لتصحيح الامتحانات وحساب الدرجات
 * مع دعم العديد من السيناريوهات المختلفة
 *
 * @author IXRAHEEM   المطور
 * @version 1.4
 */
public class ExamChecker {

    /** قائمة الخيارات الصالحة للإجابات */
    private List<Character> validOptions =
            List.of('A', 'B', 'C', 'D', 'E',' ');

    /** مؤشرات الأسئلة الثابتة الإجابة */
    private List<Integer> alwaysCorrectIndex;

    /** قائمة الإجابات الصحيحة للامتحان */
    private List<Character> correctAnswers;

    /** إجابات الطالب */
    private List<Character> studentAnswers;

    /** مؤشرات الإجابات غير الصحيحة */
    private List<Integer> incorrectIndices;

    /** قائمة الطلاب */
    private List<Student> students;

    /** الطالب الحالي */
    private Student student;

    /** أوزان الأسئلة */
    private Map<Integer, Double> questionWeights;

    /** عدد الإجابات الصحيحة */
    private int correctCount;

    /** عدد الإجابات الخاطئة */
    private int incorrectCount;

    /** الدرجة القصوى للامتحان */
    private double maxScore;

    /** مؤشر فحص الإجابات */
    private boolean isChecked;

    /** وضع الخطأ مقابل الصحيح */
    private boolean wrongVersusRight;

    /**
     * المنشئ الافتراضي لإنشاء كائن ExamChecker
     * يقوم بتهيئة جميع المتغيرات الأساسية
     */
    public ExamChecker() {
        initialize();
    }

    /**
     * منشئ لإنشاء كائن ExamChecker مع الإجابات الصحيحة والطالب
     *
     * @param correctAnswers قائمة الإجابات الصحيحة
     * @param student الطالب المراد تصحيح إجابته
     */
    public ExamChecker(List<Character> correctAnswers, Student student) {
        initialize();
        this.correctAnswers = correctAnswers;
        this.student = student;
        this.studentAnswers = student.getAnswers();
        students.add(student);
    }

    public ExamChecker(List<Character> correctAnswers) {
        initialize();
        this.correctAnswers = correctAnswers;
    }

    public ExamChecker(List<Character> correctAnswers, List<Character> studentAnswers) {
        initialize();
        this.correctAnswers = correctAnswers;
        this.studentAnswers = studentAnswers;
    }

    public void saveInformation(boolean saveInformation) {
        if (saveInformation) {
            informationWriter();
        }
    }

    private void informationWriter() {
        try {
            // Create a filename based on the current date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String timestamp = dateFormat.format(new Date());
            String filename = "exam_results_" + timestamp + ".txt";

            // Create the file in a dedicated results directory
            File resultsDir = new File("exam_results");
            if (!resultsDir.exists()) {
                resultsDir.mkdirs(); // Create directory if it doesn't exist
            }

            File resultFile = new File(resultsDir, filename);

            // Use try-with-resources to ensure proper file closing
            try (PrintWriter writer = new PrintWriter(new FileWriter(resultFile))) {
                writer.println("=== Comprehensive Exam Results ===");
                writer.println("Generated: " + new Date());
                writer.println("Total Students: " + students.size());
                writer.println("Total Questions: " + (correctAnswers != null ? correctAnswers.size() : "N/A"));
                writer.println("\n");

                // Track overall exam statistics
                int totalStudents = 0;
                double totalScores = 0;

                // Iterate through all students
                for (Student currentStudent : students) {
                    // Temporarily set the current student and their answers
                    this.student = currentStudent;
                    this.studentAnswers = currentStudent.getAnswers();

                    // Ensure answers are checked
                    checkAnswers();

                    // Write individual student results
                    writer.println("--- Student Result ---");
                    writer.println("Student ID: " + currentStudent.getStudentId());
                    writer.println("Student Name: " + currentStudent.getName());
                    writer.println("Correct Answers: " + correctCount);
                    writer.println("Incorrect Answers: " + incorrectCount);
                    writer.printf("Score: %.2f / %.2f%n", getScore(), maxScore);

                    // Write detailed answer breakdown
                    writer.println("\nDetailed Answer Breakdown:");
                    for (int i = 0; i < Math.min(correctAnswers.size(), studentAnswers.size()); i++) {
                        writer.printf("Q%d: C/A = %c, S/A = %c, R = %s%n",
                                i + 1,
                                correctAnswers.get(i),
                                studentAnswers.get(i),
                                alwaysCorrectIndex.contains(i) || correctAnswers.get(i).equals(studentAnswers.get(i)) ?
                                        "True" : "False"
                        );
                    }
                    writer.println("\n=================================");

                    // Accumulate overall statistics
                    totalStudents++;
                    totalScores += getScore();
                }

                // Write overall exam summary
                writer.println("=== Exam Summary ===");
                writer.println("Total Students: " + totalStudents);
                writer.printf("Average Score: %.2f / %.2f%n",
                        totalScores / totalStudents, maxScore);

                // Additional exam mode details
                writer.println("\n--- Exam Configuration ---");
                writer.println("Wrong vs Right Mode: " + (wrongVersusRight ? "Enabled" : "Disabled"));
                writer.println("Valid Answer Options: " + validOptions);

                if (!questionWeights.isEmpty()) {
                    writer.println("\nQuestion Weights:");
                    questionWeights.forEach((index, weight) ->
                            writer.printf("Question %d: Weight = %.2f%n", index + 1, weight)
                    );
                }

            } catch (IOException e) {
                System.err.println("Error writing exam result file: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Unexpected error in information writing: " + e.getMessage());
        }
    }

    /**
     * تهيئة المتغيرات الأساسية للنظام
     * يضمن إعداد القيم الافتراضية بشكل صحيح
     */
    private void initialize() {
        correctAnswers = new ArrayList<>();
        studentAnswers = new ArrayList<>();
        alwaysCorrectIndex = new ArrayList<>();
        questionWeights = new HashMap<>();
        students = new ArrayList<>();
        this.student = null;
        isChecked = false;
        maxScore = 100;
        correctCount = 0;
        incorrectCount = 0;
    }

    /**
     * تعيين وزن لسؤال محدد
     *
     * @param questionIndex مؤشر السؤال
     * @param weight الوزن المخصص للسؤال
     * @throws IllegalStateException إذا كان وضع "الخطأ مقابل الصحيح" مفعلاً
     */
    public void setQuestionWeight(int questionIndex, double weight) {
        if (wrongVersusRight){
            System.err.println("You can't set weights when wrongVersusRight is On\n");
        }
        else {
            questionWeights.put(questionIndex, weight);
            isChecked = false;
        }
    }

    public void nonWeights(boolean mode) {
        if (mode) {
            // Clear the weights when mode is true (disabling weights)
            questionWeights.clear();
            isChecked = false;
        }
        wrongVersusRight = !mode;
    }

    public void setCorrectAnswers(List<Character> correctAnswers) {
        if (correctAnswers == null || correctAnswers.isEmpty()) {
            throw new IllegalArgumentException("Correct answers cannot be null or empty.");
        }
        this.correctAnswers = correctAnswers;
        questionWeights.clear();
        alwaysCorrectIndex.clear();
        isChecked = false;
    }

    public void setStudentAnswers(List<Character> studentAnswers) {
        if (studentAnswers == null || studentAnswers.isEmpty()) {
            throw new IllegalArgumentException("Student answers cannot be null or empty.");
        }

        if (!new HashSet<>(validOptions).containsAll(studentAnswers)) {
            throw new IllegalArgumentException("Answers must be within valid options: " + validOptions);
        }

        if (studentAnswers.size() > correctAnswers.size()) {
            throw new IllegalArgumentException("Number of student answers (" + studentAnswers.size() +
                    ") is greater than number of questions (" + correctAnswers.size() + ").");
        }

        this.studentAnswers = studentAnswers;
        isChecked = false;
    }

    public void setMaxScore(int score) {
        if (score <= 0) {
            throw new IllegalArgumentException("Max score must be a positive number.");
        }
        this.maxScore = score;
        isChecked = false;
    }

    public void setValidOptions(List<Character> options) {
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("Valid options cannot be null or empty.");
        }
        validOptions = new ArrayList<>(options);
        validOptions.add(' ');
    }

    public void setAlwaysCorrectQuestion(int questionIndex) {
        if (questionIndex < 0 || questionIndex >= correctAnswers.size()) {
            throw new IndexOutOfBoundsException("Invalid question index: " + questionIndex);
        }
        if (!alwaysCorrectIndex.contains(questionIndex)) {
            alwaysCorrectIndex.add(questionIndex);
        }
        isChecked = false;
    }

    public void setStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        this.student = student;
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    public int getCorrectCount() {
        checkAnswers();
        return correctCount;
    }

    public int getIncorrectCount() {
        checkAnswers();
        return incorrectCount;
    }

    public double getScore() {
        checkAnswers();
        return Double.parseDouble(String.format("%.2f", calculateScore()));
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public Student getStudentByID(String studentID) {
        return students.stream()
                .filter(s -> s.getStudentId().equals(studentID))
                .findFirst()
                .orElse(null);
    }

    public String getStudentsScores() {
        StringBuilder studentsScores = new StringBuilder();
        for (Student student : students) {
            // Reload student answers for each student
            this.setStudentAnswers(student.getAnswers());
            this.setStudent(student);

            studentsScores.append("Name: ").append(student.getName())
                    .append(" Score: ").append(getScore())
                    .append(" ").append('\n');
        }
        return studentsScores.toString();
    }

    public void addStudent(Student student) {
        if (student != null && !this.students.contains(student)) {
            this.students.add(student);
        }
    }

    public void addStudents(List<Student> students) {
        if (students != null) {
            for (Student student : students) {
                if (student != null && !this.students.contains(student)) {
                    this.students.add(student);
                }
            }
        }
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }

    public void wrongVersusRight(boolean mode) {
        if (mode) {
            checkAnswers(); // Ensure answers are checked before modification

            // Calculate how many wrong answers to convert
            int wrongToEraseTrue = Math.min(incorrectCount / 3, correctCount);

            // Adjust counts carefully
            correctCount = Math.max(0, correctCount - wrongToEraseTrue);
            incorrectCount += wrongToEraseTrue;

            // Mark that wrong versus right mode is active
            wrongVersusRight = true;
            isChecked = false;
        }
    }

    private void verifyAnswersLogic() {
        // Reset counts before verification
        correctCount = 0;
        incorrectCount = 0;
        incorrectIndices = new ArrayList<>();

        // Check answers
        for (int i = 0; i < Math.min(correctAnswers.size(), studentAnswers.size()); i++) {
            if (alwaysCorrectIndex.contains(i) ||
                    (studentAnswers.get(i) != null && correctAnswers.get(i).equals(studentAnswers.get(i)))) {
                correctCount++;
            } else {
                incorrectCount++;
                incorrectIndices.add(i);
            }
        }

        // Add any unanswered questions to incorrect count
        incorrectCount += Math.max(0, correctAnswers.size() - studentAnswers.size());

        isChecked = true;
    }

    private void checkAnswers() {
        if (!isChecked) {
            verifyAnswersLogic();
        }
    }

    /**
     * حساب الدرجة النهائية للطالب
     * يدعم عدة آليات للحساب بما في ذلك الأوزان ووضع "الخطأ مقابل الصحيح"
     *
     * @return الدرجة المحسوبة بدقة قسمتين عشريتين
     */
    private double calculateScore() {
        checkAnswers();

        // حالة عدم وجود أوزان
        if (questionWeights.isEmpty()) {
            if (wrongVersusRight) {
                // خوارزمية الدرجة في وضع الخطأ مقابل الصحيح
                return ((double) Math.max(0, correctCount - (incorrectCount / 3)) / correctAnswers.size()) * maxScore;
            }
            // الحساب الافتراضي للدرجة
            return ((double) correctCount / correctAnswers.size()) * maxScore;
        }

        // حساب الدرجة مع الأوزان
        double totalWeight = 0.0;
        for (int i = 0; i < correctAnswers.size(); i++) {
            totalWeight += questionWeights.getOrDefault(i, 1.0);
        }

        double totalScore = 0.0;

        for (int i = 0; i < correctAnswers.size(); i++) {
            double questionWeight = questionWeights.getOrDefault(i, 1.0);
            double questionScore = (questionWeight / totalWeight) * maxScore;

            if (alwaysCorrectIndex.contains(i) ||
                    (i < studentAnswers.size() && correctAnswers.get(i).equals(studentAnswers.get(i)))) {
                totalScore += questionScore;
            }
        }

        return totalScore;
    }

    // طرق مساعدة لتحسين القراءة والصيانة
    private void appendIncorrectIndices(StringBuilder result) {
        if (!incorrectIndices.isEmpty()) {
            result.append("Incorrect Answer Positions: ");
            for (int index : incorrectIndices) {
                result.append(index + 1).append(" ");
            }
            result.append("\n");
        }
    }

    private void appendQuestionWeights(StringBuilder result) {
        if (!questionWeights.isEmpty()) {
            result.append("Question Weights: ");
            questionWeights.forEach((index, weight) ->
                    result.append("Q").append(index + 1).append(": ").append(weight).append(" "));
            result.append("\n");
        }
    }

    /**
     * طباعة تفصيلية لنتائج الامتحان
     * توفر معلومات شاملة عن أداء الطالب
     *
     * @return سلسلة نصية تحتوي على تفاصيل النتائج
     */
    @Override
    public String toString() {
        checkAnswers();
        StringBuilder result = new StringBuilder();
        result.append("=== Exam Results ===\n");

        // معلومات الطالب
        if (student != null) {
            result.append(student);
        }

        // إحصائيات التصحيح
        result.append("Correct Answers: ").append(correctCount).append("\n");
        result.append("Incorrect Answers: ").append(incorrectCount).append("\n");
        result.append("Final Score: ").append(String.format("%.2f", calculateScore()))
                .append(" / ").append(maxScore).append("\n");

        // تفاصيل إضافية
        appendIncorrectIndices(result);
        appendQuestionWeights(result);

        return result.toString();
    }
}