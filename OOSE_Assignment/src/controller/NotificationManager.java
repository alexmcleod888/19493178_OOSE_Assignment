package controller;

import java.util.*;
import model.*;
import view.*;
import view.Observer;

/*Author: Alex McLeod
 *Purpose: Class used for managing notifications. Used when the user selects the add Notification or remove notification options
 *         Allowing the user to add or remove notifications.
 *Date Modified: 22/05/2019
 */

public class NotificationManager 
{
	private Map<Integer, Person> people;//reference to the list of current people. User selects from this list the person they want to remove
	                                    //or add a notification for
	private Map<String, PolicyArea> policies; //reference to the list of currently added policies. User selects from this list the policy they want to remove
                                              //or add a notification for
	private DataAdder dataAdder;//reference to dataAdder. Used to add or remove observers from dataAdders lists of addKeyword and addTalkingPoint observers
	
	private TrendingDetector trendingDetector;//reference to trendingDetector. Used to add or remove observers from trendingDetectors list of trendingObservers 
	
	private PolicyView policyView;//reference for allowing input and output for policy information
	private PeopleView peopleView;//reference allowing input and output for person information
	private Messenger messenger;//imported into the constructor of new Observers so that new observers can use it as a means of sending messages

	private List<String> peopleNotified;//reference to a list of people who are currently being notified. Used when checking if anyones is currently being notified
	private List<String> policiesNotified;//reference to a list of policyAreas which has people who are being notified of their changes
	                                      //Used when checking if a policyArea has people being notified of its changes
	private NotificationView notificationView;//reference used for notification input and output
	
	//Map within a Map used so that we can get a particular observer based on a particular String (policyName) and Integer (personID).
	//To get a particular observer we search using a particular persons ID in observer2 which will return a observer1 Map, then within this map we
	//search using a particular policy name which will return the observer object we were looking for
	private Map<String, Observer> observers1;
	private Map<Integer, Map<String, Observer>> observers2;
	
	//constructor for dependency injection and initialising objects
	public NotificationManager(Map<Integer, Person> people, Map<String, PolicyArea> policies, DataAdder dataAdder, TrendingDetector trendingDetector, PolicyView policyView, PeopleView peopleView)
	{
		this.people = people;
		this.policies = policies;
		this.dataAdder = dataAdder;
		this.trendingDetector = trendingDetector;

		messenger = new Messenger();			
		
		this.policyView = policyView;
		this.peopleView = peopleView;
		peopleNotified = new LinkedList<String>();
		policiesNotified = new LinkedList<String>();
		
		observers1 = new HashMap<String, Observer>();
		observers2 = new HashMap<Integer, Map<String, Observer>>();
		
		notificationView = new NotificationView(peopleNotified, policiesNotified, people, policies);
	}
	
	//purpose: method used when user wants to add a new notification setting for a particular person and policy area
    public void addNotification()
    {
    	PolicyArea chosenPolicy;//holds the PolicyArea that user wants to add a notification for
    	Person chosenPerson;//hold the person the user wants notifications sent to
    	Observer observer;//holds the new observer object created
    	boolean error;//tells while loop whether there has been an error in the system and needs to reset
    	
    	observer = null;
    	
    	do
    	{
    		error = false;//set to false again if there was previously an error and it was sent to true
    	    try
    	    {    		
    		    chosenPerson = peopleView.personChooser();//use personChooser to get the user to input a valid person
    		    chosenPolicy = policyView.policyChooser();//user policyChooser to get the user to input a valid policyArea
    		  
    		    //if the chosen person is already being notified of changes to the policyArea then throw error
    		    if(chosenPerson.containsNotification(chosenPolicy.getName()) /*&& chosenPolicy.containsNotification(chosenPerson.getIDNumber())*/)
    		    {
    		        throw new IllegalArgumentException("Error: notification setting already implemented\n");
    		    }
    		    
    		    //create an observer based on the type of person they are for a specific policy. This is because each type of person is 
    		    //notified of different things. Depending on the type of person they will be notified of new talking points, keywords 
    		    //and/or trending keywords
    		    if(chosenPerson.getType().equals("volunteer"))
    		    {    		    	
    		    	observer = new VolunteerOb(chosenPerson, chosenPolicy, dataAdder, trendingDetector, messenger);           
    		    }
    		    else if(chosenPerson.getType().equals("strategist"))
    		    {
    		    	observer = new StrategistOb(chosenPerson, chosenPolicy, dataAdder, trendingDetector, messenger);
    		    }
    		    else if(chosenPerson.getType().equals("candidate"))
    		    {
    		    	observer = new CandidateOb(chosenPerson, chosenPolicy, dataAdder, messenger);
    		    }
    		    	
    		    peopleNotified.add(Integer.toString(chosenPerson.getIDNumber()));//add the person being notifieds ID to the list of people being notified
    		    policiesNotified.add(chosenPolicy.getName());//add the policyArea to the list of policies being notified
    		    
    		    //add the person and policy area to the observer map so a specific observer can be searched for
    		    observers1.put(chosenPolicy.getName(), observer);//
    		    observers2.put(chosenPerson.getIDNumber(), observers1);
    		    
    		    chosenPerson.addNotification(chosenPolicy.getName());
                    System.out.println("Notification Added! ");
    		    //chosenPolicy.subscriberIncrease(chosenPerson.getIDNumber());
    		
    	    }
    	    catch(EmptyException e)
    	    {
    		    System.out.println(e.getMessage());
    	    }
    	    catch(IllegalArgumentException e)
    	    {
    	    	System.out.println(e.getMessage());
    	    }
    	    catch(InputMismatchException e)
    	    {
    	    	error = true;
    	    	System.out.println("Error: an integer ID number was not given\n");
    	    }
    	}while(error);   	    	
    }
    
