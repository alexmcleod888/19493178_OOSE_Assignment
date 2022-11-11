package model;
import java.util.*;
/*Author: Alex McLeod
 *Purpose: this class acts as a container class which holds the information for a person. It allows us to set this information
 *         as well as to retrieve it.
 *Date Modified: 22/05/2019
 */

public class Person 
{
	//private fields
    private int IDNumber;//Id number which is unique for each Person object
    private String name;//String name given to the person
    private String type;//what type of person they are which can be either a volunteer, strategist or candidate
    private long mobileNum;//integer value which holds this persons mobile number if they have on
    private String twitterUser;//String for twitter username if they have one
    private String facebookID;//String for facebook username if they have one
    private Set<String> policyNotifications;//Set of String values which holds the names of each policy area it is receiving notifications for
    
    //constructor which initialise a new person initially setting contact information to 0 and null;
    public Person(int IDNumber, String name, String type)
    {
    	this.IDNumber = IDNumber;
    	this.name = name;
    	this.type = type;
    	mobileNum = 0;
    	twitterUser = null;
    	facebookID = null;
    	policyNotifications = new HashSet<String>();
    }

    //purpose: method for setting the mobile number of the person
    public void setMobileNum(long mobileNum)
    {
        this.mobileNum = mobileNum;
    }
    
    //purpose: method for setting the twitter username of a person
    public void setTwitterUser(String twitterUser)
    {
        this.twitterUser = twitterUser;
    }
    
    //purpose: method for setting facebook username
    public void setFacebookID(String facebookID)
    {
        this.facebookID = facebookID;
    }
   
    //purpose: for getting unique ID number of the person
    public int getIDNumber()
    {
    	return IDNumber;
    }
    
    //purpose: for getting the name of a person
    public String getName()
    {
    	return name;
    }
    
    //purpose: for getting the type of person
    public String getType()
    {
    	return type;
    }
    
    //purpose: for getting the mobile number of a person
    public long getMobileNum()
    {
    	return mobileNum;
    }
    
    //purpose: for getting their twitter username
    public String getTwitterUser()
    {
    	return twitterUser;
    }
    
    //purpose: for getting their facebook username
    public String getFacebookUser()
    {
    	return facebookID;
    }
    
    //purpose: to check if this person has a mobile number
    public boolean hasMobileNum()
    {
    	boolean containsMobileNum;
    	
    	containsMobileNum = true;
        if(mobileNum == 0)//if mobile number equals 0 then they do not have a phone number so return false
        {
        	containsMobileNum = false;
        }
    	return containsMobileNum;
    }
    
    //purpose: to check if this person has a twitter username
    public boolean hasTwitterUser()
    {
    	boolean containsTwitterUser;
    	
    	containsTwitterUser = true;
    	if(twitterUser == null)//if twitterUser is null then return false
    	{
    		containsTwitterUser = false;
    	}
    	return containsTwitterUser;
    }
    
    //purpose: to check if this person has a facebook username
    public boolean hasFacebookUser()
    {
    	boolean containsFacebookUser;
    	
    	containsFacebookUser = true;
    	if(facebookID == null)//return false if facebookID is null
    	{
    		containsFacebookUser = false;
    	}
    	return containsFacebookUser;
    }
    
    //purpose: for returning an organised String containing all the information of the person. Including
    //         any contact information
    public String toString()
    {
    	String output = new String("");//String for holding the organised String
    	output += "ID: " + Integer.toString(getIDNumber()) + " Name: " + getName() + " Type: " + getType() + "\n";
    	//if they have any contact information include it in the String
        if(hasMobileNum())
  	    {
  	        output += "Mobile Number: " + Long.toString(getMobileNum()) + "\n";
  	    }
  	    if(hasTwitterUser())
  	    {
  		    output += "Twitter Username: " + getTwitterUser() + "\n";
  	    }
  	    if(hasFacebookUser())
  	    {
  		    output += "Facebook Username: " + getFacebookUser() + "\n";
  	    }
  	    return output;
    }
    
    //purpose: method for checking if this person is receiving any notifications for this imported policyArea name
    public boolean containsNotification(String policyName)
    {
    	boolean contains;
    	
    	contains = false;
    	if(policyNotifications.contains(policyName))//if it does contain this policy name then return true
    	{
    		contains = true;
    	}
    	return contains;
    }
    
    //purpose: method for checking if this person has any notifications
    public boolean currentlyBeingNotified()
    {
    	boolean currentlyBeingNotified;
    	
    	currentlyBeingNotified = true;
        if(policyNotifications.isEmpty())//if they dont have any notifications then return false
        {
        	currentlyBeingNotified = false;
        }
    	return currentlyBeingNotified;
    }
    
    //purpose: method for adding a policy name to a list of policy names relating to policies it is being notified for
    public void addNotification(String policyName)
    {
    	policyNotifications.add(policyName);
    }
    
    //purpose: for removing the name of a policy from the list as it no longer is receiving notifications for it
    public void removeNotification(String policyName)
    {
    	policyNotifications.remove(policyName);
    }
     
}
