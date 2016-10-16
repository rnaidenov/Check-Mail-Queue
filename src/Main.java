import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException   {
			
		RegexFilter rf = new RegexFilter();
		Config c = new Config ();
		QueueChecker qc = new QueueChecker (c);
		ExternalProcess ep = new ExternalProcess(rf,c);
		Client cl = new Client (rf,qc,c);
	}
}

