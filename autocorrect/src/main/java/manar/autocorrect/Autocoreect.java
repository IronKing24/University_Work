package manar.autocorrect;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class Autocoreect {
    public static void main(String[] args) {

        //open the dictionary 
        Scanner dict =  null;
        try{
            dict = new Scanner(new File("src/main/resources/words.txt")).useDelimiter("[^a-zA-Z]+");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        //assign the dictionary to a hash set
        HashSet<String>dictdata =  new HashSet<String>();
        while (dict.hasNext()) {
            dictdata.add(dict.next().toLowerCase());
        }
        dict.close();

        //open input file
        Scanner inFile =  null;
            try{
                inFile =  new Scanner(getInputFileNameFromUser()).useDelimiter("[^a-zA-Z]+");
            }
            catch(Exception e){
                System.out.println("Please, select a file to spell check");
            }

        //check for bad words in the input file
        while(inFile.hasNext()){
            String word = inFile.next().toLowerCase();
            if(!dictdata.contains(word)){
                String suggustions =  "";
                for (String item :  corrections(word, dictdata)) {
                    if(suggustions == ""){
                        suggustions += item;
                    }
                    else{
                        suggustions += ", "+item;
                    }
                }


                if(suggustions == ""){
                    System.out.println(word+": (no suggestions)");
                }
                else{
                    System.out.println(word+":" + suggustions);
                }
            }
        }
        inFile.close();
    }

    /**
    * Lets the user select an input file using a standard file
    * selection dialog box.  If the user cancels the dialog
    * without selecting a file, the return value is null.
    */
    static File getInputFileNameFromUser() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setDialogTitle("Select File for Input");
        int option = fileDialog.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION)
           return null;
        else
           return fileDialog.getSelectedFile();
    }

    //corrections suggestions generator
    static TreeSet<String> corrections(String badWord, HashSet<String> dictionary){
        
        final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        TreeSet<String> corrections = new TreeSet<String>();

        //deleting a char
        for(int i = 0; i < badWord.length(); i++){
            StringBuilder word = new StringBuilder(badWord);
            word.deleteCharAt(i);
            if(dictionary.contains(word.toString())){
                corrections.add(word.toString());
            }
        }

        //change a letter to any other.
        for(int a = 0; a < badWord.length(); a++){
            for(char letter : alphabet){  
                StringBuilder word = new StringBuilder(badWord);
                word.setCharAt(a, letter);
                if(dictionary.contains(word.toString())){
                    corrections.add(word.toString());
                }
            }
        }

        //Insert any letter at any point in the misspelled word.
        for(int a = 0; a < badWord.length(); a++){
            for(char letter : alphabet){
                String word;
                if(a == 0){
                    word = letter + badWord.substring(a);
                }
                else if(a == badWord.length() - 1){
                    word = badWord + letter;
                }
                else{
                    word = badWord.substring(0,a+1) + letter + badWord.substring(a+1,badWord.length());
                }
                
                if(dictionary.contains(word)){
                    corrections.add(word);
                }
            }
        }

        //Swap any two neighboring characters in the misspelled word
        for(int i = 1; i < badWord.length(); i++){
            char[] c = badWord.toCharArray();             
            char temp = c[i-1];
            c[i -1] = c[i];
            c[i] = temp;
            String word = new String(c);
            
            if(dictionary.contains(word)){
                corrections.add(word);
            }
        }

        //insert a space
        for(int a = 0; a < badWord.length(); a++){
            String wordA = badWord.substring(0,a); 
            String wordB = badWord.substring(a,badWord.length());
        
            if(dictionary.contains(wordA)){
                corrections.add(wordA);
            }
            if(dictionary.contains(wordB)){
                corrections.add(wordB);
            }
        }
        
        return corrections;
    }
}
