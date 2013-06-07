package com.sfs.android;

import java.util.Date;

/**
 *	This is a dummy network service which provide a series of network communication methods. 
 *  In future, all these methods should be methods provided by Android.
 *
 */
public interface NetworkService {

	/**
	 * Sends the network request.
	 * @param request
	 */
	public Date send(Object request);
	
	/**
	 * Informs the application when its request has been sent.
	 * @param app name of application
	 * @param date 
	 */
	public void feedback(String app, Date date);
}
