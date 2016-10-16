import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class QueueChecker {
	
	private Logger log;
	private Config configurations;
	private int warningBound;
	private int errorBound;
	private String message;
	private String messageLevel;
	
	public QueueChecker (Config config)  {
		log = Logger.getLogger("myLogger");
		configurations = config;
		warningBound = Integer.parseInt(configurations.getProperty("warningBound"));
		errorBound = Integer.parseInt(configurations.getProperty("errorBound"));
		messageLevel = "";
	}
	
//	Returns a status report based on the message requests 
	public void checkQueueStatus (int msgQueue) {
	
		message = "The length of the message queue is : " + msgQueue;
		
		if (msgQueue >= warningBound && msgQueue < errorBound) {
			
			messageLevel = "WARNING !";
			log.info(message);
		}
		else if (msgQueue >= errorBound) {
			messageLevel = "ERROR !";
			log.info(message);
		}
		
		else {
			message = "";
		}
	}
	
//	Will delete this method.
	public int simulateExternal () throws FileNotFoundException {
		File input = new File ("C:\\Users\\Rado\\Documents\\input.txt");
		FileReader reader = new FileReader (input);
		BufferedReader br1 = new BufferedReader (reader);
		String regex = "(--\\s\\d*\\s\\w*\\s\\w*\\s)(\\d*)(\\s\\w*\\.)";
		Pattern pattern = Pattern.compile(regex);
		String line;
		String messageReq = null;
		try {
			while ((line = br1.readLine()) != null) {
				Matcher m = pattern.matcher(line);
				
				if (m.matches()) {
					messageReq = m.group(2);
					System.out.println(m.group(2));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(messageReq);
		
	}
		
//	Returns warning or error status
	public String getMessageLevel () {
		log.info(messageLevel);
		return messageLevel;
	}
	
//	Returns message with the length message queue
	public String getMessage () {
		log.info(message);
		return message;
	}
}
