package app;

import java.io.IOException;

public interface SaveInterface {
	
	void writeToFile(String filename, Controller controller, Counter counter) throws IOException;
	

}	
