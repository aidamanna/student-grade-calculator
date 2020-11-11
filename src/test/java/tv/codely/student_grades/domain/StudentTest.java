package tv.codely.student_grades.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
    }

    @Nested
    class HasNotDoneAnyExam {
        @Test
        void shouldReturnTrueIfItHasNotDoneAnyExam() {
            assertTrue(student.hasNotDoneAnyExam());
        }

        @Test
        void shouldReturnFalseIfItHasDoneAnyExam() {
            final Exam exam = new Exam(new Weight(100), new Grade(5f));
            student.addExam(exam);

            assertFalse(student.hasNotDoneAnyExam());
        }
    }

    @Nested
    class HasNotReachedMinimumClasses {
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

    @Test
    void shouldReturnExamsWeightSum() {
        final Exam anExam = new Exam(new Weight(40), new Grade(8f));
        final Exam anotherExam = new Exam(new Weight(60), new Grade(5f));
        student.addExam(anExam);
        student.addExam(anotherExam);

        assertEquals(100, student.examsWeightSum());
    }

    @Test
    void shouldReturnExamGradesSum() {
        final Exam anExam = new Exam(new Weight(40), new Grade(8f));
        final Exam anotherExam = new Exam(new Weight(60), new Grade(5f));
        student.addExam(anExam);
        student.addExam(anotherExam);

        assertEquals(6.2f, student.examGradesSum());
    }

    //round when odd exam grades
}
