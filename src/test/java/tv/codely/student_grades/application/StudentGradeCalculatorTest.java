package tv.codely.student_grades.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void shouldReturn0IfStudentHasNotDoneAnyExam() throws ExamsWeightSumIncorrect {
        studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        when(student.hasNotDoneAnyExam()).thenReturn(true);
        when(student.hasNotReachedMinimumClasses()).thenReturn(false);

        assertEquals(0, studentGradeCalculator.execute());
    }

    @Test
    void shouldReturn0IfStudentHasNotAttendedMinimumClasses() throws ExamsWeightSumIncorrect {
        studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        when(student.hasNotDoneAnyExam()).thenReturn(false);
        when(student.hasNotReachedMinimumClasses()).thenReturn(true);

        assertEquals(0, studentGradeCalculator.execute());
    }

    @Test
    void shouldThrowAnExceptionIfAllExamGradesWeightIsBelow100() {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();
        when(student.examsWeightSum()).thenReturn(80);


        assertThrows(ExamsWeightSumIncorrect.class, () -> {
            studentGradeCalculator.execute();
        });
    }

    @Test
    void shouldThrowAnExceptionIfAllExamGradesWeightIsOver100() throws ExamsWeightSumIncorrect {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();
        studentMockForMinimumClassesReachedAndAnyExamsDone();
        when(student.examsWeightSum()).thenReturn(110);

        assertThrows(ExamsWeightSumIncorrect.class, () -> {
            studentGradeCalculator.execute();
        });
    }

    @Test
    void shouldCalculateSameGradeWhenNoExtraPointApplies() throws ExamsWeightSumIncorrect {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2019, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();
        when(student.examsWeightSum()).thenReturn(100);
        when(student.examGradesSum()).thenReturn(7f);

        assertEquals(7f, studentGradeCalculator.execute());
    }

    @Test
    void shouldIncreaseOneExtraPointIfThereIsAnyBenevolentTeacherInTheYearToCalculateGrades() throws ExamsWeightSumIncorrect {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2020, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();
        when(student.examsWeightSum()).thenReturn(100);
        when(student.examGradesSum()).thenReturn(7.6f);

        when(teachersRepository.isAnyBenevolent(2020)).thenReturn(true);

        assertEquals(8.6f, studentGradeCalculator.execute());
    }

    @Test
    void shouldReturnMaximumGradeIfAddingExtraPointMakesGradeHigherThanMaximum() throws ExamsWeightSumIncorrect {
        StudentGradeCalculator studentGradeCalculator =
            new StudentGradeCalculator(2020, teachersRepository, student);

        studentMockForMinimumClassesReachedAndAnyExamsDone();
        studentMockForMinimumClassesReachedAndAnyExamsDone();
        when(student.examsWeightSum()).thenReturn(100);
        when(student.examGradesSum()).thenReturn(9.5f);

        when(teachersRepository.isAnyBenevolent(2020)).thenReturn(true);

        assertEquals(10, studentGradeCalculator.execute());
    }

    private void studentMockForMinimumClassesReachedAndAnyExamsDone() {
        when(student.hasNotDoneAnyExam()).thenReturn(false);
        when(student.hasNotReachedMinimumClasses()).thenReturn(false);
    }

    private List<Exam> getExamGradesWithWeightBelow100() {
        return List.of(
            getExamGrade(10, 4f),
            getExamGrade(10, 6f)
        );
    }

    private List<Exam> getExamGradesWithWeightOver100() {
        return List.of(
            getExamGrade(90, 4f),
            getExamGrade(20, 6f)
        );
    }

    private Exam getExamGrade(int weight, float grade) {
        return new Exam(new Weight(weight), new Grade(grade));
    }
}
