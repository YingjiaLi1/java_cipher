import java.io.FileWriter;
import java.io.IOException;

public class MonoAlphabeticCipher {
	//attributes
	private char[] alphaList;
	private char[] encrypList;
	private String keyword;
	
	
	//constructor
	public MonoAlphabeticCipher(String key){
		keyword = key;
		alphaList = new char[26];
		encrypList = new char[26];
		setAlphaList(alphaList);
	}
	
	//a method that initializes the alphabet by setting characters from A to Z
	public void setAlphaList(char[] a) {
		char j=65;
		for(int i=0;i<26;i++) {
				a[i]= j;
				j++;
		}
	}
		
	//a method that uses the keyword to create the cipher list
	public void CipherList(){				
		char[] keyAsc = keyword.toCharArray();
		int ncount = keyAsc.length;
		//the keyword occupies the initial positions of the cipher array
		for(int i=0;i<keyAsc.length;i++) {
			encrypList[i]=keyAsc[i];
		}
		//change the character to 0 in the alphabet where there are duplicate letters in the keyword.
		for(int i=0;i<alphaList.length;i++){
			for(int j=0;j<keyAsc.length;j++){
				if(alphaList[i]==keyAsc[j]){
					alphaList[i]='0';
				}
			}
		}
		//write the letters that are not 0 in the alphabet to the remaining positions in the cipher array
		for(int i=0;i<alphaList.length;i++){
			if(alphaList[i]!='0'){
				encrypList[ncount]=alphaList[i];
				ncount++;
			}
		}
	}
	
	/*
	 * the encryption process.
	 * it finds a capital letter's position in the alphabet and replaces it using the letter that
	 * has the same position in the cipher list.
	 */
	public char[] doEncrypt(char message[]) {
		for(int i=0;i<message.length;i++) {
			if((message[i]>='A')&&(message[i]<='Z')) {
				message[i]=encrypList[message[i]-'A'];
			}
		}
		System.out.println("The file is successfully encrypted"+'\n');
		System.out.println("The encrypted text: "+'\n'+String.valueOf(message));	
		return message;
	}
	
	/*
	 * the decoding process.
	 * it finds a capital letter's position in the cipher array and returns the letter that
	 * has the same position in the alphabet.
	 */
	public char[] doDecode(char message[]) {
		setAlphaList(alphaList);
		int ncount=0;
		char[] dtext = new char[message.length];
		
		for(int i=0;i<message.length;i++) {
			if((message[i]>='A')&&(message[i]<='Z')) {
				for(int j=0;j<encrypList.length;j++) {
					if(message[i]==encrypList[j]) {
						dtext[ncount++]=alphaList[j];
					}
				
				}
			}else {
				dtext[ncount++]=message[i];
			}
		}
		System.out.println("The file is successfully decoded"+'\n');
		System.out.println("The decoded text: "+'\n'+String.valueOf(dtext));
		return dtext;
	}	
}
