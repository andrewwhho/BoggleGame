import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Author: Armin O., Harris A., Andrew H.
 * Date: 06/03/2021
 * This is the test GUI for Boggle
 */

public class BoggleGame extends JFrame implements ActionListener {
    //Creating the gridArray
    static char[][] boggleGrid = new char[5][5];
    //Creating the values of the different dies
    static char[][] dice = {{'A', 'A', 'A', 'F', 'R', 'S'}, {'A', 'A', 'E', 'E', 'E', 'E'}, {'A', 'A', 'F', 'I', 'R', 'S'}, {'A', 'D', 'E', 'N', 'N', 'N'}, {'A', 'E', 'E', 'E', 'E', 'M'},
            {'A', 'E', 'E', 'G', 'M', 'U'}, {'A', 'E', 'G', 'M', 'N', 'N'}, {'A', 'F', 'I', 'R', 'S', 'Y'}, {'B', 'J', 'K', 'Q', 'X', 'Z'}, {'C', 'C', 'N', 'S', 'T', 'W'},
            {'C', 'E', 'I', 'I', 'L', 'T'}, {'C', 'E', 'I', 'L', 'P', 'T'}, {'C', 'E', 'I', 'P', 'S', 'T'}, {'D', 'D', 'L', 'N', 'O', 'R'}, {'D', 'H', 'H', 'L', 'O', 'R'},
            {'D', 'H', 'H', 'N', 'O', 'T'}, {'D', 'H', 'L', 'N', 'O', 'R'}, {'E', 'I', 'I', 'I', 'T', 'T'}, {'E', 'M', 'O', 'T', 'T', 'T'}, {'E', 'N', 'S', 'S', 'S', 'U'},
            {'F', 'I', 'P', 'R', 'S', 'Y'}, {'G', 'O', 'R', 'R', 'V', 'W'}, {'H', 'I', 'P', 'R', 'R', 'Y'}, {'N', 'O', 'O', 'T', 'U', 'W'}, {'O', 'O', 'O', 'T', 'T', 'U'}};

    static ArrayList<String> aiWords = new ArrayList<>(); //array list for the AI's words found in the grid
    static final int X = 3, Y = 3;

    //Creating SFX objects
    static SoundEffect dingSFX = new SoundEffect("SFX//ding.wav");
    static SoundEffect errorSFX = new SoundEffect("SFX//errorSFX.wav");
    static SoundEffect wrongAnswerSFX = new SoundEffect("SFX//wrongAnswerSFX.wav");
    static SoundEffect littleTimeLeft = new SoundEffect("SFX//littleTimeLeft.wav");
    static SoundEffect confirmSFX = new SoundEffect("SFX//confirmPrompt.wav");

    //Creating taskbar icons for the GUI
    static Image boggleGameIcon = Toolkit.getDefaultToolkit().getImage("Icons//boggleIcon.jpg");
    static Image menuIcon = Toolkit.getDefaultToolkit().getImage("Icons//menuIcon.png");
    static Image headsOrTailsIcon = Toolkit.getDefaultToolkit().getImage("Icons//headsOrTailsIcon.png");
    static Image endCardIcon = Toolkit.getDefaultToolkit().getImage("Icons//endCardIcon.png");

    static ArrayList<String> dictionaryWords = new ArrayList<>(); //array list for dictionary

    //Creating a variable to determine if the user chose single player or multiplayer mode
    static boolean singlePlayerMode;

    static int player1Score; // Create integer for player 1s score
    static int player2Score; // Create integer for player 2s score

    //Creating a variable to store passes by player 1
    static int player1Passes = 0;
    //Creating a variable to store passes by player 2
    static int player2Passes = 0;

    static boolean player1Turn; // Create boolean for player 1s turn

    static JLabel playerTurn; // Creating JLabel to see whose turn it is
    //Creating a variable to store the current player
    static String currentPlayer = "Current Player: ";

    //Creating a variable to store player 1s word
    static String player1Word = "";
    //Creating a variable to store player 2s word
    static String player2Word = "";

    Timer timer; // Creating the timer object
    int seconds; // Creating seconds variable

    //Creating a variable to store the rounds
    static int rounds = 1;
    //Creating a variable to store the maximum number of rounds
    static int maximumRounds = 6;

