# Spring Boot - Rate Limiting with Bucket4j

## 1. Import the dependency needed
    // Could use the one from com.bucket4j too
    <dependency>
        <groupId>com.github.vladimir-bukhtoyarov</groupId>
        <artifactId>bucket4j-core</artifactId>
        <version>4.6.0</version>
    </dependency>

## 2. Implement the Rate Limiting Service
Use a ConcurrentHashMap to store a bucket for each API key. Create the allowRequest() to check if a
request can be consumed from the bucket associated with the given API key (IP, etc.).
Current createNewBucket() creates a new bucket with a rate limit of 10 requests per minute.

## 3. Integrate the service into the controller needed
Use the allowRequest method from the RateLimitingService in the endpoints that need to be rate-limited.

---
# Second Option: Implementing Rate Limiting as Aspect

## 1. Define the @RateLimited annotation

## 2. Create a Rate Limiting Aspect
Intercept the method annotated with <code>@RateLimited</code> and enforce rate limits.
Reuse the Rate Limiting Service created above to implement the same rate limiting algorithm.

## 3. Create a RateLimitExceedException
Provide more information regarding the error thrown

## 4. Implement rate limiting in controller
Use the @RateLimited annotation on method that we want to rate limit.
