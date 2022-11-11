package controller;

import java.util.*;
import java.util.concurrent.TimeUnit;

import view.*;
import view.Observer;
import model.*;

/*Author: Alex McLeod
 *Purpose: TrendingDetector class that uses the timer class to run a thread every few minutes(used every second 2 seconds to make it
 *         faster) which counts the number of tweets and facebook posts that contain each keyword in the list. Every hour (every 10 
 *         seconds in this case) it notifies trending keyword observers if a keyword for a specific policy is trending
 *Date Modified:22/05/2019
 */


public class TrendingDetector
{
	private Set<String> keywords;//reference to current keywords
	private Map<String, PolicyArea> policies;//reference to current policies
	                                         //used to send notifications to people associated with specific policies
	//keeps track of trending statues of keywords
	private Map<String, Keyword> trendingTracker;
	private Set<String> newKeywords;//Set used to hold the keywords for the current cycle
	                                //using Keyword object to keep track of trending statues for specific keyword
    //List holding the currently initialised observers to be sent notifications
	private ArrayList<Observer> trendingObservers;
	//Twitter messenger class for detecting and counting the occurrence of each keyword on twitter
	private TwitterMessenger twitterMessenger;
	//Facebook messenger class for detecting and counting the occurrence of each keyword on facebook
	private FacebookMessenger facebookMessenger;
	//Map for holding the current number of times each keyword has been detected on twitter
	private Map<String, Integer> twitterKeywordsCount;
	//Map for holding the current number of times each keyword has been detected on facebook
	private Map<String, Integer> facebookKeywordsCount;
	private long startTime;//Integer holding the start time of the current hour cycle 
	
	//constructor for the trendingDetector
	public TrendingDetector(Set<String> keywords, Map<String, PolicyArea> policies)
	{
		//set the start time of the first cycle
    	long startTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    	//initialise Messenger classes
		this.facebookMessenger = new FacebookMessengerSub(startTime);
		this.twitterMessenger = new TwitterMessengerSub(startTime);
		//initialise reference to current keywords
		this.keywords  = keywords;
		//initialise reference to current policies
		this.policies = policies;
		//initialise Set for the keywords used for the current cycle
	    trendingTracker = new HashMap<String, Keyword>();
		newKeywords = new HashSet<String>();
		
		twitterMessenger.setKeywords(newKeywords);
		facebookMessenger.setKeywords(newKeywords); 
		trendingObservers = new ArrayList<Observer>();
	}
	
	//create timer to check trending keywords and run a seperate thread
    Timer timer = new Timer();
   	
