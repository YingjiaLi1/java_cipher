
public class VignereCipher {
	//attributes
	private char[] alphaList;
	private char[][] vigenereList;
	private String keyword;
	
	//constructor
	public VignereCipher(String key){
		keyword = key;
		alphaList = new char[26];
		vigenereList = new char[key.length()][26];
		setAlphaList(alphaList);
	}
	
	//a method that initializes the alphabet from A to Z
	public void setAlphaList(char[] a) {
		char j=65;
		for(int i=0;i<26;i++) {
				a[i]= j;
				j++;
		}
	}
	
	//a method that uses the keyword to create the cipher list
	public void CipherList(){
		char[] keyC = keyword.toCharArray();
		//the number of the rows in the cipher array is the number of the characters in the keyword
		for(int i=0;i<keyC.length;i++) {
			char pos = keyC[i];
			for(int j=0;j<26;j++) {
				if(pos>'Z') {
					pos = 'A';
				}else if(pos =='A') {
					pos = 'A';
				}
				vigenereList[i][j] = pos;
				pos++;
			}
		}
	}
	
	/*
	 * The encryption process.
	 * It finds the first capital letter's position in the alphabet and replaces it using the letter that
	 * has the same position in the first row of the cipher list.
	 * For the next capital letter, it moves to the next row of the cipher list.
	 * Loop the row until the end of the cipher list, then move to the first row again.
	 */
	public char[] doEncrypt(char message[]) {	
		int row = 0;
		for(int i=0;i<message.length;i++) {
			if((message[i]>='A')&&(message[i]<='Z')) {
				if(row>=keyword.length()) {
					row = 0;
				}
				message[i]=vigenereList[row][message[i]-'A'];
				row++;
			}
		}
		System.out.println("The file is successfully encrypted"+'\n');	
		System.out.println("The encrypted text: "+'\n'+String.valueOf(message));	

		return message;
	}

	/*
	 * The decoding process.
	 * It finds the first capital letter's position in the first row of the cipher list and return the letter
	 * in the alphabet that corresponding to that position.
	 * For the next capital letter, it moves to the next row of the cipher list.
	 * Loop the row until the end of the cipher list, then move to the first row again.
	 */
	public char[] doDecode(char message[]) {
		int row = 0;
		setAlphaList(alphaList);
		int ncount=0;
		char[] dtext = new char[message.length];
		
		for(int i=0;i<message.length;i++) {
			if((message[i]>='A')&&(message[i]<='Z')) {
				for(int j=0;j<26;j++) {
					if(row>=keyword.length()) {
						row=0;
					}
					if(message[i]==vigenereList[row][j]) {
						dtext[ncount++]=alphaList[j];
					}
				}
				row++;
			}else {
				dtext[ncount++]=message[i];
			}
	}
		System.out.println("The file is successfully decoded"+'\n');
		System.out.println("The decoded text: "+'\n'+String.valueOf(dtext));
		return dtext;
	}
}

	