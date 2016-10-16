import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class Config {
	
	private Logger log;
	private Properties props;
	
	public Config () {
		log = Logger.getLogger("myLogger");
		props = new Properties ();
		File input = new File ("resources/config.txt");
		try {
			FileReader reader = new FileReader (input);
			props.load(reader);
			reader.close();
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	public String getProperty (String propertyName) {
		return (String) props.get(propertyName);
	}
}
