/*Author: Alex McLeod
 *Purpose: CandidateOb class that inherits from the Observer class and uses the back reference of dataAdder
 *         to add the candidate observer to the list of observers in the dataAdder class to be notified when a talking
 *         point is added.
 *Data Modified: 22/05/2019 
 */


package view;

import controller.DataAdder;
import controller.Messenger;
import model.Person;
import model.PolicyArea;

public class CandidateOb extends Observer
{
	//constructor for candidateOb
	public CandidateOb(Person person, PolicyArea policyArea, DataAdder dataAdder, Messenger messenger)
	{
		super(person, messenger, policyArea);//call super importing person, messenger and policyArea for use
		                                     //in the update method
		dataAdder.addTalkingPointOb(this);//use dataAdder back reference to add this observer to list of observers
		                                  //in dataAdder class
	}
}
