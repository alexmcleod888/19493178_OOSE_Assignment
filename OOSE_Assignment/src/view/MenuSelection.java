package view;
import controller.*;
import java.util.*;

import model.Person;
import model.PolicyArea;

/*Author: Alex McLeod
 *Purpose: abstract class that holds a template method that displays a particular menu for a user to choose from
 *         and uses a particular data manipulator based on the hook methods from the specific sub class
 *Date Modified: 22/05/2019
 */

public abstract class MenuSelection
{	
	//purpose: hook method that displays a particular method and allows the user to choose from this menu
	//         performing particular operation based on the data manipulator being used.
    public void chooseOption()
    {   	
    	int selection;
    	DataManipulator dataManipulator;
    	selection = 0;
    	
    	dataManipulator = returnDataManipulator();    	
    	do
    	{
    		try
    		{
    			selection = menuDisplay();//display the menu to be used from the hook method
    			if(selection != 5)//if the user hasn't entered 5 to exit the menu then they must have chosen an option 
    			{
    			    dataManipulator.selectOption(selection);//using the dataManipulator perform an operation based on 
    			                                            //the integer entered
    			}
    		}
    		catch(IllegalArgumentException e)
			{
				System.out.println(e.getMessage());
			}
    	}while(selection != 5);//unless the user has chosen 5 to exit keep repeating the menu
    }
    
    //hook methods used within the template chooseOption method
    protected abstract int menuDisplay();//displays particular menu
    protected abstract DataManipulator returnDataManipulator();//returns particular data manipulator
}
