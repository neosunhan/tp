package jarvis.storage;

import static jarvis.storage.JsonAdaptedConsult.MISSING_FIELD_MESSAGE_FORMAT;
import static jarvis.testutil.Assert.assertThrows;
import static jarvis.testutil.TypicalLessons.CONSULT_1;
import static jarvis.testutil.TypicalStudents.ALICE;
import static jarvis.testutil.TypicalStudents.BENSON;
import static jarvis.testutil.TypicalStudents.CARL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jarvis.model.LessonAttendance;
import jarvis.model.LessonNotes;
import jarvis.model.Student;
import jarvis.model.TimePeriod;

public class JsonAdaptedConsultTest {
    private static final String VALID_DESC = "Consult";
    private static final LocalDateTime DT1 = LocalDateTime.of(2022,
            11, 11, 12, 0);
    private static final LocalDateTime DT2 = LocalDateTime.of(2022,
            11, 11, 13, 0);

    private static final ArrayList<JsonAdaptedStudent> VALID_STUDENT_LIST =
            new ArrayList<>(List.of(new JsonAdaptedStudent(ALICE), new JsonAdaptedStudent(BENSON),
                    new JsonAdaptedStudent(CARL)));
    private static final HashMap<Integer, Boolean> VALID_ATTENDANCE = new HashMap<>();
    private static ArrayList<String> VALID_GENERAL_NOTES;
    private static Map<Integer, ArrayList<String>> VALID_STUDENT_NOTES;

    @BeforeEach
    public void setUp() {
        VALID_ATTENDANCE.put(1, false);
        VALID_ATTENDANCE.put(0, false);
        VALID_GENERAL_NOTES = new ArrayList<>();
        VALID_GENERAL_NOTES.addAll(List.of("General"));
        VALID_STUDENT_NOTES = new HashMap<>();
        VALID_STUDENT_NOTES.put(0, new ArrayList<>(List.of("1", "2")));
    }

    @Test
    public void toModelType_validConsultDetails_returnsConsult() throws Exception {
        JsonAdaptedConsult Consult = new JsonAdaptedConsult(CONSULT_1);
        assertEquals(CONSULT_1, Consult.toModelType());
    }

    @Test
    public void toModelType_invalidTimePeriod_throwsIllegalArgumentException() {
        JsonAdaptedConsult Consult =
                new JsonAdaptedConsult(VALID_DESC, DT2, DT1, VALID_STUDENT_LIST, VALID_ATTENDANCE,
                        VALID_GENERAL_NOTES, VALID_STUDENT_NOTES, false);
        String expectedMessage = TimePeriod.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalArgumentException.class, expectedMessage, Consult::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalArgumentException() {
        JsonAdaptedConsult Consult =
                new JsonAdaptedConsult((String) null, (LocalDateTime) null, DT1, VALID_STUDENT_LIST, VALID_ATTENDANCE,
                        VALID_GENERAL_NOTES, VALID_STUDENT_NOTES, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TimePeriod.class.getSimpleName());
        assertThrows(IllegalArgumentException.class, expectedMessage, Consult::toModelType);
    }

    @Test
    public void toModelType_nullStudents_throwsIllegalArgumentException() {
        JsonAdaptedConsult Consult =
                new JsonAdaptedConsult((String) null, DT1, DT2, null, VALID_ATTENDANCE,
                        VALID_GENERAL_NOTES, VALID_STUDENT_NOTES, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Student.class.getSimpleName());
        assertThrows(IllegalArgumentException.class, expectedMessage, Consult::toModelType);
    }

    @Test
    public void toModelType_nullAttendance_throwsIllegalArgumentException() {
        JsonAdaptedConsult Consult =
                new JsonAdaptedConsult((String) null, DT1, DT2, VALID_STUDENT_LIST, null,
                        VALID_GENERAL_NOTES, VALID_STUDENT_NOTES, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LessonAttendance.class.getSimpleName());
        assertThrows(IllegalArgumentException.class, expectedMessage, Consult::toModelType);
    }

    @Test
    public void toModelType_nullGeneralNotes_throwsIllegalArgumentException() {
        JsonAdaptedConsult Consult =
                new JsonAdaptedConsult((String) null, DT1, DT2, VALID_STUDENT_LIST, VALID_ATTENDANCE,
                        null, VALID_STUDENT_NOTES, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LessonNotes.class.getSimpleName());
        assertThrows(IllegalArgumentException.class, expectedMessage, Consult::toModelType);
    }

    @Test
    public void toModelType_nullStudentNotes_throwsIllegalArgumentException() {
        JsonAdaptedConsult Consult =
                new JsonAdaptedConsult((String) null, DT1, DT2, VALID_STUDENT_LIST, VALID_ATTENDANCE,
                        VALID_GENERAL_NOTES, null, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LessonNotes.class.getSimpleName());
        assertThrows(IllegalArgumentException.class, expectedMessage, Consult::toModelType);
    }
}

