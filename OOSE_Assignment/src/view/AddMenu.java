package view;
import java.util.*;

/*Author: Alex McLeod
 *Purpose: A sub class of Menu Selection containing the methods menuDisplay and returnDataManipulator which act as hooks for 
 *         Menu Selection classes template method. This class represents the menu displayed when the user wants to add a new piece
 *         of data
 *Date Modified: 22/05/2019
 */

import controller.DataAdder;
import controller.DataManipulator;
import model.Person;
import model.PolicyArea;

public class AddMenu extends MenuSelection
{
	private DataAdder dataAdder;//holds the reference to the dataAdder object which DataManipulator returns
	                            //used within MenuSelections template method
	//constructor for initialising dataAdder
	public AddMenu(DataAdder dataAdder)
	{
		this.dataAdder = dataAdder;
	}

	//purpose: hook method which displays the menu for the adding a new piece of data. It also excepts input from the 
	//         user for whichever option they want
    public int menuDisplay()
    {
        int selection = 0;
        String error = new String(" ");
        Scanner sc = new Scanner(System.in);
        
        do
        {
        	try
        	{
        		System.out.println(error);//error equals nothing if it is the first cycle of the loop
        		                          //otherwise error will print out an error message
        		System.out.println("1) Add person");
        		System.out.println("2) Add policy area");
        		System.out.println("3) Add talking point");
        		System.out.println("4) Add keyword");
        		System.out.println("5) Return to previous menu");
        		selection = sc.nextInt();//user input for menu selection
        		error = "Error: input must be in the range 1 to 5";
        	}
        	catch(InputMismatchException e)
        	{
        		String flush = sc.nextLine();
        		error = "Error: wrong input data type, input must be an integer";
        	}
        }
        while(selection < 1 || selection > 5);//while the user hasnt input the right integer output error message,
                                              //redisplay menu and ask for input
     
        return selection;
    }
    
    //purpose: hook method which returns the data manipulator relating to this menu. In this case  
    //the dataAdder object reference
    public DataManipulator returnDataManipulator()
	{
	    return dataAdder;	
	}
    
}
