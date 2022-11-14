package app;

public class Counter {

	int end;
	int counter = 0;
	int highscore = 0;
	
	public Counter(int i) {
		end = i;
	}
	
	boolean count() {
		if (counter == end)
			return true;
		else {
			counter++;
			return false;
		}
	}
	
	boolean count(int i) {
		if (counter == end)
			return true;
		else {
			counter += i;
			return false;
		}
	}
	
	public int getCounter() {
		return counter;
	}


	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public int getHighscore() {
		return highscore;
	}
	
	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}
	

}
