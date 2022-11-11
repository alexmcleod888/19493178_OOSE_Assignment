package view;
import java.util.*;

import model.Person;

/*Author: Alex McLeod
 *Purpose: abstract class that uses a template method and calls two other hook methods depending on the 
           sub class used. This template is used for menu outputs and inputs for the
           different contacts that can be added. Allowing a user to either add the contact or 
           skip adding the particular type of contact.
  Date Modified: 22/05/2019
 */

public abstract class ContactAdder 
{
	//purpose: template method used by each contactAdder object to choose whether
	//to add the particular contact or skip adding the contact. Uses hooks to display 
	//the particular contact type being asked about and to set the new contact detail
	//if they choose to
	public void addContact(Person newPerson)
	{
		int selection = 0;
		String error = new String(" ");
		Scanner sc = new Scanner(System.in);
	    try
        {
            do
            {
            	try
            	{
        	        System.out.println(error);//outputs error message if it isnt the first cycle of the loop
    		        menuOutput();//displays option 1 depending on the contact detail being asked about. e.g. mobile number
    		                     //twitter username or facebook username
    		        System.out.println("2) skip");            	
    	            selection = Integer.parseInt(sc.nextLine());
                    error = "Error: input must be 1 or 2";
            	}
            	catch(NumberFormatException e)
            	{
            		System.out.println("Error: incorrect data type entered");
            	}
                
            }while(selection < 1 || selection > 2);//if integer entered is invalid display error message and
                                                   //ask again
		    switch(selection)
		    {
		        case 1://if 1 is chosen then user wants to add this contact type to the person. call setContact to begin
		        	   //adding that particular contact type
		    	    setContact(newPerson);
		    	    break;
		        case 2://if 2 is chosen then skip adding a new contact detail
		    	    break;
		    }
	    }
	    catch(IllegalArgumentException e)
    	{
		    System.out.println(e.getMessage());
	    }
	}
    
	protected abstract void menuOutput();//abstract hook method for output the particular contact option to the user
	protected abstract void setContact(Person newPerson);//abstract hook method for setting the particular contact type
	
}
