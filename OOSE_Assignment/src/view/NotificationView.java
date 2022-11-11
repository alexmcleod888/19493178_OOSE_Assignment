package view;

import java.util.*;
import model.*;

/*Author: Alex McLeod
 *Purpose: class used for output and input for notification information
 *Date Modified: 22/05/2019
 */

public class NotificationView 
{
    private List<String> peopleNotified;//holds a list of people who are currently being notified
    private List<String> policiesNotified;//hold a list of policies that have people being notified about them
    private Map<Integer, Person> people;//map of all the current people
    private Map<String, PolicyArea> policies;//map of all the current policies
    
    public NotificationView(List<String> peopleNotified, List<String> policiesNotified, Map<Integer, Person> people, Map<String, PolicyArea> policies)
    {
    	this.peopleNotified = peopleNotified;
    	this.policiesNotified = policiesNotified;
    	this.people = people;
    	this.policies = policies;
    }
    
    //purpose: method that prints out all the current notification settings
    public void printNotifications() throws IllegalArgumentException
    {
    	String personID;
    	String policyName;
    	
    	if(peopleNotified.isEmpty())//if the number of people is being notified is 0 then there are no notification and throw error 
        {
	    throw new IllegalArgumentException("Error: no current notification settings\n");
	}
	System.out.println("Current notifications: ");
	    
	Iterator<String> iter1 = peopleNotified.iterator();
	Iterator<String> iter2 = policiesNotified.iterator();
	    
	//iterate through both lists of people being notified and policies with notifications and output them
	//to output the current notification settings
	while(iter1.hasNext())
	{
	    personID = (String)iter1.next();
	    policyName = (String)iter2.next();
	    System.out.println("ID number: " + personID + " notified of: " + policyName);
        }	
    }
    
    //purpose: method allowing the user to enter the ID of a person from current people and get that person
    public String personInput()
    {
    	String personToRemoveID;
        boolean error;
      	Scanner sc = new Scanner(System.in);
        personToRemoveID = null;
      	do
        { 
            error = false;
            try
            {
    	        System.out.println("Enter the ID of a person:");
	        personToRemoveID = sc.nextLine();
               
	        if(!people.containsKey(Integer.parseInt(personToRemoveID)))//if people Map doesn't contain that ID throw exception
	        {
                    error = true;
	            throw new IllegalArgumentException("Error: person does not exist\n");
	        }
            }
            catch(IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
        }while(error);
        
	return personToRemoveID;
    }
    
    //purpose: method allowing the user to enter the name of a policy from current policies and get that policy
    public String policyInput()
    {
    	String policyToRemoveName;
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("Enter the name of a policy:");
	policyToRemoveName = sc.nextLine();
	if(!policies.containsKey(policyToRemoveName))//if policies map doesnt contain the input policy name then throw exception
	{
	    throw new IllegalArgumentException("Error: policy does not exist\n");
	}
	return policyToRemoveName;
    }
    
}
