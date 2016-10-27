package nl.ru.ai.annadenise.exercise7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Denise van Baalen (s4708237)
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
		int decision;
		while(loop==1)
		{
			System.out.println("What do you want to do? (type only the number)");
			System.out.println("(1) display all words of the text");
			System.out.println("(2) count occurences of a word in the text");
			System.out.println("(3) display the index-positions of a word");
			System.out.println("(4) display occurences of a word with context");
			System.out.println("(5) end the program");
			
			decision = scanner.nextInt();
			
			switch(decision)
			{
				case 1:
				displayFrequencies(nr, words, freqs);
				System.out.println("");
				break;
				
				case 2:
				displayWordOccurences(words, nr);
				break;
				
				case 3:
				displayIndexPositions(words,nr);
				break;
				
				case 4:
				displayWordInContext(words,nr);
				break;
					
				case 5:
				loop=0;	
				break;
				
				default:
				System.out.println("Type a valid input");
				break;
			}
			
		}
		scanner.close();
		scannerWithDelimeters.close(); 

	} 
	catch (FileNotFoundException e) 
	{
		System.out.println("The file name does not exist.");
		e.printStackTrace();
	}
  }
 

 /** Displays the number of word occurences in the text, using the occurrences as
  * found in the countWord function.
 * @param words
 * @param nr
 */
static void displayWordOccurences(String[] words, int nr)
 {
	 assert words!=null: "Array should be initialised";
	 
	 Scanner scanner = new Scanner(System.in);
	 System.out.println("Insert the word you want to count:");
	 String  word = scanner.next();
	 int occurrences = countWord(words,nr,word);
	 System.out.println("The word '" + word + "' was found in the text " + occurrences + " times.");
     System.out.println("The text has a total of " + nr + " words. So the word is " + ((double)occurrences/nr)*100 + "% of the text." );
	 System.out.println("");

 }
 
 /** Displays the index positions of a given word in a given text file, using
  * the array made in the findIndexPositions function.
 * @param words
 * @param nr
 */
static void displayIndexPositions(String[] words, int nr)
 {
	assert words!=null: "Array should be initialised.";
	
	 Scanner scanner = new Scanner(System.in);
	 System.out.println("Insert the word you want to know the index-positions of:");
	 String word = scanner.next();
	 
	 int occurences = countWord(words,nr,word);
     int[] indexPositions= findIndexPositions(words,occurences,nr,word);
     
	 System.out.println("The index-positions of '" + word + "' in the text are: ");
	 for(int i=0;i<indexPositions.length;i++)
	 {
	    System.out.print(indexPositions[i] + "  ");
	 }
	 System.out.println("");
	 System.out.println("The word '" + word + "' was found in the text " + occurences + " times.");
	 System.out.println("");

 }
 
 /** Displays the m number of words that occur before and after the given word,
  * using a nested for-loop.
  * Also prints the total number of times the given word occurs in the file.
 * @param words
 * @param nr
 */
static void displayWordInContext(String[] words, int nr)
 {
	assert words!=null: "Array should be initialised.";
	
	 Scanner scanner = new Scanner(System.in);
	 System.out.println("Insert the word you want to know the occurences with context of:");
	 String word = scanner.next();
	 System.out.println("How many words to you want displayed before and after the word?");
	 int m = scanner.nextInt();
	 int occurences = countWord(words,nr,word);
     int[] indexPositions = findIndexPositions(words,occurences,nr,word);	
	 for(int i=0;i<indexPositions.length;i++)
	 {
		for(int z=indexPositions[i]-m;z<=indexPositions[i]+m;z++)
		{
			System.out.print(words[z] + " ");
		}
	 System.out.println("");
	 }
	 System.out.println("The word '" + word + "' was found in the text " + occurences + " times.");
	 System.out.println("");
 }
	
  /** Finds the index positions of a given word by running through the words in
   * the text, and testing if they are equal to the given word.
   * Returns the array with the index positions.
 * @param words
 * @param occurences
 * @param nr
 * @param word
 * @return
 */
static int[] findIndexPositions(String[] words, int occurences, int nr, String word) 
  {
	assert words!=null: "Array should be initialised.";
	assert word!=null: "Word that is to be found should be given.";
	
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

/** Asks for file to be read, and returns a scanner to read said file.
 * A delimiter is present in the scanner, that picks out whitespace and 
 * punctuation marks as word separators that are no words themselves.
 * @return
 * @throws FileNotFoundException
 */
static Scanner openTextFile() throws FileNotFoundException
 {
	 Scanner input=new Scanner(System.in);
	 System.out.println("Please enter file name, including extension: ");
	 String fileName=input.nextLine();
	 FileInputStream inputStream = new FileInputStream(fileName);
	 return new Scanner(inputStream).useDelimiter("[\\s+\\.\\,?!\"\\[\\]:()']+|[-][-]");
 }
  


/** Finds the total number of words in the text.
 * @param scanner
 * @param words
 * @param freqs
 * @return
 */
static int findAndCountWords(Scanner scanner, String[] words, int[] freqs) 
 {
	assert words!=null && freqs!=null: "Arrays should be initialised";
	
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

/** Every time the word is found in the array, increments the 'occurrence' counter.
 * Returns the total number of times the word is found in the text.
 * @param words
 * @param nr
 * @param word
 * @return
 */
static int countWord(String[] words, int nr, String word)
{
	assert words!=null: "Array should be initialised";
	
	int occurrence=0;
	for(int i=0;i<nr;i++)
	{
		if (words[i].equals(word))
		{
			occurrence++;
		}
	}
	return occurrence;
}

/** Tests if the word occurs already within the string. If it does, increments 
 * the counter of the word. If it doesn't, adds the word to the word string and
 * sets its frequency to 1.
 * @param word
 * @param words
 * @param freqs
 * @param nr
 * @return
 */
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

/** Searches within given boundaries to a given word.
 * Returns the first position on which the word occurs, if the word occurs within
 * those boundaries.
 * If the word does not occur, returns the upper boundary.
 * @param words
 * @param from
 * @param to
 * @param word
 * @return
 */
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

/** Prints each word of the text on a new line with its frequency next to it.
 * @param nr
 * @param words
 * @param freqs
 */
static void displayFrequencies(int nr, String[] words, int[] freqs) 
{
	assert words!=null && freqs!=null: "Arrays should be initialised";
	
	for (int i = 0; i<nr; i++)
	 { 
		System.out.println(words[i]+"      "+freqs[i]); 
	 }
}


}