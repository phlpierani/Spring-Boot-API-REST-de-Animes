package academy.devdojo.springBoot2.handler;

import academy.devdojo.springBoot2.Exceptions.BadRequestException;
import academy.devdojo.springBoot2.Exceptions.BadRequestExceptionDetails;
import academy.devdojo.springBoot2.Exceptions.ValidationExceptionDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@ControllerAdvice // @ControllerAdvice é uma anotação do Spring que permite tratar exceções de forma global em toda a aplicação.
public class RestExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    // @ExceptionHandler é uma anotação do Spring que indica que o método deve ser chamado quando uma exceção específica ocorrer.
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException bre) {
        return new ResponseEntity<>(BadRequestExceptionDetails.builder()
                .timestamp(java.time.LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Bad Request Exception, check the documentation")
                .details(bre.getMessage())
                .developerMessage(bre.getClass().getName())
                .build(), HttpStatus.BAD_REQUEST); //
        // O método retorna um ResponseEntity com os detalhes da exceção e o status HTTP 400 (BAD_REQUEST).
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    // @ExceptionHandler é uma anotação do Spring que indica que o método deve ser chamado quando uma exceção específica ocorrer.
    public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidException
            (MethodArgumentNotValidException exception) {
        List<FieldError> fieldErros = exception.getBindingResult().getFieldErrors();

        String fields = fieldErros.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldErrosMessage = fieldErros.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(ValidationExceptionDetails.builder()
                .timestamp(java.time.LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Bad Request Exception, invalid Fields")
                .details("Check the fields(s) error")
                .developerMessage(exception.getClass().getName())
                .fields(fields)
                .fieldsMessage(fieldErrosMessage)
                .build(), HttpStatus.BAD_REQUEST);
        // O método retorna um ResponseEntity com os detalhes da exceção e o status HTTP 400 (BAD_REQUEST).
    }

}
