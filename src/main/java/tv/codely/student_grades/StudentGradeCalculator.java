package tv.codely.student_grades;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentGradeCalculator {
    public static final float MAX_GRADE_SUM = 10f;

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

        int gradesWeightSum = gradesWeightSum(examsGrades);

        if (gradesWeightSum > 100) {
            return -1f;
        }

        if (gradesWeightSum < 100){
            return -2f;
        }

        float gradesSum = gradesSum(examsGrades);

        return Float.min(MAX_GRADE_SUM, gradesSum + calculateExtraPointToIncrease());
    }

    private int gradesWeightSum(List<Pair<Integer, Float>> examsGrades) {
        return examsGrades.stream()
            .map(Pair::first)
            .reduce(0, Integer::sum);
    }

    private float gradesSum(List<Pair<Integer, Float>> examsGrades) {
        return examsGrades.stream()
            .map(examGrade -> (examGrade.first() * examGrade.second() / 100))
            .reduce(0f, Float::sum);
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
