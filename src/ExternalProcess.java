import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class ExternalProcess {
	
	private Logger log;
	private String line;
	private RegexFilter filter;
	private Config configurations;
	
	public ExternalProcess (RegexFilter rf,Config config) throws IOException {
		log = Logger.getLogger("myLogger");
		filter = rf;
		configurations = config;
		runCommand();
	} 
	
//	Runs mailq command
	public void runCommand () throws IOException {
		
		try {
			String command = (String) configurations.getProperty("mailqCommand");
			log.info("Starting process : " + command);
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader errorStream = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((line = inputStream.readLine()) != null) {
				filter.addText(line);
			}
			inputStream.close();
			while ((line = errorStream.readLine()) != null) {
				System.out.println(line);
			}
			errorStream.close();
			p.waitFor();
			log.info("The number of requested messages is : " + filter.getReqNo());
			} catch (IOException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			}
	}
}
