

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.*;

class BoggieAI {
    static ArrayList<String> words = new ArrayList<String>(); //array list for dictionary
    static final String dictionary[] = new String[109583];
    static char[][] letterGrid = new char[5][5]; //2d array for the letter grid
    static String[] wordsArray;
    static final int X = 5, Y = 5;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        boolean[][] letterGridVisited = new boolean[5][5]; //2d array to show if the letter has been visited during the search process

        letterRandomizer(letterGrid); //randomizes letter grid
        dictionaryRead(words); //reads dictionary into array list

        System.out.println("Following words of dictionary are present");
        findWords(letterGrid);

    }

    public static void letterRandomizer(char[][] array2d) { //method randomizes the letter board
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'}; //puts all 26 letters of the alphabet into array
        Random rand = new Random(); //random number function

        for (int row = 0; row < array2d.length; row++) { //for each row
            for (int col = 0; col < array2d[row].length; col++) { // for each column
                for (int l = 0; l < 27; l++) { //for loop for the letters
                    array2d[row][col] = letters[rand.nextInt(26)]; //puts random letters into each element of the 2d array
                }
                System.out.print(array2d[row][col] + " "); //prints value found at the row
            }
            System.out.println(); //prints a blank line
        }
        for (int row = 0; row < array2d.length; row++) { //for each row
            for (int col = 0; col < array2d[row].length; col++) { // for each column
                // System.out.print(array2dVisited[row][col] + " " ); //prints value found at the row
            }
            // System.out.println(); //prints a blank line
        }
    }

    public static void dictionaryRead(ArrayList<String> words) throws FileNotFoundException { //method to read dictionary words into array list
        File input = new File("dictionary.txt"); //uses the .txt file as an input
        Scanner fileReader = new Scanner(input); //file reader

        while (fileReader.hasNext()) { //checks if the text file has next lines to read
            words.add(fileReader.nextLine()); //adds whatever is on the next line to the array list
        }
        fileReader.close();

        for (int i = 0; i < dictionary.length; i++) { //for loop for array
            dictionary[i] = words.get(i); //puts all of the elements in the array list into a regular array
        }
        for (int i = 0; i <= words.size(); i++) {
            // System.out.println(wordsArray[i]);
        }

    }

    public static boolean wordVerification(String userWord) { //method to verify if user input matches with the dictionary
        for (int i = 0; i < words.size(); i++) { //for loop for array list size
            if (userWord.equalsIgnoreCase(words.get(i))) {
                return true; //if the user inputed word matches with a word in the dictionary, it returns true
            }
        }
        return false; //other wise it returns false
    }

    static void findWordsUtil(char boggle[][], boolean visited[][], int i, int j, String word)
    {
        // Mark current cell as visited and append current character
        // to str
        visited[i][j] = true;
        word = word + boggle[i][j];

        // If str is present in dictionary, then print it
        if (wordVerification(word))
            System.out.println(word);

        // Traverse 8 adjacent cells of boggle[i][j]
        for (int row = i - 1; row <= i + 1 && row < X; row++) {
            for (int col = j - 1; col <= j + 1 && col < Y; col++) {
                if (row >= 0 && col >= 0 && !visited[row][col]) {
                    findWordsUtil(boggle, visited, row, col, word);
                }
            }
        }

        // Erase current character from string and mark visited
        // of current cell as false
        word = "" + word.charAt(word.length() - 1);
        visited[i][j] = false;
    }

    // Prints all words present in dictionary.
    static void findWords(char boggle[][])
    {
        // Mark all characters as not visited
        boolean visited[][] = new boolean[X][Y];

        // Initialize current string
        String str = "";

        // Consider every character and look for all words
        // starting with this character
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                findWordsUtil(boggle, visited, i, j, str);
            }
        }
    }
}

// public static boolean wordVerification (String userWord) { //method to verify if user input matches with the dictionary
//     for (int i = 0 ;i < words.size(); i++) { //for loop for array list size
//         if (userWord.equals(words.get(i))) {
//             return true; //if the user inputed word matches with a word in the dictionary, it returns true
//         }
//     }
//     return false; //other wise it returns false
// }
//
// public static void findValidWords(char grid[][], boolean visited[][], int i, int j, String word) {
//     visited[i][j]  =  true;
//     word = word + grid[i][j];
//
//     if(wordVerification(word)) {
//         System.out.print(word);
//     }
//
//     for (int row = i - 1; row <= i + 1 && row < 5; row++) {
//         for (int col = j - 1; col <= j + 1 && col < 5; col++) {
//             if (row >= 0 && col >= 0 && !visited[row][col]) {
//                 findValidWords(grid, visited, row, col, word);
//             }
//         }
//
//     }
//     word = "" +word.charAt(word.length() - 1);
//     visited[i][j] = false;
//
// }
//
// public static void findWords(char grid[][]) {
//     boolean visited[][] = new boolean[5][5];
//
//     String word = "";
//
//     for (int i = 0; i < 5; i++) {
//         for (int j = 0; j < 5; j++) {
//             findValidWords(grid, visited, i, j, word);
//         }
//     }
// }

