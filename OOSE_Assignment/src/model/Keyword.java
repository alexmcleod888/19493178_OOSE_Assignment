package model;
/*Author: Alex McLeod
 *Purpose: This class is used within the trendingDetector class to keep track of current keywords.
 *         It keeps track of whether they are trending and if they are trending how many search
 *         cycles they have been held off from which has prevented notification from being sent related to
 *         that keyword
 *Date Modified: 22/05/2019
 */


public class Keyword 
{
	//private fields
    private String word;//the actual word that makes up this keyword
    private boolean prevTrending;//states whether this keyword was previously trending or not
                                 //true if is was and false if it wasn't
    private int hoursHeldOff;//holds the number of cycles this keyword has been held off
    
    //constructor that initialises the keyword, sets prevTrending to false and sets hoursHeldOff to 0
    public Keyword(String word)
    {
    	this.word = new String(word);
    	prevTrending = false;
    	hoursHeldOff = 0;
    }
    
    //purpose: returns whether the keyword was previously trending
    public boolean prevTrending()
    {
    	return prevTrending;
    }
    
    //purpose: used to set this keyword to previously trending
    public void setTrending()
    {
    	prevTrending = true;
    }
    
    //purpose: sets previously trending to false
    public void removeTrending()
    {
    	prevTrending = false;
    }
    
    //purpose: increments the number of cycles held off
    public void incrementCyclesHeldOff()
    {
    	hoursHeldOff += 1; 
    }
    
    //purpose: returns the number of cycles held off
    public int getCyclesHeldOff()
    {
    	return hoursHeldOff;
    }
    
    //purpose: resets the number of cycles held off back to 0
    public void resetCount()
    {
    	hoursHeldOff = 0;
    }
    
    //purpose: returns String of this keyword
    public String getWord()
    {
    	return word;
    }
}
