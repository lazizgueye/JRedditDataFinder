package com.gueye.DataFinderJRedditImpl.JRedditDataFinder;

import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

import com.github.jreddit.entity.User;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public class test {
	public static void main(String argv[]) {
		System.out.println("yooooo");
		RestClient restClient = new HttpRestClient();
		restClient.setUserAgent("bot/1.0 by name");

		// Connect the user 
		User user = new User(restClient, "Zizou_104", "leopold");
		try {
		    user.connect();
		    Submissions subms = new Submissions(restClient, user);
		    DataFinderJRedditImpl extendedSubms  = new DataFinderJRedditImpl(subms);
		    HashMap<URL, String> submissionsSearchExtra = (HashMap<URL, String>) extendedSubms.search("ronaldo");			
		    printSubmissionsList(submissionsSearchExtra);
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public static void printSubmissionsList(HashMap<URL, String> subs) {
		for(Entry<URL, String> s: subs.entrySet()) {
            System.out.println(s.getKey()+"\n"+s.getValue()+"\n");
        }
	}
}