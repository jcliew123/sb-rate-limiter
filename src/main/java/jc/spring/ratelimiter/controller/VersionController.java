package jc.spring.ratelimiter.controller;

import jc.spring.ratelimiter.service.RateLimitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/version")
@RequiredArgsConstructor
public class VersionController {
    private final RateLimitingService rateLimitingService;

    @GetMapping
    public ResponseEntity<String> getVersion(){
        String apiKey = "test-api-key"; // Retrieving API key from request headers or JWT
        if(rateLimitingService.allowRequest(apiKey)){
            return new ResponseEntity<>("1.0", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Too many requests", HttpStatus.TOO_MANY_REQUESTS);
        }
    }
}
