package controller;
import java.util.*;

import view.*;

import model.Person;
import model.PolicyArea;

/*Author: Alex McLeod
 *Purpose: Data Viewing class used for performing view operations where by a user wants to view the current people
 *         policies, keywords or talking points. 
 *Data Modified: 22/05/2019
 */
public class DataViewer implements DataManipulator
{
	  //protected fields
	  private PolicyView policyView;//object reference for allowing output for policy information
	  private PeopleView peopleView;//object reference for allowing output for person information
	  private KeywordsView keywordsView;//object reference for allowing output for keyword information
	  private TalkingPointsView talkingPointsView;//object reference for allowing output for talkingPoint information
	
	  //constructor for intialisation of different views used for displaying information
	  public DataViewer(Map<Integer, Person> people, Map<String, PolicyArea> policies, Set<String> keywords, Set<String> talkingPoints, PolicyView policyView, PeopleView peopleView)
	  {		  
		  this.peopleView = peopleView;
		  this.policyView = policyView;
		  this.keywordsView = new KeywordsView(keywords);
		  this.talkingPointsView = new TalkingPointsView(talkingPoints);
		  
	  }
	  
	  //purpose: method allowing dataViewer to know which view option to perform
	  public void selectOption(int selection)
	  {
		  switch(selection)
		  {
		      case 1:
		    	  viewPeople();//1 chosen display all people  information
		          break;
		      case 2:
		    	  viewPolicies();//2 chosen display all policy information
		    	  break;
		      case 3:
		    	  viewTalkingPoints();//3 chosen display talking points
		    	  break;
		      case 4:
		    	  viewKeywords();//4 chosen display keywords
		    	  break;		    	  
		  }
	  }
	
	  //purpose: method for allowing user to view all people and there information
      public void viewPeople()
      { 
    	  try
    	  {
    		  peopleView.checkEmpty();//first check whether there are currently people in the system
    		  peopleView.viewAll();//if there are people call viewAll to view all the current people and their information
    		                       //e.g. ID number, name, type and contact details
    	  }
    	  catch(EmptyException e)//if it was empty catch EmptyException and display message
    	  {
    		  System.out.println(e.getMessage());
    	  }
      }
      
      //purpose: method for allowing user to view all policies and their information
      public void viewPolicies()
      {
    	  try
    	  {
    		  policyView.checkEmpty();//first check whether there are currently policies in the system
              policyView.viewAll();//if there are policies call viewAll to view all the current policies and their information
                                   //e.g. name, talking points and keywords
    	  }
    	  catch(EmptyException e)
    	  {
    		  System.out.println(e.getMessage());
    	  }
      }
      
      //purpose: method for allowing the user to view all talking points 
      public void viewTalkingPoints()
      {
          try
          {
        	  talkingPointsView.checkEmpty();//check there are talking points using checkEmpty
    		  talkingPointsView.viewAll();//if there are talking points display them
    	  }
    	  catch(EmptyException e)
    	  {
    		  System.out.println(e.getMessage());
    	  }
      }
      
      //purpose: method for allowing the user to view all keywords 
      public void viewKeywords()
      {
    	  try
    	  {
    		  keywordsView.checkEmpty();//check there are keywords using checkEmpty
    		  keywordsView.viewAll();//if there are keywords display them
    	  }
    	  catch(EmptyException e)
    	  {
    		  System.out.println(e.getMessage());
    	  }
      }
}
