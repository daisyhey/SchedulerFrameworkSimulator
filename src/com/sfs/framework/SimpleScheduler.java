package com.sfs.framework;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sfs.entity.Need;
import com.sfs.entity.PeriodicRequestNeed;
import com.sfs.entity.RequestPlan;


/**
 * Simple scheduler assumes all requests are periodic and the phone always uses 3G. 
 * It also assumes the mobile phone is always in normal battery level.
 * 
 * @author Yiding Zhang
 *
 */
public class SimpleScheduler implements Scheduler {
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	private Date nextBatchTime;
	
	//Checkpoint is the request with the earliest default sending time. 
	private RequestPlan checkpoint;
	
	//TODO private
	public Map<Integer, RequestPlan> plans=  new HashMap<Integer,RequestPlan>();
	
	private Set<Integer> nextBatchRequests = new HashSet<Integer>();
	
	@Override
	public RequestPlan addRequest(Need need) {
		//We assume all requests are periodic.
		PeriodicRequestNeed pNeed = (PeriodicRequestNeed) need;
		RequestPlan plan = new RequestPlan(need);
		
		if(plans.isEmpty()) {
			checkpoint = plan;
		} else if(plan.getDefault().before(checkpoint.getDefault())) {
			checkpoint = plan;
		} 
		
		plans.put(pNeed.getNeedId(), plan);
		
		return plan;
	}
	
	
	/**
	 * Gets this batch of requests, and move to the next batch. 
	 * TODO
	 */
	@Override
	public Set<Integer> next() {
		//Determines requests of this batch sending
		Set<Integer> batchRequests = getNextBatchRequests();
		
		//Determines the time for this sending
		Date nextTime = getNextTime();
		
		for(Integer i : batchRequests) {
			RequestPlan plan = plans.get(i);
			plan.setLast(nextTime);
		}
		
		//Defines a checkpoint for the next batch sending.
		Date next = null;
		RequestPlan nextCheckpoint = null;
		for(RequestPlan plan : plans.values()) {
			if(next == null || plan.getDefault().before(next)) {
				next = plan.getDefault();
				nextCheckpoint = plan;
			}
		}
		
		checkpoint = nextCheckpoint;
		nextBatchRequests = new HashSet<Integer>();
		nextBatchTime = null;
		return batchRequests;
	}
	
	/**
	 * Determines requests of the next batch sending. First, fetches all request plan that has overlap with the checkpoint.
	 * @return all request r that r.min < checkpoint.max
	 */
	public Set<Integer> getNextBatchRequests() {
		if(!nextBatchRequests.isEmpty()) {
			return nextBatchRequests;
		}
		
		//First, fetches all request plans that have overlap with the checkpoint.
		Set<Integer> tmpBatchRequests = new HashSet<Integer>();
		for(RequestPlan plan : plans.values()) {
			if(plan.getMin().before(checkpoint.getMax())) {
				tmpBatchRequests.add(plan.getRequestID());
			}
		}
		
		//Determines the time for the next batch sending.
		Date minimalmax = null;
		for(Integer id : tmpBatchRequests) {
			Date tmp = plans.get(id).getMax();
			if(minimalmax == null || tmp.before(minimalmax)) {
				minimalmax = tmp;
			}
		}
		nextBatchTime = minimalmax;
		
		//Removes request plans that r.min > nextBatchTime from request plans that overlaps with the checkpoint
		for(Integer id: tmpBatchRequests) {
			Date tmp = plans.get(id).getMin();
			if(!tmp.after(nextBatchTime)) {
				nextBatchRequests.add(id);
			}
		}
		
		return nextBatchRequests;
	}
	
	/**
	 * Return the time to send the next batch of requests.
	 */
	public Date getNextTime() {
		if(nextBatchTime != null) {
			return nextBatchTime;
		}
		Date minimalmax = null;
		nextBatchRequests = getNextBatchRequests();
		for(Integer id : nextBatchRequests) {
			Date tmp = plans.get(id).getMax();
			if(minimalmax == null || tmp.before(minimalmax)) {
				minimalmax = tmp;
			}
		}
		nextBatchTime = minimalmax;
		
		return  nextBatchTime;
	}
	
	public RequestPlan getCheckpoint() {
		return checkpoint;
	}
	
	public static void main(String[] args) {
		SimpleScheduler scheduler = new SimpleScheduler();
		int n = 6;
		for(int i = 1; i < n; i++) {
			PeriodicRequestNeed need = new PeriodicRequestNeed();
			need.setAppName("Test");
			need.setNeedId(i);
			need.setInterval(3+i*2);
			need.setSlack(n-i);
			RequestPlan plan = scheduler.addRequest(need);
			System.out.println("## Request" + plan.getRequestID() + "  min:" + plan.getMinInterval() +
					"  default:" + plan.getDefInterval() + "  max:" + plan.getMaxInterval());
		}
		System.out.println("Begin at: " + dateFormat.format(new Date()));
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i < 100; i ++) {
//			System.out.println("Checkpoint: " + ((SimpleScheduler)scheduler).checkpoint.getRequestID());
//			System.out.println("Send time: " + dateFormat.format(scheduler.getNextTime()));
//			System.out.println("# Batch " + i + ": ");
			Set<Integer> batchRequests = scheduler.getNextBatchRequests();
			sb.append("Batch ").append(i).append(", Time: ").append(dateFormat.format(scheduler.getNextTime())).
				append("\n").append("Request IDs: ");
			for(Integer id : batchRequests) {
//				System.out.println("Request ID: " + id + "  min:" + scheduler.plans.get(id).getMin() );
				sb.append(id).append(" ");
			}
			sb.append("\n\n");
		    batchRequests = scheduler.next();
		}
		
		System.out.println(sb.toString());
//		System.out.println("Checkpoint: " + ((SimpleScheduler)scheduler).checkpoint.getRequestID());
//		System.out.println("Send time: " + dateFormat.format(scheduler.getNextTime()));
//		Set<Integer> batchRequests = scheduler.getNextBatchRequests();
//		System.out.println("# Batch: ");
//		for(Integer id : batchRequests) {
//			System.out.println("Request ID: " + id);
//		}
//	    batchRequests = scheduler.next();
//		System.out.println("Send time: " + dateFormat.format(scheduler.getNextTime()));
//
//	    for(Integer id : batchRequests) {
//			System.out.println("*Request ID: " + id);
//		}
//		System.out.println("Size: " + batchRequests.size());
//		System.out.println(dateFormat.format(scheduler.getNextTime()));
	}
}
