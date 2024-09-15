package jc.spring.ratelimiter.Handler;

import jc.spring.ratelimiter.Exception.RateLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<String> handleRateLimitExceeded(RateLimitExceededException ex) {
        return new ResponseEntity<>("Rate limit exceeded. Try again later.", HttpStatus.TOO_MANY_REQUESTS);
    }
}
