package tv.codely.student_grades.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentTest {

    Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
    }

    @Test
    void shouldReturnTrueIfItHasNotDoneAnyExam() {
        assertTrue(student.hasNotDoneAnyExam());
    }

    @Test
    void shouldReturnFalseIfItHasDoneAnyExam() {
        final Exam exam =
            new Exam(new Weight(100), new Grade(5f));
        student.addExam(exam);

        assertFalse(student.hasNotDoneAnyExam());
    }

    @Test
    void shouldReturnTrueIfItHasNotReachedMinimumClasses() {
        assertTrue(student.hasNotReachedMinimumClasses());
    }

    @Test
    void shouldReturnFalseIfItHasReachedMinimumClasses() {
        student.attendedClass();

        assertFalse(student.hasNotReachedMinimumClasses());
    }
}
