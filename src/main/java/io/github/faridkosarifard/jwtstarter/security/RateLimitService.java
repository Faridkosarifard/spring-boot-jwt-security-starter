package io.github.faridkosarifard.jwtstarter.security;

import io.github.faridkosarifard.jwtstarter.config.AppProperties;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private final ConcurrentHashMap<String, Deque<Instant>> attempts = new ConcurrentHashMap<>();
    private final AppProperties properties;

    public RateLimitService(AppProperties properties) {
        this.properties = properties;
    }

    public boolean allow(String key) {
        return allow(key, properties.security().loginRateLimit(), properties.security().loginRateWindow());
    }

    public boolean allow(String key, int limit, Duration window) {
        Instant now = Instant.now();
        Instant cutoff = now.minus(window);
        Deque<Instant> hits = attempts.computeIfAbsent(key, ignored -> new ArrayDeque<>());
        synchronized (hits) {
            while (!hits.isEmpty() && hits.peekFirst().isBefore(cutoff)) {
                hits.removeFirst();
            }
            if (hits.size() >= limit) {
                return false;
            }
            hits.addLast(now);
            return true;
        }
    }

    public void clear() {
        attempts.clear();
    }
}
