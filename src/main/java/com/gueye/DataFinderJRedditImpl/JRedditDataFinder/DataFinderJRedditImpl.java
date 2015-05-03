package com.gueye.DataFinderJRedditImpl.JRedditDataFinder;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.QuerySyntax;
import com.github.jreddit.retrieval.params.SearchSort;
import com.github.jreddit.retrieval.params.TimeSpan;
import com.github.jreddit.utils.RedditConstants;

public class DataFinderJRedditImpl {
	
	private Submissions submissions;
	HashMap<URL, String> result = new HashMap<URL, String>();

	public DataFinderJRedditImpl(Submissions submissions) {
		this.submissions = submissions;
	}
	
	public Map<URL, String> search(String query) throws MalformedURLException {	        
	        List<Submission> SearchExtra = this.search(query, SearchSort.RELEVANCE, TimeSpan.ALL, 532);
	        
	        for (int i=0; i<SearchExtra.size(); i++) {
				result.put(new URL(SearchExtra.get(i).getUrl()), SearchExtra.get(i).getTitle());
			}
	        return result;
	 }
    
    /**
     * Get submissions from the specified subreddit after a specific submission, as the given user, attempting to retrieve the desired amount.
     * 
     * @param query 			Search query
     * @param sort				Search sorting method (e.g. new or top)
     * @param time				Search time (e.g. day or all)
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @param after				Submission after which the submissions need to be fetched.
     * @return					List of the submissions
     */
    public List<Submission> search(String query, SearchSort sort, TimeSpan time, int amount, Submission after) {
    	
    	if (amount < 0) {
    		System.err.println("You cannot retrieve a negative amount of submissions.");
    		return null;
    	}

    	// List of submissions
        List<Submission> result = new LinkedList<Submission>();

        // Do all iterations
        int counter = 0;
		while (amount >= 0) {
			
			// Determine how much still to retrieve in this iteration
			int limit = (amount < RedditConstants.MAX_LIMIT_LISTING) ? amount : RedditConstants.MAX_LIMIT_LISTING;
			amount -= limit;
			
			// Retrieve submissions
			List<Submission> subresult = submissions.search(query, QuerySyntax.LUCENE, sort, time, counter, limit, after, null, true);
			if (subresult == null) {
				return new ArrayList<Submission>();
			}
			result.addAll(subresult);
			
			// Increment counter
			counter += limit;
			
			// If the end of the submission stream has been reached
			if (subresult.size() != limit) {
				System.out.println("API Stream finished prematurely: received " + subresult.size() + " but wanted " + limit + ".");
				break;
			}
			
			// If nothing is left desired, exit.
			if (amount <= 0) {
				break;
			}
			
			// Previous last submission
			after = subresult.get(subresult.size() - 1);
			
		}
		
		return result;
    	
    }
    
    /**
     * Search for submissions using the query with the given sorting method and within the given time as the given user and with maximum amount returned.
	 * 
     * @param query 	Search query
     * @param sort		Search sorting method
     * @param time		Search time
     * @param amount	How many to retrieve (if possible, result <= amount guaranteed)
     * @return <code>List</code> of submissions that match the query.
     */
    public List<Submission> search(String query, SearchSort sort, TimeSpan time, int amount) {
    	return search(query, sort, time, amount, null);
    }
    
    /**
     * Search for submissions using the query with the given sorting method and within the given time as the given user.
	 * 
     * @param query 	Search query
     * @param sort		Search sorting method
     * @param time		Search time
     * @return <code>List</code> of submissions that match the query.
     */
    public List<Submission> search(String query, SearchSort sort, TimeSpan time) {
    	return search(query, sort, time, RedditConstants.APPROXIMATE_MAX_LISTING_AMOUNT);
    }

 
	
}
