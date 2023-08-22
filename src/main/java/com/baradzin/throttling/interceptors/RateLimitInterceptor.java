package com.baradzin.throttling.interceptors;

import com.baradzin.throttling.exceptions.InvalidDataException;
import com.baradzin.throttling.exceptions.TooManyRequestsException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    @Value("${bucket.limit}")
    private long rateLimit;

    @Value("${bucket.duration}")
    private int duration;

    private Map<String, Bucket> requestsMap = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {
        String apiKey = request.getHeader("api-key");
        if (StringUtils.isBlank(apiKey)) {
            throw new InvalidDataException("Absent header 'api-key'");
        }
        if (!requestsMap.containsKey(apiKey)) {
            requestsMap.put(apiKey, createBucket());
        }
        ConsumptionProbe probe = requestsMap.get(apiKey).tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.addHeader("rate-limit-remaining", String.valueOf(probe.getRemainingTokens()));
            return true;
        } else {
            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
            response.addHeader("rate-limit-retry-after-seconds", String.valueOf(waitForRefill));
            throw new TooManyRequestsException();
        }
    }

    private Bucket createBucket() {
        return Bucket
                .builder()
                .addLimit(Bandwidth.classic(
                        rateLimit,
                        Refill.intervally(rateLimit, Duration.ofMinutes(duration))))
                .build();
    }
}
