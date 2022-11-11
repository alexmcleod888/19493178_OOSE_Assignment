package controller;

/*Author: Alex McLeod
 *Purpose: EmptyException class which extends the java Exception class, thrown when a List, Set or Map is Empty
 *Date Modified: 22/05/2019
 */

public class EmptyException extends Exception
{
	//contructor that imports error message to be set by exception
	public EmptyException(String message)
	{
		super(message);
	}

}
