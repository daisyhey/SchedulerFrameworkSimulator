package com.sfs.entity;

import java.util.Date;

/**
 * This class contains all information about a request's communication needs.
 * 
 * @author Yiding Zhang
 *
 */
public class Need {
	
	/**
	 * It represents the type of communication needs. 
	 * We only have one type for now.
	 */
	public enum NeedType {
		//Fixed, 
		One_Off
	}
	
	public enum Sender {
		User, Application
	}
	
	public Need() {
	}

	//The unique identification of communication need.
	private int needId;
	
	//The name of the application.
	private String appName;
	
	//The sender of network request.
	private Sender sender;
	
	//The maximal tolerated delay time, its unit is millisecond.
	private long delay;
	
	//The specified time for network access.
	private Date time;
	
	private NeedType needType = NeedType.One_Off;
	
	public int getNeedId() {
		return needId;
	}

	public void setNeedId(int needId) {
		this.needId = needId;
	}
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public NeedType getNeedType() {
		return needType;
	}

	public void setNeedType(NeedType needType) {
		this.needType = needType;
	}

	@Override
	public int hashCode() {
		return (appName + needId).hashCode();
	}

}
