package app;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Save implements SaveInterface {
	
	public void writeToFile(String filename, Controller controller, Counter counter) throws IOException {
		
		PrintWriter writer = new PrintWriter(filename);
		
		writer.print(counter.getHighscore());
		writer.flush();
		writer.close();
	}
	
	public int loadFromFile(String filename) throws IOException {
		
		Scanner scanner = new Scanner(new File(filename));
		int highscore = scanner.nextInt();
		scanner.close();
		return highscore;
	}
}
