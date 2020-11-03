package tv.codely.student_grades.domain;

public class Exam {

    private final Weight weight;
    private final Grade grade;

    public Exam(Weight weight, Grade grade) {
        this.weight = weight;
        this.grade = grade;
    }

    public int getWeight() {
        return weight.getValue();
    }

    public float getGrade() {
        return grade.getValue();
    }
}
