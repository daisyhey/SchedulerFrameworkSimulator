package com.sfs.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.sfs.entity.Need;
import com.sfs.entity.Request;
import com.sfs.entity.Need.Sender;

/**
 *	This is a framework simulator.  
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
		int id = idGenerator.incrementAndGet();
		request.getNeed().setNeedId(id);
		scheduler.addNeed(request.getNeed());
		requestMap.put(id, request);
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
				while(!done) {
					Long time = System.currentTimeMillis();
					if(time >= scheduler.nextTime() && scheduler.nextTime() != -1) {
						List<Request> requests = new ArrayList<Request>();
						for(int i : scheduler.next()) {
							requests.add( requestMap.get(i));
						}
						batchSendRequest(requests);
					}
				}
			}
		});
		
		t.start();
	}
	
	public static void main(String[] args) {
		Scheduler scheduler = new SimpleScheduler();
		Framework framework = new Framework(scheduler, new NetworkService());
		framework.start();
		
		Need n = new Need();
		n.setAppName("email");
		n.setSender(Sender.Application);
		n.setDelay(1000);
		n.setTime(new Date(System.currentTimeMillis()));
		
		Request r = new Request("Test_email", n);
		framework.receiveRequest(r);
		
		n = new Need();
		n.setAppName("facebook");
		n.setSender(Sender.Application);
		n.setDelay(1000);
		n.setTime(new Date(System.currentTimeMillis() + 2000));
		
		r = new Request("Test_facebook", n);
		framework.receiveRequest(r);
		
	}
	
}
