package controller;

import java.util.*;
import model.*;

/*Author: Alex McLeod
 *Purpose: File reader class that simulates a file being read in using a stub method that generates hard coded values for
 *         new people, contact information, policies, keywords and talking points'
 *Date Modified: 22/05/2019
 */

public class FileReaderWriter 
{
    private DataAdder dataAdder;//reference to data adder used to add new data
	
    //constructor for dependency injection
    public FileReaderWriter(DataAdder dataAdder) 
    {
        this.dataAdder = dataAdder;
    }
	
    /*STUB*/
    //purpose: method that simulates file reading by adding hard coded information to people, policy, talking points and keywords lists
    public void readFile(Map<Integer, Person> people, Map<String, PolicyArea> policies)
    {
    	dataAdder.addPersonFromFile("Bill", "volunteer", new String[] {"123342321"});
    	dataAdder.addPersonFromFile("Ray", "strategist", new String[] {"345346345", "bigRay", "ray1938" });
    	dataAdder.addPersonFromFile("Charles", "candidate", new String[] {"23423432", "smallCharles"});
    	dataAdder.addPersonFromFile("Mac", "volunteer", new String[] {});
    	dataAdder.addPersonFromFile("Ralph", "strategist", new String[] {"231423542"});
    	dataAdder.addPolicyFromFile("Work", new String[] {"yo what up", "i love people"}, new String[] {"strong"});
    	dataAdder.addPolicyFromFile("Education", new String[] {"i love to study"}, new String[] {"study"});
    	dataAdder.addPolicyFromFile("Enviroment", new String[] {"i love the enviroment", "lovely trees"}, new String[] {"tree"});
    	dataAdder.addPolicyFromFile("Safety", new String[] {"this is dangerous"}, new String[] {"helmet"});
    }

    /*STUB*/
    //purpose: simulates data being written to file by displaying a data written to file message
    public void writeFile()
    {
        System.out.println("Data written to file");
    }

    
}
