package view;

import java.util.*;

/*Author: Alex McLeod
 *Purpose: class used for output and input for policy information
 *Date Modified: 22/05/2019
 */

import controller.EmptyException;
import model.PolicyArea;

public class PolicyView 
{
    private Map<String, PolicyArea> policies;//reference to the map of current policies
    private Set<String> keywords;
    private Set<String> talkingPoints;
	
    //constructor for initialising the current policies
    public PolicyView(Map<String, PolicyArea> policies, Set<String> keywords, Set<String> talkingPoints)
    {
	this.policies = policies;
        this.keywords = keywords;
        this.talkingPoints = talkingPoints;
    }
	
	//purpose: method for allowing the user to choose a particular policy 
    public PolicyArea policyChooser() throws IllegalArgumentException, EmptyException
    {
    	String chosenPolicyString;
    	PolicyArea chosenPolicy;
    	
    	Scanner sc = new Scanner(System.in);
    	
    	checkEmpty();//check whether there are currently any policies
        viewAll();//view all policies
    	System.out.println("Choose a policy: ");
        chosenPolicyString = sc.nextLine();
        if(!policies.containsKey(chosenPolicyString))//if polict enter does not equal any that exist throw an error
        {
        	throw new IllegalArgumentException("Error: chosen policy does not exist\n");
        }
        chosenPolicy = policies.get(chosenPolicyString);

        return chosenPolicy;
    }
    
    //purpose: method allowing the user to input a policy name
    public String policyInput() throws IllegalArgumentException
    {
    	String name = new String();
    	
    	Scanner sc =  new Scanner(System.in);
    	
    	System.out.println("Enter the name of the Policy:");
    	name = sc.nextLine();
    	if(name.trim().isEmpty())//if input is empty then throw exception
    	{
    		throw new IllegalArgumentException("Error: no policy name given\n");
    	}
    	return name;
    }
    
    //purpose: method for allowing the user to input a new talking point for a policy
    public String talkingPointInput() throws IllegalArgumentException
    {
    	String talkingPoint;
    	boolean error;
    	Scanner sc = new Scanner(System.in);
    	
    	
    	talkingPoint = null;
    
    	do
    	{
            error = false;
    	    try
    	    {
    	        System.out.println("Enter a talking point for the chosen policy");
    	        talkingPoint = new String(sc.nextLine());
    	        if(talkingPoint.trim().isEmpty())//if input empty throw error
    	        {
    	    	    error = true;
    		    throw new IllegalArgumentException("Error: no talking point given\n");
    	        }
    	        else if(talkingPoints.contains(talkingPoint))//if policy name already exists throw an error
		{
    	    	    error = true;
	            throw new IllegalArgumentException("Error: talking point already exists \n");
		}
    	    }
    	    catch(IllegalArgumentException e)
    	    {
    	        System.out.println(e.getMessage());
            }
    	}while(error);//repeat the loop if there was an error
    	return talkingPoint;
    }
    
    //purpose: method for allowing user to input a keyword 
    public String keywordInput() throws IllegalArgumentException
    {
    	String keyword;
    	String[] wordsInString;
    	int numWords = 0;
    	Scanner sc = new Scanner(System.in);
    			
        System.out.println("Enter a keyword for the chosen policy");
    	keyword = new String(sc.nextLine());
    	wordsInString = keyword.trim().split(" ");//create an array with all the Strings entered in the input
    	for(int i = 0; i < wordsInString.length; i++)//count how many words were entered
    	{
    	    numWords += 1;
    	}
    	if(numWords != 1)//if the keyword entered is not 1 word then throw an exception
    	{ 
    	    throw new IllegalArgumentException("Error: Keyword consists of multiple words\n");
    	}
    	else if(keyword.trim().isEmpty())//if the keyword is blank then throw exception
    	{
    	    throw new IllegalArgumentException("Error: no keyword given\n");
    	}
        else if(keywords.contains(keyword))
        {
            throw new IllegalArgumentException("Error: keyword already exists");
        }
    	return keyword;    	
    }
    
    //purpose: method used to check if the map of policies is empty
    public void checkEmpty() throws EmptyException
    {
	if(policies.isEmpty())
	{
            throw new EmptyException("Error: no current policies");
        }
    }
    
    //purpose: method used to display all current policies
    public void viewAll()
    {
    	System.out.println("Current Policies: ");
    	
    	for(PolicyArea policy : policies.values())
	{
            System.out.println(policy.toStringPolicy());
	}	
    }
    
    //purpose: method used to delete an imported policy and to output a menu to double check if the user wants
    //         to delete the policy given it has related talking points and keywords
    public int policyDelete(PolicyArea chosenPolicy) 
    {
        int selection = 0;
	    String error = new String(" ");
           
	    Scanner sc = new Scanner(System.in);
	    
        do
        {
            try
            {
                System.out.println(error);
                System.out.println(chosenPolicy.toStringPolicy());
                System.out.println("Are you sure you want to Delete this policy: ");
                System.out.println("1) delete policy");
                System.out.println("2) skip");            	
                selection = Integer.parseInt(sc.nextLine());
                error = "Error: input must be 1 or 2";
            }
            catch(NumberFormatException e)
            {
                System.out.println("Error: Incorrect data type entered");
            }
        }while(selection < 1 || selection > 2);//repeat selection process if input is not 1 or 2
        return selection;
    }
}
