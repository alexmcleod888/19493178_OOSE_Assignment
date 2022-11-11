/*Author: Alex McLeod
 *Purpose: Abstract Observer class which candidateOb, colunteerOb and strategistOb inherit from.
 *         Allows the use of polymorphism where by the class using the observers doesn't need to know
 *         the exact type of observer and can reference all observer sub classes as Observer's. Reduces
 *         coupling so the classes using observers dont assume too much about the particular observer
 *Date Modified: 22/05/2019
 */


package view;

import controller.Messenger;
import model.*;

public abstract class Observer 
{
	//private fields
	private Person person;//reference to the particular person who the notification will be sent to
	private Messenger messenger;//reference to Messenger object which is used to send the message
	private PolicyArea policyArea;//reference to the related policy area which the person is receiving
	                              //a message for
	
	//constructor which assigns values to reference
	public Observer(Person person, Messenger messenger, PolicyArea policyArea)
	{
		this.person = person;
	    this.messenger = messenger;
	    this.policyArea = policyArea;
	}
	
	//purpose: class used by observer sub classes to send messages to particular contact details if the person
	//         has them
    public void update(String policy, String message)
    {
    	if(policyArea.getName().equals(policy))//if this person is receiving notifications for the chosen
    		                                   //imported policy area then send them notifications
    	{
    		/*check what contact information the person has and send a notification to the ones he has*/
    		if(person.hasMobileNum())
    		{
    			messenger.sendSMS(person.getMobileNum(), message);
    		}
    		
    		if(person.hasTwitterUser())
    		{
    			messenger.sendTwitterMsg(person.getTwitterUser(), message);
    		}
    		
    		if(person.hasFacebookUser())
    		{
    			messenger.sendFacebookMsg(person.getFacebookUser(), message);
    		}
    	}
    }
}
