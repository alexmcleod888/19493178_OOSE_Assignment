package model;
import java.util.*;

/*Author: Alex McLeod
 *Purpose: this class acts as a container class which holds the information for a policyArea. It allows us to set this information
 *         as well as to retrieve it.
 *Date Modified: 22/05/2019
 */

public class PolicyArea
{
    //private fields
    private String name;// unique name for this policyArea
    private Set<String> talkingPoints;//a set of the different talking points related to this Policy Area
    private Set<String> keywords;//a set of the different keywords related to this Policy Area
  
    
    //contructor for initialising a new policy area
    public PolicyArea(String name)
    {
    	this.name = name;
    	this.talkingPoints = new HashSet<String>();
    	this.keywords = new HashSet<String>();
    }
      
    //purpose: for getting the name of this policy area
    public String getName()
    {
    	return name;
    }
     
    //purpose: method for adding a new talking point to the list of talking points
    public void addTalkingPoint(String talkingPoint) throws IllegalArgumentException
    {
    	if(talkingPoints.contains(talkingPoint))//checking that the talking point doesnt already exist
    	{
    		throw new IllegalArgumentException("Error: talking point already exists\n");
    	}
    	talkingPoints.add(talkingPoint);
    }
    
    //purpose: method for adding a new keyword to the list of keywords
    public void addKeyword(String keyword) throws IllegalArgumentException
    {
    	if(keywords.contains(keyword))//checking that the keyword doesnt already exist
    	{
    		throw new IllegalArgumentException("Error: keyword already exists\n");
    	}
    	keywords.add(keyword);
    }
    
    //purpose: method for returning a structure String of all the information for this policy area
    public String toStringPolicy()
    {
    	String output = new String("");
    	
    	output += "Policy Area: " + getName() + "\n";
    	output += "Talking Points: \n";
    	output += toStringTalkingPoints();
    	output += "Keywords: \n";
    	output += toStringKeywords();
    	return output;
    }
    
    //purpose: returns a structure String of all the talking points of this policy area
    public String toStringTalkingPoints()
    {
    	String output = new String("");

    	for(String talkingPoint : talkingPoints)
    	{
    		output += "   " + talkingPoint + "\n"; 
    	}
    	return output;
    }
    
    //purpose: returns a structured String of all the keywords of this policy area
    public String toStringKeywords()
    {
    	String output = new String("");

    	for(String keyword : keywords)
    	{
    		output += "   " + keyword + "\n"; 
    	}
    	return output;
    }
    
    //purpose: method for checking whether this policy area has any talking points
    public boolean hasTalkingPoints()
    {
    	boolean containsTalkingPoints;
    	
    	containsTalkingPoints = true;
        if(talkingPoints.isEmpty())//if empty return false for no talking points
        {
        	containsTalkingPoints = false;
        }
        return containsTalkingPoints;
    }
    
    //purpose: method for checking whether this policy area has any keywords
    public boolean hasKeywords()
    {
        boolean containsKeywords;
    	
    	containsKeywords = true;
        if(keywords.isEmpty())//if return empty then there are no keywords hence return false
        {
        	containsKeywords = false;
        }
        return containsKeywords;
    }   
    
    //purpose: method for removing a set of imported talking points from this policy areas list of talking points
    public void removeTalkingPoints(Set<String> allTalkingPoints)
    {
    	for(String talkingPoint : talkingPoints)
    	{
    		allTalkingPoints.remove(talkingPoint);
    	}
        talkingPoints.clear();
    }
    
    //purpose: method for removing a set of imported keywords from this policy areas list of keywords
    public void removeKeywords(Set<String> allKeywords)
    {
    	for(String keyword : keywords)
    	{
    		allKeywords.remove(keyword);
    	}
    	keywords.clear();
    }
    
    //purpose: method for removing a specific imported talking point from the list
    public void removeTalkingPoint(String talkingPoint)
    {
    	talkingPoints.remove(talkingPoint);
    }
    
    //purpose: method for removing a specific import keyword from the list
    public void removeKeyword(String keyword)
    {
    	keywords.remove(keyword);
    } 
    
    //purpose: method to check whether this policy contains the imported keyword
    public boolean containsKeyword(String keywordToCheck)
    {
    	boolean contains;
    	
    	contains = false;
    	for(String keyword : keywords)//cycle through each keyword
    	{
    		if(keywordToCheck.equals(keyword))//if one of these equals the imported keyword then return true
    		{
    			contains = true;
    		}
    	}
    	return contains;
    }
    
    //purpose: method to check whether this policy contains the imported talking point
    public boolean containsTalkingPoint(String talkingPointToCheck)
    {
    	boolean contains;
    	
    	contains = false;
    	for(String talkingPoint : talkingPoints)//cycle through each talking point
    	{
    		if(talkingPointToCheck.equals(talkingPoint))//if one of these equals the imported talking point then return true
    		{
    			contains = true;
    		}
    	}
    	return contains;
    }
} 
