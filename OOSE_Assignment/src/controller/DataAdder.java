package controller;
import java.util.*;

import model.Person;
import model.PolicyArea;
import view.*;
import view.Observer;

/*Author: Alex McLeod
 *Purpose: DataAdding class used for performing adding operations where by a user wants to add a new person
 *         policyArea, keyword, talking point or add data via file. It also contains observers for people who want to be 
 *         notified when particular keywords or talkingPoints are added by the user. Also implements the 
 *         Data Manipulator interface forcing it to implement the selectOption method used by all DataManipulator's
 *         to select which option to use. 
 *Data Modified: 22/05/2019
 */

public class DataAdder implements DataManipulator
{
	//private fields
	private int currentAutoID;//used to assign contact ID's to new people. incremented each time a new person is added
	                          //to ensure everyones ID is unique.
	private Map<Integer, ContactAdder> contactAdder;//used to hold the different contactAdd operations depending if
	                                                //a new person to be added wants to add particular contact details
	private PolicyView policyView;//object reference for allowing input and output for policy information
	private PeopleView peopleView;//object reference allowing input and output for person information
	private Map<Integer, Person> people;//reference to the list of currently added people
	private Map<String, PolicyArea> policies;//reference to the list of currently added policies
	private ArrayList<Observer> keywordObservers;//reference list of people to be notified when a new keyword is added
	private ArrayList<Observer> talkingPointObservers;//reference list of people to be notified when a new talking point is added
	private Set<String> keywords;//reference list to all current keywords within each policy
	private Set<String> talkingPoints;//reference list to all current talking points within each policy
	
	//constructor for dependency injection and intialisation of objects
	public DataAdder(Map<Integer, Person> people, Map<String, PolicyArea> policies, Set<String> keywords, Set<String> talkingPoints, PolicyView policyView, PeopleView peopleView)
	{
		currentAutoID = 1;//set the first ID as 1
		
		this.keywords = keywords;
		this.talkingPoints = talkingPoints;
		this.people = people;
		this.policies = policies;
		keywordObservers = new ArrayList<Observer>();
		talkingPointObservers = new ArrayList<Observer>();
		this.policyView = policyView;
		this.peopleView = peopleView;
		contactAdder = new HashMap<Integer, ContactAdder>();
		//initialise the three different types of contact add operations
		contactAdder.put(1, new MobileAdder());
		contactAdder.put(2, new TwitterAdder());
		contactAdder.put(3, new FacebookAdder());
	}
	
	//purpose: method allowing dataAdder to know which add option to perform
	public void selectOption(int selection)
	{
		switch(selection)
		{
		    case 1://if 1 is chosen add a new person
		        addPerson();
		        break;
		    case 2://if 2 add a new policy
		    	addPolicy();
		    	break;
		    case 3://if 3 begin add word operation
		    	addWord(3);
		        break;
		    case 4://if 4 begin add word operation also
		    	addWord(4);
		        break;		    	  
	    }
	}
	
	//purpose: method for allowing a user to add a new person to the list of people within the program
    public void addPerson()
    {
        int IDNumber;
        String name;
        String type;
		
        IDNumber = currentAutoID;//assign there ID number given by the program 
        name = peopleView.personAddInput();//retrieve name input of new person via peopleView
        type = peopleView.personTypeInput();//retrieve the type input of person via peopleView 
            
        Person newPerson = new Person(IDNumber, name, type);//construct new person
        
        //call contact added methods to add contact information specifed by the user
        (contactAdder.get(1)).addContact(newPerson);
        (contactAdder.get(2)).addContact(newPerson);
        (contactAdder.get(3)).addContact(newPerson);
        
        currentAutoID += 1;//increment ID number so the next new person does not recieve the same ID
        people.put(newPerson.getIDNumber(), newPerson);//add person to the list of people in the program
        
        
    }
    
    //purpose: method for allowing a user to add a new policy to the list of policies within the program
    public void addPolicy()
    {
    	String name;
    	
        policyView.viewAll();//use policyView to view all current policies
    	name = policyView.policyInput();//use policy view to retrieve the input of new policy name
    	PolicyArea newPolicy = new PolicyArea(name);//create new policy
    	policies.put(name, newPolicy);//add to the list of policies in the program
    }
    
    //purpose: method for allowing the user to add a new talking point to a particular policy
    private void addTalkingPoint() throws EmptyException, IllegalArgumentException
    {
    	PolicyArea chosenPolicy;
    	String talkingPoint;
    	
    	chosenPolicy = policyView.policyChooser();//call policyChooser from policyView to allow the use to select an existing policy
    	talkingPoint = policyView.talkingPointInput();//call talkingPointInput from policyView to allow the user to enter a new talking point    	 
    	chosenPolicy.addTalkingPoint(talkingPoint);//add the talking point to the chosen policy area
    	talkingPoints.add(talkingPoint);//add talkingpoint to the list of all talking points in the program
    	//notify all talking point observers that a new talking point has been added to the specific policy area 
    	notifyTalkingPointObs(chosenPolicy.getName(), "New talking point '" + talkingPoint + "' added to " + chosenPolicy.getName());   	    	
    }
    
