import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class AssEx2 {
	public static void main (String[] args) {
		getText();
	}
	
	//a method to get the file name and the keyword
	public static void getText() {							
		System.out.println("Please enter your file name, end with P or C: ");
		Scanner s = new Scanner(System.in);
		String file = s.nextLine();	
		
		System.out.println("Please enter your keyword: ");	
		String keyword = s.nextLine();
		
		//check if the keyword meets the requirement
		while (!checkKey(keyword)) {
			System.out.println("Error! Please enter a valid keyword: ");	
		    keyword = s.nextLine();
		}
		
		//encrypt or decrypt the file using the keyword
		encryptDecode(file,keyword);

	}
	
	//a method that encrypts or decodes the file using the keyword provided
	public static void encryptDecode(String file,String keyword) {
		MonoAlphabeticCipher m = new MonoAlphabeticCipher(keyword);			//using monoalphabetic cipher
//		VignereCipher m = new VignereCipher(keyword);						//using vigenere cipher
		
		m.CipherList();		//generate the cipher list
		
		char[] message= readFile(file);				//read the file content
		char[] filename=file.toCharArray();			//convert the file name from String to char array
		
		char[] lf;
		
		/* 
		 *if the user enters the file name with 'P' in the end, then encrypt the file using the keyword.
		 *write the encrypted file into a new file with 'C' in the end.
		 *write another file which calculates letter frequencies in the encrypted file.
		 */
		if(file.charAt(file.length()-1)=='P') {
			lf = m.doEncrypt(message);
			filename[filename.length-1]='C';
			writeFile(String.valueOf(filename),lf);
			calFrequency(file,lf);
			/* 
			 *if the user enters the file name with 'C' in the end, then decode the file using the keyword.
			 *write the decoded file into a new file with 'D' in the end.
			 *write another file which calculates letter frequencies in the decoded file.
			 */
		}else if(file.charAt(file.length()-1)=='C') {
			lf = m.doDecode(message);
			filename[filename.length-1]='D';
			writeFile(String.valueOf(filename),lf);
			calFrequency(file,lf);
			/* 
			 *if the user enters the file name without 'P' or 'C' in the end,
			 *ask the user to reenter the file name.
			 */
		}else {
			System.out.println("Error! Please enter a valid file name!");
		}
	}
	
	
	/*
	 * 	a method that checks if the every character in the keyword is a capital letter,
	 * and there are no duplicate characters in the keyword
	 */	
	public static boolean checkKey(String k) {
		char[] kc = k.toCharArray();
		for(int i=0;i<kc.length;i++) {
			if(kc[i]>='A'&&kc[i]<='Z') {
				for(int j=i+1;j<kc.length;j++) {
					if(kc[i]==kc[j]) {
						return false;						
					}
				}
			}else {
				return false;	
			}
		}
		return true;
	}
	
	
	//a method that reads the content from the file
	public static char[] readFile(String fl){
		FileReader fr = null;
		String m = "";
		
		try {
			fr = new FileReader(fl+".txt");
			Scanner s = new Scanner(fr);
			while(s.hasNextLine()) {
				m += s.nextLine()+'\n';
			}	
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				fr.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		return m.toCharArray();
	}

	
	public static void calFrequency(String file, char[] text) {			//The “Freq” column gives the number of times the letter is seen in the output.		
		int ncount = 0;
		int[] letterCounts = new int[26];
		LetterFrequencies[] letter = new LetterFrequencies[26];
		char[] alphaletters = new char[26];
		setLetter(alphaletters);
		double[] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0, 0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0, 6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};
		
		for(int i=0;i<text.length;i++) {				//check every letter in the message
			if(text[i]>='A'&&text[i]<='Z') {				//if letter A is a capital letter, ncount+1
				letterCounts[text[i]-'A']++;
				ncount++;
				
				for(int j=0;j<26;j++) {
					if(text[i]==alphaletters[j]) {
						break;
					}
					alphaletters[text[i]-'A'] = text[i];
				}
			}
		}
		
		
		for(int i=0;i<26;i++) {
			letter[i] = new LetterFrequencies(alphaletters[i],letterCounts[i],avgCounts[i],ncount);
		}
		
		int p = mostFrequent(letterCounts);
		String mostF = "The most frequent letter is "+letter[p].getLetter()+" at "+String.format("%.1f", letter[p].getFrequency())+"%";
		
		
		writeFrequency(file,letter,mostF);
	}

	
	//a method that can find the most frequent letter in the file
	public static int mostFrequent(int[] lc) {
		int n=0;
		int pos = 0;
		for(int i=0;i<26;i++) {			//find the letter that has the largest number of time in the file
			if(n<lc[i]) {
				n=lc[i];
				pos = i;
			}			
		}
		return pos;
		
	}

	
	//a method that set the alphabetic list from A to Z
	public static void setLetter(char[] l) {
		char j=65;
		for(int i=0;i<26;i++) {
				l[i]= j;
				j++;
		}
	}
	
	//a method that writes the encrypted file or the decrypted file into a new file
	public static void writeFile(String filename, char[] m) {
		try {
			FileWriter fw = new FileWriter(filename+".txt");
			fw.write(String.valueOf(m));
			fw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/*a method that writes letter frequencies for each alphabetic upper case letter in the output file
	*into a new file whose name is end with 'F'
	*/
	public static void writeFrequency(String file, LetterFrequencies[] c,String mf) {
		try {
			char[] filename = file.toCharArray();
			String a="";
			filename[filename.length-1]='F';
			FileWriter fw = new FileWriter(String.valueOf(filename)+".txt");
						
			for(int i=0;i<26;i++) {
				a+=c[i];
			}
			
			String content = "LETTER ANALYSIS"+'\n'+'\n'+"  Letter   Freq   Freq%   AvgFreq%   Diff"+'\n'+a+'\n'+'\n'+mf;

			fw.write(content);
			fw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
