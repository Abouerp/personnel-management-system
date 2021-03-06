package zsc.edu.abouerp.service.exception;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zsc.edu.abouerp.common.entiry.ResultBean;

/**
 * @author Abouerp
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static ResponseEntity<ResultBean<Object>> handleException(ClientErrorException exception) {
        return ResponseEntity.status(exception.getCode())
                             .body(exception.getResultBean());
    }

    @ExceptionHandler(ClientErrorException.class)
    public HttpEntity<ResultBean<Object>> clientErrorException(ClientErrorException ex) {
        return handleException(ex);
    }
}
