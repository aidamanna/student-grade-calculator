package tv.codely.student_grades;

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
        final ExamGradeWeighted examGradeWeighted =
            new ExamGradeWeighted(new ExamWeight(100), new ExamGrade(5f));
        student.addExamGrade(examGradeWeighted);

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
