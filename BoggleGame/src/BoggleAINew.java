
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
class BoggleAINew {
    static ArrayList<String> words = new ArrayList<>(); //array list for dictionary
    static ArrayList<String> AIwords = new ArrayList<>(); //array list for the AI's words found in the grid
    static final int X = 5, Y = 5;

    static char[][] dice = {{'A', 'A', 'A', 'F', 'R', 'S'}, {'A', 'A', 'E', 'E', 'E', 'E'}, {'A', 'A', 'F', 'I', 'R', 'S'}, {'A', 'D', 'E', 'N', 'N', 'N'}, {'A', 'E', 'E', 'E', 'E', 'M'},
            {'A', 'E', 'E', 'G', 'M', 'U'}, {'A', 'E', 'G', 'M', 'N', 'N'}, {'A', 'F', 'I', 'R', 'S', 'Y'}, {'B', 'J', 'K', 'Q', 'X', 'Z'}, {'C', 'C', 'N', 'S', 'T', 'W'},
            {'C', 'E', 'I', 'I', 'L', 'T'}, {'C', 'E', 'I', 'L', 'P', 'T'}, {'C', 'E', 'I', 'P', 'S', 'T'}, {'D', 'D', 'L', 'N', 'O', 'R'}, {'D', 'H', 'H', 'L', 'O', 'R'},
            {'D', 'H', 'H', 'N', 'O', 'T'}, {'D', 'H', 'L', 'N', 'O', 'R'}, {'E', 'I', 'I', 'I', 'T', 'T'}, {'E', 'M', 'O', 'T', 'T', 'T'}, {'E', 'N', 'S', 'S', 'S', 'U'},
            {'F', 'I', 'P', 'R', 'S', 'Y'}, {'G', 'O', 'R', 'R', 'V', 'W'}, {'H', 'I', 'P', 'R', 'R', 'Y'}, {'N', 'O', 'O', 'T', 'U', 'W'}, {'O', 'O', 'O', 'T', 'T', 'U'}};

    static char[][] boggleGrid = new char[5][5];

    //method to verify if user input matches with the dictionary
    public static boolean wordVerification1(String userWord) {
        for (int i = 0; i < words.size(); i++) { //for loop for array list size
            if (userWord.equalsIgnoreCase(words.get(i))) {
                return true; //if the user inputted word matches with a word in the dictionary, it returns true
            }
        }
        return false; //other wise it returns false
    }

    //Creating the binary search method
    public static boolean wordVerification(ArrayList<String> array, String target){
        //Creating variables for the start and end points
        int startPoint = 0, endpoint = array.size() - 1;
        //Creating a while loop to index the array
        while (startPoint <= endpoint){
            //Creating a variable to store the value of the midpoint
            int midpoint = (startPoint + endpoint) / 2;

            //Determining what side of the array the target on
            int side = target.compareTo(array.get(midpoint));

            //If the target is at the midpoint
            if (side == 0){
                //Returning true
                return true;
            }
            //If the target is on the right of the array
            if (side > 0){
                //The start point is the midpoint+1
                startPoint = midpoint + 1;
            }
            //If the target is on the left side of the array
            else {
                //The endpoint is the midpoint-1
                endpoint = midpoint - 1;
            }
        }

        //If the search completes and no values were found
        //Returning false
        return false;
    }

    //Creating the Development.diceRandomizer method
    public static void diceRandomizer(char[][] values){
        //Creating randomizer
        Random rand = new Random();

        //Creating a variable for the rows
        int rows = 0;
        //Creating a variable for the columns
        int columns = 0;

        //Creating a counted loop to pick the random faces
        for (char[] value : values) {
            //Creating a variable to store the random number
            int randNum = rand.nextInt(6);

            //Picking a random index
            char selectedFace = value[randNum];

            //Adding values to the boggleGrid array
            boggleGrid[rows][columns] = selectedFace;

            //Adding 1 to the columns
            columns++;

            //If "columns" has reached the end of the columns
            if (columns == boggleGrid[0].length) {
                //Resetting columns
                columns = 0;
                //Adding 1 to the rows
                rows++;
            }
            //If the rows has reached the end of the array
            if (rows == boggleGrid.length) {
                //Resetting the rows
                rows = 0;
            }
        }
    }

    //method to read dictionary words into array list
    public static void dictionaryRead(ArrayList<String> words) throws FileNotFoundException {
        File input = new File("dictionary.txt"); //uses the .txt file as an input
        Scanner fileReader = new Scanner(input); //file reader

        while (fileReader.hasNext()) { //checks if the text file has next lines to read
            words.add(fileReader.nextLine()); //adds whatever is on the next line to the array list
        }
        fileReader.close();
    }

    // Recursive function to find words on the randomized letter grid with respect to the dictionary
    static void wordFinderRecursive(char grid[][], boolean used[][], int x, int y, String word) { //takes in

        //used is a 2d boolean array which determines if the word on the grid has been used or not to form the word
        used[x][y] = true; // sets all values on the grid to true
        word = word + grid[x][y];

        if (wordVerification1(word)) { //if the word exists on the grid, it adds it to the AI's word array list
            AIwords.add(word);
            System.out.print(word);
        }

        //searches all adjacent squares for matching letters of the word
        for (x = 0;  x < X; x++) {
            for (y = 0; y < Y; y++) {
                //if (x >= 0 && y >= 0 && !used[x][y]) {
                //    wordFinderRecursive(grid, used, x, y, word);
                //}
                if ((x != 0 && !used[x-1][y])) wordFinderRecursive(grid, used,x-1,y, word);
                if ((x != x - 1 && !used[x+1][y])) wordFinderRecursive(grid, used,x+1,y, word);

                if ((y != 0 && !used[x][y-1])) wordFinderRecursive(grid, used,x,y-1, word);
                if ((y != y - 1 && !used[x][y+1])) wordFinderRecursive(grid, used,x,y+1, word);

                if ((y != 0 && x != 0 && !used[x-1][y-1])) wordFinderRecursive(grid, used,x-1,y-1, word);
                if ((y != y - 1 && x != x - 1 && used[x+1][y+1])) wordFinderRecursive(grid, used, x + 1, y + 1, word);

                if ((y != y - 1 && x != 0 && !used[x-1][y+1])) wordFinderRecursive(grid, used,x-1,y+1, word);
                if ((y != 0 && x != x - 1 && !used[x+1][y-1])) wordFinderRecursive(grid, used,x+1,y-1, word);
            }
        }

        word = "" + word.charAt(word.length() - 1); //erases the character from the word as it has been used
        used[x][y] = false; //marks the letter has not been used
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

    public static void arrayPrint(){
        for (int r = 0; r < boggleGrid.length; r++){
            for (int c = 0; c < boggleGrid[r].length; c++){
                System.out.print(boggleGrid[r][c] + "\s");
            }
            System.out.println();
        }
    }
    public static void main(String args[])  throws FileNotFoundException
    {
        diceRandomizer(dice);
        dictionaryRead(words);
        arrayPrint();
        
        System.out.println("bot has selected this word");
        wordFinderAI(boggleGrid);
        randomWord(AIwords);
    }
}