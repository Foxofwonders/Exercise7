/**
 * Description of application/ class.
 */
package nl.ru.ai.annadenise.exercise7;

/**
 * @author Denise van Baalen (s000)
 * @author a (s000)
 */
public class Concordances
{

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    System.out.println("Hello, world!");
    int a = 5;
    int b = 1;
    
    System.out.println(multiply(add(a, b), b));
    
    System.out.println("Should be: " + ((5 + 30) * 30));

  }
  
  public static int multiply(int a, int b)
  {
   return a*b; 
  }
  
  public static int add(int a, int b)
  {
    
    return a + b;
  }

}