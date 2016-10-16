import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

public class Client {
	
	private RegexFilter filter;
	private QueueChecker checker;
	private Logger log;
	private Config configurations;
	private String time;
	
	
	public Client (RegexFilter rf,QueueChecker qc,Config config) {
		log = Logger.getLogger("myLogger");
		filter = rf;
		checker = qc;
		configurations = config;
		
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        time = timeFormat.format(cal.getTime());
		
		try {
			makeRequest();
		} catch (ClientProtocolException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
	}
	
//	Makes post request to server
	public void makeRequest () throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault(); 
		HttpPost httpPost = new HttpPost (configurations.getProperty("http://192.168.145.206:8033/api/Sra/LogMessage"));
		
		List <NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("Importance",configurations.getProperty("importance")));
		params.add(new BasicNameValuePair("Site",configurations.getProperty("site")));
		params.add(new BasicNameValuePair("System",configurations.getProperty("system")));
		params.add(new BasicNameValuePair("Time",time));
		params.add(new BasicNameValuePair("Workstation",""));
		checker.checkQueueStatus(filter.getReqNo());
		params.add(new BasicNameValuePair("MessageLevel",checker.getMessageLevel()));
		params.add(new BasicNameValuePair("Message",checker.getMessage()));
		params.add(new BasicNameValuePair("ExceptionText",""));
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
	}
}
