package tv.codely.student_grades.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tv.codely.student_grades.application.StudentGradeCalculator;
import tv.codely.student_grades.domain.*;
import tv.codely.student_grades.infrastructure.TeachersRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StudentGradeCalculatorTest {
    private TeachersRepository teachersRepository;
    private Student student;
    private StudentGradeCalculator studentGradeCalculator;

    @BeforeEach
    void setUp() {
        teachersRepository = mock(TeachersRepository.class);
        student = mock(Student.class);
    }

    @Test
    void shouldReturn0IfStudentHasNotDoneAnyExam() throws IncorrectExamGradesWeightSum {
        studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        when(student.hasNotDoneAnyExam()).thenReturn(true);
        when(student.hasNotReachedMinimumClasses()).thenReturn(false);

        assertEquals(0, studentGradeCalculator.execute());
    }

    @Test
    void shouldReturn0IfStudentHasNotAttendedMinimumClasses() throws IncorrectExamGradesWeightSum {
        studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        when(student.hasNotDoneAnyExam()).thenReturn(false);
        when(student.hasNotReachedMinimumClasses()).thenReturn(true);

        assertEquals(0, studentGradeCalculator.execute());
    }

    @Test
    void shouldCalculateSameGradeIfStudentDidOneSingleExam() throws IncorrectExamGradesWeightSum {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<ExamGrade> examGrades = getExamsGradesForOneExam();
        when(student.getExamGradesWeighted()).thenReturn(examGrades);

        assertEquals(5, studentGradeCalculator.execute());
    }

    @Test
    void shouldCalculateAverageGradeIfStudentDidDifferentExams() throws IncorrectExamGradesWeightSum {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<ExamGrade> examGrades = getExamGradesForMultipleExams();
        when(student.getExamGradesWeighted()).thenReturn(examGrades);

        assertEquals(5, studentGradeCalculator.execute());
    }

    @Test
    void shouldRoundUpToTwoDecimalsIfOddExamGrades() throws IncorrectExamGradesWeightSum {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<ExamGrade> examGrades = getExamGradesForOddExamGrades();
        when(student.getExamGradesWeighted()).thenReturn(examGrades);

        assertEquals(4.5f, studentGradeCalculator.execute());
    }

    @Test
    void shouldThrowAnExceptionIfAllExamGradesWeightIsBelow100() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<ExamGrade> examGrades = getExamGradesWithWeightBelow100();
        when(student.getExamGradesWeighted()).thenReturn(examGrades);

        assertThrows(IncorrectExamGradesWeightSum.class, () -> {
            studentGradeCalculator.execute();
        });
    }

    @Test
    void shouldThrowAnExceptionIfAllExamGradesWeightIsOver100() throws IncorrectExamGradesWeightSum {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<ExamGrade> examGrades = getExamGradesWithWeightOver100();
        when(student.getExamGradesWeighted()).thenReturn(examGrades);

        assertThrows(IncorrectExamGradesWeightSum.class, () -> {
            studentGradeCalculator.execute();
        });
    }

    @Test
    void shouldIncreaseOneExtraPointIfThereIsAnyBenevolentTeacherInTheYearToCalculateGrades() throws IncorrectExamGradesWeightSum {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2020, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<ExamGrade> examGrades = List.of(getExamGrade(100, 5f));
        when(student.getExamGradesWeighted()).thenReturn(examGrades);

        when(teachersRepository.isAnyBenevolent(2020)).thenReturn(true);

        assertEquals(6, studentGradeCalculator.execute());
    }

    @Test
    void shouldReturnMaximumGradeIfAddingExtraPointMakesGradeHigherThanMaximum() throws IncorrectExamGradesWeightSum {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2020, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();
        final List<ExamGrade> examGrades = List.of(getExamGrade(100, 9.8f));

        when(student.getExamGradesWeighted()).thenReturn(examGrades);
        when(teachersRepository.isAnyBenevolent(2020)).thenReturn(true);

        assertEquals(10, studentGradeCalculator.execute());
    }

    private void studentMockForMinimumClassesReachedAndAnyExamsDone() {
        when(student.hasNotDoneAnyExam()).thenReturn(false);
        when(student.hasNotReachedMinimumClasses()).thenReturn(false);
    }

    private List<ExamGrade> getExamsGradesForOneExam() {
        return List.of(
            getExamGrade(100, 5f)
        );
    }

    private List<ExamGrade> getExamGradesForMultipleExams() {
        return List.of(
            getExamGrade(10, 4f),
            getExamGrade(10, 6f),
            getExamGrade(10, 2f),
            getExamGrade(10, 8f),
            getExamGrade(10, 0f),
            getExamGrade(10, 10f),
            getExamGrade(10, 0f),
            getExamGrade(10, 10f),
            getExamGrade(10, 0f),
            getExamGrade(10, 10f)
        );
    }

    private List<ExamGrade> getExamGradesForOddExamGrades() {
        return List.of(
            getExamGrade(50, 4f),
            getExamGrade(50, 5f)
        );
    }

    private List<ExamGrade> getExamGradesWithWeightBelow100() {
        return List.of(
            getExamGrade(10, 4f),
            getExamGrade(10, 6f)
        );
    }

    private List<ExamGrade> getExamGradesWithWeightOver100() {
        return List.of(
            getExamGrade(90, 4f),
            getExamGrade(20, 6f)
        );
    }

    private ExamGrade getExamGrade(int weight, float grade) {
        return new ExamGrade(new Weight(weight), new Grade(grade));
    }
}
