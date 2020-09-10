package socket;
import java.io.IOException;
import java.util.Scanner;

public class Httpc {
	
	public static void main(String[] args) throws IOException { 
		
		String userInput = "";
		Scanner user = new Scanner(System.in);
		HttpLibrary library = new HttpLibrary();
		
		System.out.print("Please enter the operation that you want to perform (Enter 'quit' to quit application): ");
		userInput = user.nextLine();
		
		System.out.println();
		
		// Main loop of the application
		while(!userInput.equalsIgnoreCase("quit")) {
			
			// Help options
			if(userInput.contains("help")) { 
				// General http help
				if(userInput.contains("httpc help") & !userInput.contains("get") & !userInput.contains("httpc post")) { 
					library.httpcHelp();
				}
				
				// Get Usage
				if(userInput.contains("httpc help") & userInput.contains("get") & !userInput.contains("httpc post")) { 
					library.httpcHelpGet();
				} 
				
				// Post Usage
				if(userInput.contains("httpc help") & !userInput.contains("get") & userInput.contains("httpc post")) { 
					library.httpcHelpPost();
				}
			}
			
			else { 
				if(userInput.contains("get")) { 
					
					// Verbose option
					if(userInput.contains("-v")) { 
						library.getVerbose(userInput);
					}
					
					else {
						// Get Query Parameter
						library.getRequest(userInput);
					}
				}
			}
			
			// Post with inline data
			// Ask the user question again
			System.out.print("Please enter 'quit' if you want to quit else keep going by inputing any other command: ");
			userInput = user.nextLine();
		}
		
		System.out.println("Application terminated!! ");
	}
}