    //Creating the exitPrompt method
    public static void exitPrompt(){
        //Playing confirmSFX
        confirmSFX.play();

        //Asking the user if they wish to exit the program
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the program?",
                "Quit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            //If the user chooses "Yes," closing the program
            System.exit(0);
        }
    }

    //Creating the title of the main window
    String originalTitle = "Boggle Game";

    //Creating JPanel for the start pane
    JPanel startPane;
    //Creating button for single player
    JButton singlePlayerOption;
    //Creating a button for multiplayer option
    JButton multiplayerOption;
    //Creating the title for the start pane
    JLabel startMenuText;

    //Creating the main JPanel container
    JPanel mainContainer;

    //Creating the left JPanel container
    JPanel rightSideContainer;
    //Creating an exit button
    JButton quitButton;
    //Creating the exit button
    JButton restartButton;
    //Creating the resume button
    JButton resumeButton;
    //Creating the menu button
    JButton menuButton;
    //Creating the "Shake-up the board" button
    static JButton shakeUpBoard;
    //Creating the "Pass" button
    JButton passRound;
    //Creating the information button
    JButton gameInfo;
    //Creating a JLabel for the timer
    JLabel timerLabel;
    //Creating a JLabel for player 1s score
    static JLabel player1LeaderboardScore;
    //Creating a JLabel for player 2s score
    static JLabel player2LeaderboardScore;

    //Creating JLabel to display the current gamemode
    static JLabel currentGameMode;

    //Creating a JLabel to display the current round number
    static JLabel roundNumber;

    //Creating a JPanel to contain the game grid
    JPanel gridContainer;
    //Creating an array to store the buttons
    public static JButton[][] gridButtons;

    //Creating a JSlider for the number of rounds
    JSlider maxRounds;

    //Creating the menuFrame
    JFrame menuFrame;

    //Creating the GUI constructor
    public BoggleGame() {
        //Setting the title of the window
        setTitle(originalTitle);
        //Setting the size of the window
        setSize(1800, 1000);
        //Setting the default close operation
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //Setting the ImageIcon of the main GUI
        setIconImage(boggleGameIcon);

        //Checking to see if the close button was pressed on the window (on the title bar)
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                //If the close button was pressed, the "exitPrompt" method is called
                exitPrompt();
            }
        });

        //////////CODE BLOCK FOR THE MAIN GAME BOARD\\\\\\\\\\
        //Creating the main JPanel container
        mainContainer = new JPanel();

        //Creating a JPanel to contain the game grid
        gridContainer = new JPanel();
        //Making the gridContainer transparent
        gridContainer.setOpaque(false);
        //Creating a GridLayout for the buttons
        GridLayout gridLayout = new GridLayout(5, 5);
        //Creating an array to store the buttons
        gridButtons = new JButton[5][5];

        //Creating a counted loop to index the 2D button array (rows)
        for (int r = 0; r < gridButtons.length; r++){
            //Creating a counted loop to index the number of columns
            for (int c = 0; c < gridButtons[r].length; c++){
                //Creating the buttons at the current indexes
                gridButtons[r][c] = new JButton();
                //Setting the name of each button to its location in the 2D array
                gridButtons[r][c].setName(r + "\s" + c);

                //Adding action listeners to each button
                gridButtons[r][c].addActionListener(this);

                //Setting the gridButton font
                gridButtons[r][c].setFont(new Font("DIN", Font.BOLD, 55));
                //Setting the text color to white
                gridButtons[r][c].setForeground(Color.WHITE);
                //Setting the background to light gray
                gridButtons[r][c].setBackground(new Color(85, 84, 84));
                //Applying borders to the gridButton
                gridButtons[r][c].setBorder(new LineBorder(Color.BLACK, 3, false));

                //Adding each button to the JPanel
                gridContainer.add(gridButtons[r][c]);
            }
        }

        //Applying grid layout to the gridContainer
        gridContainer.setLayout(gridLayout);

        //Initializing the restart button
        restartButton = new JButton("Restart");
        //Setting the font of the restart button
        restartButton.setFont(new Font("DIN", Font.BOLD, 24));
        //Setting the text color to white
        restartButton.setForeground(Color.WHITE);
        //Setting the background of the restartButton
        restartButton.setBackground(new Color(109, 109, 109));
        //Applying border to the restartButton
        restartButton.setBorder(new LineBorder(Color.BLACK, 3, false));
        //Adding an action listener to the restart button
        restartButton.addActionListener(this);
        //Initializing the exit button
        quitButton = new JButton("Quit");
        //Setting the font of the exit button
        quitButton.setFont(new Font("DIN", Font.BOLD, 24));
        //Setting the text color to white
        quitButton.setForeground(Color.WHITE);
        //Setting the background of the quitButton
        quitButton.setBackground(new Color(109, 109, 109));
        //Applying border to the quitButton
        quitButton.setBorder(new LineBorder(Color.BLACK, 3, false));
        //Applying border to the quitButton
        quitButton.setBorder(new LineBorder(Color.BLACK, 3, false));
        //Adding an action listener to the exit button
        quitButton.addActionListener(this);
        //Initializing the resume button
        resumeButton = new JButton("Resume");
        //Setting the font of the resume button
        resumeButton.setFont(new Font("DIN", Font.BOLD, 24));
        //Setting the font color to white
        resumeButton.setForeground(Color.WHITE);
        //Setting the background of the resumeButton
        resumeButton.setBackground(new Color(109, 109, 109));
        //Applying border to the resumeButton
        resumeButton.setBorder(new LineBorder(Color.BLACK, 3, false));
        //Adding an action listener to the resume button
        resumeButton.addActionListener(this);
        //Initializing the menu button
        menuButton = new JButton("Menu");
        //Setting the font of the menu button
        menuButton.setFont(new Font("DIN", Font.BOLD, 14));
        //Setting the text color to white
        menuButton.setForeground(Color.WHITE);
        //Setting the background of the menuButton
        menuButton.setBackground(new Color(109, 109, 109));
        //Adding an action listener to the menu button
        menuButton.addActionListener(this);
        //Initializing the "passRound" button
        passRound = new JButton("Pass");
        //Setting the font of the passRound button
        passRound.setFont(new Font("DIN", Font.BOLD, 14));
        //Setting the text color to white
        passRound.setForeground(Color.WHITE);
        //Setting the background of the passRound button
        passRound.setBackground(new Color(109, 109, 109));
        //Adding an action listener to the passRound button
        passRound.addActionListener(this);
        //Initializing the shakeUpBoard button
        shakeUpBoard = new JButton("Shake-Up the Board");
        //Setting the shakeUpBoard button's text
        shakeUpBoard.setFont(new Font("DIN", Font.BOLD, 14));
        //Setting the text color to white
        shakeUpBoard.setForeground(Color.WHITE);
        //Setting the background of the shakeUpBoard button
        shakeUpBoard.setBackground(new Color(109, 109, 109));
        //Adding an action listener to the shakeUpBoard button
        shakeUpBoard.addActionListener(this);
        //Making the shakeUpBoard button not clickable
        shakeUpBoard.setEnabled(false);

        //Creating a header for the timer
        JLabel timerHeader = new JLabel("Time Remaining:");
        //Setting the header font
        timerHeader.setFont(new Font("DIN", Font.BOLD, 25));
        //Setting the font color to white
        timerHeader.setForeground(Color.WHITE);
        //Initializing the timer JLabel
        timerLabel = new JLabel("00s");
        //Setting the timerLabel font
        timerLabel.setFont(new Font("DIN", Font.PLAIN, 18));
        //Setting the font color to white
        timerLabel.setForeground(Color.WHITE);
        //Creating a header for the leaderboard
        JLabel leaderboardHeader = new JLabel("Leaderboard:");
        //Setting the header font
        leaderboardHeader.setFont(new Font("DIN", Font.BOLD, 25));
        //Setting the font color to white
        leaderboardHeader.setForeground(Color.WHITE);
        //Initializing the leaderboardPlayer1 JLabel
        JLabel leaderboardPlayer1 = new JLabel("Player 1 Score:");
        //Setting the leaderboardPlayer1 Font
        leaderboardPlayer1.setFont(new Font("DIN", Font.PLAIN, 18));
        //Setting the font color to white
        leaderboardPlayer1.setForeground(Color.WHITE);
        //Initializing the leaderboardPlayer2 JLabel
        JLabel leaderboardPlayer2 = new JLabel("Player 2 Score:");
        //Setting the leaderboardPlayer2 Font
        leaderboardPlayer2.setFont(new Font("DIN", Font.PLAIN, 18));
        //Setting the font color to white
        leaderboardPlayer2.setForeground(Color.WHITE);

        //Making the button container for the rightSideContainer
        JPanel rightButtonContainer = new JPanel();
        //Adding the buttons to the rightButtonContainer
        rightButtonContainer.add(menuButton);
        rightButtonContainer.add(passRound);
        rightButtonContainer.add(shakeUpBoard);
        //Applying BoxLayout to the panel
        rightButtonContainer.setLayout(new BoxLayout(rightButtonContainer, BoxLayout.Y_AXIS));
        //Making the rightButtonContainer transparent
        rightButtonContainer.setOpaque(false);

        //Creating a JPanel for the timer elements
        JPanel timerElements = new JPanel();
        //Adding the timerHeader and timerLabel
        timerElements.add(timerHeader);
        timerElements.add(timerLabel);
        //Applying GridLayout
        timerElements.setLayout(new FlowLayout());
        //Making timerElements transparent
        timerElements.setOpaque(false);

        //Initializing player 1s leaderboard score
        player1LeaderboardScore = new JLabel("0");
        //Setting the font
        player1LeaderboardScore.setFont(new Font("DIN", Font.PLAIN, 14));
        //Setting the font color to white
        player1LeaderboardScore.setForeground(Color.WHITE);
        //Initializing player 2s leaderboard score
        player2LeaderboardScore = new JLabel("0");
        //Setting the font
        player2LeaderboardScore.setFont(new Font("DIN", Font.PLAIN, 14));
        //Setting the font color to white
        player2LeaderboardScore.setForeground(Color.WHITE);

        //Creating a JPanel for the leaderboard elements
        JPanel leaderboardElements = new JPanel();
        //Adding the two leaderboards and the header to the JPanel
        leaderboardElements.add(leaderboardHeader);
        leaderboardElements.add(leaderboardPlayer1);
        leaderboardElements.add(player1LeaderboardScore);
        leaderboardElements.add(leaderboardPlayer2);
        leaderboardElements.add(player2LeaderboardScore);
        //Applying BoxLayout
        leaderboardElements.setLayout(new BoxLayout(leaderboardElements, BoxLayout.Y_AXIS));
        //Making leaderboardElements transparent
        leaderboardElements.setOpaque(false);

        //Creating the game mode header
        JLabel gameModeHeader = new JLabel("Gamemode:");
        //Setting the header font
        gameModeHeader.setFont(new Font("DIN", Font.BOLD, 25));
        //Setting the font color to white
        gameModeHeader.setForeground(Color.WHITE);
        //Initializing the currentGameMode JLabel
        currentGameMode = new JLabel();
        //Setting the currentGameMode's font
        currentGameMode.setFont(new Font("DIN", Font.PLAIN, 18));
        //Setting the font color to white
        currentGameMode.setForeground(Color.WHITE);

        //Creating a JPanel for the current gamemode information
        JPanel gamemodeContainer = new JPanel();
        //Adding the currentGameMod and gameModeHeader to the JPanel
        gamemodeContainer.add(gameModeHeader);
        gamemodeContainer.add(currentGameMode);
        //Applying BoxLayout to the JPanel
        gamemodeContainer.setLayout(new BoxLayout(gamemodeContainer, BoxLayout.Y_AXIS));
        //Making the gamemodeContainer transparent
        gamemodeContainer.setOpaque(false);

        //Creating the playerContainer
        JPanel playerContainer = new JPanel();

        //Creating the playerTurn header
        JLabel playerTurnHeader = new JLabel(currentPlayer);
        //Setting the font
        playerTurnHeader.setFont(new Font("DIN", Font.BOLD, 25));
        //Setting the font color to white
        playerTurnHeader.setForeground(Color.WHITE);

        //Initializing the playerTurn JLabel
        playerTurn = new JLabel();
        //Setting the font of the playerTurn JLabel
        playerTurn.setFont(new Font("DIN", Font.PLAIN, 18));
        //Setting the font color to white
        playerTurn.setForeground(Color.WHITE);
        //If it's player 1s turn
        if (player1Turn){
            //Setting the text to "Player 1"
            playerTurn.setText("Player 1");
        }
        //If it's player 2s turn
        else {
            //Setting the text to "Player 2"
            playerTurn.setText("Player 2");
        }

        //Adding the playerTurnHeader and the playerTurn JLabels to the playerContainer
        playerContainer.add(playerTurnHeader);
        playerContainer.add(playerTurn);
        //Applying BoxLayout to the playerContainer
        playerContainer.setLayout(new BoxLayout(playerContainer, BoxLayout.Y_AXIS));
        //Making playerContainer transparent
        playerContainer.setOpaque(false);

        //Creating a header for the rounds
        JLabel roundHeader = new JLabel("Round:");
        //Setting the font of the roundHeader
        roundHeader.setFont(new Font("DIN", Font.BOLD, 25));
        //Setting the font color to white
        roundHeader.setForeground(Color.WHITE);
        //Initializing the roundNumber JLabel
        roundNumber = new JLabel(String.valueOf(rounds));
        //Setting the font of the roundNumber
        roundNumber.setFont(new Font("DIN", Font.PLAIN, 18));
        //Setting the font color to white
        roundNumber.setForeground(Color.WHITE);

        //Creating a JPanel to contain the roundHeader and the roundNumber
        JPanel roundContainer = new JPanel();
        //Adding the JLabels to the panel
        roundContainer.add(roundHeader);
        roundContainer.add(roundNumber);
        //Applying BoxLayout to the roundContainer
        roundContainer.setLayout(new BoxLayout(roundContainer, BoxLayout.Y_AXIS));
        //Making roundContainer transparent
        roundContainer.setOpaque(false);

        //Creating a JPanel for the top half of the rightSideContainer
        JPanel rightTopContainer = new JPanel();
        //Adding the timerElements JPanel, leaderboardElements JPanel, playerContainer JPanel,
        //gamemodeContainer JPanel, and the roundContainer JPanel to the rightTopContainer
        rightTopContainer.add(timerElements);
        rightTopContainer.add(leaderboardElements);
        rightTopContainer.add(playerContainer);
        rightTopContainer.add(gamemodeContainer);
        rightTopContainer.add(roundContainer);
        //Applying GridLayout to the rightTopContainer
        rightTopContainer.setLayout(new GridLayout(5, 1));
        //Making rightTopContainer transparent
        rightTopContainer.setOpaque(false);

        //Initializing the rightSideContainer
        rightSideContainer = new JPanel();
        //Applying BorderLayout to the rightSideContainer
        rightSideContainer.setLayout(new BorderLayout());
        //Adding the rightTopContainer and rightButtonContainer to the rightSideContainer
        rightSideContainer.add(rightTopContainer);
        rightSideContainer.add(BorderLayout.SOUTH, rightButtonContainer);
        //Setting the rightSideContainer background to dark gray
        rightSideContainer.setBackground(Color.DARK_GRAY);

        //Applying BorderLayout to the mainContainer
        mainContainer.setLayout(new BorderLayout());
        //Adding the gridContainer to the mainContainer
        mainContainer.add(gridContainer);
        //Adding the rightSideContainer to the right side of the mainContainer
        mainContainer.add(BorderLayout.EAST, rightSideContainer);
        //Setting the mainContainer background to dark gray
        mainContainer.setBackground(Color.DARK_GRAY);

        //Creating a dimension for the start menu
        Dimension startMenuDimensions = new Dimension(500, 500);
        //////////START MENU TEXT\\\\\\\\\\
        //Creating the text for the start menu
        startMenuText = new JLabel("Select Your Gamemode:");
        //Centering the text (Horizontally)
        startMenuText.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        //Setting the startMenuText font
        startMenuText.setFont(new Font("DIN", Font.BOLD, 75));
        //Setting the startMenuText color to white
        startMenuText.setForeground(Color.WHITE);

        //////////SINGLE PLAYER BUTTON\\\\\\\\\\
        //Creating the single player JButton
        singlePlayerOption = new JButton("Single Player Mode");
        //Setting the font on the single player button
        singlePlayerOption.setFont(new Font("DIN", Font.BOLD, 35));
        //Setting the background color for the single player button
        singlePlayerOption.setBackground(new Color(85, 84, 84));
        //Setting the font color for the single player button
        singlePlayerOption.setForeground(Color.WHITE);
        //Setting the single player button border
        singlePlayerOption.setBorder(new LineBorder(Color.BLACK, 5, true));
        //Adding an action listener to the single player button
        singlePlayerOption.addActionListener(this);
        //Setting the preferred size of the single player button
        singlePlayerOption.setPreferredSize(startMenuDimensions);

        //////////MULTIPLAYER BUTTON\\\\\\\\\\
        //Creating the multiplayer JButton
        multiplayerOption = new JButton("Multiplayer Mode");
        //Setting the font on the multiplayer button
        multiplayerOption.setFont(new Font("DIN", Font.BOLD, 35));
        //Setting the background color for the multiplayer button
        multiplayerOption.setBackground(new Color(85, 84, 84));
        //Setting the font color for the multiplayer button
        multiplayerOption.setForeground(Color.WHITE);
        //Setting the multiplayer button border
        multiplayerOption.setBorder(new LineBorder(Color.BLACK, 5, true));
        //Adding an action listener to the multiplayer button
        multiplayerOption.addActionListener(this);
        //Setting the preferred size of the multiplayer button
        multiplayerOption.setPreferredSize(startMenuDimensions);

        //Creating a JPanel for the round confirmation
        JPanel roundSetup = new JPanel();
        roundSetup.setPreferredSize(new Dimension(200, 100));
        //Making roundSetup transparent
        roundSetup.setOpaque(false);

        //Creating a JLabel to display the max number of rounds
        JLabel currentMaxRounds = new JLabel("Maximum Rounds: 06");
        //Setting the font of the label
        currentMaxRounds.setFont(new Font("DIN", Font.BOLD, 25));
        //Setting the font color to white
        currentMaxRounds.setForeground(Color.WHITE);

        //Initializing the maxRound JSlider
        maxRounds = new JSlider(0, 50, 6);
        //Making the track paintable
        maxRounds.setPaintTrack(true);
        //Making the ticks paintable
        maxRounds.setPaintTicks(true);
        //Making the labels paintable
        maxRounds.setPaintLabels(true);
        //Setting the color
        maxRounds.setForeground(new Color(16, 125, 212));
        //Setting the large tick spacing
        maxRounds.setMajorTickSpacing(10);
        //Setting the minor tick spacing
        maxRounds.setMinorTickSpacing(1);
        //Making the slider transparent
        maxRounds.setOpaque(false);

        //Adding a ChangeListener to the maxRounds slider
        maxRounds.addChangeListener(e -> {
            //Creating a variable to store the JLabel text
            String labelText = "Maximum Rounds: ";

            //Setting the max number of rounds
            maximumRounds = maxRounds.getValue();

            //If the value of the maximum rounds is less than 10
            if (maximumRounds < 10){
                //Updating the GUI to display the max rounds
                currentMaxRounds.setText(labelText + "0" + maximumRounds);
            }
            //If the value of the maximum rounds is greater than or equal to 10
            else {
                //Updating the GUI to display the max rounds
                currentMaxRounds.setText(labelText + maximumRounds);
            }
        });

        //Adding the maxRounds slider and the currentMaxRounds label to the roundSetup JPanel
        roundSetup.add(maxRounds);
        roundSetup.add(currentMaxRounds);
        //Applying BoxLayout to the panel
        roundSetup.setLayout(new GridBagLayout());

        //Creating the JPanel to store the buttons
        JPanel buttonPane = new JPanel();
        //Adding the buttons to the buttonPane
        buttonPane.add(singlePlayerOption);
        buttonPane.add(multiplayerOption);
        //Applying a FlowLayout to the buttonPane
        buttonPane.setLayout(new FlowLayout());
        //Setting the background of the button panel to dark gray
        buttonPane.setBackground(Color.DARK_GRAY);

        //Creating a JPanel for the text pane
        JPanel topPanel = new JPanel();
        //Applying BorderLayout to the top panel
        topPanel.setLayout(new GridLayout(2, 1));
        //Adding the startMenuText to the topPanel
        topPanel.add(startMenuText);
        //Adding the roundSetup JPanel
        topPanel.add(BorderLayout.SOUTH, roundSetup);
        //Setting the background of the top panel to dark gray
        topPanel.setBackground(Color.DARK_GRAY);

        //Creating the startPane
        startPane = new JPanel();
        //Applying the GridLayout to the startPane
        startPane.setLayout(new BorderLayout());
        //Adding the topPanel to the startPane
        startPane.add(topPanel);
        //Adding the buttonPane to the startPane
        startPane.add(BorderLayout.SOUTH, buttonPane);

        //Adding the startPane to the main constructor
        add(startPane);

        //Calling the timer method
        timer();

        //Maximizing the window
        setExtendedState(MAXIMIZED_BOTH);
        //Making the window visible
        setVisible(true);

        //Adding a KeyListener to the GUI
        addKeyListener(new KeyListener() {
            //If a key is pressed
            @Override
            public void keyPressed(KeyEvent e) {
                //If the enter key was pressed
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    //Running the "wordConfirmation" method
                    wordConfirmation();
                }
            }
            //If a key is types
            @Override
            public void keyTyped(KeyEvent e) {
                //Do nothing
            }
            //If a key is released
            @Override
            public void keyReleased(KeyEvent e) {
                //Do nothing
            }
        });
    }

    //Creating a boolean canContinue
    static boolean canContinue = false;

    //Creating the ActionListener
    public void actionPerformed(ActionEvent e){
        //Creating a variable to store the action performed
        String actionPerformed = e.getActionCommand();

        //Creating a boolean condition to break the outer loop
        boolean breakLoop = false;
        //Creating a counted loop to traverse the rows of the gridButtons
        for (JButton[] gridButton : gridButtons) {
            //If the code is told to break the outer loop
            if (breakLoop) {
                //Breaking the loop
                break;
            }
            //Creating a counted loop to traverse the columns of the gridButtons
            for (JButton jButton : gridButton) {
                //If the button at the current index was pressed
                if (jButton.getModel().isArmed()) {
                    //Appending the letters on the board
                    letterAppend(player1Turn, jButton.getText());

                    //Changing the color of the button button to red
                    jButton.setForeground(Color.RED);

                    //Focusing on the window again
                    requestFocus();

                    //Telling the outer loop to break
                    breakLoop = true;

                    //Breaking the inner loop
                    break;
                }
            }
        }

        switch (actionPerformed) {
            //If the user chooses single player or multiplayer
            case "Single Player Mode", "Multiplayer Mode" -> {
                //If the max rounds is set to 0
                if (maximumRounds == 0){
                    //Playing errorSFX
                    errorSFX.play();

                    //Displaying error message to the user
                    JOptionPane.showMessageDialog(null, "Maximum number of rounds cannot be 0!" +
                            "\nPlease select a value greater than 0.", "Invalid Number of Rounds", JOptionPane.ERROR_MESSAGE);
                }

                //If the program can continue
                else if (canContinue){
                    //Try to pause code execution for 200ms
                    try {
                        //Pausing code execution for 200ms
                        Thread.sleep(200);
                    } catch (InterruptedException interruptedException) { //If an exception is caught
                        //Do nothing
                    }

                    //Hiding the startPane
                    //noinspection deprecation
                    startPane.hide();

                    //If the user chose single player
                    if (actionPerformed.equals("Single Player Mode")) {
                        //Setting the variable "singlePlayerMode" to true
                        singlePlayerMode = true;

                        //Setting the currentGameMode text to "Single Player"
                        currentGameMode.setText("Single Player");

                        //Disabling the "Pass" button
                        passRound.setEnabled(false);
                        //Enabling the "Shake-Up Board" button
                        shakeUpBoard.setEnabled(true);
                    }
                    //If the user chose multiplayer mode
                    else {
                        //Setting singlePlayerMode to false
                        singlePlayerMode = false;

                        //Setting the currentGameMode text to "Multiplayer"
                        currentGameMode.setText("Multiplayer");
                    }

                    //Adding the mainContainer to the GUI constructor
                    add(mainContainer);

                    //Starting the timer
                    timer.start();

                    //Requesting the focus on the main window
                    requestFocus();
                }

                //If the program cannot continue
                else {
                    //Playing errorSFX
                    errorSFX.play();

                    //Displaying error message to the user
                    JOptionPane.showMessageDialog(null, "Please select either \"Heads\" or \"Tails\" before continuing!",
                            "Please Select From The Heads or Tails Menu", JOptionPane.ERROR_MESSAGE);
                }
            }

            //If the user presses the quit button
            case "Quit" ->
                    //Running the "exitPrompt" method
                    exitPrompt();

            //If the user presses the menu button
            case "Menu" -> {
                //////////PAUSE TIMER AND THE MAIN CODE HERE\\\\\\\\\\
                //Pausing the timer
                timer.stop();

                //////////CODE BLOCK FOR PAUSE MENU\\\\\\\\\\
                //Changing the title
                setTitle(originalTitle + " - PAUSED");

                //Initializing the menuFrame
                menuFrame = new JFrame("Pause Menu");
                //Setting the frame's default close operation
                menuFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                //Setting the size of the frame
                menuFrame.setSize(280, 515);
                //Making the frame not resizable
                menuFrame.setResizable(false);
                //Setting the menu Icon
                menuFrame.setIconImage(menuIcon);
                //Setting the background of the menu to dark gray
                menuFrame.setBackground(Color.DARK_GRAY);

                //Initializing the gameInfo button
                gameInfo = new JButton("Help");
                //Setting the gameInfo button font
                gameInfo.setFont(new Font("DIN", Font.BOLD, 24));
                //Applying border to the gameInfo button
                gameInfo.setBorder(new LineBorder(Color.BLACK, 3, false));
                //Setting the font color to white
                gameInfo.setForeground(Color.WHITE);
                //Adding an action listener to the gameInfo button
                gameInfo.addActionListener(this);
                //Setting the background of the gameInfo button
                gameInfo.setBackground(new Color(109, 109, 109));

                ///Creating a JPanel to store the buttons
                JPanel mainButtonContainer = new JPanel();

                //Creating the preferred dimension for the buttons
                Dimension preferredButtonSize = new Dimension(250, 113);

                //Applying button sizes
                resumeButton.setPreferredSize(preferredButtonSize);
                gameInfo.setPreferredSize(preferredButtonSize);
                restartButton.setPreferredSize(preferredButtonSize);
                quitButton.setPreferredSize(preferredButtonSize);

                //Adding the buttons to the mainButtonContainer
                mainButtonContainer.add(resumeButton);
                mainButtonContainer.add(gameInfo);
                mainButtonContainer.add(restartButton);
                mainButtonContainer.add(quitButton);
                //Applying flow layout to the mainButtonContainer
                mainButtonContainer.setLayout(new FlowLayout());
                //Setting the background of the main button container
                mainButtonContainer.setBackground(Color.DARK_GRAY);

                //Creating a dimension for the center of the screen
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                //Centering the menu GUI on the center of the screen
                menuFrame.setLocation(dim.width / 2 - menuFrame.getSize().width / 2, dim.height / 2 - menuFrame.getSize().height / 2);

                //Adding the mainButtonContainer to the menu frame
                menuFrame.add(mainButtonContainer);
                //Making the frame visible
                menuFrame.setVisible(true);
                //Requesting focus on the menu frame
                menuFrame.requestFocus();
            }
            //If the user presses the "Resume" button in the menu
            case "Resume" -> {
                //Changing the title back to the original
                setTitle(originalTitle);

                //Closing the menuFrame
                menuFrame.dispose();

                //////////RESUME TIMER & REST OF CODE HERE\\\\\\\\\\
                //Restarting the timer
                timer.start();
            }

            //If the user presses the "Restart" button
            case "Restart" -> {
                //Playing confirmSFX
                confirmSFX.play();

                //Asking the user if they are sure they want to restart
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to restart? " +
                                "All current progress will be lost!", "Restart?",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION){

                    //Changing the title back to the original
                    setTitle(originalTitle);

                    //Closing the menu
                    menuFrame.dispose();

                    //Clearing the variables
                    timerLabel.setText("00s");

                    ///////////CLEAR OTHER VARIABLES HERE\\\\\\\\\\
                    //Restarting the timer
                    timer.restart();
                    timer.stop();

                    //Clearing the ai's stored words
                    aiWords.clear();

                    //Generating the random letters
                    diceRandomizer(dice);

                    //Setting seconds to 0
                    seconds = 0;

                    //Resetting both player scores
                    player1Score = 0;
                    player2Score = 0;
                    //Updating the GUI
                    player1LeaderboardScore.setText("0");
                    player2LeaderboardScore.setText("0");
                    //Resetting both player guesses
                    player1Word = "";
                    player2Word = "";

                    //Telling the program it cannot continue
                    canContinue = false;

                    //Resetting the number of rounds
                    rounds = 1;
                    //Updating the text on the GUI
                    roundNumber.setText(String.valueOf(rounds));

                    //Hiding the mainContainer panel
                    remove(mainContainer);

                    //Calling the headsOrTailsFlip method
                    headsOrTailsFlip();

                    //Showing the startPane
                    //noinspection deprecation
                    startPane.show();
                }
            }

            //If the user presses the "Help" button
            case "Help" -> {
                //Creating a variable storing the game instructions
                String helpText = """
                        Hi! Welcome to Boggle!
                        Boggle is a game where the goal is to come up with as many words as you can
                        from a single letter.
                        To create a word:
                         1. First pick a letter on the board
                         2. Use other letters surrounding the letter you picked to come up with the word
                         Note: The surrounding letters MUST be touching the picked letter. Letters are considered\s
                              "touching" if they are horizontally, vertically, or diagonally adjacent to the
                               picked letter.
                         3. Once you have come up with a word by clicking the letters on the grid, press the "Enter" (Windows)\s
                            or "return" (Mac) button
                            on your keyboard to confirm that word.
                         4. Come up with as many words as you can using that letter before the timer reaches 15s
                         5. Enjoy!""";

                //Creating a JMessageDialogue window to display the message to the user
                JOptionPane.showMessageDialog(null, helpText,
                        "General Information", JOptionPane.INFORMATION_MESSAGE);
            }

            //If the user presses the pass button
            case "Pass" -> {
                //If the current number of rounds is less than or equal to the maximum round limit
                if (rounds != maximumRounds){
                    //If it's player 1s turn
                    if (player1Turn){
                        //Adding 1 to player 1s passes
                        player1Passes++;

                        //Reset the timer
                        seconds = 0;

                        //Update the GUI
                        timerLabel.setText("0" + (seconds) + "s");
                        //Setting the font to black
                        timerLabel.setForeground(Color.WHITE);

                        //Making it player 2s turn
                        player1Turn = false;
                        //Updating the GUI
                        playerTurn.setText("Player 2");
                    }
                    //If it's player 2s turn
                    else {
                        //Adding 2 to player 2s passes
                        player2Passes++;

                        //Reset the timer
                        seconds = 0;

                        //Update the GUI
                        timerLabel.setText("0" + (seconds) + "s");
                        //Setting the font to black
                        timerLabel.setForeground(Color.WHITE);

                        //Making it player 2s turn
                        player1Turn = true;
                        //Updating the GUI
                        playerTurn.setText("Player 1");
                    }

                    //Running the "playerPassVerification" method
                    playerPassVerification();
                }
                //If the rounds are over the limit
                else {
                    //Running the endCardGUI method
                    endCardGUI();
                }
            }

            //If the player presses the shake-up board button
            case "Shake-Up the Board" -> {
                //Randomizing the board
                diceRandomizer(dice);

                //If the game is not in single player mode
                if (!singlePlayerMode){
                    //Disabling the button
                    shakeUpBoard.setEnabled(false);
                }
            }
        }
    }

    //Creating a method to check if both players have passes twice
    public static void playerPassVerification(){
        //Adding 1 to the rounds
        rounds++;
        //Updating the GUI
        roundNumber.setText(String.valueOf(rounds));

        //If both players have passed twice
        if (player1Passes == 2 && player2Passes == 2){
            //Enabling the shake up the board button
            shakeUpBoard.setEnabled(true);

            //Resetting the passes for both players
            player1Passes = 0;
            player2Passes = 0;
        }
    }
    //Creating the headsOrTailsFlip method
    public static void headsOrTailsFlip(){
        //Creating the JFrame
        JFrame firstMove = new JFrame("Heads or Tails?");
        //Setting the size of the window
        firstMove.setSize(500, 350);
        //Creating the dimension for the center of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //Centering the JFrame on the screen
        firstMove.setLocation(dim.width/2-firstMove.getSize().width/2, dim.height/2-firstMove.getSize().height/2);
        //Making the window non-resizable
        firstMove.setResizable(false);
        //Disabling the window's close button
        firstMove.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //Setting firstMove Icon
        firstMove.setIconImage(headsOrTailsIcon);
        //Adding a window listener to the JFrame
        firstMove.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                //If the close button was pressed, the "exitPrompt" method is called
                exitPrompt();
            }
        });

        //Creating a dimension variable for the button sizes
        Dimension buttonSize = new Dimension(225, 225);

        //Creating the "heads" JButton
        JButton heads = new JButton("Heads");
        //Setting the "heads" JButton Font
        heads.setFont(new Font("DIN", Font.BOLD, 25));
        //Setting the background of the button to light grey
        heads.setBackground(new Color(85, 84, 84));
        //Applying a border to the "heads" JButton
        heads.setBorder(new LineBorder(Color.BLACK, 3, true));
        //Setting the font color to white
        heads.setForeground(Color.WHITE);
        //Setting the preferred size of the button
        heads.setPreferredSize(buttonSize);
        //Adding an ActionListener to the "heads" button
        heads.addActionListener(e -> {
            //Getting the ActionCommand
            String buttonPress = e.getActionCommand();

            //If the value chosen matches the return value of the "firstMove" method
            if (buttonPress.equals(firstMove())){
                //It is Player 1s turn
                player1Turn = true;

                //Setting Player 1s turn
                playerTurn.setText("Player 1");

                //Setting the button background to green
                heads.setBackground(Color.GREEN);

                //Telling the user(s) that Player 1 is going first
                JOptionPane.showMessageDialog(null, "Player 1 won the coin flip!" +
                        "\nThey will be going first!", buttonPress + " Won!", JOptionPane.INFORMATION_MESSAGE);
            }
            //If the value does not match
            else {
                //It is not Player 1s turn
                player1Turn = false;

                //Setting Player 2s turn
                playerTurn.setText("Player 2");

                //Setting the button background to red
                heads.setBackground(Color.RED);

                //Telling the user(s) that Player 1 is not going first
                JOptionPane.showMessageDialog(null, "Player 1 did not win the coin flip." +
                        "\nPlayer 2 will be going first!", buttonPress + " Lost!", JOptionPane.INFORMATION_MESSAGE);
            }

            //Closing the JFrame
            firstMove.dispose();

            //Letting the program know it can continue
            canContinue = true;
        });
        //Creating the "tails" JButton
        JButton tails = new JButton("Tails");
        //Setting the "tails" JButton Font
        tails.setFont(new Font("DIN", Font.BOLD, 25));
        //Setting the background of the button to light grey
        tails.setBackground(new Color(85, 84, 84));
        //Applying a border to the "tails" JButton
        tails.setBorder(new LineBorder(Color.BLACK, 3, true));
        //Setting the font color to white
        tails.setForeground(Color.WHITE);
        //Setting the preferred size of the button
        tails.setPreferredSize(buttonSize);
        //Adding an ActionListener to the "tails" button
        tails.addActionListener(e -> {
            //Getting the ActionCommand
            String buttonPress = e.getActionCommand();

            //If the value chosen matches the return value of the "firstMove" method
            if (buttonPress.equals(firstMove())){
                //It is Player 1s turn
                player1Turn = true;

                //Setting Player 1s turn
                playerTurn.setText("Player 1");

                //Setting the button color to green
                tails.setBackground(Color.GREEN);

                //Telling the user(s) that Player 1 is going first
                JOptionPane.showMessageDialog(null, "Player 1 won the coin flip!" +
                        "\nThey will be going first!", buttonPress + " Won!", JOptionPane.INFORMATION_MESSAGE);
            }
            //If the value does not match
            else {
                //It is not Player 1s turn
                player1Turn = false;

                //Setting Player 2s turn
                playerTurn.setText("Player 2");

                //Setting the button color to red
                tails.setBackground(Color.RED);

                //Telling the user(s) that Player 1 is not going first
                JOptionPane.showMessageDialog(null, "Player 1 did not win the coin flip." +
                        "\nPlayer 2 will be going first!", buttonPress + " Lost!", JOptionPane.INFORMATION_MESSAGE);
            }

            //Closing the JFrame
            firstMove.dispose();

            //Letting the program know it can continue
            canContinue = true;
        });

        //Creating a container JPanel for the buttons
        JPanel buttonContainer = new JPanel();
        //Adding the buttons to the buttonContainer
        buttonContainer.add(heads);
        buttonContainer.add(tails);
        //Applying BoxLayout to the buttonContainer
        buttonContainer.setLayout(new FlowLayout());
        //Making the buttonContainer transparent
        buttonContainer.setOpaque(false);

        //Creating JLabels for the user message
        JLabel userMessage1 = new JLabel("Pick \"Heads\" or \"Tails.\"");
        //Setting "userMessage1"s font
        userMessage1.setFont(new Font("DIN", Font.BOLD, 18));
        //Centering the text
        userMessage1.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        //Setting the text color to white
        userMessage1.setForeground(Color.WHITE);
        //Creating the second message
        JLabel userMessage2 = new JLabel("If the coin lands on your choice, you go first!");
        //Setting "userMessage2"s font
        userMessage2.setFont(new Font("DIN", Font.PLAIN, 18));
        //Centering the text
        userMessage2.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        //Setting the text color to white
        userMessage2.setForeground(Color.WHITE);

        //Creating a container JPanel for the user messages
        JPanel messageContainer = new JPanel();
        //Applying BorderLayoutLayout to the messageContainer
        messageContainer.setLayout(new GridLayout(2, 1));
        //Adding the messages to the container
        messageContainer.add(userMessage1);
        messageContainer.add(userMessage2);
        //Making the messageContainer clear
        messageContainer.setOpaque(false);

        //Creating the finalContainerPanel
        JPanel finalContainerPanel = new JPanel();
        //Applying BorderLayout to the finalContainerPanel
        finalContainerPanel.setLayout(new BorderLayout());
        //Adding the messageContainer and the buttonContainer
        finalContainerPanel.add(messageContainer);
        finalContainerPanel.add(BorderLayout.SOUTH, buttonContainer);
        //Setting the background to dark gray
        finalContainerPanel.setBackground(Color.DARK_GRAY);

        //Adding the finalContainerPanel to the JFrame
        firstMove.add(finalContainerPanel);
        //Making the JFrame visible
        firstMove.setVisible(true);
        //Setting the focus of the panel
        firstMove.requestFocus();
    }

    //Creating the firstMove method
    public static String firstMove() { //method returns heads or tails
        Random rand = new Random();
        int randomNum = rand.nextInt(2); //generates random number between 1-2
        if (randomNum == 1) { //if the number is 1, it returns heads
            return "Heads";
        }
        else {
            return "Tails"; //if number is 2, it returns tails
        }
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

            //Adding the current selected face to the current index of the grid
            gridButtons[rows][columns].setText(String.valueOf(selectedFace));
            //Adding values to the boggleGrid array
            boggleGrid[rows][columns] = selectedFace;

            //Adding 1 to the columns
            columns++;

            //If "columns" has reached the end of the columns
            if (columns == gridButtons[0].length) {
                //Resetting columns
                columns = 0;
                //Adding 1 to the rows
                rows++;
            }
            //If the rows has reached the end of the array
            if (rows == gridButtons.length) {
                //Resetting the rows
                rows = 0;
            }
        }
    }

    public static void dictionaryRead(ArrayList<String> words) throws FileNotFoundException { //method to read dictionary words into array list
        File input = new File("dictionary.txt"); //uses the .txt file as an input

        //If the file doesn't exist
        if (!input.exists()){
            //Playing errorSFX
            errorSFX.play();

            //Displaying error message to the user
            JOptionPane.showMessageDialog(null, """
                            The file "dictionary.txt" was not found.
                            If you are using your IDE, please make sure the file is correctly placed.
                            If you are using the .jar version, please make sure the file is in the same
                            folder as the "BoggleGame.jar" file.""",
                    "File Not Found Exception Occurred", JOptionPane.ERROR_MESSAGE);

            //Closing the program
            System.exit(0);
        }

        Scanner fileReader = new Scanner(input); //file reader

        while(fileReader.hasNextLine()) { //checks if the text file has next lines to read
            words.add(fileReader.nextLine()); //adds whatever is on the next line to the array list
        }
        //Closing the Scanner
        fileReader.close();
    }

    //Creating a variable to play or stop the sound effect
    boolean fiveSecondsRemaining = false;
    //Creating a variable to determine if the AI can run
    boolean aiTurn = true;
    //Creating a delay for the AI guesses
    int aiDelay = 3;
    // Timer Method
    public void timer() {
        //Creating the timer object
        timer = new Timer(1000, e -> {
            //If the timer hits the 15s limit
            if (seconds == 15) {
                //If the current number of rounds has hit the maximum number of rounds
                if (rounds == maximumRounds){
                    //Stopping the timer
                    timer.stop();

                    //Running the endCardGUI method
                    endCardGUI();

                    //Voiding the method
                    return;
                }
                //Resetting the timer
                seconds = 0;

                rounds++; // Move onto next round

                //Setting 5 seconds remaining to false
                fiveSecondsRemaining = false;

                //Updating the text on the GUI
                roundNumber.setText(String.valueOf(rounds));

                //If it is currently player 1s turn
                if (player1Turn) {
                    //If the gamemode is in single player
                    if(singlePlayerMode){
                        //Disabling the AI
                        aiTurn = true;
                    }

                    //It is no longer player 1s turn
                    player1Turn = false;

                    //Resetting Player 2s word
                    player2Word = "";

                    //Setting the text so the user can see who's turn it is
                    playerTurn.setText("Player 2");
                }
                //If it's not player 1s turn
                else {
                    //If the gamemode is in single player
                    if(singlePlayerMode){
                        //Disabling the AI
                        aiTurn = false;
                    }

                    //It is now player 1s turn
                    player1Turn = true;

                    //Resetting Player 1s word
                    player1Word = "";

                    //Setting the text so the user can see who's turn it is
                    playerTurn.setText("Player 1");
                }

                //Playing the errorSFX
                errorSFX.play();

                //Calling the gridColorReset method
                gridColorReset();
            }

            //Adding seconds
            seconds++;

            //If the aiDelay is less than 3s
            if (aiDelay != 3){
                //Adding 1s to the aiDelay
                aiDelay++;
            }

            //If the current gamemode is single player and it is not player 1s turn and the AI can run and it has been 3s since the last AI run
            if (singlePlayerMode && !player1Turn && aiTurn && aiDelay == 3){
                //Running the aiMethod
                aiMethod();

                //Resetting the aiDelay
                aiDelay = 0;
            }

            //If the timer is under 10 seconds
            if (seconds < 10){
                //Setting the value of the timerLabel as 0xs
                timerLabel.setText("0" + (seconds) + "s");
                //Setting the color to black
                timerLabel.setForeground(Color.WHITE);
            }
            //If the timer is at 10 seconds or greater
            else {
                //If the sound effect is not playing
                if (!fiveSecondsRemaining){
                    //Playing sound effect
                    littleTimeLeft.play();

                    //Setting fiveSecondsRemaining to true
                    fiveSecondsRemaining = true;
                }
                //Setting the value of the timerLabel as xxs
                timerLabel.setText((seconds) + "s");
                //Setting the color to red
                timerLabel.setForeground(Color.RED);
            }

        });
    }

    //Creating the gameExitMessage method
    public static void gameExitMessage(){
        //Displaying thank you message
        JOptionPane.showMessageDialog(null, "Thanks for using this program! :)",
                "Thank You For Using This Program!", JOptionPane.INFORMATION_MESSAGE);

        //Exiting the program
        System.exit(0);
    }
    //Creating the endCardGUI method
    public static void endCardGUI(){
        //Creating the JFrame
        JFrame endCard = new JFrame();
        //Setting the frame title
        endCard.setTitle("Game Results");
        //Setting the size of the window
        endCard.setSize(new Dimension(550, 400));
        //Making the window non-resizable
        endCard.setResizable(false);
        //Disabling the close button
        endCard.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //Creating a dimension for the center of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //Centering the endCard GUI on the center of the screen
        endCard.setLocation(dim.width / 2 - endCard.getSize().width / 2, dim.height / 2 - endCard.getSize().height / 2);
        //Applying the window icon
        endCard.setIconImage(endCardIcon);

        //Creating a variable to store the winner
        String winner = "";
        //Creating a variable to store the loser
        String loser = "";

        //Creating a JLabel to display the game winner
        JLabel winnerLabel = new JLabel("Winner: ");
        //Setting the font of the winnerLabel
        winnerLabel.setFont(new Font("DIN", Font.PLAIN, 26));
        //Setting the text color to white
        winnerLabel.setForeground(Color.WHITE);
        //Creating a JLabel to display the game loser
        JLabel loserLabel = new JLabel("Loser: ");
        //Setting the font of the loserLabel
        loserLabel.setFont(new Font("DIN", Font.PLAIN, 26));
        //Setting the font color to white
        loserLabel.setForeground(Color.WHITE);
        //Creating the header panel
        JLabel headerPanel = new JLabel("Wow what an exciting game of Boggle!");
        //Setting the font of the headerPanel
        headerPanel.setFont(new Font("DIN", Font.BOLD, 25));
        //Setting the text color to white
        headerPanel.setForeground(Color.WHITE);
        //Creating the second header panel
        JLabel headerPanel2 = new JLabel("Here are the results:");
        //Setting the font of the headerPanel
        headerPanel2.setFont(new Font("DIN", Font.BOLD, 25));
        //Setting the text color to white
        headerPanel2.setForeground(Color.WHITE);

        //////////DETERMINING WINNER\\\\\\\\\\
        //If the players have a score greater than 0
        if (player1Score > 0 || player2Score > 0){
            //If player 1 won the game
            if (player1Score > player2Score){
                //Setting the winner as player 1 and the loser as player 2
                winner = "Player 1";
                loser = "Player 2";
            }
            //If player 2 won the game
            else if (player2Score > player1Score){
                //Setting the winner as player 2 and the loser as player 1
                winner = "Player 2";
                loser = "Player 1";
            }

            //Creating a JLabel to display the game winner
            winnerLabel.setText("Winner: " + winner);
            //Creating a JLabel to display the game loser
            loserLabel.setText("Loser: " + loser);
        }

        //Creating a JPanel to container the JElements for the top half of the frame
        JPanel topContainer = new JPanel();
        //Adding the panels
        topContainer.add(headerPanel);
        topContainer.add(headerPanel2);
        topContainer.add(winnerLabel);
        topContainer.add(loserLabel);
        //Applying GridBagLayout
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        //Making the top container dark gray
        topContainer.setBackground(Color.DARK_GRAY);

        //Creating user prompt JLabel
        JLabel userPrompt = new JLabel("Would you like to store the results to a .txt file?");
        //Setting the font
        userPrompt.setFont(new Font("DIN", Font.BOLD, 20));
        //Setting the text color to white
        userPrompt.setForeground(Color.WHITE);
        //Creating "yes" JButton
        JButton yesButton = new JButton("Yes");
        //Setting the button font
        yesButton.setFont(new Font("DIN", Font.BOLD, 18));
        //Setting the button text color to white
        yesButton.setForeground(Color.WHITE);
        //Adding an ActionListener to the yesButton
        yesButton.addActionListener(e -> {
            //If the user chooses yes
            //Creating file "gameResults.txt"
            try {
                PrintWriter fileWriter = new PrintWriter("gameResults.txt");
                //Writing player 1s results
                fileWriter.println("Player 1's Score: " + player1Score);
                //Writing player 2s results
                fileWriter.println("Player 2's Score: " + player2Score);
                //If the game was in single player mode
                if (singlePlayerMode){
                    fileWriter.println("Gamemode: Single Player" );
                }
                //If the game was in multiplayer mode
                else {
                    fileWriter.println("Gamemode: Multiplayer");
                }
                //Writing the total number of rounds
                fileWriter.println("Total # of Rounds: " + rounds);
                //Saving changes and closing the file
                fileWriter.close();
            } catch (FileNotFoundException fileNotFoundException) { //If an exception is caught
                //Do nothing
            }

            //Running the game exitMessage method
            gameExitMessage();
        });

        //Creating "no" JButton
        JButton noButton = new JButton("No");
        //Setting the button font
        noButton.setFont(new Font("DIN", Font.BOLD, 18));
        //Setting the button text color to white
        noButton.setForeground(Color.WHITE);
        //Adding an ActionListener to the noButton
        noButton.addActionListener(e -> {
            //Running the game exitMessage method
            gameExitMessage();
        });

        //Creating the preferred size of the buttons
        Dimension preferredButtonSize = new Dimension(100, 80);

        //Setting the preferred size of the buttons
        yesButton.setPreferredSize(preferredButtonSize);
        noButton.setPreferredSize(preferredButtonSize);

        //Setting the button backgrounds
        yesButton.setBackground(new Color(85, 84, 84));
        noButton.setBackground(new Color(85, 84, 84));

        //Creating the button container
        JPanel buttonContainer = new JPanel();
        //Adding the buttons to the buttonContainer
        buttonContainer.add(yesButton);
        buttonContainer.add(noButton);
        //Setting the preferred size of the buttonContainer
        buttonContainer.setSize(new Dimension(220, 90));
        //Applying FlowLayout
        buttonContainer.setLayout(new FlowLayout());
        //Making the buttonContainer transparent
        buttonContainer.setOpaque(false);

        //Creating a JPanel to contain the buttons and the prompt
        JPanel userPromptPanel = new JPanel();
        //Adding the userPrompt and the buttonContainer
        userPromptPanel.add(userPrompt, new GridBagConstraints());
        userPromptPanel.add(buttonContainer);
        //Applying GridBagLayout
        userPromptPanel.setLayout(new GridLayout(2, 1));
        //Making the userPromptPanel dark gray
        userPromptPanel.setBackground(Color.DARK_GRAY);

        //Applying BorderLayout to the endCard
        endCard.setLayout(new BorderLayout());
        //Adding the JPanels to the main frame
        endCard.add(topContainer);
        endCard.add(BorderLayout.SOUTH, userPromptPanel);
        //Making the frame visible
        endCard.setVisible(true);
    }

    //Creating the AI method
    public static void aiMethod(){
        //Calling the "wordFinderAI" method
        wordFinderAI(boggleGrid);
        //Displaying random word
        randomWord(aiWords);
    }

    // Recursive function to find words on the randomized letter grid with respect to the dictionary
    public static void wordFinderRecursive(char[][] grid, boolean[][] used, int x, int y, String word) { //takes in

        //used is a 2d boolean array which determines if the word on the grid has been used or not to form the word
        used[x][y] = true; // sets all values on the grid to true
        word = word + grid[x][y];

        if (binarySearch(dictionaryWords, word.toLowerCase())) { //if the word exists on the grid, it adds it to the AI's word array list
            aiWords.add(word);
        }

        //searches all adjacent squares for matching letters of the word
        for (int row = x - 1; row <= x + 1 && row < X; row++) {
            for (int col = y - 1; col <= y + 1 && col < Y; col++) {
                if (row >= 0 && col >= 0 && !used[row][col]) {
                    //Using recursion
                    wordFinderRecursive(grid, used, row, col, word);
                }
            }
        }

        used[x][y] = false; //marks the letter has been used
    }

    //Adds all words found to the AI's word list with respect to the dictionary
    static void wordFinderAI(char[][] boggle)
    {
        boolean[][] used = new boolean[X][Y]; //2d array to determine if the character is used or not
        String word = ""; //initializes string for use

        for (int i = 0; i < X; i++)
            for (int j = 0; j < Y; j++)
                wordFinderRecursive(boggle, used, i, j, word);
    }

    //method which selects a random word from the AI's available word list
    public static void randomWord (ArrayList<String> aiWords) {
        Random rand = new Random();
        int randomNum = rand.nextInt(aiWords.size()); //variable that generates random number within the arraylist size

        //Setting player 2s word as the AI's word
        player2Word = aiWords.get(randomNum);

        //Calling the wordConfirmation method
        wordConfirmation();
    }

    //Creating the letter appending method
    public static void letterAppend(boolean player1Turn, String buttonValue){
        //If it's player 1s turn
        if (player1Turn){
            //Creating a variable to store the current content of player 1s word
            String currentString = player1Word;

            //Setting the value of player 1s word to the previous value + the new letter
            player1Word = currentString + buttonValue;
        }
        //If it's player 2s turn
        else {
            //Creating a variable to store the current content of player 1s word
            String currentString = player2Word;

            //Setting the value of player 1s word to the previous value + the new letter
            player2Word = currentString + buttonValue;
        }
    }

    // Function to check if a word exists in a grid
    public static boolean findMatch(char[][] grid, String word, int x, int y, int row, int col, int level)
    {
        int wordLength = word.length();

        // Index of the letter is matched
        if (level == wordLength)
            return true;

        // Out of Bounds exceptions by checking the current X and Y position of the grid
        if (x < 0 || y < 0 || x >= row || y >= col)
            return false;

        // If the grid matches with a letter while recurring
        if (grid[x][y] == word.charAt(level))
        {

            // Search letters in adjacent cells
            return (
                    findMatch(grid, word, x - 1, y, row, col, level + 1) ||
                            findMatch(grid, word, x + 1, y, row, col, level + 1) ||
                            findMatch(grid, word, x, y - 1, row, col, level + 1) ||
                            findMatch(grid, word, x, y + 1, row, col, level + 1) ||
                            findMatch(grid, word, x + 1, y + 1, row, col, level + 1) ||
                            findMatch(grid, word, x - 1, y + 1, row, col, level + 1) ||
                            findMatch(grid, word, x + 1, y - 1, row, col, level + 1) ||
                            findMatch(grid, word, x - 1, y - 1, row, col, level + 1) );
        }

        // If the grid does not match with a letter while recurring
        else {
            return false;
        }
    }

    // Function to check if the word exists in the grid or not
    public static boolean checkMatch(char[][] grid, String word, int row, int col)
    {
        // Declare variable word length
        int wordLength = word.length();


        // If the total characters in the grid are less than the word length
        if (wordLength > row * col)
            return false;

        // Traverse in the grid
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {
                // If first letter matches and is found on the grid, then recur and check
                if (grid[i][j] == word.charAt(0))
                    if (findMatch(grid, word, i, j, row, col, 0))
                        return true;
            }
        }
        return false;
    }

    //Creating the binary search method
    public static boolean binarySearch(ArrayList<String> array, String target){
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

    //Creating the "wordConfirmation" method
    public static void wordConfirmation(){
        //If it's player 1s turn and their word exists on the board
        if (player1Turn && checkMatch(boggleGrid, player1Word, 5 ,5)){
            //Verifying the word exists in the dictionary
            if (binarySearch(dictionaryWords, player1Word.toLowerCase())){
                //Running the pointAllocation method
                pointAllocation(player1Word, player1Turn);

                //Playing dingSFX
                dingSFX.play();
            }
            //If the word doesn't exist
            else {
                //Playing wrongAnswerSFX
                wrongAnswerSFX.play();
            }
        }
        //If it's player 2s turn their word exists on the board
        else if (!player1Turn && checkMatch(boggleGrid, player2Word, 5, 5)){
            //Verifying the word exists in the dictionary
            if (binarySearch(dictionaryWords, player2Word.toLowerCase())){
                //Running the pointAllocation method
                pointAllocation(player2Word, player1Turn);

                //Playing dingSFX
                dingSFX.play();
            }
            //If the word doesn't exist
            else {
                //Playing wrongAnswerSFX
                wrongAnswerSFX.play();
            }
        }
        //If the word is not on the grid
        else {
            //Playing wrongAnswerSFX
            wrongAnswerSFX.play();
        }

        //Resetting player 1s guess word
        player1Word = "";

        //Resetting player 2s guess word
        player2Word = "";

        //Calling the grid color reset method
        gridColorReset();
    }

    //Creating the pointAllocation method
    public static void pointAllocation(String word, boolean player1){
        //Creating a counted to store the number of letters in the word
        int counter = 0;

        //Creating a counted loop to count the number of letters in the word
        for (int i = 0; i < word.length(); i++){
            //Adding to the counter
            counter++;
        }

        //If it is player 1s turn
        if (player1){
            //Assigning the points to player 1
            player1Score += counter;

            //Updating player 1s score
            player1LeaderboardScore.setText(String.valueOf(player1Score));
        }
        //If it is player 2s turn
        else {
            //Assigning the points to player 2
            player2Score += counter;

            //Updating player 2s score
            player2LeaderboardScore.setText(String.valueOf(player2Score));
        }
    }

    //Creating the gridColorReset method
    public static void gridColorReset(){
        //Creating a counted loop to traverse the rows
        for (JButton[] gridButton : gridButtons) {
            //Creating a counted loop to traverse the columns
            for (JButton jButton : gridButton) {
                //Setting the buttons to blue
                jButton.setForeground(Color.WHITE);
            }
        }
    }
    //Creating the main method
    public static void main(String[] args)throws FileNotFoundException{
        //Importing files from the dictionary into ArrayList
        dictionaryRead(dictionaryWords);

        //Running the main GUI
        new BoggleGame();

        //Running the headsOrTailsFlip method
        headsOrTailsFlip();

        //Generating the random letters
        diceRandomizer(dice);
    }
}