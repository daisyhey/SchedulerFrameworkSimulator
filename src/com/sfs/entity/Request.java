package com.sfs.entity;

/**
 * This is framework formatted network request.
 * 
 */
public class Request {
	
	/**
	 * This is the original network request object.
	 */
	private Object networkRequest;
	
	private Need need;
	
	public Request(Object networkRequest, Need need) {
		this.setNetworkRequest(networkRequest);
		this.setNeed(need);
	}

	public Need getNeed() {
		return need;
	}

	public void setNeed(Need need) {
		this.need = need;
	}

	public Object getNetworkRequest() {
		return networkRequest;
	}

	public void setNetworkRequest(Object networkRequest) {
		this.networkRequest = networkRequest;
	}
	
	
}
