package view;
import java.util.*;

/*Author: Alex McLeod
 *Purpose: A sub class of contact adder that contains the two hook methods used for 
 *         ContactAdder's template method addContact. Allows the output and input for
 *         adding a new mobile number for a person.
 *Date Modifed: 22/05/2019
 */

import model.Person;

public class MobileAdder extends ContactAdder 
{
	//purpose: hook method that displays option 1 for the addContact menu. In
	//         this case it outputs option 1 to add mobile number
    public void menuOutput()
    {
    	System.out.println("1) Add mobile number");
    }
    
    //purpose: hook method that gives output and allows the user to input a mobile number for
    //         a person
    public void setContact(Person newPerson)
    {
    	long mobileNum;
        boolean error;
    	Scanner sc = new Scanner(System.in);
       
        mobileNum = 0;

        do
        {
            error = false;
    	    try
            {
    	        System.out.println("Enter mobile number:");                
    	        mobileNum = Long.parseLong(sc.nextLine());
            }
            catch(NumberFormatException e)
            {
                error = true;
                System.out.println("Error: input must be an integer value");
            }
        }
        while(error);
    			
        newPerson.setMobileNum(mobileNum);//for the imported person set the input number as their phone number
    }
}
