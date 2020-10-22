package tv.codely.student_grades;

import java.util.List;

public class StudentGradeCalculator {
    public static final float MAX_GRADE_SUM = 10f;
    private final TeachersByYear teachersByYear;
    private final Student student;
    private final int yearToCalculate;

    public StudentGradeCalculator(final int yearToCalculate, TeachersByYear teachersByYear, Student student) {
        this.teachersByYear = teachersByYear;
        this.student = student;
        this.yearToCalculate = yearToCalculate;
    }

    public float execute() {
        if (student.hasNotDoneAnyExam() || student.hasNotReachedMinimumClasses()) {
            return 0f;
        }

        int gradesWeightSum = gradesWeightSum(student.examGrades());

        if (gradesWeightSum > 100) {
            return -1f;
        }

        if (gradesWeightSum < 100){
            return -2f;
        }

        return gradeSumWithExtraPointIfApplies();
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

    private float gradeSumWithExtraPointIfApplies() {
        int extraPointToAdd = teachersByYear.isAnyBenevolent(yearToCalculate) ? 1 : 0;
        float gradesSum = gradesSum(student.examGrades());

        return Float.min(MAX_GRADE_SUM, gradesSum + extraPointToAdd);
    }
}
