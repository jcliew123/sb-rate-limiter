package jc.spring.ratelimiter.Configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RateLimitConfig {
    @Value("${myapp.limit.request}")
    private int requests;

    @Value("${myapp.limit.seconds}")
    private int seconds;
}