    //purpose: method for allowing the user to add a new keyword to a particular policy
    private void addKeyword() throws EmptyException, IllegalArgumentException
    {
    	PolicyArea chosenPolicy;
    	String keyword;
    		   
    	chosenPolicy = policyView.policyChooser();//call policyChooser from policyView to allow the use to select an existing policy    	       
    	keyword = policyView.keywordInput();//call keywordInput from policyView to allow the user to enter a new keyword
    	chosenPolicy.addKeyword(keyword);//add keyword to the chosen policy area
    	keywords.add(keyword);//add keyword to the list of all keyword in the program
    	//notify all keyword observers that a new keyword has been added to the specific policy area 
    	notifyKeywordObs(chosenPolicy.getName(), "New Keyword '" + keyword + "' added to " + chosenPolicy.getName());
    }
   
    //purpose: called when the user want to add words such as talking points or keywords. By using this method it reduces the amount
    //repeated by addTalkingPoint and addKeyword
    public void addWord(int selection)//using a integer to decide whether to add a talkingPoint or keyword
                                      //allows a reduction in code
    {
    	boolean error;   	

    	do
    	{
    		error = false;//reset error to false after every cycle of while loop
    		try
    		{
    			if(policies.isEmpty())//if there are no policies throw an exception
    			{   				
    				throw new EmptyException("Error: There are currently no policies\n");
    			}
    			
    	        if(selection == 3)//if selection is equal to three then begin adding to talking points
    	        {
    	        	addTalkingPoint();
    	        }
    	        else//otherwise selection must be 4 so begin adding to keywords
    	        {
    	        	addKeyword();
    	        }
    		}
    	    catch(IllegalArgumentException e)
    	    {
    	    	error = true;//if IllegalArgumentException caught then there must be policies but the one selected was invalid
    	    	             //or some other problem occured so let user try again
    	    	System.out.println(e.getMessage());
    	    }
    		catch(EmptyException e)//there are no policies so words can't be added so don't set error to true and exit method
    		{
    			System.out.println(e.getMessage());
    		}
    	}while(error);
    }
    
    /*STUB*/
    //purpose: stub method that simulates adding a person from file
    public void addPersonFromFile(String name, String type, String[] contacts)
    {
    	int IDNumber;
    	
    	IDNumber = currentAutoID;
    	Person person = new Person(IDNumber, name, type);//create person from imported values
    	//depending on how many elements are in the array determines how many contacts need to be added
    	if(contacts.length == 1)//if 1 then only 1 contact is added - mobile number
    	{
    		person.setMobileNum(Integer.parseInt(contacts[0]));
    	}
    	else if(contacts.length == 2)//if 2 then only 2 contacts are added - mobile and twitter
    	{
    		person.setMobileNum(Integer.parseInt(contacts[0]));
    		person.setTwitterUser(contacts[1]);
    	}
    	else if(contacts.length == 3)//if 3 then 3 contacts added - mobile, twitter and facebook
    	{
    		person.setMobileNum(Integer.parseInt(contacts[0]));
    		person.setTwitterUser(contacts[1]);
    		person.setFacebookID(contacts[2]);
    	}
    	currentAutoID++;//increment ID num as new person has been added
    	people.put(IDNumber, person);//add to the list of people
    }
    
    /*STUB*/
    //purpose: stub method that simulates adding a policy from file
    public void addPolicyFromFile(String name, String[] newTalkingPoints, String[] newKeywords)
    {
    	PolicyArea policy = new PolicyArea(name);//create new policy from imported name
    	
    	for(int i = 0; i < newTalkingPoints.length; i++)//add talking points to policy given in imported array
    	{
    		policy.addTalkingPoint(newTalkingPoints[i]);//add to policy
    		talkingPoints.add(newTalkingPoints[i]);//add to list of talking points
    	}
    	for(int j = 0; j < newKeywords.length; j++)//add keywords to policy given in imported array
    	{
    		policy.addKeyword(newKeywords[j]);//add to policy
    		keywords.add(newKeywords[j]);//add to list of keywords
    	}
    	policies.put(name, policy);//add to list of policies
    }
    
    //purpose: method for adding Observers to list of keyword add observers
    //when the user wants a particular person to be notified when a new keyword is
    //added to a particular policy area
    public void addKeywordOb(Observer newObserver)
    {
        keywordObservers.add(newObserver);
    	
    }
    
    //purpose: method for adding observers to list of talking point add observers
    //when the user wants a particular person to be notified when a new talking point is
    //added to a particular policy area
    public void addTalkingPointOb(Observer newObserver)
    {
        talkingPointObservers.add(newObserver);
    	
    }
    
    //purpose: method for removing observers from list of keyword add observers. Used when a user no 
    //longer wants a person to be notified when a particular keyword is added to a 
    //specific policy
    public void removeKeywordOb(Observer deleteObserver)
    {
    	keywordObservers.remove(deleteObserver);
    }
    
    //purpose: method for removing observers from list of talking point add observers. Used when a user no 
    //longer wants a person to be notified when a particular talking point is added to a 
    //specific policy
    public void removeTalkingPointOb(Observer deleteObserver)
    {
        talkingPointObservers.remove(deleteObserver);
    	
    }
    
    //purpose: method to notify all keyword observers. Used when a keyword is added.  
    public void notifyKeywordObs(String policyArea, String message)
    {
    	//update method called for all observers in list to send messages to specific people
    	for(Observer observer : keywordObservers)
    	{
    		observer.update(policyArea, message);
    	}
    }
    
    //purpose: method to notify all talking point observers. Used when a talkingpoint is added. 
    public void notifyTalkingPointObs(String policyArea, String message)
    {
    	//update method called for all observers in list to send messages to specific people
    	for(Observer observer : talkingPointObservers)
    	{
    		observer.update(policyArea, message);
    	}
    }
    
}
