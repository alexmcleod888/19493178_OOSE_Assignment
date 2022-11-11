package view;

import controller.DataAdder;
import controller.Messenger;
import controller.TrendingDetector;
import model.*;

/*Author: Alex McLeod
 *Purpose: Volunteer observer class that extends Observer class. Uses back reference to dataAdder and trendingDetector to add this
           Observer to trending keywords, add talking points and add keywords observer lists. It also uses its parent class update method to send notifications
           to its contacts when a keyword becomes trending and a talking point or keyword is added
  Date Modified: 22/05/2019
 */

public class VolunteerOb extends Observer 
{
	//constructor for initialising observer fields and adding Observer to lists
	public VolunteerOb(Person person, PolicyArea policyArea, DataAdder dataAdder, TrendingDetector trendingDetector, Messenger messenger)
	{
		super(person, messenger, policyArea);
		
		dataAdder.addKeywordOb(this);
	    trendingDetector.addTrendingOb(this);
	    dataAdder.addTalkingPointOb(this);
	
	}
}
