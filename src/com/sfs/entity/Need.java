package com.sfs.entity;


/**
 * This class contains all information about a request's communication needs.
 * 
 * @author Yiding Zhang
 *
 */
public abstract class Need {
	
	public enum Sender {
		User, Application
	}
	
	public enum Priority {
		High, Normal, Low
	}
	
	public Need() {
	}

	//The unique identification of a request need.
	private int needId;
	
	//The name of the application.
	private String appName;
	
	//The sender of network request.
	private Sender sender;
	
	//The priority of the request.
	private Priority priority;
	
	private String description;
	
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

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return (appName + needId).hashCode();
	}

}
