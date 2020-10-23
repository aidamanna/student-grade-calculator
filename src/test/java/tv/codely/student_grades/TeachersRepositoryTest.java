package tv.codely.student_grades;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeachersRepositoryTest {

    @Test
    void shouldReturnFalseIfNonOfTheTeachersForThatYearIsBenevolent() {
        TeachersRepository teachersRepository = new TeachersRepository();

        assertFalse(teachersRepository.isAnyBenevolent(2019));
    }

    @Test
    void shouldReturn1IfAnyOfTheTeachersForThatYearIsBenevolent() {
        TeachersRepository teachersRepository = new TeachersRepository();

        assertTrue(teachersRepository.isAnyBenevolent(2020));
    }
}
