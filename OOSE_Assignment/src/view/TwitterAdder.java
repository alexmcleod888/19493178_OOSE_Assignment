package view;
import java.util.*;

/*Author: Alex McLeod
 *Purpose: A sub class of contact adder that contains the two hook methods used for 
 *         ContactAdder's template method addContact. Allows the output and input for
 *         adding a new twitter username for a person.
 *Date Modifed: 22/05/2019
 */

import model.Person;

public class TwitterAdder extends ContactAdder
{
	//purpose: hook method that displays option 1 for the addContact menu. In
    //         this case it outputs option 1 to add a twitter username
	public void menuOutput()
    {
    	System.out.println("1) Add twitter username");
    }
    
	//purpose: hook method that gives output and allows the user to input a twitter username for
    //         a person
    public void setContact(Person newPerson)
    {
    	String twitterUser;
        boolean error;
    	Scanner sc = new Scanner(System.in);
        twitterUser = null;    	

        do
        {
            error = false;
            try
            {
    	        System.out.println("Enter twitter username:");
    	        twitterUser = sc.nextLine();
                if((twitterUser.trim()).equals(""))
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
        newPerson.setTwitterUser(twitterUser);//for the imported person set the input number as their twitter username
        
    }
}
