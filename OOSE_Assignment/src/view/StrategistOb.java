package view;

import controller.DataAdder;
import controller.Messenger;
import controller.TrendingDetector;
import model.Person;

/*Author: Alex McLeod
 *Purpose: Strategist observer class that extends Observer class. Uses back reference to dataAdder and trendingDetector to add this
           Observer to trending keywords and add keywords observer lists. It also uses its parent class update method to send notifications
           to its contacts when a keyword becomes trending and a talking point is added
  Date Modified: 22/05/2019
 */

import model.PolicyArea;

public class StrategistOb extends Observer
{
	//constructor for initialising observer fields and adding Observer to lists
	public StrategistOb(Person person, PolicyArea policyArea, DataAdder dataAdder, TrendingDetector trendingDetector, Messenger messenger)
	{
		super(person, messenger, policyArea);
		
		trendingDetector.addTrendingOb(this);
    	dataAdder.addTalkingPointOb(this);
	}
}
