package tv.codely.student_grades.application;

import tv.codely.student_grades.domain.Exam;
import tv.codely.student_grades.domain.ExamsWeightSumIncorrect;
import tv.codely.student_grades.domain.Student;
import tv.codely.student_grades.infrastructure.TeachersRepository;

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

    public float execute() throws ExamsWeightSumIncorrect {
        if (student.hasNotDoneAnyExam() || student.hasNotReachedMinimumClasses()) {
            return 0f;
        }

        int examsWeightSum = examsWeightSum(student.getExams());

        if (examsWeightSum > 100) {
            throw new ExamsWeightSumIncorrect("Exam grades weight sum is above 100");
        }

        if (examsWeightSum < 100){
            throw new ExamsWeightSumIncorrect("Exam grades weight sum is below 100");
        }

        return examGradesSumWithExtraPointIfApplies();
    }

    private int examsWeightSum(List<Exam> exams) {
        return exams.stream()
            .map(exam -> exam.getWeight())
            .reduce(0, Integer::sum);
    }

    private float examGradesSumWithExtraPointIfApplies() {
        int extraPointToAdd = teachersRepository.isAnyBenevolent(yearToCalculate) ? 1 : 0;
        float gradesSum = examGradesSum(student.getExams());

        return Float.min(MAX_GRADE_SUM, gradesSum + extraPointToAdd);
    }

    private float examGradesSum(List<Exam> exams) {
        return exams.stream()
            .map(exam -> (exam.getWeight() * exam.getGrade() / 100))
            .reduce(0f, Float::sum);
    }
}
