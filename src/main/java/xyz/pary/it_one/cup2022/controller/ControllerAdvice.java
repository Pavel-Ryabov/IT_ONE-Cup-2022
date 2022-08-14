package xyz.pary.it_one.cup2022.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import xyz.pary.it_one.cup2022.exception.InvalidSingleQueryId;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Void> invalidFormatException(InvalidFormatException e) {
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Void> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InvalidSingleQueryId.class)
    public ResponseEntity<Void> invalidSingleQueryId(InvalidSingleQueryId e, HttpServletRequest request) {
        if (request.getRequestURI().endsWith("/add-new-query")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
