package com.sfs.entity;

import java.util.Date;

public class RequestNeed extends Need {

	//The specified time for network access.
	private Date time;
	
	//The maximal tolerated delay time (second), and its unit is second.
	private int delay;
	
	//The maximal time (second) allowed to send the request ahead.
	//Usually the delay time is equivalent to the ahead time
	private int ahead;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public int getAhead() {
		return ahead;
	}

	public void setAhead(int ahead) {
		this.ahead = ahead;
	}
}
