package com.sfs.entity;

import java.util.Date;

public class RequestPlan {

	//NeedID
	private int requestID;
	
	private int minInterval;
	
	private int defInterval;

	private int maxInterval;
	
	//The time of the last sending
	private Date last;
	
	public RequestPlan(Need need) {
		this.requestID = need.getNeedId();
		if(need instanceof PeriodicRequestNeed) {
			PeriodicRequestNeed pNeed = (PeriodicRequestNeed) need;
			setDefInterval(pNeed.getInterval());
			setMaxInterval(pNeed.getInterval() + pNeed.getSlack());
			setMinInterval(pNeed.getInterval() - pNeed.getSlack());
			setLast(new Date());
		}
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	
	public int getMinInterval() {
		return minInterval;
	}


	public void setMinInterval(int minInterval) {
		this.minInterval = minInterval;
	}


	public int getDefInterval() {
		return defInterval;
	}


	public void setDefInterval(int defInterval) {
		this.defInterval = defInterval;
	}


	public int getMaxInterval() {
		return maxInterval;
	}


	public void setMaxInterval(int maxInterval) {
		this.maxInterval = maxInterval;
	}

	public Date getLast() {
		return last;
	}

	public void setLast(Date last) {
		this.last = last;
	}
	
	/**
	 * Gets the minimal time for the next sending.
	 * @return
	 */
	public Date getMin() {
		return new Date(last.getTime() + minInterval * 1000);
	}
	
	/**
	 * Gets the default time for the next sending.
	 * @return
	 */
	public Date getDefault() {
		return new Date(last.getTime() + defInterval * 1000);
	}
	
	/**
	 * Gets the maximal time for the next sending.
	 * @return
	 */
	public Date getMax() {
		return new Date(last.getTime() + maxInterval * 1000);
	}
	
	public static void main(String[] args) {
		PeriodicRequestNeed need = new PeriodicRequestNeed();
		need.setAppName("Test");
		need.setNeedId(1);
		need.setInterval(4);
		need.setSlack(1);
		RequestPlan rp = new RequestPlan(need);
		
		rp.setLast(new Date());
		System.out.println("last: " + rp.getLast());
		System.out.println("default next: " + rp.getDefault());
		System.out.println("min next: " + rp.getMin());
	}
}
