package tv.codely.student_grades;

import java.util.ArrayList;
import java.util.List;

public class Student {
    public static final int MINIMUM_CLASSES_TO_ATTEND = 1;

    private List<Pair<Integer, Float>> examGrades;
    private int classesAttended;

    public Student() {
        examGrades = new ArrayList<>();
        classesAttended = 0;
    }

    public void addExamGrade(Pair<Integer, Float> examGrade) {
        examGrades.add(examGrade);
    }

    public void attendedClass() {
        classesAttended++;
    }

    public boolean hasNotDoneAnyExam() {
        return examGrades.isEmpty();
    }

    public boolean hasNotReachedMinimumClasses() {
        return classesAttended < MINIMUM_CLASSES_TO_ATTEND;
    }

    public List<Pair<Integer, Float>> examGrades() {
        return null;
    }
}
