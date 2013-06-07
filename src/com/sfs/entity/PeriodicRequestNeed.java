package com.sfs.entity;

public class PeriodicRequestNeed extends Need {
	
	//The default interval (second) to send the periodic request. 
	private int interval;
	
	//The scheduler can change the interval to (defaultInterval +/- slack);
	private int slack;

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getSlack() {
		return slack;
	}

	public void setSlack(int slack) {
		this.slack = slack;
	}
	
}
