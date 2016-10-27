package nl.ru.ai.annadenise.exercise7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Denise van Baalen (s000)
 * @author Anna Gansen (s4753755)
 */
public class Concordances
{
	final static int MAX_NR_OF_WORDS = 80000 ;
	

 public static void main(String[] args)
  {
	
	try 
	{
		String [] words=new String [MAX_NR_OF_WORDS] ;
		int [] freqs=new int[MAX_NR_OF_WORDS] ;
		Scanner scannerWithDelimeters = openTextFile();
		Scanner scanner = new Scanner(System.in);
		int nr = findAndCountWords (scannerWithDelimeters, words, freqs);
		int loop=1;
		String word=null;
		int occurences=0;
		int[] indexPositions=null;
		while(loop==1){
		System.out.println("What do you want to do? (type only the number)");
		System.out.println("(1) display all words of the text");
		System.out.println("(2) count occurences of a word in the text");
		System.out.println("(3) display the index-positions of a word");
		System.out.println("(4) display occurences of a word with context");
		System.out.println("(5) end the program");
		int decision = scanner.nextInt();
		switch(decision)
		{
		case 1:
		displayFrequencies(nr, words, freqs);
		break;
		
		case 2:
		System.out.println("Insert the word you want to count:");
	    word = scanner.next();
		occurences = countWord(words,nr,word);
		System.out.println("The word " + word + " was found in the text " + occurences + " times.");
		System.out.println("The text has a total of " + nr + " words. So the word is " + ((double)occurences/nr)*100 + "% of the text." );
		break;
		
		case 3:
		System.out.println("Insert the word you want to know the index-positions of:");
		word = scanner.next();
	    occurences = countWord(words,nr,word);
		indexPositions= findIndexPositions(words,occurences,nr,word);	
		System.out.println("The index-positions of " + word + " in the text are: ");
		for(int i=0;i<indexPositions.length;i++)
		{
		System.out.print(indexPositions[i] + "  ");
		}
		System.out.println("The word " + word + " was found in the text " + occurences + " times.");
		break;
		
		case 4:
		System.out.println("Insert the word you want to know the occurences with context of:");
		word = scanner.next();
		System.out.println("How many words to you");
	    occurences = countWord(words,nr,word);
		indexPositions= findIndexPositions(words,occurences,nr,word);	
		System.out.println("The word " + word + " was found in the text " + occurences + " times.");
		break;
			
		case 5:
		loop=0;	
		break;
		
		default:
		System.out.println("Type a valid input");
		break;
		}
		}
		scannerWithDelimeters.close(); 
		scanner.close();
	} 
	catch (FileNotFoundException e) 
	{
		System.out.println("The file name does not exist.");
		e.printStackTrace();
	}
  }
 

	
  static int[] findIndexPositions(String[] words, int occurences, int nr, String word) 
  {
	int[] indexPositions = new int[occurences];
	int x=0;
	for(int i=0;i<nr;i++)
	{
		if (words[i].equals(word))
		{
			indexPositions[x]=i;
			x++;
		}
	}
	return indexPositions;
  }

static Scanner openTextFile() throws FileNotFoundException
 {
	 Scanner input=new Scanner(System.in);
	 System.out.println("Please enter file name, including extension: ");
	 String fileName=input.nextLine();
	 FileInputStream inputStream = new FileInputStream(fileName);
	 //return new Scanner(inputStream).useDelimiter("(?! [a-zA-Z ]+'[a-zA-Z ]+)[ ,;?\\.\\s!']+");
	 return new Scanner(inputStream).useDelimiter("[\\s+\\.\\,?!\"\\[\\]:()']+|[-][-]");
	 //return new Scanner(inputStream).useDelimiter("([^a-z]'[A-Z])([a-z]'[^a-z])");
 }
  


static int findAndCountWords(Scanner scanner, String[] words, int[] freqs) 
 {
	assert words!=null && freqs!=null;
	
	int nr = 0;
	while (scanner.hasNext())
	{
		String word=scanner.next();
		if(updateWord(word,words,freqs,nr))
		{
		nr++;
		}
	}
	return nr;
 }
static int countWord(String[] words, int nr, String word)
{
	
	int occurence=0;
	for(int i=0;i<nr;i++)
	{
		if (words[i].equals(word))
		{
			occurence++;
		}
	}
	return occurence;
}

static boolean updateWord(String word, String[] words, int[] freqs, int nr) 
 {
	assert nr >= 0 && words	!= null &&	freqs != null: "Error while updating word";
	int pos = sequentialSearch(words, 0, nr, word);
	if (pos<nr)
	{
		freqs [pos]++ ;
		return false;
	}
	else
	{
		words [pos]= word ;
		freqs [pos] = 1 ;
		return true ;
	} 
 }

static int sequentialSearch (String[] words, int from, int to, String word) 
{
	assert 0 <= from && 0 <= to : "Invalid bounds";
	assert words!=null : "Array should be initialized";
	
	if (from>to)
	{
	return to;
	}
	
	int position = from;
	while (position<to && words[position]!=word)
	{
		position++;
	}
	return position; 
}

static void displayFrequencies(int nr, String[] words, int[] freqs) 
{
	for (int i = 0; i<nr; i++)
	 { 
		System.out.println(words[i]+"      "+freqs[i]); 
	 }
}


}