    //purpose: method used when user wants to remove a notification setting for a particular person and policy area
    public void removeNotification()
    {
    	String personToRemoveID;//ID of person user wants to remove notification for
    	String policyToRemoveName;//name of policyArea user wants to remove a person from being notified for
    	Person personToRemove;//person object user wants to remove notification for
    	PolicyArea policyToRemove;//policyArea object a person wants to remove a notification for
    	Observer deleteObserver;//holds the observer to be deleted
    	
    	try
    	{
    		notificationView.printNotifications();//printNotifications method used to display the current notification settings
    	    personToRemoveID = notificationView.personInput();//personInput to retrieve person ID to remove notification for
    	    policyToRemoveName = notificationView.policyInput();//policyInput to retrieve policy name to remove notification for
    	    
    	    //get the specific person and policy Object based on a persons ID number and policy name
    	    personToRemove = people.get(Integer.parseInt(personToRemoveID));
    	    policyToRemove = policies.get(policyToRemoveName);
    	        	       	    
    	    //use containsNotification of the person object to check whether it is currently being notified on the chosen policyArea
    	    //if it isn't being notified then throw error exception
    	    if(!personToRemove.containsNotification(policyToRemoveName) /*|| !policyToRemove.containsNotification(Integer.parseInt(personToRemoveID)*/)
         	{
    		    throw new IllegalArgumentException("Error: setting does not exist");
    	    }
    	
    	    //remove notification from list of policies the chosen person is being notified of
    	    personToRemove.removeNotification(policyToRemoveName);
    	    /*policyToRemove.subscriberDecrease(Integer.parseInt(personToRemoveID));*/
    	    
    	    //get the observer to be deleted
    	    deleteObserver = (observers2.get(personToRemove.getIDNumber())).get(policyToRemove.getName());
    	    //depending on the type of person who is having there notification removed. Specific people are being notified of
    	    //different things so observers will have to be removed from different observer lists
    	    if(personToRemove.getType().equals("volunteer"))
    	    {    		   
    	    	//if a volunteer remove from every list of observers
    		    dataAdder.removeKeywordOb(deleteObserver);
    		    trendingDetector.removeTrendingOb(deleteObserver);
    		    dataAdder.removeTalkingPointOb(deleteObserver);
    	    }
    	    else if(personToRemove.getType().equals("strategist"))
    	    {    		
    	    	//if strategist remove from add and trending keyword observer lists
    		    dataAdder.removeKeywordOb(deleteObserver);
    		    trendingDetector.removeTrendingOb(deleteObserver);
    	    }
    	
    	    else if(personToRemove.getType().equals("candidate"))
    	    {
    	    	//if candidate just remove from add keyword observer list
    		    dataAdder.removeKeywordOb(deleteObserver);
    	    }
    	    
    	    //remove from notification lists
    	    peopleNotified.remove(personToRemoveID);
    	    policiesNotified.remove(policyToRemoveName);
            System.out.println("Notification Removed");
    	}
    	catch(IllegalArgumentException e)
    	{
    		System.out.println(e.getMessage());
    	}
    	catch(InputMismatchException e)
    	{
    		System.out.println("Error: incorrect input given\n");
    	}
    	
    }
}
