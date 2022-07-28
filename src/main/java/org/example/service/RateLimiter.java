package org.example.service;

import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.concurrent.TimedSemaphore;

public class RateLimiter {
    private final int PERMITS_PER_SECOND;
    private TimedSemaphore semaphore;
    public static RateLimiter create(int permitsPerSecond) {
        return new RateLimiter(permitsPerSecond);
    }

    private RateLimiter(int permitsPerSecond) {
        PERMITS_PER_SECOND = permitsPerSecond;
        semaphore = new TimedSemaphore(1, TimeUnit.SECONDS, PERMITS_PER_SECOND);
    }
    /**
     * If 'count' number of permits are available, claim them.
     * Else, wait.
     */
    public void acquire(int count) {

    }

    /**
     * If 1 permit is available, claim it.
     * Else, wait.
     */
    public void acquire() throws InterruptedException {
        semaphore.acquire();
    }



}
