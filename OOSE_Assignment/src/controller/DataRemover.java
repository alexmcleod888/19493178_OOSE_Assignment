package controller;
import java.util.*;


import model.Person;
import model.PolicyArea;
import view.*;

/*Author: Alex McLeod
 *Purpose: DataRemoving class used for performing removal operations where by a user wants to remove an existing person
 *         policyArea, keyword or talking point.
 *Data Modified: 22/05/2019
 */

public class DataRemover implements DataManipulator
{
	
	private PolicyView policyView;//object reference for allowing input and output for policy information
	private PeopleView peopleView;//object reference allowing input and output for person information
	private KeywordsView keywordsView;//object reference allowing input and output for keyword information
	private TalkingPointsView talkingPointsView;//object reference allowing input and output for talking point information
	private Map<Integer, Person> people;//reference to the list of currently added people
	private Map<String, PolicyArea> policies;//reference to the list of currently added policies
	private Set<String> keywords;//reference list to all current keywords within each policy
	private Set<String> talkingPoints;//reference list to all current talking points within each policy
 	
	//constructor for dependency injection and initialisation of objects
	public DataRemover(Map<Integer, Person> people, Map<String, PolicyArea> policies, Set<String> keywords, Set<String> talkingPoints, PolicyView policyView, PeopleView peopleView)
	{
		this.keywords = keywords;
		this.talkingPoints = talkingPoints;
		this.people = people;
		this.policies = policies;
		this.keywordsView = new KeywordsView(keywords);
		this.talkingPointsView = new TalkingPointsView(talkingPoints);
		this.policyView = policyView;
		this.peopleView = peopleView;
	}
	
	//purpose: method allowing dataRemover to know which remove option to perform
	public void selectOption(int selection)
	{
		switch(selection)
		{
		    case 1:
		        removePerson();//if 1 chosen begin remove a person operation
		        break;
		    case 2:
		    	removePolicy();//if 2 chosen begin remove a policy operation
		    	break;
		    case 3:
		        removeTalkingPoint();//if 3 chosen begin remove a talking point operation
		        break;
		    case 4:
		        removeKeyword();//if 4 chosen begin remove a keyword operation
		        break;		    	  
	    }
	}
	
	//purpose: method for allowing a user to remove an existing person from the list of people within the program
	public void removePerson()
	{
		Person personToRemove;
		
		try
		{
			personToRemove = peopleView.personChooser();//call to allow user to select a person
			people.remove(personToRemove.getIDNumber());//remove the person from the people map
			System.out.println("removed ID: " + personToRemove.getIDNumber());
			
		}
		catch(IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
		catch(EmptyException e)
		{
			System.out.println(e.getMessage());
		}
		catch(InputMismatchException e)
		{
			System.out.println("Error: incorrect data type entered");
		}
		
	}
	
	//purpose: method for allowing a user to remove an existing policyArea from the list of policyArea's within the program
	public void removePolicy()
	{
		PolicyArea chosenPolicy;
		try
		{
		    chosenPolicy = policyView.policyChooser();//call policyChooser from policyview to allow the user to select a policy
		    if(chosenPolicy.hasTalkingPoints() || chosenPolicy.hasKeywords())//if chosen policyArea has talking points or keywords
		    	                                                             //begin double check procedure to ask user if they are
		    	                                                             //sure they want to remove the input policy
		    {
			    int selection = 0;

	        	selection = policyView.policyDelete(chosenPolicy);//output options to delete or exit to user and recieve input
	        	                                                  //of either 1 to delete or 2 to skip deletion
			    switch(selection)
			    {
			        case 1://if user chooses 1 then continue deletion of policy area and its associated keywords
			    	    chosenPolicy.removeTalkingPoints(talkingPoints);
			    	    chosenPolicy.removeKeywords(keywords);
			    	    policies.remove(chosenPolicy.getName());
			    	    System.out.println("Policy Area and related talking points and keywords removed!\n");
			    	    break;
			        case 2://if user selects 2 then skip deletion
			    	    break;
			    }	
		    }
		    else// if policy Area doesn have any talking points or keywords then it is fine to just delete
		    {
			    System.out.println("Policy contains no talking points or keywords.\nPolicy removed.");
			    policies.remove(chosenPolicy.getName());
		    }
		}
		catch(EmptyException e)
		{
			System.out.println(e.getMessage());
		}
		catch(IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	//purpose: method for removing a specific keyword from the list of keywords and its policyArea
	public void removeKeyword()
	{
		String keyword;
		
		try
		{
		    keyword = keywordsView.keywordChooser();//call keywordChooser to allow user to select a specific keyword to remove
		    //cycle through each policy area and check to find the policy that contains this keyword
		    for(PolicyArea policy : policies.values())
		    {
		        if(policy.containsKeyword(keyword))//once found remove the keyword from that policyArea
		        {
		        	policy.removeKeyword(keyword);
		        }
		    }	
		    keywords.remove(keyword);//remove keyword from the total list of all keywords
		}
		catch(EmptyException e)
		{			
			System.out.println(e.getMessage());
		}
	}
	
	//purpose: method for removing talking points from the list of talking points and its specific policyArea
	public void removeTalkingPoint()
	{
		String talkingPoint;
		
		try
		{
			talkingPoint = talkingPointsView.talkingPointChooser();//call talkingPointChooser to allow user to select a specific talking point to remove
			//cycle through each policy area and check to find the policy that contains this talking point
			for(PolicyArea policy : policies.values())
			{
				if(policy.containsTalkingPoint(talkingPoint))//once found remove the talking point from that policyArea
				{
					policy.removeTalkingPoint(talkingPoint);
				}
			}
			talkingPoints.remove(talkingPoint);//remove talking point from the total list of talking points
		}
		catch(EmptyException e)
		{
			System.out.println(e.getMessage());
		}
	}

}
