package jarvis.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jarvis.commons.exceptions.IllegalValueException;
import jarvis.model.Task;
import jarvis.model.TaskDeadline;
import jarvis.model.TaskDesc;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tasks's %s field is missing!";

    private final String taskDesc;
    private final LocalDate deadline;
    private final boolean isDone;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("taskDesc") String taskDesc,
                           @JsonProperty("deadline") LocalDate deadline,
                           @JsonProperty("isDone") boolean isDone) {
        this.taskDesc = taskDesc;
        this.deadline = deadline;
        this.isDone = isDone;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        taskDesc = source.getDesc().taskDesc;
        deadline = source.getDeadline().deadline;
        isDone = source.isDone();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Task toModelType() throws IllegalValueException {

        if (taskDesc == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TaskDesc.class.getSimpleName()));
        }
        final TaskDesc modelTaskDesc = new TaskDesc(taskDesc);
        final TaskDeadline modelTaskDeadline = new TaskDeadline(deadline);
        Task task = new Task(modelTaskDesc, modelTaskDeadline);
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

}
