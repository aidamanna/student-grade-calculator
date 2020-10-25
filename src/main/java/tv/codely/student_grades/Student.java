package tv.codely.student_grades;

import java.util.ArrayList;
import java.util.List;

public class Student {
    public static final int MINIMUM_CLASSES_TO_ATTEND = 1;

    private final List<ExamGradeWeighted> examGrades;
    private final ClassesAttended classesAttended;

    public Student() {
        examGrades = new ArrayList<>();
        classesAttended = new ClassesAttended();
    }

    public void addExamGrade(ExamGradeWeighted examGrade) {
        examGrades.add(examGrade);
    }

    public void attendedClass() {
        classesAttended.add();
    }

    public boolean hasNotDoneAnyExam() {
        return examGrades.isEmpty();
    }

    public boolean hasNotReachedMinimumClasses() {
        return classesAttended.get() < MINIMUM_CLASSES_TO_ATTEND;
    }

    public List<ExamGradeWeighted> getExamGradesWeighted() {
        return examGrades;
    }
}
