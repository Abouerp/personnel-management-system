package zsc.edu.abouerp.service.exception;

/**
 * @author Abouerp
 */
public class StationNotFoundException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "Station Not Found";

    public StationNotFoundException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
