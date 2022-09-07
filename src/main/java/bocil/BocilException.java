package bocil;

/**
 * Describes all the exception handled by the program.
 */
public class BocilException extends Exception {
    private static final String EMPTY_INPUT = "Please enter something!";
    private static final String FILE_NOT_FOUND = "I cannot find your file";
    private static final String FILE_WRONG_FORMAT = "I cannot understand the formatting inside the storage file. "
            + "Please fix it and restart the app!";
    private static final String INVALID_INDEX = "The task number you input is invalid";
    private static final String INVALID_FORMAT = "The input format is invalid";
    private static final String INVALID_TAG_FORMAT = "The tag format you input is invalid";
    private static final String INVALID_DATE_FORMAT = "The date format you input is invalid";
    private static final String UNKNOWN_COMMAND = "I can't understand what you are saying..";

    /**
     * Constructs a {@link BocilException} object using a specific message.
     *
     * @param message String message to be shown to the user.
     */
    public BocilException(String message) {
        super(message);
    }

    /**
     * Handles errors where the user input is empty.
     *
     * @return {@link BocilException}
     */
    public static BocilException bocilEmptyInputException() {
        return new BocilException(EMPTY_INPUT);
    }

    /**
     * Handles errors where the storage file cannot be found.
     *
     * @return {@link BocilException}
     */
    public static BocilException bocilFileNotFoundException() {
        return new BocilException(FILE_NOT_FOUND);
    }

    /**
     * Handles errors where the storage file has the wrong text formatting.
     *
     * @return {@link BocilException}
     */
    public static BocilException bocilFileWrongFormatException() {
        return new BocilException(FILE_WRONG_FORMAT);
    }

    /**
     * Handles errors where the task number is out of range or is not an integer.
     *
     * @return {@link BocilException}
     */
    public static BocilException bocilInvalidIndexException() {
        return new BocilException(INVALID_INDEX);
    }

    /**
     * Handles errors where the format of the input is incorrect.
     *
     * @return {@link BocilException}
     */
    public static BocilException bocilInvalidFormatException() {
        return new BocilException(INVALID_FORMAT);
    }

    /**
     * Handles errors where the tag format of the input is incorrect.
     *
     * @return {@link BocilException}
     */
    public static BocilException bocilInvalidTagFormatException() {
        return new BocilException(INVALID_TAG_FORMAT);
    }

    /**
     * Handles errors where the date format of the task is incorrect.
     *
     * @return {@link BocilException}
     */
    public static BocilException bocilInvalidDateFormatException() {
        return new BocilException(INVALID_DATE_FORMAT);
    }

    /**
     * Handles errors where the command is not known by the program.
     *
     * @return {@link BocilException}
     */
    public static BocilException bocilUnknownCommandException() {
        return new BocilException(UNKNOWN_COMMAND);
    }
}