    //purpose: create new task for searching for these trending keywords
    TimerTask task = new TimerTask()
    {
    	public void run()
    	{
    		int twitterCount, facebookCount, totalCount;
    	    Keyword keyword;
		
    		if(twitterMessenger.getCycleFinished() == true)//check if the current cycle has finished
    		{
    			System.out.println("\nCycle Finished\n");
    			//add counts of both twitter and facebook
    			for(String keywordString : newKeywords)//for each of the current keywords in this cycle check which ones are trending
    			{
    				//get each keywords count from twitter and facebook
    				twitterCount = twitterKeywordsCount.get(keywordString);
    				facebookCount = facebookKeywordsCount.get(keywordString);
    				keyword = trendingTracker.get(keywordString);//get the word from the trendingTracker
    				totalCount = twitterCount + facebookCount;//get the total of twitter and facebook
    				if(totalCount >= 50)//if the word is currently trending
    				{
    					isTrending(keyword, keywordString);//call isTrending to check if this specific keyword
    					                                   //will cause notifications
    				}
    				else//if the word is not trending
    				{
    					notTrending(keyword);//decide what to do with the keyword if its not trending   				
    				}
    				
    			}
    			   			
    			twitterMessenger.resetCycle();//set cycleFinished to false to begin new cycle
    			//clear the count of each keyword in both twitter and facebooks messengers
    			twitterMessenger.clearCount();
    			facebookMessenger.clearCount();
    			
    			newKeywords = new HashSet<String>();//create new set of keywords for the new cycle
    			newKeywords.addAll(keywords);//set the new sets keywords to current keywords in the system
    			twitterMessenger.setStartTime(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));//set the start time of this cycle
    			//set the new keywords for this cycle in the twitter and facebook messengers
    		    twitterMessenger.setKeywords(newKeywords);
        		facebookMessenger.setKeywords(newKeywords);
        		//fills map that keeps track of previously trending keywords 
        		setTrendingTracker();
    		}
    		//get the count for each keyword from twitter and facebook for this cycles adding it on to the previous searches in this cycle
    		twitterKeywordsCount = twitterMessenger.stubKeywordTest();
			facebookKeywordsCount = facebookMessenger.stubKeywordTest();
    	}
    };
    
    //purpose: if a keyword is trending then decide what to do with the keyword and whether to send a notification 
    public void isTrending(Keyword keyword, String keywordString)
    {
		if(keyword.prevTrending() == false)//if the word was not trending before and hasnt been made 
                                           //to wait 24 cycles (3 cycles in this case) then you can send notifications
        {
            sendNotification(keywordString);//send notifications for this keyword 
            keyword.setTrending();//set the keyword as being trending
        }
        else if(keyword.prevTrending() == true && keyword.getCyclesHeldOff() == 3)//if it was trending before but has finished its
                                                                                  //24 cycles(3 cycles) then send notification and begin another cycle
        {
            sendNotification(keywordString);
            //because the word is trending again reset its count and begin incrementing how many cycles it has been held off for again,
            //for another 24 cycles (3 cycles)
            keyword.resetCount();
            keyword.incrementCyclesHeldOff();
        } 
        else//if the keyword was trending before but has not reached its 24th cycle (3rd in this case) then keep incrementing how many 
        	//cycles it has been held off
        {
            keyword.incrementCyclesHeldOff();
        }
    }
    
    //purpose: if a keyword is not trending then decide what to do with the keyword
    public void notTrending(Keyword keyword)
    {
    	//if it was previously trending and it has reached its 24th (3rd cycle in this case) then change keyword to not trending and reset 
    	//the number of times it has been held off
    	if(keyword.prevTrending() && keyword.getCyclesHeldOff() == 3)//if the word was previously trending and the count is equals to 24(3 in this case)
		{
			keyword.removeTrending();//set its trending statues to false
			keyword.resetCount();//reset its count because it is no longer trending
		}
		else if(keyword.prevTrending() && keyword.getCyclesHeldOff() != 3)//if it was previously trending but has not reached it 24th cycle(3rd cycle) then
			                                                              //increment the number of times it has been held off
		{ 
			keyword.incrementCyclesHeldOff();
		}
    }   
    
    //purpose: finds the policy relating to the specific imported keyword and creates the message to be imported into notifyTrendingKeyword
    public void sendNotification(String keyword)
    {
    	for(PolicyArea policy : policies.values())
	    {
		    if(policy.containsKeyword(keyword))
		    {
			    notifyTrendingObs(policy.getName(), "New Trending keyword '" + keyword + "' from " + policy.getName());
		    }
	    }
    }
    
    //purpose: for every new cycle it sets a new list of keywords which manages which ones are tracking, for how long they 
    //have been trending and how long they have been held off from cycles for
    public void setTrendingTracker()
    {
    	LinkedList<String> toRemove = new LinkedList<String>();
    	//add keywords to the list
    	for(String keyword : keywords)
    	{
    		//if the currently trendingList does not contain this keyword then
    		//add it and intialise it with a count of 0
    		//otherwise if it does exist leave it 
    		if(!trendingTracker.containsKey(keyword))
    		{
    			Keyword word = new Keyword(keyword);
    			trendingTracker.put(keyword, word);
    		}
    		
    	}
    	
    	//add keywords that have been removed during the last cycle to the toRemove list
    	for(String keyword : trendingTracker.keySet())
    	{
    		//if the set of new keywords does not contain a keyword
    		//from the trending keywords then add it to the toRemove list 
    		if(!keywords.contains(keyword))
    		{
    			toRemove.add(keyword);
    		}
    	}
    	
    	//remove all keywords from the toRemove list from the trendingTracker list
    	for(String keyword : toRemove)
    	{
    		trendingTracker.remove(keyword);
    	}
    }
    	
    //purpose: called by menu manager to begin timer which runs the task every few minutes(2 seconds in this case)
    public void start()
    {
        timer.schedule(task, 0, 2000);
    }
    
    //purpose: called by menu manager to stop the timer when user exits the program
    public void stop()
    {
    	timer.cancel();
    }
    
    //purpose: calls the update method for each observer to send message to people who need to be notified
    public void notifyTrendingObs(String policyArea, String message)
    {
    	for(Observer observer : trendingObservers)
    	{
    		observer.update(policyArea, message);
    	}
    }
    
    public void removeTrendingOb(Observer deleteObserver)
    {
        trendingObservers.remove(deleteObserver);   	
    }
    
    public void addTrendingOb(Observer newObserver)
    {
        trendingObservers.add(newObserver);    	
    }    
   
}
