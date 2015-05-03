/**
 * iPOJO sample service consumer.
 * REMOVE THIS CLASS AS SOON AS YOU UNDERSTOOD WHAT THIS FILE GOING TO DO.
 */
package com.gueye.DataFinderJRedditImpl.JRedditDataFinder.internal;

import com.gueye.DataFinderJRedditImpl.JRedditDataFinder.DataFinderJRedditImpl;

public class SampleConsumerImpl {
	private DataFinderJRedditImpl svc;
	void setService(DataFinderJRedditImpl service) {
		System.out.println("Got service");
		svc = service;
	}
	void clearService() {
		svc = null;
	}
	void start() {
		// do something
		System.out.println("started");
	}
	void stop() {
		// do something
		System.out.println("stopped");
	}
}
