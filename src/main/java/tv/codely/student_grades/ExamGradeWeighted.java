package tv.codely.student_grades;

public class ExamGradeWeighted {

    private final ExamWeight weight;
    private final ExamGrade grade;

    public ExamGradeWeighted(ExamWeight weight, ExamGrade grade) {
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
