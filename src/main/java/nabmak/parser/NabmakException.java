package nabmak.parser;

/**
<<<<<<< HEAD
 * Represents an exception caused by an invalid Nabmak command.
 */
public class NabmakException extends Exception {
    /**
     * Creates a NabmakException with the specified error message.
     *
     * @param err Error message describing the invalid command.
     */    
=======
 * An exception caused by invalid command.
 */
public class NabmakException extends Exception {

    /**
     * Creates NabmakException with specified error message.
     *
     * @param err Error message.
     */
>>>>>>> branch-Level-9
    public NabmakException(String err) {
        super(err);
    }
}
