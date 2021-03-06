package zsc.edu.abouerp.service.exception;

/**
 * @author Abouerp
 */
public class UserNotFoundException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "User Not Found";

    public UserNotFoundException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
