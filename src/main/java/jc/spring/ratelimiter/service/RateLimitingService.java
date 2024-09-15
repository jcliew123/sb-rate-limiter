package jc.spring.ratelimiter.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimitingService {
    @Value("${rate.limit.request}")
    private int requests;

    @Value("${rate.limit.seconds}")
    private int seconds;

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean allowRequest(String apiKey){
        Bucket bucket = buckets.computeIfAbsent(apiKey, this::createNewBucket);
        return bucket.tryConsume(1);
    }

    private Bucket createNewBucket(String apiKey){
        Bandwidth limit = Bandwidth.classic(this.requests, Refill.intervally(this.requests, Duration.ofSeconds(this.seconds)));
        return Bucket4j.builder().addLimit(limit).build();
    }
}
