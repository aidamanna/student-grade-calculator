package tv.codely.student_grades;

public class ExamGrade {

    private final Weight weight;
    private final Grade grade;

    public ExamGrade(Weight weight, Grade grade) {
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
