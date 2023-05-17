package Chess;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Pieces.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import static Chess.EatenPieces.eatenBlack;
import static Chess.EatenPieces.eatenWhite;


/**
 * This is the Chess.Main Class of our project.
 * All GUI Elements are declared, initialized and used in this class itself.
 * It is inherited from the JFrame Class of Java's Swing Library.
 *
 */

public class Game extends JFrame implements MouseListener {
    //private static final long serialVersionUID = 1L;

    //Variable Declaration
    private static final int Height = 700;
    private static final int Width = 1110;
    public static Rook wr01, wr02, br01, br02;
    public static Knight wk01, wk02, bk01, bk02;
    public static Bishop wb01, wb02, bb01, bb02;
    protected static Pawn [] wp, bp;
    protected static Queen wq, bq;
    protected static King wk, bk;
    private Cell c, previous;
    private int chance = 0;
    public Cell[][] boardState;
    private ArrayList<Cell> destinationlist = new ArrayList<Cell>();
    private Player White = null, Black = null;
    public static JPanel board = new JPanel(new GridLayout(8, 8));
    private JPanel wdetails = new JPanel(new GridLayout(3, 3)); // FOR PLAYERS INFORMATION
    private JPanel bdetails = new JPanel(new GridLayout(3, 3));
    private JPanel wcombopanel = new JPanel(); // are panels that hold the combo boxes used for selecting the white and black players, respectively.
    private JPanel bcombopanel = new JPanel();
    private JPanel controlPanel, WhitePlayer, BlackPlayer, temp, displayTime, showPlayer, time; //  are panels used to organize various components of the GUI.
    private JSplitPane split; // is a JSplitPane used to divide the GUI into two sections, one for the chess board and one for various controls and information.
    private JLabel label, mov; //are JLabels used to display various information about the game, such as the current player's turn and the most recent move made.
    private static JLabel CHNC; // is a static JLabel used to indicate whose turn it is to make a move.
    public Chess.Time timerWhite;
    public Chess.Time timerBlack;
    public static Game Mainboard;
    private boolean selected = false, end = false;
    private Container content; //  This creates a reference variable of type Container, which is later used to add GUI components to the application window.
    private ArrayList<Player> wplayer, bplayer; //  These create two empty ArrayList objects which are used to store instances of the Player class representing white and black players
    private ArrayList<String> Wnames = new ArrayList<String>(); // This creates an empty ArrayList object named Wnames, which is used to store the names of white players.
    private ArrayList<String> Bnames = new ArrayList<String>(); //same
    private JComboBox<String> wcombo, bcombo;// to allow the user to select items from a list of strings Names
    private String wname = null, bname = null, winner = null;
    static String move;
    private Player tempPlayer;
    private JScrollPane wscroll, bscroll;// are used to provide scrolling functionality in case the number of items in the combo boxes exceeds the available space on the GUI.
    private String[] WNames = {}, BNames = {};// names of playres
    private JSlider timeSlider; // for time slider
    private BufferedImage image; // holds an image that will be displayed in the GUI.
    private Button start, wselect, bselect, WNewPlayer, BNewPlayer;
    PawnPromotion window;
    /*
     start declares a button that a user can click to start some process or action in the GUI.
 wselect and  bselect are buttons that allow a user to select a white or black player in the GUI.
  WNewPlayer and BNewPlayer likely allow a user to create a new white or black player in the GUI.
     */
    public static int timeRemaining = 60;
    EatenPieces pieces =new EatenPieces();



