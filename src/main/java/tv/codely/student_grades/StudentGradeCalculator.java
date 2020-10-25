package tv.codely.student_grades;

import java.util.List;

public class StudentGradeCalculator {
    public static final float MAX_GRADE_SUM = 10f;
    private final TeachersRepository teachersRepository;
    private final Student student;
    private final int yearToCalculate;

    public StudentGradeCalculator(final int yearToCalculate, TeachersRepository teachersRepository, Student student) {
        this.teachersRepository = teachersRepository;
        this.student = student;
        this.yearToCalculate = yearToCalculate;
    }

    public float execute() {
        if (student.hasNotDoneAnyExam() || student.hasNotReachedMinimumClasses()) {
            return 0f;
        }

        int gradesWeightSum = gradesWeightSum(student.getExamGradesWeighted());

        if (gradesWeightSum > 100) {
            return -1f;
        }

        if (gradesWeightSum < 100){
            return -2f;
        }

        return gradeSumWithExtraPointIfApplies();
    }

    private int gradesWeightSum(List<ExamGrade> examGradesWeighted) {
        return examGradesWeighted.stream()
            .map(examGrade -> examGrade.getWeight())
            .reduce(0, Integer::sum);
    }

    private float gradeSumWithExtraPointIfApplies() {
        int extraPointToAdd = teachersRepository.isAnyBenevolent(yearToCalculate) ? 1 : 0;
        float gradesSum = gradesSum(student.getExamGradesWeighted());

        return Float.min(MAX_GRADE_SUM, gradesSum + extraPointToAdd);
    }

    private float gradesSum(List<ExamGrade> examGrades) {
        return examGrades.stream()
            .map(examGrade -> (examGrade.getWeight() * examGrade.getGrade() / 100))
            .reduce(0f, Float::sum);
    }
}
