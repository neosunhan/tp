package jarvis.logic.commands;

import static jarvis.commons.util.CollectionUtil.requireAllNonNull;
import static jarvis.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static jarvis.logic.parser.CliSyntax.PREFIX_NOTE;
import static jarvis.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static jarvis.model.Model.PREDICATE_SHOW_ALL_LESSONS;
import static java.util.Objects.requireNonNull;

import java.util.List;

import jarvis.commons.core.Messages;
import jarvis.commons.core.index.Index;
import jarvis.logic.commands.exceptions.CommandException;
import jarvis.model.Lesson;
import jarvis.model.Model;
import jarvis.model.Student;

/**
 * Adds a note to a lesson.
 * Note added can be for the lesson or for a specific student in the lesson if the student is specified.
 * The student is identified using its displayed index from the student book.
 * The lesson is identified using its displayed index from the lesson book.
 */
public class AddNoteCommand extends Command {

    public static final String COMMAND_WORD = "addnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a note to a lesson or for a specific student in a lesson if optional student index is specified."
            + "The student and lesson are identified by their respective index number used in the displayed student "
            + "list and in the displayed lesson list.\n"
            + "Parameters: "
            + PREFIX_NOTE + "NOTE "
            + PREFIX_LESSON_INDEX + "LESSON INDEX "
            + "[" + PREFIX_STUDENT_INDEX + "STUDENT_INDEX] \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NOTE + "Get back to jeff on streams "
            + PREFIX_LESSON_INDEX + "1 " + PREFIX_STUDENT_INDEX + "2";

    public static final String MESSAGE_ADD_OVERALL_NOTE_SUCCESS = "Noted for lesson %1$s: %2$s";
    public static final String MESSAGE_ADD_STUDENT_NOTE_SUCCESS = "Noted for %1$s in lesson %2$s: %3$s";

    private final Index lessonIndex;
    private final Index studentIndex;
    private final String note;

    /**
     * Creates an AddNoteCommand to add a note to a lesson or to a specified student in the lesson.
     */
    public AddNoteCommand(Index lessonIndex, Index studentIndex, String note) {
        requireAllNonNull(lessonIndex, note);
        this.lessonIndex = lessonIndex;
        this.studentIndex = studentIndex;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Lesson> lastShownLessonList = model.getFilteredLessonList();
        if (lessonIndex.getZeroBased() >= lastShownLessonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }
        Lesson lessonToAdd = lastShownLessonList.get(lessonIndex.getZeroBased());
        String successMessage;

        if (studentIndex != null) {
            List<Student> lastShownStudentList = model.getFilteredStudentList();
            if (studentIndex.getZeroBased() >= lastShownStudentList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            }
            Student studentToAdd = lastShownStudentList.get(studentIndex.getZeroBased());
            lessonToAdd.addStudentNotes(note, studentToAdd);
            successMessage = String.format(MESSAGE_ADD_STUDENT_NOTE_SUCCESS, studentToAdd, lessonToAdd, note);
        } else {
            lessonToAdd.addOverallNotes(note);
            successMessage = String.format(MESSAGE_ADD_OVERALL_NOTE_SUCCESS, lessonToAdd, note);
        }
        model.setLesson(lessonToAdd, lessonToAdd);
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        return new CommandResult(successMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        }

        if (!(other instanceof AddNoteCommand)) { // instanceof handles nulls
            return false;
        }

        AddNoteCommand otherAddNote = (AddNoteCommand) other;

        boolean studentIndexEquality;
        if (studentIndex == null) {
            studentIndexEquality = otherAddNote.studentIndex == null;
        } else {
            studentIndexEquality = studentIndex.equals(otherAddNote.studentIndex);
        }
        return lessonIndex.equals(otherAddNote.lessonIndex)
                && studentIndexEquality
                && note.equals(otherAddNote.note);
    }
}