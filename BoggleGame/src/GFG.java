import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/*
 Boggle AI
 Andrew H
 June 12th, 2021
 */
class GFG {
    static ArrayList<String> words = new ArrayList<String>(); //array list for dictionary
    static ArrayList<String> AIwords = new ArrayList<String>(); //array list for the AI's words found in the grid
    static final String dictionary[] = {"FAR", "ART", "FART"};
    static final int X = 5, Y = 5;

    //method to verify if user input matches with the dictionary
    public static boolean wordVerification(String userWord) {
        for (int i = 0; i < words.size(); i++) { //for loop for array list size
            if (userWord.equalsIgnoreCase(words.get(i))) {
                return true; //if the user inputed word matches with a word in the dictionary, it returns true
            }
        }
        return false; //other wise it returns false
    }

    //method to read dictionary words into array list
    public static void dictionaryRead(ArrayList<String> words) throws FileNotFoundException {
        File input = new File("dictionary.txt"); //uses the .txt file as an input
        Scanner fileReader = new Scanner(input); //file reader

        while (fileReader.hasNext()) { //checks if the text file has next lines to read
            words.add(fileReader.nextLine()); //adds whatever is on the next line to the array list
        }
        fileReader.close();

        for (int i = 0; i < dictionary.length; i++) { //for loop for array
            dictionary[i] = words.get(i); //puts all of the elements in the array list into a regular array
            System.out.print(dictionary[i]);
        }

    }

    // Recursive function to find words on the randomized letter grid with respect to the dictionary
    static void wordFinderRecursive(char grid[][], boolean used[][], int x, int y, String word) { //takes in

        //used is a 2d boolean array which determines if the word on the grid has been used or not to form the word
        used[x][y] = true; // sets all values on the grid to true
        word = word + grid[x][y];

        if (wordVerification(word)) { //if the word exists on the grid, it adds it to the AI's word array list
            //AIwords.add(word);
            System.out.println(word);
        }

        //searches all adjacent squares for matching letters of the word
        for (int row = x - 1; row <= x + 1 && row < X; row++) {
            for (int col = y - 1; col <= y + 1 && col < Y; col++) {
                if (row >= 0 && col >= 0 && !used[row][col]) {
                    wordFinderRecursive(grid, used, row, col, word);
                }
            }
        }

        word = "" + word.charAt(word.length() - 1); //erases the character from the word as it has been used
        used[x][y] = false; //marks the letter has been used
    }

    //Adds all words found to the AI's word list with respect to the dictionary
    static void wordFinderAI(char boggle[][])
    {
        boolean used[][] = new boolean[X][Y]; //2d array to determine if the character is used or not
        String word = ""; //initalizes string for use

        for (int i = 0; i < X; i++)
            for (int j = 0; j < Y; j++)
                wordFinderRecursive(boggle, used, i, j, word);
    }

    //method which selects a random word from the AI's available word list
    public static void randomWord (ArrayList<String> AIwords) {
        Random rand = new Random();
        int randomNum = rand.nextInt(AIwords.size()); //variable that generates random number within the arraylist size

        System.out.println(AIwords.get(randomNum)); //prints a random word from the arraylist
    }

    public static void main(String args[])  throws FileNotFoundException
    {
        char boggle[][] =  {{'A', 'A', 'A', 'F', 'R'}, {'A', 'A', 'E', 'E', 'E'}, {'A', 'A', 'F', 'I', 'R'}, {'A', 'D', 'E', 'N', 'N'}, {'A', 'E', 'E', 'E', 'E'} };

        dictionaryRead(words);
            System.out.println("bot has selected this word");
            wordFinderAI(boggle);

    }
}