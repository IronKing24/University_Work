package manar.DirectoryList;

import java.io.File;
import java.util.Scanner;

/**
 * This program lists the files in a directory specified by
 * the user.  The user is asked to type in a directory name.
 * If the name entered by the user is not a directory, a
 * message is printed and the program ends.
 */
public class DirectoryList {


   public static void main(String[] args) {

      String directoryName;  // Directory name entered by the user.
      File directory;        // File object referring to the directory.
      Scanner scanner;       // For reading a line of input from the user.

      scanner = new Scanner(System.in);  // scanner reads from standard input.

      System.out.print("Enter a directory name: ");
      directoryName = scanner.nextLine().trim();
      directory = new File(directoryName);

      scanner.close();
      
      if (directory.isDirectory() == false) {
         if (directory.exists() == false)
            System.out.println("There is no such directory!");
         else
            System.out.println("That file is not a directory.");
      }
      else {
         Dircontent(directory,directory.getName());
      }

   } // end main()

   public static File Dircontent(File dir, String filename){
      if(dir.getPath().split("/").length <= 20){ // checking for depth
         String [] files = dir.list();   // Array of file names in the directory.
         if(files == null)
            return null; //if there are no files in the directory
         for (String subFile : files) {
            File file =  new File(dir , subFile);
            if (file.isDirectory()) {
               Dircontent(file,subFile);
            }
            else{
               System.out.println(file);
            }
         }
      }
      return null;
   }

} // end class DirectoryList
