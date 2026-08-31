package nabmak.parser;

/**
 * An exception caused by invalid command.
 */
public class NabmakException extends Exception {

    /**
     * Creates NabmakException with specified error message.
     *
     * @param err Error message.
     */
    public NabmakException(String err) {
        super(err);
    }
}
