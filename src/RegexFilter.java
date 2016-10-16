import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class RegexFilter {
	
	private Logger log;
	private String regex;
	private Pattern p;
	private Matcher m;
	private String reqNo;
	private ArrayList <String> cmdText;
	
	public RegexFilter () {
		log = Logger.getLogger("myLogger");
		regex = "(--\\s\\d*\\s\\w*\\s\\w*\\s)(\\d*)(\\s\\w*\\.)";
		cmdText = new ArrayList <String> () ;
		p = Pattern.compile(regex);
	}
	
	
	public void addText (String text) {
		cmdText.add(text);
	}
	
//	Gets message requests based on regex
	public int getReqNo () {
		for (int i=0;i<cmdText.size();i++) {
			m = p.matcher(cmdText.get(i));
			if (m.matches()) {
				reqNo = m.group(2);
				log.info("Extracting message requests from line : " + cmdText.get(i));
			}
		}
		log.info("Number of requests is : " + reqNo);
		return Integer.parseInt(reqNo);
	}
}