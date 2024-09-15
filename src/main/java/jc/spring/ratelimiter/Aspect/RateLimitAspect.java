package jc.spring.ratelimiter.Aspect;

import jakarta.servlet.http.HttpServletRequest;
import jc.spring.ratelimiter.Exception.RateLimitExceededException;
import jc.spring.ratelimiter.service.RateLimitingService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {
    private final RateLimitingService rateLimitingService;

    @Around("@annotation(jc.spring.ratelimiter.Annotation.RateLimited)")
    public Object enforceRateLimit(ProceedingJoinPoint pjp) throws Throwable {
        // Get the current request from the JoinPoint

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String key = getKey(request);

        if(!rateLimitingService.allowRequest(key)){
            throw new RateLimitExceededException("Rate limit exceeded");
        }

        return pjp.proceed();
    }

    private String getKey(HttpServletRequest request){
        // Retrieve IP from request as rate limit key
        return request.getRemoteAddr();
    }
}
