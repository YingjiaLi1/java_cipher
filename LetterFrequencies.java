import java.io.FileWriter;
import java.io.IOException;

public class LetterFrequencies {
	//attributes
	private char letter;
	private int letterCounts;
	private double frequencyPercent;
	private int ncount;
	private double avgCounts;
	private double diff;

	//constructor
	public LetterFrequencies(char l,int lc,double ac,int nc) {
		letter = l;
		letterCounts = lc;
		frequencyPercent=(100.0*lc)/nc;
		avgCounts = ac;
		diff=frequencyPercent-avgCounts;
		ncount = nc;
	}
	
	//a getter method that can return the value of letter
	public char getLetter() {
		return letter;
	}
	
	//a getter method that can return the value of freq%
	public double getFrequency() {
		return frequencyPercent;
	}
	
	//a toString method that can print out the object
	public String toString() {
		String show = String.format("%5c % 7d % 8.1f % 8.1f % 9.1f", letter,letterCounts,frequencyPercent,avgCounts,diff)+'\n';
		return show;
	}
}
