package tv.codely.student_grades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void shouldReturn0IfStudentHasNotDoneAnyExam() {
        studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        when(student.hasNotDoneAnyExam()).thenReturn(true);
        when(student.hasNotReachedMinimumClasses()).thenReturn(false);

        assertEquals(0, studentGradeCalculator.execute());
    }

    @Test
    void shouldReturn0IfStudentHasNotAttendedMinimumClasses() {
        studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        when(student.hasNotDoneAnyExam()).thenReturn(false);
        when(student.hasNotReachedMinimumClasses()).thenReturn(true);

        assertEquals(0, studentGradeCalculator.execute());
    }

    @Test
    void shouldCalculateSameGradeIfStudentDidOneSingleExam() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<ExamGradeWeighted> examGradesWeighted = getExamsGradesForOneExam();
        when(student.getExamGradesWeighted()).thenReturn(examGradesWeighted);

        assertEquals(5, studentGradeCalculator.execute());
    }

    @Test
    void shouldCalculateAverageGradeIfStudentDidDifferentExams() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<ExamGradeWeighted> examGradesWeighted = getExamGradesWeightedForMultipleExams();
        when(student.getExamGradesWeighted()).thenReturn(examGradesWeighted);

        assertEquals(5, studentGradeCalculator.execute());
    }

    @Test
    void shouldRoundUpToTwoDecimalsIfOddExamGrades() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<ExamGradeWeighted> examGradesWeighted = getExamGradesWeightedForOddExamGrades();
        when(student.getExamGradesWeighted()).thenReturn(examGradesWeighted);

        assertEquals(4.5f, studentGradeCalculator.execute());
    }

    @Test
    void shouldReturnMinus2IfAllExamGradesWeightIsBelow100() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<ExamGradeWeighted> examGradesWeighted = getExamGradeWithWeightBelow100();
        when(student.getExamGradesWeighted()).thenReturn(examGradesWeighted);

        assertEquals(-2, studentGradeCalculator.execute());
    }

    @Test
    void shouldReturnMinus1IfAllExamGradesWeightIsOver100() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<ExamGradeWeighted> examGradesWeighted = getExamGradeWithWeightOver100();
        when(student.getExamGradesWeighted()).thenReturn(examGradesWeighted);

        assertEquals(-1, studentGradeCalculator.execute());
    }

    @Test
    void shouldIncreaseOneExtraPointIfThereIsAnyBenevolentTeacherInTheYearToCalculateGrades() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2020, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();

        final List<ExamGradeWeighted> examGradesWeighted = List.of(getExamGrade(100, 5f));
        when(student.getExamGradesWeighted()).thenReturn(examGradesWeighted);

        when(teachersRepository.isAnyBenevolent(2020)).thenReturn(true);

        assertEquals(6, studentGradeCalculator.execute());
    }

    @Test
    void shouldReturnMaximumGradeIfAddingExtraPointMakesGradeHigherThanMaximum() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2020, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();
        final List<ExamGradeWeighted> examGradesWeighted = List.of(getExamGrade(100, 9.8f));

        when(student.getExamGradesWeighted()).thenReturn(examGradesWeighted);
        when(teachersRepository.isAnyBenevolent(2020)).thenReturn(true);

        assertEquals(10, studentGradeCalculator.execute());
    }

    private void studentMockForMinimumClassesReachedAndAnyExamsDone() {
        when(student.hasNotDoneAnyExam()).thenReturn(false);
        when(student.hasNotReachedMinimumClasses()).thenReturn(false);
    }

    private List<ExamGradeWeighted> getExamsGradesForOneExam() {
        return List.of(
            getExamGrade(100, 5f)
        );
    }

    private List<ExamGradeWeighted> getExamGradesWeightedForMultipleExams() {
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

    private List<ExamGradeWeighted> getExamGradesWeightedForOddExamGrades() {
        return List.of(
            getExamGrade(50, 4f),
            getExamGrade(50, 5f)
        );
    }

    private List<ExamGradeWeighted> getExamGradeWithWeightBelow100() {
        return List.of(
            getExamGrade(10, 4f),
            getExamGrade(10, 6f)
        );
    }

    private List<ExamGradeWeighted> getExamGradeWithWeightOver100() {
        return List.of(
            getExamGrade(90, 4f),
            getExamGrade(20, 6f)
        );
    }

    private ExamGradeWeighted getExamGrade(int weight, float grade) {
        return new ExamGradeWeighted(new ExamWeight(weight), new ExamGrade(grade));
    }
}
