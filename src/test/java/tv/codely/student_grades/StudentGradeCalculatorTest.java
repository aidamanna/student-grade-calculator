package tv.codely.student_grades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StudentGradeCalculatorTest {
    private TeachersByYear teachersByYear;
    private Student student;
    private StudentGradeCalculator studentGradeCalculator;

    @BeforeEach
    void setUp() {
        teachersByYear = mock(TeachersByYear.class);
        student = mock(Student.class);
    }

    @Test
    void shouldReturn0IfStudentHasNotDoneAnyExam() {
        studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersByYear, student);

        when(student.hasNotDoneAnyExam()).thenReturn(true);
        when(student.hasNotReachedMinimumClasses()).thenReturn(false);

        assertEquals(0, studentGradeCalculator.execute());
    }

    @Test
    void shouldReturn0IfStudentHasNotAttendedMinimumClasses() {
        studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersByYear, student);

        when(student.hasNotDoneAnyExam()).thenReturn(false);
        when(student.hasNotReachedMinimumClasses()).thenReturn(true);

        assertEquals(0, studentGradeCalculator.execute());
    }

    @Test
    void shouldCalculateSameGradeIfStudentDidOneSingleExam() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersByYear, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<Pair<Integer, Float>> examsGrades = List.of(new Pair<>(100, 5f));
        when(student.examGrades()).thenReturn(examsGrades);

        assertEquals(5, studentGradeCalculator.execute());
    }

    @Test
    void shouldCalculateAverageGradeIfStudentDidDifferentExams() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersByYear, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<Pair<Integer, Float>> examsGrades = List.of(
            new Pair<>(10, 4f),
            new Pair<>(10, 6f),
            new Pair<>(10, 2f),
            new Pair<>(10, 8f),
            new Pair<>(10, 0f),
            new Pair<>(10, 10f),
            new Pair<>(10, 0f),
            new Pair<>(10, 10f),
            new Pair<>(10, 0f),
            new Pair<>(10, 10f)
        );
        when(student.examGrades()).thenReturn(examsGrades);

        assertEquals(5, studentGradeCalculator.execute());
    }

    @Test
    void shouldRoundUpToTwoDecimalsIfOddExamGrades() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersByYear, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<Pair<Integer, Float>> examsGrades = List.of(
            new Pair<>(50, 4f),
            new Pair<>(50, 5f)
        );
        when(student.examGrades()).thenReturn(examsGrades);

        assertEquals(4.5f, studentGradeCalculator.execute());
    }

    @Test
    void shouldReturnMinus2IfAllExamGradesWeightIsBelow100() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersByYear, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<Pair<Integer, Float>> examsGrades = List.of(
            new Pair<>(10, 4f),
            new Pair<>(10, 6f)
        );
        when(student.examGrades()).thenReturn(examsGrades);

        assertEquals(-2, studentGradeCalculator.execute());
    }

    @Test
    void shouldReturnMinus1IfAllExamGradesWeightIsOver100() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersByYear, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<Pair<Integer, Float>> examsGrades = List.of(
            new Pair<>(90, 4f),
            new Pair<>(20, 6f)
        );
        when(student.examGrades()).thenReturn(examsGrades);

        assertEquals(-1, studentGradeCalculator.execute());
    }

    @Test
    void shouldIncreaseOneExtraPointIfThereIsAnyBenevolentTeacherInTheYearToCalculateGrades() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2020, teachersByYear, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<Pair<Integer, Float>> examsGrades = List.of(new Pair<>(100, 5f));
        when(student.examGrades()).thenReturn(examsGrades);

        when(teachersByYear.isAnyBenevolent(2020)).thenReturn(true);

        assertEquals(6, studentGradeCalculator.execute());
    }

    @Test
    void shouldReturnMaximumGradeIfAddingExtraPointMakesGradeHigherThanMaximum() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2020, teachersByYear, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();
        final List<Pair<Integer, Float>> examsGrades = List.of(new Pair<>(100, 9.8f));

        when(student.examGrades()).thenReturn(examsGrades);
        when(teachersByYear.isAnyBenevolent(2020)).thenReturn(true);

        assertEquals(10, studentGradeCalculator.execute());
    }

    private void studentMockForMinimumClassesReachedAndAnyExamsDone() {
        when(student.hasNotDoneAnyExam()).thenReturn(false);
        when(student.hasNotReachedMinimumClasses()).thenReturn(false);
    }
}
