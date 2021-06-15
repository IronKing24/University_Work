package manar.SortingAgorithemTimer;

import java.util.*;
import textio.TextIO;

/*sorting a list of size 1000:
selectionSort runtime: 0.003 of a second
Java sort runtime: 0.001 of a second

sorting a list of size 10000:
selectionSort runtime: 0.048 of a second
Java sort runtime: 0.006 of a second

sorting a list of size 100000:
selectionSort runtime: 2.398 seconds
Java sort runtime: 0.016 of a second*/

class SortTimer{
    public static void main(String[] args) {
    	while(true) {
    		System.out.println("please enter array size");
    		Benchmark(TextIO.getInt());
    	}
    }

    /**a method that takes in an integer makes two arrays of that size and prints the time of the called methods into the console */
    static void Benchmark(int arraysize)
    {
        int [] A = new int [arraysize];
        int [] B = new int [arraysize];
        for(int i = 0; i < arraysize; i++)
        {
            int val = (int)(Integer.MAX_VALUE * Math.random());
            A[i] = val;
            B[i] = val;
        }
      
        long startTimeA = System.currentTimeMillis();
        selectionSort(A);
        long runTimeA = System.currentTimeMillis() - startTimeA;
        
        long startTimeB = System.currentTimeMillis();
        Arrays.sort(B);
        long runTimeB = System.currentTimeMillis() - startTimeB;
        
        System.out.println("sorting a list of size " + arraysize + ':' +"\n"
        +"selectionSort runtime: " + runTimeA/1000.0d + ((runTimeA/1000.0 > 1) ? " seconds" : " of a second") +"\n"
        +"Java sort runtime: " + runTimeB/1000.0d + ((runTimeB/1000.0 > 1) ? " seconds" : " of a second") +"\n");
    }

    /**a method from the selection sort algorithm from the book, Eck (2020)*/
    static void selectionSort(int[] A) {
        // Sort A into increasing order, using selection sort
        
     for (int lastPlace = A.length-1; lastPlace > 0; lastPlace--) {
           // Find the largest item among A[0], A[1], ...,
           // A[lastPlace], and move it into position lastPlace 
           // by swapping it with the number that is currently 
           // in position lastPlace.
           
        int maxLoc = 0;  // Location of largest item seen so far.
        
        for (int j = 1; j <= lastPlace; j++) {
           if (A[j] > A[maxLoc]) {
                 // Since A[j] is bigger than the maximum we've seen
                 // so far, j is the new location of the maximum value
                 // we've seen so far.
              maxLoc = j;  
           }
        }
        
        int temp = A[maxLoc];  // Swap largest item with A[lastPlace].
        A[maxLoc] = A[lastPlace];
        A[lastPlace] = temp;
    }  // end of for loop
}
}

/*References:
 Eck,D.(2020) Introduction to Programming Using Java, Eighth Edition. retrieved from
 https://math.hws.edu/javanotes/index.html
 */
