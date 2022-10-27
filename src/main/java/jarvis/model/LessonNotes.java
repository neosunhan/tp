package jarvis.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeMap;

import jarvis.model.exceptions.NoteNotFoundException;
import jarvis.model.exceptions.StudentNotFoundException;

/**
 * Represents the notes for a lesson in JARVIS.
 */
public class LessonNotes {
    private final ArrayList<String> generalNotes;
    private final TreeMap<Student, ArrayList<String>> studentNotes;

    /**
     * Creates the notes for a lesson.
     * @param students Students who are involved in the lesson.
     */
    public LessonNotes(Collection<Student> students) {
        studentNotes = new TreeMap<>(Comparator.comparing(s -> s.getName().toString()));
        generalNotes = new ArrayList<>();
        for (Student stu : students) {
            studentNotes.put(stu, new ArrayList<>());
        }
    }

    /**
     * Adds on to the overall lesson notes.
     * @param notes Lines to append to the overall lesson notes.
     */
    public void addNote(String notes) {
        generalNotes.add(notes);
    }

    /**
     * Adds a note regarding a specific student.
     * @param student Student that is involved in the lesson.
     * @param notes Note to add for the student.
     */
    public void addNote(Student student, String notes) {
        if (!studentNotes.containsKey(student)) {
            throw new StudentNotFoundException();
        }
        studentNotes.get(student).add(notes);
    }

    /**
     * Deletes note at given index from overall notes.
     *
     * @param index Index of the note according to the list of overall notes.
     * @return String of the deleted note.
     */
    public String deleteNote(int index) {
        if (index >= generalNotes.size()) {
            throw new NoteNotFoundException();
        }
        return generalNotes.remove(index);
    }

    /**
     * Deletes note at given index of from a specific student's notes.
     *
     * @param student Student to delete notes from.
     * @param index Index of the note according to the list of student notes.
     * @return String of the deleted note.
     */
    public String deleteNote(Student student, int index) {
        if (!studentNotes.containsKey(student)) {
            throw new StudentNotFoundException();
        }
        ArrayList<String> specifiedStudentNotes = studentNotes.get(student);
        if (index >= specifiedStudentNotes.size()) {
            throw new NoteNotFoundException();
        }
        return specifiedStudentNotes.remove(index);
    }

    public String getGeneralNotes() {
        StringBuilder formattedGeneralNotes = new StringBuilder("Lesson Notes:\n");
        int index = 0;
        for (String generalNote: generalNotes) {
            formattedGeneralNotes.append(++index + ". ");
            formattedGeneralNotes.append(generalNote);
            formattedGeneralNotes.append("\n");
        }
        return formattedGeneralNotes.toString();
    }

    public String getStudentNotes(Student student) {
        if (!studentNotes.containsKey(student)) {
            throw new StudentNotFoundException();
        }
        StringBuilder formattedStudentNotes = new StringBuilder();
        int index = 0;
        for (String studentNote: studentNotes.get(student)) {
            formattedStudentNotes.append(++index + ". ");
            formattedStudentNotes.append(studentNote);
            formattedStudentNotes.append("\n");
        }
        return formattedStudentNotes.toString();
    }

    public String getAllNotes() {
        StringBuilder formattedAllNotes = new StringBuilder(getGeneralNotes());
        formattedAllNotes.append("\nNotes for individual students:\n");
        for (Student student: studentNotes.keySet()) {
            formattedAllNotes.append(student.toString());
            formattedAllNotes.append(":\n");
            formattedAllNotes.append(getStudentNotes(student));
            formattedAllNotes.append("\n");
        }
        return formattedAllNotes.toString();
    }

    public void setStudent(Student targetStudent, Student editedStudent) {
        ArrayList<String> tempNotes = studentNotes.get(targetStudent);
        assert tempNotes != null;
        studentNotes.remove(targetStudent);
        studentNotes.put(editedStudent, tempNotes);
    }

    @Override
    public String toString() {
        return getAllNotes();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LessonNotes)) {
            return false;
        }

        LessonNotes otherLessonNotes = (LessonNotes) other;
        return otherLessonNotes.getAllNotes().equals(getAllNotes());
    }

    @Override
    public int hashCode() {
        return getAllNotes().hashCode();
    }
}
