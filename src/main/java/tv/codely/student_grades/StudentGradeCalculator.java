package tv.codely.student_grades;

import java.util.List;

public class StudentGradeCalculator {
    public static final float MAX_GRADE_SUM = 10f;
    private final int yearToCalculate;
    private final TeachersByYear teachersByYear;

    public StudentGradeCalculator(final int yearToCalculate, TeachersByYear teachersByYear) {
        this.yearToCalculate = yearToCalculate;
        this.teachersByYear = teachersByYear;
    }

    public float calculateGrades(final List<Pair<Integer, Float>> examsGrades, final boolean hasReachedMinimumClasses) {
        if (examsGrades.isEmpty() || !hasReachedMinimumClasses) {
            return 0f;
        }

        int gradesWeightSum = gradesWeightSum(examsGrades);

        if (gradesWeightSum > 100) {
            return -1f;
        }

        if (gradesWeightSum < 100){
            return -2f;
        }

        float gradesSum = gradesSum(examsGrades);

        return Float.min(MAX_GRADE_SUM, gradesSum + teachersByYear.increaseInGrade(yearToCalculate));
    }

    private int gradesWeightSum(List<Pair<Integer, Float>> examsGrades) {
        return examsGrades.stream()
            .map(Pair::first)
            .reduce(0, Integer::sum);
    }

    private float gradesSum(List<Pair<Integer, Float>> examsGrades) {
        return examsGrades.stream()
            .map(examGrade -> (examGrade.first() * examGrade.second() / 100))
            .reduce(0f, Float::sum);
    }
}
