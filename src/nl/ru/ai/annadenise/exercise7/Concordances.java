package nl.ru.ai.annadenise.exercise7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Denise van Baalen (s000)
 * @author a (s000)
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
		
		Scanner scanner = openTextFile();
		int nr = findAndCountWords (scanner, words, freqs);
		displayFrequencies(nr, words, freqs);
		scanner.close(); 
	} 
	catch (FileNotFoundException e) 
	{
		System.out.println("The file name does not exist.");
		e.printStackTrace();
	}
  }
 
 static Scanner openTextFile() throws FileNotFoundException
 {
	 Scanner input=new Scanner(System.in);
	 System.out.println("Please enter file name, including extension: ");
	 String fileName=input.nextLine();
	 FileInputStream inputStream = new FileInputStream(fileName);
	 //return new Scanner(inputStream).useDelimiter("(?! [a-zA-Z ]+'[a-zA-Z ]+)[ ,;?\\.\\s!']+");
	 return new Scanner(inputStream).useDelimiter("[\\s+\\.\\,?!\"\\[\\]]+|[-][-]");
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

static boolean updateWord(String word, String[] words, int[] freqs, int nr) 
 {
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
		System.out.println(words[i]+" "+freqs[i]); 
	 }
}


}