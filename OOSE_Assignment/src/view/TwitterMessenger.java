package view;

import java.util.*;
import java.util.concurrent.*;

/*Author: Alex McLeod
 *Purpose: abstract class inherited by TwitterMessengerSub that simulates the actions of a twitter messenger. Allowing messages to be sent to people and to detect
 *         what keywords are trending on twitter.
 *Date Modified: 22/05/2019
 */

public abstract class TwitterMessenger 
{
	private Set<String> keywords;//list of keywords from all policies
	protected long startTime;//integer value for when the current search cycle started
	protected Map<String, Integer> trendingKeywords;//Map of keywords whos occurrence on twitter are being tracked in the current search cycle
                                          //holds the total occurences of each keyword added to during each search, analysed after the cycle 
                                          //and reset after every cycle
	boolean cycleFinished;//value that states whether the current cycle has finished 
	
	//contructor that initialises values
    public TwitterMessenger(long startTime)
    {
    	trendingKeywords = new HashMap<String, Integer>();
    	this.startTime = startTime;
    	cycleFinished = false;
    }
    
    //default contructor for when we are creating a twitter messenger just for sending messages and not searching for keywords
    public TwitterMessenger()
    {
    	
    }
    
    /*Stub*/
    //purpose: stub method that simulates sending a message to a particular person
    public void sendPrivateMessage(String id, String message)
    {
    	System.out.println(message + " - sent to: " + id);
    }
    
    //purpose: method for setting the current keywords to search for on twitter
    public void setKeywords(Set<String> newKeywords)
    {
        this.keywords = newKeywords;//set the new keywords that are being search for
    	//add keywords to the list that have been added to policies while the last cycle occuring
    	for(String keyword : newKeywords)
    	{
    		//if the current list of keywords to be searched for does not contain this keyword then
    		//add it and intialise it with a count of 0
    		//otherwise if it does exist leave it 
    		if(!trendingKeywords.containsKey(keyword))
    		{
    			trendingKeywords.put(keyword, 0);
    		}
    		
    	}
    	
    	//remove keywords that have been removed during the last cycle
    	for(String keyword : trendingKeywords.keySet())
    	{
    		//if the set of new keywords does not contain a keyword
    		//from the trending keywords then remove it from the 
    		//trending keywords
    		if(!newKeywords.contains(keyword))
    		{
    			trendingKeywords.remove(keyword);
    		}
    	}
    }
    
    /*STUB*/
    //Purpose: template method that simulates twitter messenger searching throughout twitter for each keyword 
    //         and counting the amount of times each keyword was found
    public Map<String, Integer> stubKeywordTest()
    {
    	long timestamp;
    	
    	Map<String, Integer> keywordOccurrences = new HashMap<String, Integer>();//create a Map to hold the occurrences of each keyword
                                                                                 //for this search
    	
    	for(String keyword : keywords)//for each keyword in the current keywords assign a random number to it
    	{
    		keywordOccurrences.put(keyword, getRandomNum());
    	}
    	timestamp = System.currentTimeMillis();//get the current time since the epoch (01/01/1970)
    	timestamp = TimeUnit.MILLISECONDS.toSeconds(timestamp);//convert this number to milliseconds
    	keywordsDetected(keywordOccurrences, timestamp);//call keywordsDetected to detect how much time has past during the cycle and
                                                        //to add the occurrence of each keyword during this search to the total times
                                                        //it has occurred during this cycle
    	return trendingKeywords;//return trendingKeywords which has had its totals added to by keywordsDetected 
    }
    
    /*STUB*/
    //purpose: stub method used to get a random number for how times each keyword occurred during the search
    public int getRandomNum()
    {
    	double randomReal;
    	int randomInteger;
    	
        randomReal = Math.random();//get a random number between 0.0 and 0.1
    	randomReal = randomReal * 50 + 1;//multiply by 50 and add 1 to get a number between 1.0 and 50.0
    	randomInteger = (int) randomReal;//convert to an integer
    	return randomInteger;
    }
    
    //purpose: method used to return whether the current cycle has finished
    public boolean getCycleFinished()
    {
    	return cycleFinished;
    }
    
    //purpose: used to reset the cycle from being finished back to no being finished
    public void resetCycle()
    {
    	cycleFinished = false;
    }
    
    //purpose: method used to set the start time of the current cycle
    public void setStartTime(long time)
    {
    	startTime = time;
    }
    
    //purpose: method used to clear the total occurrences of each keyword during the last cycle
    public void clearCount()
    {
    	trendingKeywords.clear();
    }
    
    //purpose: abstract hook method called by StubKeywordTest to add current searches results to the total 
    //         during the current cycle
    public abstract void keywordsDetected(Map<String, Integer> keywords, long timestamp);
}
