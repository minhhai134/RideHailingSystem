package EP.RideHailingSystem.exception.handler;

import EP.RideHailingSystem.exception.ClientNotFoundException;
import EP.RideHailingSystem.exception.DriverNotFoundException;
import EP.RideHailingSystem.exception.InvalidRequestException;
import EP.RideHailingSystem.exception.UpdateLocationErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_CODE_INTERNAL = "INTERNAL_ERROR";

    private static final Map<Class<? extends RuntimeException>, HttpStatus> EXCEPTION_TO_HTTP_STATUS_CODE = Map.of(
            DriverNotFoundException.class, HttpStatus.NOT_FOUND,
            UpdateLocationErrorException.class, HttpStatus.INTERNAL_SERVER_ERROR,
            InvalidRequestException.class, HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE,
            ClientNotFoundException.class, HttpStatus.NOT_FOUND,
            MessagingException.class, HttpStatus.INTERNAL_SERVER_ERROR
    );

    private static final Map<Class<? extends RuntimeException>, String> EXCEPTION_TO_ERROR_CODE = Map.of(
            DriverNotFoundException.class, "DRIVER_NOT_FOUND",
            UpdateLocationErrorException.class,"UPDATE_LOCATION_ERROR",
            InvalidRequestException.class, "INVALID_REQUEST",
            ClientNotFoundException.class,"CLIENT_NOT_FOUND",
            MessagingException.class, "MESSAGE_SENDING_ERROR"
    );

    @ExceptionHandler()
    ResponseEntity<ApiExceptionResponse> handleException(RuntimeException exception){
        HttpStatus httpStatus = EXCEPTION_TO_HTTP_STATUS_CODE.getOrDefault(exception.getClass(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        String errorCode = EXCEPTION_TO_ERROR_CODE.getOrDefault(exception.getClass(), ERROR_CODE_INTERNAL);

        final ApiExceptionResponse response = ApiExceptionResponse.builder().status(httpStatus).errorCode(errorCode)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
