package nabmak.parser;

/**
 * Represents an exception caused by an invalid Nabmak command.
 */
public class NabmakException extends Exception {
    /**
     * Creates a NabmakException with the specified error message.
     *
     * @param err Error message describing the invalid command.
     */    
    public NabmakException(String err) {
        super(err);
    }
}
