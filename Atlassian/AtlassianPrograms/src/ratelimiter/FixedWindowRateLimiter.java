package ratelimiter;

public class FixedWindowRateLimiter {
    private final int maxRequests;
    private final long windowMillis;
    private long lastResetTime;
    private int counter;

    public FixedWindowRateLimiter(int maxRequests, long windowSeconds) {
        this.maxRequests = maxRequests;
        this.windowMillis = windowSeconds * 1000;
        this.lastResetTime = System.currentTimeMillis();
        System.out.println(lastResetTime);
        this.counter = 0;
    }

    public synchronized boolean allowRequest() {
        long currentTimeMillis = System.currentTimeMillis();
        if(currentTimeMillis - lastResetTime > windowMillis) {
        	counter = 0;
        	lastResetTime = currentTimeMillis;
        }else {
        	if(counter < maxRequests) {
        		counter++;
        		return true;
        	}else {
        		return false;
        	}
        }
        counter++;
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        FixedWindowRateLimiter rateLimiter = new FixedWindowRateLimiter(3, 60); // 3 requests per 60 seconds
        int count = 1;
        while(true) {
        	Thread.sleep(1000);
        	if(rateLimiter.allowRequest()) {
        		System.out.println("Request " + count + " allowed");
        	}else {
        		System.out.println("Request " + count + " blocked");
        	}
        	count++;
        }
    }
}
