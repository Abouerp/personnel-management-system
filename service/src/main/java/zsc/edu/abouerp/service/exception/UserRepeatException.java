package zsc.edu.abouerp.service.exception;

/**
 * @author Abouerp
 */
public class UserRepeatException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "User is Existing";

    public UserRepeatException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
