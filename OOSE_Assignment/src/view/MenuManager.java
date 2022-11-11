package view;
import java.util.*;


import model.Person;
import model.PolicyArea;
import controller.*;

public class MenuManager 
{
	private Map<Integer, MenuSelection> menus;//map used to hold the different Menu options
	private Map<Integer, Person> people;//map for holding all the current people
	private Map<String, PolicyArea> policies;//map for holding all the current policies
	private FileReaderWriter fileReaderWriter;//FileReader for reading a file
	private NotificationManager notificationManager;//NotificationManager used for adding and removing notification settings
	private Set<String> keywords;//set used to hold all the keywords from every policy
	private Set<String> talkingPoints;//set used to hold all the talking points from every policy
	private TrendingDetector trendingDetector;//trendingDetector used to run the seperate thread that counts the occurence of each keyword
	                                          //sending notifications if they trend
	private PolicyView policyView;
        private PeopleView peopleView;

	
	//constructor that initialises values 
	public MenuManager()
	{
		keywords = new HashSet<String>();
		talkingPoints = new HashSet<String>();
		people = new HashMap<Integer, Person>();
		policies = new HashMap<String, PolicyArea>();
                policyView = new PolicyView(policies, keywords, talkingPoints);
                peopleView = new PeopleView(people);
		//construct the different Data Manipulators for adding, removing and viewing data
		DataAdder dataAdder = new DataAdder(people, policies, keywords, talkingPoints, policyView, peopleView);
		DataRemover dataRemover = new DataRemover(people, policies, keywords, talkingPoints, policyView, peopleView);
		DataViewer dataViewer = new DataViewer(people, policies, keywords, talkingPoints, policyView, peopleView);
		fileReaderWriter = new FileReaderWriter(dataAdder);
		trendingDetector = new TrendingDetector(keywords, policies);
		trendingDetector.start();//start seperate thread that check trending keywords
		notificationManager = new NotificationManager(people, policies, dataAdder, trendingDetector, policyView, peopleView);
		//initialises the different menu type to be used depending on which option the user chooses
		menus = new HashMap<Integer, MenuSelection>();
		menus.put(1, new AddMenu(dataAdder));
		menus.put(2, new RemoveMenu(dataRemover));
		menus.put(3, new ViewMenu(dataViewer));
	}
	
	//purpose: method to allow user to choose which option the user wants to select from th main menu
	public void selectOption()
	{
		int selection = 0;
		
		do
		{
			try
			{
				selection = menuDisplay();//display the main menu and get input for their selection
				if(selection > 3)
				{
					switch(selection)
					{
				        case 4://if 4 selected read from file
			    	        fileReaderWriter.readFile(people, policies);		
			    	        System.out.println("File read");
			    	        break;
			            case 5://if 5 selected write to file
                                        fileReaderWriter.writeFile();//call stub to simulate writing to file
			                break;
			            case 6://if 6 selected let user create a new notification
			    	        notificationManager.addNotification();
			    	        break;
			            case 7://if 7 selected let user remove a notification
			                notificationManager.removeNotification();
			                break;
			            case 8://if 8 selected exit program
			    	        System.out.println("Exiting Program");
			    	        trendingDetector.stop();//end thread timer as program has finished
			    	    break;
				    }
				}
			    else
				{
		 	        (menus.get(selection)).chooseOption();
		  		}
			}
			catch(IllegalArgumentException e)
			{
		    	System.out.println(e.getMessage());
			}
		}while(selection != 8);//if the selected option is not 8 dont let the program end
	}
	
	//purpose: method for displaying the main menu to the sure and allowing the user to select which option they would like
	public int menuDisplay()
    {
        int selection = 0;
        String error = new String(" ");
        Scanner sc = new Scanner(System.in);
        
        do
        {
        	try
        	{
        		System.out.println(error);//output error if it isnt the first cycle of the loop
        		//display menu
        		System.out.println("1) Add");
        		System.out.println("2) Remove");
        		System.out.println("3) View");
        		System.out.println("4) Read in File");
        		System.out.println("5) Write to File");
        		System.out.println("6) Add Notification Setting");
        		System.out.println("7) Remove Notification Setting");
        		System.out.println("8) Exit");
        		selection = sc.nextInt();//let user choose which option
        		error = "Error: input must be in the range 1 to 8";
        	}
        	catch(InputMismatchException e)
        	{
        		String flush = sc.nextLine();
        		error = "Error: wrong input data type, input must be an integer";
        	}
        }
        while(selection < 1 || selection > 8);//if number selection was invalid then repeat loop
        return selection;
    }
}