    //Constructor
    public Game() {
        //variable initialization
        wr01 = new Rook("WR01", "White_Rook.png", 0);
        wr02 = new Rook("WR02", "White_Rook.png", 0);
        br01 = new Rook("BR01", "Black_Rook.png", 1);
        br02 = new Rook("BR02", "Black_Rook.png", 1);
        wk01 = new Knight("WK01", "White_Knight.png", 0);
        wk02 = new Knight("WK02", "White_Knight.png", 0);
        bk01 = new Knight("BK01", "Black_Knight.png", 1);
        bk02 = new Knight("BK02", "Black_Knight.png", 1);
        wb01 = new Bishop("WB01", "White_Bishop.png", 0);
        wb02 = new Bishop("WB02", "White_Bishop.png", 0);
        bb01 = new Bishop("BB01", "Black_Bishop.png", 1);
        bb02 = new Bishop("BB02", "Black_Bishop.png", 1);
        wq = new Queen("WQ", "White_Queen.png", 0);
        bq = new Queen("BQ", "Black_Queen.png", 1);
        wk = new King("WK", "White_King.png", 0, 7, 4);
        bk = new King("BK", "Black_King.png", 1, 0, 4);
        wp = new Pawn[8];
        bp = new Pawn[8];
        for (int i = 0; i < 8; i++) {
            wp[i] = new Pawn("WP0" + (i + 1), "White_Pawn.png", 0);
            bp[i] = new Pawn("BP0" + (i + 1), "Black_Pawn.png", 1);
        }

        timeRemaining = 60;
        timeSlider = new JSlider();
        move = "White";
        wname = null;
        bname = null;
        winner = null;
        board = new JPanel(new GridLayout(8, 8));
        wdetails = new JPanel(new GridLayout(3, 3));
        bdetails = new JPanel(new GridLayout(3, 3));
        bcombopanel = new JPanel();
        wcombopanel = new JPanel();
        Wnames = new ArrayList<String>();
        Bnames = new ArrayList<String>();
        board.setMinimumSize(new Dimension(800, 700));
        ImageIcon img = new ImageIcon(this.getClass().getResource("icon.png"));
        this.setIconImage(img.getImage());

        //Time Slider Details
        timeSlider.setMinimum(1);
        timeSlider.setMaximum(15);
        timeSlider.setValue(1);
        timeSlider.setMajorTickSpacing(2);
        timeSlider.setPaintLabels(true);
        timeSlider.setPaintTicks(true);
        timeSlider.addChangeListener(new TimeChange());


        //Fetching Details of all Players
        wplayer = Player.fetch_players();
        // This retrieves a list of all the white players

        Iterator<Player> witr = wplayer.iterator();
        while (witr.hasNext())
            Wnames.add(witr.next().name());
        // This loops through the white player list and adds each player's name to the Wnames array list.

        bplayer = Player.fetch_players();
        Iterator<Player> bitr = bplayer.iterator();
        while (bitr.hasNext())
            Bnames.add(bitr.next().name());

        WNames = Wnames.toArray(WNames);
        BNames = Bnames.toArray(BNames);
        //This converts the Wnames and Bnames array lists into arrays of strings, which can be used to populate the combo boxes for player selection.

        Cell cell;
        board.setBorder(BorderFactory.createLoweredBevelBorder());
        Pieces.Piece P;

        content = getContentPane();
        setSize(Width, Height);
        setTitle("Chess");
        content.setBackground(Color.black);
		/*
		These lines set up the main window of the chess game. getContentPane() retrieves the container of the frame, which is where all the components will be placed.
		setSize() sets the size of the window,
		setTitle() sets the title of the window, and
		setBackground() sets the background color to black.
		 */

        controlPanel = new JPanel();
        content.setLayout(new BorderLayout());
        controlPanel.setLayout(new GridLayout(3, 3));
        controlPanel.setBorder(BorderFactory.createTitledBorder(null, "Statistics", TitledBorder.TOP, TitledBorder.CENTER, new Font("Lucida Calligraphy", Font.PLAIN, 20), Color.BLACK));
		/*
		These lines create a new panel for the control panel,
		set the layout of the main container to a border layout,
		 set the layout of the control panel to a 3x3 grid, and set a titled border with a specific font and color.
		 */


        //Defining the Player Box in Control Panel
        WhitePlayer = new JPanel();
        WhitePlayer.setBorder(BorderFactory.createTitledBorder(null, "White Player", TitledBorder.TOP, TitledBorder.CENTER, new Font("times new roman", Font.BOLD, 18), Color.lightGray));
        WhitePlayer.setLayout(new BorderLayout());

        BlackPlayer = new JPanel();
        BlackPlayer.setBorder(BorderFactory.createTitledBorder(null, "Black Player", TitledBorder.TOP, TitledBorder.CENTER, new Font("times new roman", Font.BOLD, 18), Color.BLACK));
        BlackPlayer.setLayout(new BorderLayout());


        JPanel whitestats = new JPanel(new GridLayout(3, 3));
        JPanel blackstats = new JPanel(new GridLayout(3, 3));
        // These lines create two new panels for displaying statistics for the selected players, with a 3x3 grid layout.


        wcombo = new JComboBox<String>(WNames);
        bcombo = new JComboBox<String>(BNames);
        wscroll = new JScrollPane(wcombo);
        bscroll = new JScrollPane(bcombo);
        wcombopanel.setLayout(new FlowLayout());
        bcombopanel.setLayout(new FlowLayout());
        //These lines create combo boxes for selecting white and black players,
        // and wrap them in scroll panes. The combo boxes are added to separate panels with flow layouts.

        wselect = new Button("Select");
        bselect = new Button("Select");


        wselect.addActionListener(new SelectHandler(0));
        bselect.addActionListener(new SelectHandler(1));
        // These listeners are implemented in the SelectHandler class, which is responsible for updating the player statistics
        // on the control panel when a player is selected.

        WNewPlayer = new Button("New Player");
        BNewPlayer = new Button("New Player");
        // These buttons are used to add a new player to the game. When clicked,
        // they open a dialog box to enter the new player's name and save it to the database.

        WNewPlayer.addActionListener(new Handler(0));
        BNewPlayer.addActionListener(new Handler(1));
        //These listeners are implemented in the Handler class, which is responsible for handling the creation of a new player
        // and updating the player statistics on the control panel.

        wcombopanel.add(wscroll);
        wcombopanel.add(wselect);
        wcombopanel.add(WNewPlayer);
        //adding the white player selection drop-down, "Select" button, and "New Player" button to the white player selection panel,
        bcombopanel.add(bscroll);
        bcombopanel.add(bselect);
        bcombopanel.add(BNewPlayer);
        //same

        WhitePlayer.add(wcombopanel, BorderLayout.NORTH);
        BlackPlayer.add(bcombopanel, BorderLayout.NORTH);
        //adding the white and black player selection panels to the corresponding player panels

        whitestats.add(new JLabel("Name   :"));
        whitestats.add(new JLabel("Played :"));
        whitestats.add(new JLabel("Won    :"));
        //adding labels to the white player statistics panel, indicating the meaning of the statistics that will be displayed.

        blackstats.add(new JLabel("Name   :"));
        blackstats.add(new JLabel("Played :"));
        blackstats.add(new JLabel("Won    :"));
        // same

        WhitePlayer.add(whitestats, BorderLayout.WEST);
        BlackPlayer.add(blackstats, BorderLayout.WEST);
        controlPanel.add(WhitePlayer);
        controlPanel.add(BlackPlayer);
        //are adding the white and black player statistics panels to the corresponding player panels


        //Defining all the Cells
        boardState = new Cell[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                P = null;
                if (i == 0 && j == 0)
                    P = br01;
                else if (i == 0 && j == 7)
                    P = br02;
                else if (i == 7 && j == 0)
                    P = wr01;
                else if (i == 7 && j == 7)
                    P = wr02;
                else if (i == 0 && j == 1)
                    P = bk01;
                else if (i == 0 && j == 6)
                    P = bk02;
                else if (i == 7 && j == 1)
                    P = wk01;
                else if (i == 7 && j == 6)
                    P = wk02;
                else if (i == 0 && j == 2)
                    P = bb01;
                else if (i == 0 && j == 5)
                    P = bb02;
                else if (i == 7 && j == 2)
                    P = wb01;
                else if (i == 7 && j == 5)
                    P = wb02;
                else if (i == 0 && j == 3)
                    P = bq;
                else if (i == 0 && j == 4)
                    P = bk;
                else if (i == 7 && j == 3)
                    P = wq;
                else if (i == 7 && j == 4)
                    P = wk;
                else if (i == 1)
                    P = bp[j];
                else if (i == 6)
                    P = wp[j];
                cell = new Cell(i, j, P); // set the piece in the cell by creating an object

                cell.addMouseListener(this);
                // adding a listener to detect any mouse events that occur on the cell object.
                // This will allow the program to detect when a player clicks on a cell on the board.

                board.add(cell);
                //adding the cell object to the board container,
                // which is a JPanel that holds all the cells in the chess board.

                boardState[i][j] = cell;
                //setting the cell object at the (i, j) position in the boardState array.
                // The boardState array is used to keep track of which piece is located at each position on the board.
                // This will be useful later when updating the board after a player moves a piece.
            }


        showPlayer = new JPanel(new FlowLayout());
        showPlayer.add(timeSlider);
        JLabel setTime = new JLabel("Set Timer(in mins):");
        start = new Button("Start");
        start.setBackground(Color.RED);
        start.setForeground(Color.white);
        //Set the background color of the "start" button to black and the text color to white.

        start.addActionListener(new START());
        //Add an action listener to the "start" button using an instance of the "START" class.

        start.setPreferredSize(new Dimension(120, 40));
        setTime.setFont(new Font("Arial", Font.BOLD, 16));
        label = new JLabel("Time Starts now", JLabel.CENTER);
        label.setFont(new Font("SERIF", Font.BOLD, 30));
        displayTime = new JPanel(new FlowLayout());



        time = new JPanel(new GridLayout(3, 3));
       // time.add(setTime);
        time.add(showPlayer);
        displayTime.add(start);
        time.add(displayTime);
        //Create a new JPanel with a GridLayout and assign it to the variable "time".
        // Add the "setTime", "showPlayer", and "displayTime" components to the "time" panel.

        controlPanel.add(time);
        board.setMinimumSize(new Dimension(800, 700));

        //The Left Layout When Game is inactive
        temp = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                try {
                    image = ImageIO.read(this.getClass().getResource("zoz.png"));
                } catch (IOException ex) {
                    System.out.println("not found");
                }

                g.drawImage(image, 0, 0, null);
            }
        };
        //Create a new JPanel with an overridden paintComponent method that sets the background image to "clash.jpg"
        // and assign it to the variable "temp".


        temp.setMinimumSize(new Dimension(800, 700));
        controlPanel.setMinimumSize(new Dimension(285, 700));
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, temp, controlPanel);
        //Create a new JSplitPane with "temp" as the left component and "controlPanel" as the right component,
        // and assign it to the variable "split".

        content.add(split);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    } // end of the constructor


    // A function to change the chance from White Player to Black Player or vice verse
    // It is made public because it is to be accessed in the Time Class
    public void changechance() {
        if (boardState[getKing(chance).getX()][getKing(chance).getY()].isCheck()) {
            chance ^= 1;
            gameend();
        }
        //This if statement checks if the current player's king is in check.
        // If so, it toggles the player's turn (by XORing chance with 1) and calls the gameend() method to end the game.

        if (destinationlist.isEmpty() == false)
            cleandestinations(destinationlist);
        // This checks if there are any highlighted destinations on the board,
        // and if so, it calls the  method to remove them.

        if (previous != null)
            previous.deselect();
        previous = null;
        //This checks if there is a previously selected piece on the board, and if so,
        // it deselects it by calling its deselect() method.
        // It then sets the previous variable to null to indicate that there is no selected piece.

        chance ^= 1;
        // This toggles the player's turn by XORing chance with 1. This line will always execute,
        // regardless of whether the king is in check.

        if (chance==0) {
            if (Main.move == "Black") {
                Main.move = "White";
                CHNC.setText(Main.move);
            }
            timerBlack.stop();
            timerWhite.start();

        }
        if( chance==1){
            if(Main.move=="White") {
                Main.move = "Black";
                CHNC.setText(Main.move);
            }

            timerWhite.stop();
            timerBlack.start();

        }
        //temporary
        wk.castling();
        bk.castling();
    }

    //A function to retrieve the Black King or White King
    private King getKing(int color) {
        if (color == 0)
            return wk;
        else
            return bk;
    }

    //A function to clean the highlights of possible destination cells
    private void cleandestinations(ArrayList<Cell> destlist)      //Function to clear the last move's destinations
    {
        ListIterator<Cell> it = destlist.listIterator();
        //This line declares the method signature for cleandestinations with a parameter destlist of type ArrayList<Cell>.
        // add to listiterator , then go hasnext means to go forward

        while (it.hasNext())
            it.next().removePossibleDestination();
        //This line starts a while loop that will execute as long as there are more elements in the destlist ArrayList to iterate over.
    }

    //A function that indicates the possible moves by highlighting the Cells
    private void highlightdestinations(ArrayList<Cell> destlist) {
        ListIterator<Cell> it = destlist.listIterator();
        while (it.hasNext())
            it.next().setPossibleDestination();
    }
    // same but for set not remove


    //Function to check if the king will be in danger if the given move is made
    private boolean willkingbeindanger(Cell fromcell, Cell tocell)
    // two arguments: the source cell from which the piece is being moved
    // and the destination cell to which the piece is being moved.
    {
        Cell[][] newboardstate = new Cell[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                try {
                    newboardstate[i][j] = new Cell(boardState[i][j]);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                    System.out.println("There is a problem with cloning !!");
                }
            }
        //This creates a new 8x8 array of Cell objects that represents a copy of the current board state.
        // The try block attempts to clone each cell in the current board state and add it to the new board state.
        // If there is a problem with cloning, the catch block prints an error message.

        if (newboardstate[tocell.x][tocell.y].getpiece() != null)
            newboardstate[tocell.x][tocell.y].removePiece();
        //This checks if there is a piece at the destination cell, and if so, removes it from the board state.

        newboardstate[tocell.x][tocell.y].setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
        //This sets the piece at the destination cell to be the same as the piece at the source cell.

        if (newboardstate[tocell.x][tocell.y].getpiece() instanceof King) {
            ((King) (newboardstate[tocell.x][tocell.y].getpiece())).setX(tocell.x);
            ((King) (newboardstate[tocell.x][tocell.y].getpiece())).setY(tocell.y);
        }
        //This code checks if the piece being moved is a king and updates its position if so.

        newboardstate[fromcell.x][fromcell.y].removePiece();
        //This removes the piece from the source cell.

        return ((King) (newboardstate[getKing(chance).getX()][getKing(chance).getY()].getpiece())).isInDanger(newboardstate) == true;
        //This calls the isindanger() function of the king of the current player,
        // passing in the new board state, to check if the king is in danger.
        // If the king is in danger, the function returns true, otherwise it returns false.
    }

    //A function to eliminate the possible moves that will put the King in danger
    private ArrayList<Cell> filterdestination(ArrayList<Cell> destlist, Cell fromcell) {
        ArrayList<Cell> newlist = new ArrayList<Cell>();
        Cell[][] newboardstate = new Cell[8][8];
        ListIterator<Cell> it = destlist.listIterator();
        //This creates a new ListIterator called it that iterates over the elements in the ArrayList destlist.

        int x, y;

        //This starts a while loop that continues while there are more elements to iterate over in the ArrayList destlist.
        while (it.hasNext()) {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++) {
                    try {
                        newboardstate[i][j] = new Cell(boardState[i][j]);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            //This initializes the 2D array newboardstate by cloning each cell from the existing boardState 2D array.

            Cell tempc = it.next();
            //This gets the element in the ArrayList destlist and stores it.

            if (newboardstate[tempc.x][tempc.y].getpiece() != null)
                newboardstate[tempc.x][tempc.y].removePiece();
            newboardstate[tempc.x][tempc.y].setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
            //This removes any piece that exists in the cell at the position represented by tempc,
            // then sets that cell's piece to the piece that exists in the fromcell position.

            x = getKing(chance).getX();
            y = getKing(chance).getY();
            //This sets the values of x and y to the x and y coordinates of the current player's King.

            if (newboardstate[fromcell.x][fromcell.y].getpiece() instanceof King) {
                ((King) (newboardstate[tempc.x][tempc.y].getpiece())).setX(tempc.x);
                ((King) (newboardstate[tempc.x][tempc.y].getpiece())).setY(tempc.y);
                x = tempc.x;
                y = tempc.y;
            }
            //This checks if the piece in the fromcell position is a King.
            // If so, it sets the King's x and y coordinates to the x and y coordinates of the tempc position,
            // and sets the values of x and y to the x and y coordinates of the tempc position.

            newboardstate[fromcell.x][fromcell.y].removePiece();
            //This removes the piece from the fromcell position.

            if ((((King) (newboardstate[x][y].getpiece())).isInDanger(newboardstate) == false))
                newlist.add(tempc);
            //This checks if the current player's King is in danger in the newboardstate.
            // If not, it adds the tempc position to the newlist ArrayList.
        }
        return newlist;
    }

    //A Function to filter the possible moves when the king of the current player is under Check
    private ArrayList<Cell> incheckfilter(ArrayList<Cell> destlist, Cell fromcell, int color) {
        ArrayList<Cell> newList = new ArrayList<Cell>();
        Cell[][] newBoardState = new Cell[8][8];
        ListIterator<Cell> it = destlist.listIterator();
        int x, y;
        while (it.hasNext()) // Iterate over the destination cells
        {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++) {
                    try {
                        newBoardState[i][j] = new Cell(boardState[i][j]);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            // Create a new copy of the board state for each destination cell

            Cell tempc = it.next(); // Get the next destination cell

            if (newBoardState[tempc.x][tempc.y].getpiece() != null)
                newBoardState[tempc.x][tempc.y].removePiece();
            // If the destination cell is not empty, remove the piece on it

            newBoardState[tempc.x][tempc.y].setPiece(newBoardState[fromcell.x][fromcell.y].getpiece());
            // Move the piece from the source cell to the destination cell

            x = getKing(color).getX();
            y = getKing(color).getY();

            if (newBoardState[tempc.x][tempc.y].getpiece() instanceof King) {
                ((King) (newBoardState[tempc.x][tempc.y].getpiece())).setX(tempc.x);
                ((King) (newBoardState[tempc.x][tempc.y].getpiece())).setY(tempc.y);
                x = tempc.x;
                y = tempc.y;
            } // If the piece on the destination cell is a king, update its position


            newBoardState[fromcell.x][fromcell.y].removePiece();
            // Remove the piece from the source cell

            if ((((King) (newBoardState[x][y].getpiece())).isInDanger(newBoardState) == false)) // Check if the king is still in check
                newList.add(tempc); // If the king is not in check, add the destination cell to the filtered list
        }
        return newList;
    }

    //A function to check if the King is check-mate. The Game Ends if this function returns true.
    public boolean checkmate(int color) {
        ArrayList<Cell> dlist = new ArrayList<Cell>();
        // Iterate over all the cells on the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // If the current cell has a piece of the given color
                if (boardState[i][j].getpiece() != null && boardState[i][j].getpiece().getColor() == color) {
                    // Get the possible moves of the piece
                    dlist.clear();// clear the list from any previous moves
                    dlist = boardState[i][j].getpiece().move(boardState, i, j);// Filter out the moves that would leave the king in check
                    dlist = incheckfilter(dlist, boardState[i][j], color); // If there are still possible moves, the king is not in checkmate
                    if (dlist.size() != 0)
                        return false;
                }
            }
        }
        // If no piece of the given color has any moves that do not result in check, the king is in checkmate
        return true;
    }


    @SuppressWarnings("deprecation")
    public void gameend() {
        // Clear all previous move destinations from the board
        cleandestinations(destinationlist);
        // Disable the timer and stop it from counting down
        displayTime.disable();
        timerWhite.countdownTimer.stop();
        // If there was a previous move, remove the piece from the board
        if (previous != null)
            previous.removePiece();
        // Determine the winner and update their game stats
        if (chance == 0) {
            White.updateGamesWon();
            White.Update_Player();
            winner = White.name();
        } else {
            Black.updateGamesWon();
            Black.Update_Player();
            winner = Black.name();
        }
        // Display a message box indicating the winner
        JOptionPane.showMessageDialog(board, "Checkmate!!!\n" + winner + " wins");
        // Remove player details and timer from the GUI
        WhitePlayer.remove(wdetails);
        BlackPlayer.remove(bdetails);
        displayTime.remove(label);
        // Add the "Start Game" button back to the timer panel
        timerWhite.reset();
        displayTime.add(start);
        // Remove the "Move #" and "Current Turn" labels from the player panel
        showPlayer.remove(mov);
        showPlayer.remove(CHNC);
        showPlayer.revalidate();
        // Add the time slider back to the player panel
        showPlayer.add(timeSlider);
        // Replace the chess board with a temporary panel
        split.remove(board);
        split.add(temp);
        // Enable all player selection buttons
        WNewPlayer.enable();
        BNewPlayer.enable();
        wselect.enable();
        bselect.enable();
        // Set the end flag to true
        end = true;
        // Disable the current game window
        Mainboard.disable();
        // Dispose of the current game window
        Mainboard.dispose();
        // Create a new game window
        Mainboard = new Game();
        // Display the new game window
        Mainboard.setVisible(true);
        // Disable resizing of the new game window
        Mainboard.setResizable(false);
    }

    public void gameend2() {
        // Clear all previous move destinations from the board
        cleandestinations(destinationlist);

        // Disable the timer and stop it from counting down
        timerWhite.reset();
        timerBlack.reset();

        // Remove the "Time Starts now" label from displayTime panel
        displayTime.removeAll();

        // Add the timer labels to the displayTime panel
        displayTime.add(timerWhite.getTime());
        displayTime.add(timerBlack.getTime());

        // If there was a previous move, remove the piece from the board
        if (previous != null)
            previous.removePiece();

        // Determine the winner and update their game stats
        if (chance == 0) {
            White.updateGamesWon();
            White.Update_Player();
            winner = White.name();
        } else {
            Black.updateGamesWon();
            Black.Update_Player();
            winner = Black.name();
        }

        // Remove player details and timer from the GUI
        WhitePlayer.remove(wdetails);
        BlackPlayer.remove(bdetails);

        // Add the "Start Game" button back to the timer panel
        displayTime.add(start);

        // Remove the "Move #" and "Current Turn" labels from the player panel
        showPlayer.remove(mov);
        showPlayer.remove(CHNC);
        showPlayer.revalidate();

        // Add the time slider back to the player panel
        showPlayer.add(timeSlider);

        // Replace the chess board with a temporary panel
        split.remove(board);
        split.add(temp);

        // Enable all player selection buttons
        WNewPlayer.enable();
        BNewPlayer.enable();
        wselect.enable();
        bselect.enable();

        // Set the end flag to true
        end = true;

        // Disable the current game window
        Mainboard.disable();

        // Dispose of the current game window
        Mainboard.dispose();

        // Create a new game window
        Mainboard = new Game();

        // Start the timers for the new game
        timerWhite.start();
        timerBlack.start();

        // Display the new game window
        Mainboard.setVisible(true);

        // Disable resizing of the new game window
        Mainboard.setResizable(true);
    }

    public  void timerEnd()
    {

        if(chance==0)
        {
            JOptionPane.showMessageDialog(null, "Time is over \n Black wins");


        }
        else
        {

            JOptionPane.showMessageDialog(null, "Time is over \n White wins");
        }
        gameend2();

    }












    //These are the abstract function of the parent class. Only relevant method here is the On-Click Fuction
    //which is called when the user clicks on a particular cell
    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
        // Get the clicked cell
        c = (Cell) arg0.getSource();
        // If no previous cell is selected
        if (previous == null) {
            // If the clicked cell has a piece of the same color as the current player's turn, select it and find its possible destinations
            if (c.getpiece() != null) {
                if (c.getpiece().getColor() != chance)
                    return;
                c.select();
                previous = c;
                destinationlist.clear();
                destinationlist = c.getpiece().move(boardState, c.x, c.y);
                // If the piece is a king, filter the destination list to avoid moving the king into check
                if (c.getpiece() instanceof King)
                    destinationlist = filterdestination(destinationlist, c);
                else
                // If the current player's king is in check, restrict the destination list to only moves that block or capture the attacking piece
                {
                    if (boardState[getKing(chance).getX()][getKing(chance).getY()].isCheck())
                        destinationlist = new ArrayList<Cell>(filterdestination(destinationlist, c));
                        // If there are any moves that would result in the king being in check, remove them from the destination list
                    else if (destinationlist.isEmpty() == false && willkingbeindanger(c, destinationlist.get(0)))
                        destinationlist.clear();
                }
                // Highlight the possible destinations
                highlightdestinations(destinationlist);
            }
        } else {
            // If the clicked cell is the same as the previous cell, deselect it and clear the destination list
            if (c.x == previous.x && c.y == previous.y) {
                c.deselect();
                cleandestinations(destinationlist);
                destinationlist.clear();
                previous = null;
            }
            // If the clicked cell is a possible destination
            else if (c.getpiece() == null || previous.getpiece().getColor() != c.getpiece().getColor()) {
                if (c.isPossibleDestination())
                // Move the piece from the previous cell to the clicked cell
                {
                    if(c.getpiece()!=null) {
                        if(c.getpiece().getColor()==0) {
                            eatenWhite.add(0,c.getpiece().getPath());
                            pieces.showPieceWhite();

                        }
                        else {
                            eatenBlack.add(0, c.getpiece().getPath());
                            pieces.showPieceBlack();
                        }
                        c.removePiece();




                    }
                    c.setPiece(previous.getpiece());
                    if (previous.isCheck())
                        previous.reMoveCheck();
                    previous.removePiece();
                    // If the opponent's king is now in check, check if it's checkmate
                    if (getKing(chance ^ 1).isInDanger(boardState)) {
                        boardState[getKing(chance ^ 1).getX()][getKing(chance ^ 1).getY()].setCheck();
                        // End the game if it's checkmate
                        if (checkmate(getKing(chance ^ 1).getColor())) {
                            previous.deselect();
                            if (previous.getpiece() != null)
                                previous.removePiece();
                            gameend();
                        }
                    }
                    // If the current player's king is not in danger, remove the check status from the opponent's cells
                    if (getKing(chance).isInDanger(boardState) == false)
                        boardState[getKing(chance).getX()][getKing(chance).getY()].reMoveCheck();
                    // If the piece moved is a king, update its position
                    if (c.getpiece() instanceof King) {
                        ((King) c.getpiece()).setX(c.x);
                        ((King) c.getpiece()).setY(c.y);
                    }

                    if(c.getpiece() instanceof Pawn && (c.x==0 || c.x==7)) {
                        window = new PawnPromotion(boardState, c.x, c.y, c.getpiece().getColor());
                    }
                    // Change the turn to the other player and start the timer
                    changechance();
//                    if (!end) {
//                        timer.reset();
//                        timer.start();
//                    }
                }
                // Deselect the previous selected cell and clear the destination list
                if (previous != null) {
                    previous.deselect();
                    previous = null;
                }
                cleandestinations(destinationlist);
                destinationlist.clear();
            }
            // Check if the user has clicked on a cell with a piece of the same color as the current player
            else if (previous.getpiece().getColor() == c.getpiece().getColor()) {
                // Deselect the previously selected cell and clear the list of valid destinations
                previous.deselect();
                cleandestinations(destinationlist);
                destinationlist.clear();

                // Select the new cell and update the list of valid destinations
                c.select();
                previous = c;
                destinationlist = c.getpiece().move(boardState, c.x, c.y);

                // Filter the valid destinations based on whether the selected piece is a king or not
                if (c.getpiece() instanceof King)
                    destinationlist = filterdestination(destinationlist, c);
                else {
                    // If the current player's king is in check, restrict the valid destinations to those that prevent or escape check
                    if (boardState[getKing(chance).getX()][getKing(chance).getY()].isCheck())
                        destinationlist = new ArrayList<Cell>(filterdestination(destinationlist, c));

                        // If the selected move would put the current player's king in check, remove that destination from the list
                    else if (destinationlist.isEmpty() == false && willkingbeindanger(c, destinationlist.get(0)))
                        destinationlist.clear();
                }
                // Highlight the valid destinations
                highlightdestinations(destinationlist);
            }
        }
        if (c.getpiece() != null && c.getpiece() instanceof King) {
            ((King) c.getpiece()).setX(c.x);
            ((King) c.getpiece()).setY(c.y);
        }
    }

    //Other Irrelevant abstract function. Only the Click Event is captured.
    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }


    class START implements ActionListener {

        @SuppressWarnings("deprecation")
        @Override
        public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub
            // Check if player details are filled
            if (White == null || Black == null) {
                JOptionPane.showMessageDialog(controlPanel, "Fill in the details");
                return;
            }
            // Update player details
            White.updateGamesPlayed();
            White.Update_Player();
            Black.updateGamesPlayed();
            Black.Update_Player();
            // Disable player selection

            WNewPlayer.disable();
            BNewPlayer.disable();
            wselect.disable();
            bselect.disable();
            // Display the board
            split.remove(temp);
            split.add(board);
            // Display move information
            showPlayer.remove(timeSlider);
            mov = new JLabel("Move:");
            mov.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            mov.setForeground(Color.red);
            showPlayer.add(mov);
            CHNC = new JLabel(move);
            CHNC.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            CHNC.setForeground(Color.blue);
            showPlayer.add(CHNC);
            // Start the timer
            displayTime.remove(start);
            displayTime.add(label);
            timerWhite= Chess.Time.getInstance(label);
            timerBlack= Chess.Time.getInstance2(label);
            timerWhite.start();





        }
    }

    class TimeChange implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent arg0) {
            timeRemaining = timeSlider.getValue() * 60;
        }
    }


    class SelectHandler implements ActionListener {
        // color represents the color of the player
        private int color;

        // constructor for SelectHandler class
        SelectHandler(int i) {
            color = i;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub
            tempPlayer = null;

            // get the name of the current player and the opponent's name
            String n = (color == 0) ? wname : bname;
            JComboBox<String> jc = (color == 0) ? wcombo : bcombo;
            JComboBox<String> ojc = (color == 0) ? bcombo : wcombo;
            //JComboBox<String> is a class in the Java Swing library that represents a drop-down list component,
            // which allows the user to select a value from a list of options.
            //The JComboBox class is parameterized with the type of objects that it contains,
            // which is specified in the angle brackets <String> in this case.
            // This means that the JComboBox is expected to contain String objects.


            // get the list of players for the current player and the opponent
            ArrayList<Player> pl = (color == 0) ? wplayer : bplayer;
            ArrayList<Player> opl = Player.fetch_players();
            if (opl.isEmpty())
                return;
            JPanel det = (color == 0) ? wdetails : bdetails;
            JPanel PL = (color == 0) ? WhitePlayer : BlackPlayer;

            // if a player has already been selected, remove the details panel
            if (selected == true)
                det.removeAll();

            // get the selected player
            n = (String) jc.getSelectedItem();
            Iterator<Player> it = pl.iterator();
            Iterator<Player> oit = opl.iterator();
            //This line starts a while loop that iterates over each element in the collection pl using the iterator it.
            // The hasNext() method checks if there are any more elements in the collection.
            while (it.hasNext()) {
                Player p = it.next();
                if (p.name().equals(n)) {
                    tempPlayer = p;
                    break;
                }
            }
            // remove the selected player from the opponent's player list
            while (oit.hasNext()) {
                Player p = oit.next();
                if (p.name().equals(n)) {
                    opl.remove(p);
                    break;
                }
            }
            // if no player is selected, return
            if (tempPlayer == null)
                return;
            // set the current player to the selected player
            if (color == 0)
                White = tempPlayer;
            else
                Black = tempPlayer;
            // update the opponent's player list
            bplayer = opl;
            // remove all the items from opponent's player list and add updated items
            ojc.removeAllItems();

            for (Player s : opl)
                ojc.addItem(s.name());
            // add details of selected player to the details panel
            det.add(new JLabel(" " + tempPlayer.name()));
            det.add(new JLabel(" " + tempPlayer.gamesplayed()));
            det.add(new JLabel(" " + tempPlayer.gameswon()));

            // update and refresh the player panel
            PL.revalidate();
            PL.repaint();
            PL.add(det);
            selected = true;
        }

    }


    class Handler implements ActionListener {
        private int color;

        Handler(int i) {
            color = i;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the name of the player and the corresponding panel to update
            String n = (color == 0) ? wname : bname;
            JPanel j = (color == 0) ? WhitePlayer : BlackPlayer;

            // Get all the existing players and the panel to display player details
            ArrayList<Player> N = Player.fetch_players();
            Iterator<Player> it = N.iterator();
            JPanel det = (color == 0) ? wdetails : bdetails;

            // Show a dialog box to get the player name from the user
            n = JOptionPane.showInputDialog(j, "Enter your name");

            if (n != null) {
                // Check if a player with the same name already exists
                while (it.hasNext()) {
                    if (it.next().name().equals(n)) {
                        JOptionPane.showMessageDialog(j, "Player exists");
                        return;
                    }
                }

                if (n.length() != 0) {
                    // Create a new player and update the corresponding variable
                    Player tem = Player.getInstance(n);
                    tem.Update_Player();
                    if (color == 0)
                        White = tem;
                    else
                        Black = tem;
                } else
                    return;
            } else
                return;

            // Clear the panel and display the details of the selected player
            det.removeAll();
            det.add(new JLabel(" " + n));
            det.add(new JLabel(" 0"));
            det.add(new JLabel(" 0"));
            j.revalidate();
            j.repaint();
            j.add(det);
            selected = true;
        }
    }
}
