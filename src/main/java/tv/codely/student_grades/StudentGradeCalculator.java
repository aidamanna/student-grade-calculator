package tv.codely.student_grades;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class StudentGradeCalculator {
    final private Map<Integer, List<Pair<String, Boolean>>> allYearsTeachers = Map.ofEntries(
        new AbstractMap.SimpleImmutableEntry<>(
            2020,
            List.of(
                new Pair<>("Josefina", true),
                new Pair<>("Edonisio", true),
                new Pair<>("Edufasio", false)
            )
        ),
        new AbstractMap.SimpleImmutableEntry<>(
            2019,
            List.of(
                new Pair<>("Eduarda", false),
                new Pair<>("Abelardo", false),
                new Pair<>("Francisca", false)
            )
        )
    );
    private final int yearToCalculate;

    public StudentGradeCalculator(final int yearToCalculate) {
        this.yearToCalculate = yearToCalculate;
    }

    public float calculateGrades(final List<Pair<Integer, Float>> examsGrades, final boolean hasReachedMinimumClasses) {
        if (examsGrades.isEmpty() || !hasReachedMinimumClasses) {
            return 0f;
        }

        int gradesWeightSum = 0;
        float gradesSum = 0f;

        for (Pair<Integer, Float> examGrade : examsGrades) {
            gradesSum += (examGrade.first() * examGrade.second() / 100);
            gradesWeightSum += examGrade.first();
        }

        if (gradesWeightSum > 100) {
            return -1f;
        }

        if (gradesWeightSum < 100){
            return -2f;
        }

        return Float.min(10f, gradesSum + calculateExtraPointToIncrease());
    }

    private int calculateExtraPointToIncrease() {
        int extraPointToIncrease = 0;

        boolean hasExtraPoint = allYearsTeachers.get(yearToCalculate)
            .stream().anyMatch(teacher -> teacher.second() == true);

        if (hasExtraPoint) {
            extraPointToIncrease = 1;
        }

        return extraPointToIncrease;
    }
}
