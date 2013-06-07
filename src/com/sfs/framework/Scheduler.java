package com.sfs.framework;

import java.util.Date;
import java.util.Set;

import com.sfs.entity.Need;
import com.sfs.entity.RequestPlan;

public interface Scheduler {
	
	public RequestPlan addRequest(Need need);
	
	public Set<Integer> next();
	
	public Set<Integer> getNextBatchRequests();
	
	public Date getNextTime();
	
}
