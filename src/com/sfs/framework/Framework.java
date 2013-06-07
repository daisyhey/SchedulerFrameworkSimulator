package com.sfs.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.sfs.android.NetworkService;
import com.sfs.entity.Need;
import com.sfs.entity.PeriodicRequestNeed;
import com.sfs.entity.Request;
import com.sfs.entity.RequestPlan;
import com.sfs.entity.Need.Sender;

/**
 *	This is the framework simulator.  
 *
 */
public class Framework {

	private AtomicInteger idGenerator = new AtomicInteger();
	
	private Scheduler scheduler;
	
	//A map that stores all network requests.
	private Map<Integer, Request> requestMap = new HashMap<Integer, Request>();
	
	private NetworkService service;
	
	public Framework(Scheduler scheduler, NetworkService service) {
		this.scheduler = scheduler;
		this.service = service;
	}
	
	/**
	 * Receive request from an application.
	 * @param requests
	 */
	public void receiveRequest(Request request) {
		scheduler.addRequest(request.getNeed());
		requestMap.put(request.getNeed().getNeedId(), request);
		System.out.println("Recieved request " + request.getNeed().getAppName() + request.getNeed().getNeedId());
	}
	
	/**
	 * Batch send network requests.
	 * @param requests
	 */
	public void batchSendRequest(Collection<Request> requests) {
		//do requests sending.
		for (Request request : requests) {
			Date date = service.send(request.getNetworkRequest());
			System.out.println("Request " + request.getNeed().getAppName() + request.getNeed().getNeedId() + " is sent at " + date.toString());
			//return the actual time of each request to application.
			service.feedback(request.getNeed().getAppName(), date);
		}
	}
	
	public void start() {
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Framework is running now!");
				boolean done = false;
				Date next = scheduler.getNextTime();
				while(!done) { //Some conditions.
					Date now = new Date();
					if(!now.before(next)) { //TODO
						System.out.println("--------------------");
						List<Request> requests = new ArrayList<Request>();
						for(int i : scheduler.next()) {
							requests.add( requestMap.get(i));
						}
						batchSendRequest(requests);
						next = scheduler.getNextTime();
					}
				}
			}
		});
		
		t.start();
	}
	
	public static void main(String[] args) {
		Scheduler scheduler = new SimpleScheduler();
		Framework framework = new Framework(scheduler, new NetworkService() {
			
			@Override
			public Date send(Object request) {
				return new Date();
			}
			
			@Override
			public void feedback(String app, Date date) {
			}
		});
		
		
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
			Request r = new Request("Test_facebook", need);
			framework.receiveRequest(r);
		}
		framework.start();
	}
	
}
