package org.example.service;

import org.apache.commons.lang3.concurrent.TimedSemaphore;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class RateLimiter {
    private final int PERMITS_PER_SECOND;
    private Semaphore semaphore;
    Timer t;
    public static RateLimiter create(int permitsPerSecond) {
        return new RateLimiter(permitsPerSecond);
    }

    private RateLimiter(int permitsPerSecond) {
        PERMITS_PER_SECOND = permitsPerSecond;
        semaphore = new Semaphore(PERMITS_PER_SECOND);
        t = new Timer();
    }
    /**
     * If 'count' number of permits are available, claim them.
     * Else, wait.
     */
    public void acquire(int count) throws InterruptedException {
        // Acquire permit
        semaphore.acquire(count);

        // Create a TamerTask to run with a 1 second delay to RELEASE the permit
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                semaphore.release(count);
            }
        }, 1000);
    }

    /**
     * If 1 permit is available, claim it.
     * Else, wait.
     */
    public void acquire() throws InterruptedException {
            // Call to use 1 permit
            this.acquire(1);
    }
}
