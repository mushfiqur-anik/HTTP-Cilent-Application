package socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

public class HttpLibrary {
	// Variables 
	String[] arrOfStr;
	String mainAddress = "";
	final String VERSION = "HTTP/1.0";
	final int PORT = 80;
	
	Socket socket;
	PrintWriter out;
	Scanner in;
	
	// General Help
	public void httpcHelp() { 
		System.out.println("httpc is a curl-like application but supports HTTP protocol only.\n" + 
				"Usage:\n" + 
				"    httpc command [arguments]\n" + 
				"The commands are:\n" + 
				"    get executes a HTTP GET request and prints the response.\n" + 
				"    post executes a HTTP POST request and prints the response.\n" + 
				"    help prints this screen.\n" + 
				"Use \"httpc help [command]\" for more information about a command.\n");
	}
	
	public void httpcHelpGet() { 
		System.out.println("httpc help get\n" + 
				"usage: httpc get [-v] [-h key:value] URL\n" + 
				"Get executes a HTTP GET request for a given URL.\n" + 
				"   -v             Prints the detail of the response such as protocol, status,\n" + 
				"and headers.\n" + 
				"   -h key:value   Associates headers to HTTP Request with the format\n" + 
				"'key:value'.\n");
	}
	
	public void httpcHelpPost() { 
		System.out.println("usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\n" + 
				"Post executes a HTTP POST request for a given URL with inline data or from\n" + 
				"file.\n" + 
				"-v Prints the detail of the response such as protocol, status,\n" + 
				"and headers.\n" + 
				"-h key:value Associates headers to HTTP Request with the format\n" + 
				"'key:value'.\n" + 
				"-d string Associates an inline data to the body HTTP POST request.\n" + 
				"-f file Associates the content of a file to the body HTTP POST\n" + 
				"request.\n" + 
				"Either [-d] or [-f] can be used but not both.");
	}
	
	// Get Request
	public void getRequest(String str) throws IOException {
		int count = 0;
		String temp2 = "";
		String temp = "";
	
		arrOfStr = str.split(" ", 0);
		String mainStr = arrOfStr[2];
		mainStr = mainStr.replace("\'", "");
		
		URL url = new URL(mainStr);
		
		socket = new Socket(url.getHost(), PORT);
		out = new PrintWriter(socket.getOutputStream());
		in = new Scanner(socket.getInputStream());
		
		out.write("GET " + url.getFile() + " HTTP/1.0\r\nUser-Agent: Hello\r\n\r\n");      
		out.flush();
		
		while(in.hasNextLine()) {

			if(count > 8) {
				temp += in.nextLine() + "\n";
			} 
			else {
				temp2 = in.nextLine();
			}
			count++;
		}
		System.out.println(temp);
	}
	
	// Get with verbose option 
	public void getVerbose(String str) throws IOException { 
		
		arrOfStr = str.split(" ", 0);
		String mainStr = arrOfStr[3];
		mainStr = mainStr.replace("\'", "");
	
		URL url = new URL(mainStr);
		
		socket = new Socket(url.getHost(), PORT);
		out = new PrintWriter(socket.getOutputStream());
		in = new Scanner(socket.getInputStream());
	
		out.write("GET " + url.getFile() + " HTTP/1.0\r\nUser-Agent: Hello\r\n\r\n");      
		out.flush();
		
		while(in.hasNextLine()) {
			System.out.println(in.nextLine());
		}
		
		System.out.println();
		
		out.close();
		in.close();
		socket.close();
	}
	
	// Post request 
		public void postRequest(String str) throws IOException { 
			
			// Variables
			final String METHOD = "POST";
			String headerValuePair = "";
			String entityBody = "";
			int entityBodyLength;
			int indexOfHost = 0;
			
			// Divide the link into array
			arrOfStr = str.split(" ", 0);
			
			// Find entity body
			for(int i = 0; i < arrOfStr.length; i++) { 
				if(arrOfStr[i].contains("{")) {
					entityBody += arrOfStr[i];
				}
				
				if(arrOfStr[i].contains("}")) {
					entityBody += arrOfStr[i];
				}
				
				if(arrOfStr[i].contains("-h")) { 
					if(arrOfStr[i+1].contains("-d")) { 
						
					}
					
					else {
						headerValuePair += arrOfStr[i+1] + "\r\n";
					}
				}
				
				if(arrOfStr[i].contains("http://")) { 
					indexOfHost = i;
				}
			}
			
			entityBodyLength = entityBody.length();
			
			if(entityBody!= "") { 
			  headerValuePair += "Content-Length:" + entityBodyLength + "\r\n";
			}
			
			System.out.println(arrOfStr[indexOfHost]);
			URL url = new URL(arrOfStr[indexOfHost]);
			
			// start the socket
			socket = new Socket(url.getHost(), PORT);
			out = new PrintWriter(socket.getOutputStream());
			in = new Scanner(socket.getInputStream());
			
			out.write(METHOD + " " + url.getFile() + " " + VERSION + "\r\n" + headerValuePair + "\r\n" + entityBody);      
			out.flush();
			
			while(in.hasNextLine()) {
				System.out.println(in.nextLine());
			}
			
			System.out.println();
			
			out.close();
			in.close();
			socket.close();	
		}
}
