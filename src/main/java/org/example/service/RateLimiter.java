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
//        System.out.println("Available permits S=> " + semaphore.availablePermits());
        semaphore.acquire(count);
//        Date date = new Date();
//        date.setSeconds(date.getSeconds() + 5);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                semaphore.release(count);
            }
        }, 1000);
//        System.out.println("Available permits E=> " + semaphore.availablePermits());
    }

    /**
     * If 1 permit is available, claim it.
     * Else, wait.
     */
    public void acquire() throws InterruptedException {
            this.acquire(1);
    }
}
