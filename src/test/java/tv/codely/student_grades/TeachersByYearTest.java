package tv.codely.student_grades;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeachersByYearTest {

    @Test
    void shouldReturnFalseIfNonOfTheTeachersForThatYearIsBenevolent() {
        TeachersByYear teachersByYear = new TeachersByYear();

        assertFalse(teachersByYear.isAnyBenevolent(2019));
    }

    @Test
    void shouldReturn1IfAnyOfTheTeachersForThatYearIsBenevolent() {
        TeachersByYear teachersByYear = new TeachersByYear();

        assertTrue(teachersByYear.isAnyBenevolent(2020));
    }
}
