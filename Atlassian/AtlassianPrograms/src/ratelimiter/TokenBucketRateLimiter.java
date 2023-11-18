package ratelimiter;

public class TokenBucketRateLimiter {
	private final long capacity;
	private final long tokensPerSecond;
	private long lastRefillTime;
	private long tokensAvailable;
	
	public TokenBucketRateLimiter(long capacity, long tokensPerSecond) {
		this.capacity = capacity;
		this.tokensPerSecond = tokensPerSecond;
		this.lastRefillTime = System.currentTimeMillis();
		this.tokensAvailable = capacity;
	}
	
	public synchronized boolean allowRequest() {
		refill();
		if(tokensAvailable > 0) {
			tokensAvailable --;
			return true;
		}
		return false;
	}
	
	private void refill() {
		long now = System.currentTimeMillis();
		long elapsedTime = now - lastRefillTime;
		long tokensToBeAdded = (elapsedTime/1000) * tokensPerSecond;
		if(tokensToBeAdded > 0) {
			tokensAvailable = Math.min(capacity, tokensToBeAdded + tokensAvailable);
			lastRefillTime = now;
		}
	}
	
}
