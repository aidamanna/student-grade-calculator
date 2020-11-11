package tv.codely.student_grades.domain;

import java.util.ArrayList;
import java.util.List;

public class Student {
    public static final int MINIMUM_CLASSES_TO_ATTEND = 1;

    private final List<Exam> exams;
    private final ClassesAttended classesAttended;

    public Student() {
        exams = new ArrayList<>();
        classesAttended = new ClassesAttended();
    }

    public void addExam(Exam exam) {
        exams.add(exam);
    }

    public void attendedClass() {
        classesAttended.add();
    }

    public boolean hasNotDoneAnyExam() {
        return exams.isEmpty();
    }

    public boolean hasNotReachedMinimumClasses() {
        return classesAttended.get() < MINIMUM_CLASSES_TO_ATTEND;
    }

    public int examsWeightSum() {
        return exams.stream()
            .map(exam -> exam.getWeight())
            .reduce(0, Integer::sum);
    }

    public float examGradesSum() {
        return exams.stream()
            .map(exam -> (exam.getWeight() * exam.getGrade() / 100))
            .reduce(0f, Float::sum);
    }
}
