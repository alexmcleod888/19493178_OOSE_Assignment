package controller;
/* Author: Alex McLeod
 * Purpose: Method used by Observer objectects to initialise different Messenger objects and send messages using these
 *          objects
 * Data Modified: 22/05/2019
 */
import java.util.*;

import view.FacebookMessengerSub;
import view.SMS;
import view.TwitterMessengerSub;

public class Messenger 
{
	//private fields
    private SMS mobileSms;//reference to SMS object used to send SMS messages by mobile
    private TwitterMessengerSub twitterMsg;//reference to TwitterMessengerSub object to send messages on twitter
    private FacebookMessengerSub facebookMsg;//reference to FacebookMessengerSub object to send message on facebook
    
    //constructor for initialising different messenger objects
    public Messenger()
    {
    	this.mobileSms = new SMS();
    	this.twitterMsg = new TwitterMessengerSub();
        this.facebookMsg = new FacebookMessengerSub();
    }
    
    //purpose: method that imports a mobile number and a message. It then sends the message to the specified phone number using
    //         the SMS object 
    public void sendSMS(long mobileNum, String message)
    {
    	mobileSms.sendSMS(mobileNum, message);
    }
    
    //purpose: method that imports a twitter username and a message. It then sends the message to the specified twitter user using
    //the TwitterMessengerSub object 
    public void sendTwitterMsg(String twitterUser, String message)
    {
    	twitterMsg.sendPrivateMessage(twitterUser, message);
    }
    
    //purpose: method that imports a facebook username and a message. It then sends the message to the specified facebook user using
    //the FacebookMessengerSub object 
    public void sendFacebookMsg(String facebookUser, String message)
    {
    	facebookMsg.sendPrivateMessage(facebookUser, message);
    }
    
}
