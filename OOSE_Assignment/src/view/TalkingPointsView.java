package view;

import java.util.Scanner;
import java.util.Set;

import controller.EmptyException;

/*Author: Alex McLeod
 *Purpose: class used for output and input for talking point information
 *Date Modified: 22/05/2019
 */

public class TalkingPointsView 
{
    private Set<String> talkingPoints;//reference used to point to the talking points from all policies
	
    //constructor for initialising the set of talkingPoints
	public TalkingPointsView(Set<String> talkingPoints)
	{
		this.talkingPoints = talkingPoints;
	}
	
	//purpose: method for allowing a user to choose a talking point from the set of current talking points
	public String talkingPointChooser() throws IllegalArgumentException, EmptyException
	{
		String chosenTalkingPoint;
		
		Scanner sc = new Scanner(System.in);
		
		checkEmpty();//called to check whether there are currently talking points
	    viewAll();//called to view all the current talking points
	    System.out.println("Enter a Talking Point to Select: ");
	    chosenTalkingPoint = sc.nextLine();
	    if(!talkingPoints.contains(chosenTalkingPoint))//if the current talking points map doesn't 
	    	                                           //contain the input talking point throw an exception
        {
            throw new IllegalArgumentException("Error: chosen talking point does not exist");
		}
		return chosenTalkingPoint;		
		
	}
	
	//purpose: method used to check whether there are any talking points
	public void checkEmpty() throws EmptyException
	{
		if(talkingPoints.isEmpty())
	    {
		      throw new EmptyException("Error: no current talking points");
	    }
	}
	
	//purpose: method used to view all current talking points
    public void viewAll() 
    {
    	System.out.println("Current Talking Points: ");
    	for(String word : talkingPoints)
    	{
    		System.out.println(word);
    	}
    }
}
