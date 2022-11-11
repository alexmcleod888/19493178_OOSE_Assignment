package view;
import java.util.*;

import model.Person;

/*Author: Alex McLeod
 *Purpose: A sub class of contact adder that contains the two hook methods used for 
 *         ContactAdder's template method addContact. Allows the output and input for
 *         adding a new facebook username for a person.
 *Date Modifed: 22/05/2019
 */

public class FacebookAdder extends ContactAdder
{
	//purpose: hook method that displays option 1 for the addContact menu. In
    //         this case it outputs option 1 to add a facebook username
	public void menuOutput()
    {
    	System.out.println("1) Add facebook ID");
    }
    
	//purpose: hook method that gives output and allows the user to input a facebook username
	//         for a person
    public void setContact(Person newPerson)
    {
    	String facebookID;
        boolean error;
    	Scanner sc = new Scanner(System.in);
        
        facebookID = null;
        do
        {   	
            error = false;
            try
            {
    	        System.out.println("Enter facebookID:");
    	        facebookID = sc.nextLine();
                if((facebookID.trim()).equals(""))
                {
                    throw new IllegalArgumentException("Error: Input is blank");
                }
            }
            catch(IllegalArgumentException e)
            {
                error = true;
                System.out.println(e.getMessage());
            }
        }while(error);			  
        newPerson.setFacebookID(facebookID);//for the imported person set the input String as their facebook username
    }
}
