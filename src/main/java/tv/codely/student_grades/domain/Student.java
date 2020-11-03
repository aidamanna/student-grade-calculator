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

    public List<Exam> getExams() {
        return exams;
    }
}
