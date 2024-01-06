import javax.swing.*;

/**
 * Author: Armin Owrak
 * Date: 6/11/2021
 * This is the error menu to display and handle errors from the "SoundEffect" class
 */

public class fileNotFoundError {
    //Creating the main constructor
    protected fileNotFoundError(){}

    //Displaying the error message
    protected void displayError(){
        //Displaying error message
        JOptionPane.showMessageDialog(null, """
                We've notice there are one or more files missing
                If you are using an IDE, please make sure all files are put in the correct project file.
                If you are using the "BoggleGame.jar" file, please ensure all files are in the same
                folder as the .jar file before trying again.""",
                "File Not Found Exception", JOptionPane.ERROR_MESSAGE);

        //Closing the program
        System.exit(0);
    }
}
