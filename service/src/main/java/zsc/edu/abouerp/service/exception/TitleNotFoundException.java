package zsc.edu.abouerp.service.exception;

/**
 * @author Abouerp
 */
public class TitleNotFoundException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "Title Not Found";

    public TitleNotFoundException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
