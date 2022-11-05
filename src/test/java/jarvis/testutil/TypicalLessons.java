package jarvis.testutil;

import static jarvis.testutil.TypicalStudents.ALICE;
import static jarvis.testutil.TypicalStudents.BENSON;
import static jarvis.testutil.TypicalStudents.getTypicalStudents;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jarvis.model.Consult;
import jarvis.model.Lesson;
import jarvis.model.LessonBook;
import jarvis.model.LessonDesc;
import jarvis.model.MasteryCheck;
import jarvis.model.Student;
import jarvis.model.Studio;
import jarvis.model.TimePeriod;

/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLessons {
    public static final LessonDesc STUDIO_DESCRIPTION_1 = new LessonDesc("Studio 1");
    public static final LessonDesc STUDIO_DESCRIPTION_2 = new LessonDesc("Studio 2: Recursion");
    public static final LessonDesc CONSULT_DESCRIPTION_1 = new LessonDesc("Recursion Consultation");
    public static final LessonDesc CONSULT_DESCRIPTION_2 = new LessonDesc("Streams Consultation");
    public static final LessonDesc MASTERY_CHECK_DESCRIPTION_1 = new LessonDesc("Mastery Check 1");
    public static final LessonDesc MASTERY_CHECK_DESCRIPTION_2 = new LessonDesc("Mastery Check 2");

    public static final List<Student> STUDIO_STUDENTS = getTypicalStudents();
    public static final List<Student> MASTERY_CHECK_STUDENTS = List.of(ALICE, BENSON);
    public static final List<Student> CONSULT_STUDENTS = List.of(ALICE);

    public static final LocalDateTime DT1 = LocalDateTime.of(2022, 12, 12, 10, 0);
    public static final LocalDateTime DT2 = LocalDateTime.of(2022, 12, 12, 11, 0);
    public static final LocalDateTime DT3 = LocalDateTime.of(2022, 12, 12, 12, 0);
    public static final LocalDateTime DT4 = LocalDateTime.of(2022, 12, 12, 13, 0);

    public static final Consult CONSULT_1 = new Consult(CONSULT_DESCRIPTION_1, new TimePeriod(DT3, DT4),
            CONSULT_STUDENTS);
    public static final Consult CONSULT_2 = new Consult(CONSULT_DESCRIPTION_2, new TimePeriod(DT1, DT2),
            CONSULT_STUDENTS);
    public static final MasteryCheck MC_1 = new MasteryCheck(MASTERY_CHECK_DESCRIPTION_1, new TimePeriod(DT1, DT2),
            MASTERY_CHECK_STUDENTS);
    public static final MasteryCheck MC_2 = new MasteryCheck(MASTERY_CHECK_DESCRIPTION_2, new TimePeriod(DT2, DT4),
            MASTERY_CHECK_STUDENTS);
    public static final Studio STUDIO_1 = new Studio(STUDIO_DESCRIPTION_1, new TimePeriod(DT2, DT3),
            STUDIO_STUDENTS);
    public static final Studio STUDIO_2 = new Studio(STUDIO_DESCRIPTION_2, new TimePeriod(DT2, DT4),
            STUDIO_STUDENTS);

    private TypicalLessons() {} // prevents instantiation

    /**
     * Returns an {@code LessonBook} with all the typical lessons.
     */
    public static LessonBook getTypicalLessonBook() {
        LessonBook lb = new LessonBook();
        for (Lesson lesson : getTypicalLessons()) {
            lb.addLesson(lesson);
        }
        return lb;
    }

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(CONSULT_1, MC_1, STUDIO_1));
    }
}