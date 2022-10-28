package jarvis.model;

import static jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;

/**
 * Represents a Student in the student book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {
    // Comparator
    public static final Comparator<Student> NAME_COMPARATOR = Comparator.comparing(s -> s.getName().toString());

    // Identity fields
    private final StudentName studentName;
    private final MatricNum matricNum;

    // Data fields
    private final GradeProfile gradeProfile;

    /**
     * Every field must be present and not null.
     */
    public Student(StudentName studentName, MatricNum matricNum, GradeProfile gradeProfile) {
        requireAllNonNull(studentName, matricNum, gradeProfile);
        this.studentName = studentName;
        this.matricNum = matricNum;
        this.gradeProfile = gradeProfile;
    }

    public StudentName getName() {
        return studentName;
    }

    public MatricNum getMatricNum() {
        return matricNum;
    }

    public GradeProfile getGradeProfile() {
        return gradeProfile;
    }

    /**
     * Updates the student's result for the given assessment.
     */
    public void updateMark(Assessment assessment, int marks) {
        gradeProfile.grade(assessment, marks);
    }

    public void updateGrades(GradeProfile gp) {
        gradeProfile.updateGrades(gp);
    }


    /**
     * Returns true if both students have the same matriculation number.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return otherStudent.getMatricNum().equals(getMatricNum());
    }

    @Override
    public int hashCode() {
        return matricNum.hashCode();
    }

    @Override
    public String toString() {
        return getName().fullName;
    }
}
