package controller;

/*Author: Alex McLeod
 *Purpose: interface DataManipulator implemented by the dataAdder, dataRemover and dataViewer classes
 *         forcing them to implement the selectOption method so that a particular option can be chosen,
 *         It also allows for polymorphism, with the different manipulators being referenced as
 *         DataManipulators within the MenuSelection class so that the menu doesnt need to know more then
 *         it needs to
 *Data Modified: 22/05/2019 
 */

public interface DataManipulator 
{
	//abstract class for selecting an option for the data manipulator to perform
    public abstract void selectOption(int selection);
    
}
