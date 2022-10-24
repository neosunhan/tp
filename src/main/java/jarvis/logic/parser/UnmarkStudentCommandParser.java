package jarvis.logic.parser;

import static jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static jarvis.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static jarvis.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;

import java.util.stream.Stream;

import jarvis.commons.core.index.Index;
import jarvis.logic.commands.UnmarkStudentCommand;
import jarvis.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnmarkStudentCommand object
 */
public class UnmarkStudentCommandParser implements Parser<UnmarkStudentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkStudentCommand
     * and returns a UnmarkStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LESSON_INDEX, PREFIX_STUDENT_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_LESSON_INDEX, PREFIX_STUDENT_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkStudentCommand.MESSAGE_USAGE));
        }

        Index lessonIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_LESSON_INDEX).get());
        Index studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT_INDEX).get());

        return new UnmarkStudentCommand(lessonIndex, studentIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
