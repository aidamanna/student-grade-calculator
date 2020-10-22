package tv.codely.student_grades;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class TeachersByYear {

    private final Map<Integer, List<Pair<String, Boolean>>> entries;

    public TeachersByYear() {
        entries = Map.ofEntries(
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
    }

    public int increaseInGrade(int year) {
        return entries.get(year).stream().anyMatch(Pair::second) ? 1 : 0;
    }
}
