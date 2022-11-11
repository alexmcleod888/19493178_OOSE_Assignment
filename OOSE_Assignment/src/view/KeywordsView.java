package view;

import java.util.*;

import controller.EmptyException;

/*Author: Alex McLeod
 *Purpose: class used for output and input for keyword information
 *Date Modified: 22/05/2019
 */

public class KeywordsView 
{
	private Set<String> keywords;//reference used to point to the keywords from all policies
	
	//constructor for initialising the set of keywords
	public KeywordsView(Set<String> keywords)
	{
		this.keywords = keywords;
	}
	
	//purpose: method for allowing a user to choose a keyword from the set of current keywords
	public String keywordChooser() throws IllegalArgumentException, EmptyException
	{
		String chosenKeyword;
		
		Scanner sc = new Scanner(System.in);
	
		checkEmpty();//called to check whether there are currently keywords
		viewAll();//called to view all the current keywords
		System.out.println("Enter keyword to Select: ");
		chosenKeyword = sc.nextLine();
		if(!keywords.contains(chosenKeyword))//if the current keywords map doesnt contain the input keyword throw an exception
		{
			throw new IllegalArgumentException("Error: chosen keyword does not exist");
		}
		return chosenKeyword;		
		
	}
	
	//purpose: method used to check whether there are any keywords
	public void checkEmpty() throws EmptyException
	{
		if(keywords.isEmpty())
	    {
		      throw new EmptyException("Error: no current keywords");
	    }
	}
	
	//purpose: method used to view all current keywords
    public void viewAll() 
    {
    	System.out.println("Current Keywords: ");
    	for(String word : keywords)
    	{
    		System.out.println(word);
    	}
    }
}
