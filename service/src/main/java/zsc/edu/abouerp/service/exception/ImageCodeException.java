package zsc.edu.abouerp.service.exception;

/**
 * @author Abouerp
 */
public class ImageCodeException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "ImageCode Error ";

    public ImageCodeException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
