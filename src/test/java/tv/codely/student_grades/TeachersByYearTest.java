package tv.codely.student_grades;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeachersByYearTest {

    @Test
    void shouldReturn0IfNonOfTheTeachersForThatYearIsBenevolent() {
        TeachersByYear teachersByYear = new TeachersByYear();

        assertEquals(0, teachersByYear.increaseInGrade(2019));
    }

    @Test
    void shouldReturn1IfAnyOfTheTeachersForThatYearIsBenevolent() {
        TeachersByYear teachersByYear = new TeachersByYear();

        assertEquals(1, teachersByYear.increaseInGrade(2020));
    }
}
