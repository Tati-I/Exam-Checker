package corrector.exam;

import java.util.List;

public class Student {
    private final String name;
    private final String studentId;
    private final List<Character> answers;

    public Student(String name, String studentId, List<Character> answers) {
        this.name = name;
        this.studentId = studentId;
        this.answers = answers;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public List<Character> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return "Student: " +
                "{name: '" + name + '\'' +
                ", studentId: '" + studentId + '\'' +
                '}'+'\n';
    }
}

