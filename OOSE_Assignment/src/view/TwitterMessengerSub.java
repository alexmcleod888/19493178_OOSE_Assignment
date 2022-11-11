package view;

import java.util.Map;
import java.util.*;

/*Author: Alex McLeod
 *Purpose: Subclass of TwitterMessenger that takes the occurrences of each keyword during the current
 *         search and adds them to the total occurrences during the current cycle. Also check whether the current
 *         cycle has ended before adding to the totals
 *Date Modifed: 22/05/2019
 */

public class TwitterMessengerSub extends TwitterMessenger 
{	
	//constructor for initialising trendingKeywords that holds the totals of each keyword for the current cycle
	public TwitterMessengerSub(long startTime)
	{
		super(startTime);//send start to super constructor to set the start time of the first cycle
		trendingKeywords = new HashMap<String, Integer>();
	}
	
	//default constructor which is used when the messenger is just being used to send messages and not for searching
	public TwitterMessengerSub()
	{
		super();
	}
	
	//purpose: hook method used by TwitterMessenger's template class StubKeywordTest to add the occurrences of each keyword during the current
    //         search to the total occurrences during the current cycle. Also to check whether the current
    //         cycle has ended before adding to the totals
    public void keywordsDetected(Map<String, Integer> keywords, long timestamp)
    {
    	int oldValue, newValue;
    	
    	if((timestamp - startTime) != 10)//negate the start time of the cycle from the current time to check if at has been an "hour" (10 seconds in
                                         //this case)
    	{ 
    		for(String keyword : keywords.keySet())//for each keyword
    		{
    			oldValue = trendingKeywords.get(keyword);//get the old total of that keyword
    			newValue = oldValue + keywords.get(keyword);//add its recent occurrences to the old total
    			trendingKeywords.remove(keyword);//remove the old value from the list
    			trendingKeywords.put(keyword, newValue);//add new total occurrence to the list
    		}
    		
    	}
    	else//if the cycle has finished then set cycleFinished to true to let StubKeywordTest that the cycle has finished
    	{
    		cycleFinished = true;
    	}
    	
    }
    

}
