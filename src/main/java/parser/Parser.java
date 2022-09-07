package parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import bocil.BocilException;
import executor.Executor;
import task.TaskList;

/**
 * Handles the processing of user input and commands Bocil to do specific tasks.
 */
public class Parser {
    /** Date patterns accepted by the parser. */
    private static final String[] DATE_PATTERNS = {
        "yyyy-M-d HH:mm", "yyyy/M/d HH:mm", "yyyy M d HH:mm",
        "d-M-yyyy HH:mm", "d/M/yyyy HH:mm", "d M yyyy HH:mm",
        "yyyy-MM-d HH:mm", "yyyy/MM/d HH:mm", "yyyy MM d HH:mm",
        "d-MM-yyyy HH:mm", "d/MM/yyyy HH:mm", "d MM yyyy HH:mm",
        "yyyy-MMM-d HH:mm", "yyyy/MMM/d HH:mm", "yyyy MMM d HH:mm",
        "d-MMM-yyyy HH:mm", "d/MMM/yyyy HH:mm", "d MMM yyyy HH:mm",
        "yyyy-MMMM-d HH:mm", "yyyy/MMMM/d HH:mm", "yyyy MMMM d HH:mm",
        "d-MMMM-yyyy HH:mm", "d/MMMM/yyyy HH:mm", "d MMMM yyyy HH:mm",
        "yyyy-M-dd HH:mm", "yyyy/M/dd HH:mm", "yyyy M dd HH:mm",
        "dd-M-yyyy HH:mm", "dd/M/yyyy HH:mm", "dd M yyyy HH:mm",
        "yyyy-MM-dd HH:mm", "yyyy/MM/dd HH:mm", "yyyy MM dd HH:mm",
        "dd-MM-yyyy HH:mm", "dd/MM/yyyy HH:mm", "dd MM yyyy HH:mm",
        "yyyy-MMM-dd HH:mm", "yyyy/MMM/dd HH:mm", "yyyy MMM dd HH:mm",
        "dd-MMM-yyyy HH:mm", "dd/MMM/yyyy HH:mm", "dd MMM yyyy HH:mm",
        "yyyy-MMMM-dd HH:mm", "yyyy/MMMM/dd HH:mm", "yyyy MMMM dd HH:mm",
        "dd-MMMM-yyyy HH:mm", "dd/MMMM/yyyy HH:mm", "dd MMMM yyyy HH:mm"
    };
    private final Executor executor;
    private final TaskList taskList;

    /**
     * Constructs a dummy {@link Parser} object to parse datetime.
     */
    public Parser() {
        this.taskList = new TaskList();
        this.executor = new Executor(taskList);
    }

    /**
     * Constructs a {@link Parser} object.
     *
     * @param taskList {@link TaskList} object to modify.
     */
    public Parser(TaskList taskList) {
        this.taskList = taskList;
        this.executor = new Executor(taskList);
    }

    /**
     * Reads user input and commands the {@link Executor} to do actions on the {@link TaskList} object.
     *
     * @param input String line that the user inputs.
     * @return Response line of the program.
     * @throws BocilException If the user input is not of the accepted format.
     */
    public String processInput(String input) throws BocilException {
        String[] split = input.split("\\s+", 2);
        if (input.matches("\\s*")) {
            throw BocilException.bocilEmptyInputException();
        }

        String command = split[0];
        String response;
        switch (command) {
        case "todo":
            String todoName = split[1];
            String placeholderDate = "1-1-2000 00:00";
            response = this.executor.addNewTask(command, todoName, this.parseTime(placeholderDate));
            break;
        case "deadline":
            String[] deadlineDetails = split[1].split("\\s+/by\\s+");
            String deadlineName = deadlineDetails[0];
            String deadlineTime = deadlineDetails[1];
            response = this.executor.addNewTask(command, deadlineName, this.parseTime(deadlineTime));
            break;
        case "event":
            String[] eventDetails = split[1].split("\\s+/at\\s+");
            String eventName = eventDetails[0];
            String eventTime = eventDetails[1];
            response = this.executor.addNewTask(command, eventName, this.parseTime(eventTime));
            break;
        case "mark":
            response = this.executor.markAsDone(split);
            break;
        case "unmark":
            response = this.executor.unmarkAsDone(split);
            break;
        case "delete":
            response = this.executor.deleteTaskFromList(split);
            break;
        case "find":
            response = this.executor.findTaskFromList(split);
            break;
        case "bye":
            response = this.executor.endProgram(input);
            break;
        case "list":
            response = this.executor.showList(input);
            break;
        default:
            throw BocilException.bocilUnknownCommandException();
        }
        return response;
    }

    /**
     * Converts date from user input into a {@link LocalDateTime} object.
     *
     * @param date Date string that the user inputs.
     * @return Date in the form of {@link LocalDateTime}.
     * @throws BocilException If inputted date is not of the accepted format.
     */
    public LocalDateTime parseTime(String date) throws BocilException {
        for (String pattern: DATE_PATTERNS) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                return LocalDateTime.parse(date, formatter);
            } catch (DateTimeParseException e) {
                // Proceeds to the next format if the current one is not matched
            }
        }
        throw BocilException.bocilInvalidDateFormatException();
    }
}
