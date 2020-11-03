package tv.codely.student_grades.infrastructure;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class TeachersRepository {

    private final Map<Integer, List<Pair<String, Boolean>>> entries;

    public TeachersRepository() {
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

    public boolean isAnyBenevolent(int year) {
        return entries.get(year).stream().anyMatch(Pair::second);
    }
}
