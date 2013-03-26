package com.sfs.framework;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.sfs.entity.Need;

public class SimpleScheduler implements Scheduler {

	private Set<Need> needs = new HashSet<Need>();
	
	//A map for each batch of requests. Key: time to send this batch requests; value: batch request needs;
	private Map<Long, Set<Integer>> schedules = new TreeMap<Long, Set<Integer>>();
	
	
	public void addNeed(Need need) {
		needs.add(need);
		schedule();
	}
	
	public void addNeeds(List<Need> needs) {
		needs.addAll(needs);
		schedule();
	}
	
	/**
	 * Haven't implemented any schedule algorithm.
	 */
	private void schedule() {	
		//TODO for now, it will wait all requests and send them together.
		schedules.clear();
		
		long max = 0;
		Set<Integer> needIDs = new HashSet<Integer>();
		for(Need need: needs) {
			needIDs.add(need.getNeedId());
			long t = need.getTime().getTime() + need.getDelay();
			if (t > max) {
				max = t;
			}
		}
		schedules.put(max,needIDs);
	}
	
	/**
	 * Returns the next batch of requests.
	 * 
	 */
	public Set<Integer> next() {
		 Entry<Long, Set<Integer>> nextSet = schedules.entrySet().iterator().next();
		 schedules.remove(nextSet.getKey());
		 return nextSet.getValue();
	}
	
	/**
	 * Return the time to send the next batch of requests.
	 */
	public long nextTime() {
		if(!schedules.isEmpty()) {
			return schedules.entrySet().iterator().next().getKey();
		} else {
			return -1;
		}
		
	}
}
