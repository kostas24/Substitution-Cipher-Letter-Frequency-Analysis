import java.io.*;
import java.util.*;

public class Cipher {
	
	public static void main(String[] args)
	{
		Scanner kb = new Scanner(System.in);
		
		char [] plainText = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
		'n','o','p','q','r','s','t','u','v','w','x','y','z'};
		
		//create encrypted alphabet
		char [] cipherText = plainText.clone();
		scramble(cipherText);
		
	    System.out.println("plaintext = " + Arrays.toString(plainText));
	    System.out.println("encrypted = " + Arrays.toString(cipherText));
		
	    //Encryption
	    boolean sFound = false;
	    String plainInput = null;
	    while(!sFound)
	     {
	    	 System.out.println("Enter a string to encrypt (-1 to quit)");
	    	 plainInput = kb.next();
	    	 plainInput = plainInput.toLowerCase();
	    	 
	    	 if(plainInput.matches("[a-zA-Z]+"))	    	 
	    		 sFound = true;    	     	 
	    	 else if(plainInput.equals("-1"))	 
	    		 System.exit(0);    	 	    	 
	    	 else
	    		 System.out.println("English letters only");	    	
	    	 
	     } 
	    
	    String encOutput = "";
	    
	    for(int i =0; i < plainInput.length() ;i++)
	    {
	    	char currChar = plainInput.charAt(i);
	    		for(int j =0; j < 26; j++) {
	    			if(currChar == plainText[j])
	    			{
	    				encOutput = encOutput + cipherText[j];
	    			}
	    		}    	   	
	    }
	    
	    System.out.println("\nEncrypted Message = " + encOutput);
	    
	    //Decryption
	    
	    boolean found = false;
	    String encInput = "";
	    while(!found)
	     {
	    	 System.out.println("Enter a string to decrypt (-1 to quit)");
	    	 encInput = kb.next();
	    	 encInput = encInput.toLowerCase();

	    	 if(encInput.matches("[a-zA-Z]+"))	    	 
	    		 found = true;    	 
	    	 
	    	 else if(encInput.equals("-1"))	 
	    		 System.exit(0);    	     	 
	    	 else
	    		 System.out.println("English letters only");	    	
	    	 
	     } 
	    String plainOutput = "";
	    
	    for(int i =0; i < encInput.length() ;i++)
	    {
	    	char currChar = encInput.charAt(i);
	    	for(int j =0; j < 26; j++) {
	    		if(currChar == cipherText[j])
	    		{
	    			plainOutput = plainOutput + plainText[j];
	    		}
	    	}    	   	
	    }
	    
	    System.out.println("\nDecrypted Message = " + plainOutput);
	    
	    //Letter Analysis

	    Scanner inputFile = null;

	    try {
	    	inputFile = new Scanner(new File("PlainDict.txt"));
	    }	
	    catch(Exception e) {
	    	System.out.println("Input file PlainDict.txt not found!");
	    	return;
	    }
 
	    String encryptedfile= "";

	    System.out.println("Processing Letter Frequency Analysis...\n");
	    while(inputFile.hasNextLine()) {
	    	String line = inputFile.nextLine();
	    	boolean containsLetter = line.matches("[a-zA-Z]+");
	    	for(int i = 0; i < line.length(); i++) {
	    		
	    		if(!containsLetter)
	    		{
	    			encryptedfile = encryptedfile + "\n";    			
	    			containsLetter = true;
	    			
	    		}
	    		if(line.charAt(i) == ' ') {
	    			encryptedfile = encryptedfile + " ";
	    		}
	    		if(line.charAt(i) == '!' || line.charAt(i) == ',' || line.charAt(i) == ';' || line.charAt(i) == '.' || line.charAt(i) == '?' || line.charAt(i) == '-' ||  
	                    line.charAt(i) == '\'' || line.charAt(i) == '"' || line.charAt(i) == ':' || line.charAt(i) == '“')
	    		{
	    			encryptedfile = encryptedfile + line.charAt(i);
	    		}
	    		else {
	    			for(int j = 0; j < 26; j++) {
	    				if(line.toLowerCase().charAt(i) == plainText[j]) {
	    					encryptedfile = encryptedfile + cipherText[j]; 
	    					break;
	    				}
	    				
	    			}
	    		}
	    	}
	}
	    inputFile.close();

	    int [] freq = new int[26];
	    for(int x = 0; x < encryptedfile.length(); x++) {
	    	for(int y = 0; y < 26; y++) {
	    		if(encryptedfile.charAt(x)==plainText[y]) {
	    			freq[y]++;
	    		}
	    	}
	    }
 
	    //open and create file
	    BufferedWriter outputFile = null;
	    try {
	    	outputFile = new BufferedWriter(new FileWriter(new File("CipherDict.txt")));
	    	outputFile.write(encryptedfile);
	    	outputFile.flush();
	    	outputFile.close();
	    }
	    catch(Exception e) {
	    	System.out.println("Error with CipherDict.txt!");
	    	return;
	    }
  
	    double [] freqpercent = new double[26];
	    for(int i = 0; i < 26; i++) {
	    	freqpercent[i] = (double)freq[i] / (double)encryptedfile.length();
	    }
		char [] freqText = plainText.clone();
	    sort(freqText, freqpercent); 
	    System.out.println("Encrypted");
	    for(int i = 0; i < 26; i++) {
	    	System.out.println(freqText[i] + " : " + freqpercent[i]);
	    }
	}
	 	
	private static void scramble(char [] array) {
		int size = array.length;
		for(int i = 0; i < size; i++) {
			int random = (int)(Math.random() * size);
			char temp = array[0];
			array[0] = array[random];
			array[random] = temp;
			}
		}	
	 private static void sort(char [] plainText, double [] freq) {
		   for(int i = 0; i < plainText.length - 1; i++) {
			   for(int j = i + 1; j < plainText.length; j++) {
				   	if(freq[i] > freq[j]) {
				   		double d = freq[i];
				   		freq[i] = freq[j];
				   		freq[j] = d;
				   		char c = plainText[i];
				   		plainText[i] = plainText[j];
				   		plainText[j] = c;
		   			}
			   }
		   }
	 }
	
}    