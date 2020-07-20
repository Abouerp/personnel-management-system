package zsc.edu.abouerp.service.exception;

/**
 * @author Abouerp
 */
public class ParamErrorException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "Param Error";

    public ParamErrorException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
