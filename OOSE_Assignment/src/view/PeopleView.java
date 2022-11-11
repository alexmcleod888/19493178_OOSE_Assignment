package view;

import java.util.*;
import controller.*;


import model.Person;
import model.PolicyArea;

/*Author: Alex McLeod
 *Purpose: class used for output and input for people information
 *Date Modified: 22/05/2019
 */

public class PeopleView 
{
	private Map<Integer, Person> people;//reference to the map of current people
	
	//constructor for initialising the current people
	public PeopleView(Map<Integer, Person> people)
	{
		this.people = people;
	}
	
	//purpose: method for allowing the user to choose a person from the map of people
	public Person personChooser() throws IllegalArgumentException, EmptyException
	{
		int chosenPersonID;
    	Person chosenPerson;
    	
    	Scanner sc = new Scanner(System.in);
    	
    	checkEmpty();//method to check if there is currently any people
    	viewAll();//view all the current people
	    System.out.println("Enter the ID of a person: ");
        chosenPersonID = sc.nextInt();
        if(!people.containsKey(chosenPersonID))//if entered ID doesnt exist throw exception
        {
            throw new IllegalArgumentException("Error: chosen person does not exist\n");
        }
        chosenPerson = people.get(chosenPersonID);//if it does exist then get the person from the map

        return chosenPerson;
	}
    
	//purpose: allows user to enter the name of a new person being added to the map
    public String personAddInput()
    {
    	String name;
    	
    	Scanner sc = new Scanner(System.in);
    	boolean error;
        name = null;
    	System.out.println("Current People: ");
        viewAll();//output all current people


        do
        {
            error = false;
            try
            {
	        System.out.println("Enter the name of a person to add: ");
	        name = sc.nextLine();//enter the name of the new person
                if((name.trim()).equals(""))
                {
                    throw new IllegalArgumentException("Error: input is blank");
                }
            }
            catch(IllegalArgumentException e)
            {
                error = true;
                System.out.println(e.getMessage());
            }
        }while(error);
	return name;
    }
    
    //purpose: allows user to enter the type of the new person being added to the map 
    public String personTypeInput()
    {	
    	String type;
    	String error = new String(" ");
    	Scanner sc = new Scanner(System.in);
    	
        do
        {
        	System.out.println(error);
            System.out.println("enter the type of person:");
            type = sc.nextLine();
            error = "Error: type must either be a volunteer, candidate or a strategist";
        }while(!type.equals("volunteer") && !type.equals("candidate") && !type.equals("strategist"));//if input is not one of the three types
                                                                                                     //repeat loop and make user enter the type again
        
        return type;
    }
    
    //purpose: used to check whether the current map of people is empty
    public void checkEmpty() throws EmptyException
	{
		if(people.isEmpty())//if it is empty throw an error exception
	    {
		      throw new EmptyException("Error: no current people");
	    }
	}
    
    //purpose: method to display all current people
    public void viewAll() 
    {
    	for(Person person : people.values())
	    {
		    System.out.println(person.toString());
	    }	
    }
    
}
