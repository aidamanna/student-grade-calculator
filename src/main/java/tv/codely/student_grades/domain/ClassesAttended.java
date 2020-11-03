package tv.codely.student_grades.domain;

public class ClassesAttended {

    private int count;

    public ClassesAttended() {
        count = 0;
    }

    public int get() {
        return count;
    }

    public void add() {
        count++;
    }
}
