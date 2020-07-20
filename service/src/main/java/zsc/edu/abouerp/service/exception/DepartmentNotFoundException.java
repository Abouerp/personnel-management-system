package zsc.edu.abouerp.service.exception;

/**
 * @author Abouerp
 */
public class DepartmentNotFoundException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "Department Not Found";

    public DepartmentNotFoundException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
