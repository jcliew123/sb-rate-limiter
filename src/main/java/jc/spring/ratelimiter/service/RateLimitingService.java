package jc.spring.ratelimiter.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jc.spring.ratelimiter.Configuration.RateLimitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RateLimitingService {
    private final RateLimitConfig rateLimitConfig;

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean allowRequest(String apiKey){
        Bucket bucket = buckets.computeIfAbsent(apiKey, this::createNewBucket);
        return bucket.tryConsume(1);
    }

    private Bucket createNewBucket(String apiKey){
        Bandwidth limit = Bandwidth.classic(rateLimitConfig.getRequests(), Refill.intervally(rateLimitConfig.getRequests(), Duration.ofSeconds(rateLimitConfig.getSeconds())));
        return Bucket4j.builder().addLimit(limit).build();
    }
}
