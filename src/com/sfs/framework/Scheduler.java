package com.sfs.framework;

import java.util.List;
import java.util.Set;

import com.sfs.entity.Need;

public interface Scheduler {
	
	public void addNeed(Need need);
	
	public void addNeeds(List<Need> needs);
	
	public Set<Integer> next();
	
	public long nextTime();
	
}
