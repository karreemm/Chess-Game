package Chess;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;


/**
 * This is the Player Class
 * It provides the functionality of keeping track of all the users
 * Objects of this class is updated and written in the Game's Data Files after every Game
 *
 */
public class Player implements Serializable{

    private static final long serialVersionUID = 1L; // this is a unique identifier used to serialize the object.
    private String name;
    private Integer gamesplayed;
    private Integer gameswon;
    private static Player player;
    //Constructor
    private Player(String name)
    {
        this.name = name.trim(); // to get name without spaces
        gamesplayed = new Integer(0);
        gameswon = new Integer(0);
    }

    public static Player getInstance(String name) {
        if (player == null)
            player = new Player(name);
        return player;
    }

    //Name Getter
    public String name()
    {
        return name;
    }

    //Returns the number of games played
    public Integer gamesplayed()
    {
        return gamesplayed;
    }

    //Returns the number of games won
    public Integer gameswon()
    {
        return gameswon;
    }

    //Calculates the win percentage of the player
    public Integer winpercent()
    {
        return new Integer((gameswon*100)/gamesplayed);
    }

    //Increments the number of games played
    public void updateGamesPlayed()
    {
        gamesplayed++;
    }

    //Increments the number of games won
    public void updateGamesWon()
    {
        gameswon++;
    }


    public static ArrayList<Player> fetch_players()         //Function to fetch the list of the players
    {
        Player tempplayer;
        ObjectInputStream input = null;
        ArrayList<Player> players = new ArrayList<Player>();
        //These lines define three variables: tempplayer of type Player, input of type ObjectInputStream,
        // and players of type ArrayList<Player>. tempplayer is used to temporarily store each Player object read from the file.
        // input is used to read from the file, and players will eventually store all of the Player objects.

        try
        {
            File infile = new File(System.getProperty("user.dir")+ File.separator + "chessgamedata.dat");
            input = new ObjectInputStream(new FileInputStream(infile));
            //These lines create a File object named infile that points to the "chessgamedata.dat" file in the current directory,
            // and an ObjectInputStream named input that will be used to read from infile.

            try
            {
                while(true)
                {
                    tempplayer = (Player) input.readObject();
                    players.add(tempplayer);
                }
            }
            catch(EOFException e)
            {
                input.close();
            }
            //These lines use a while loop to read each Player object from infile using input,
            // add it to players, and continue looping until the end of the file is reached
            // (as indicated by an EOFException). Once the end of the file is reached, input is closed.
        }
        catch (FileNotFoundException e)
        {
            players.clear();
            return players;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            try {input.close();} catch (IOException e1) {}
            JOptionPane.showMessageDialog(null, "Unable to read the required Game files !!");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Game Data File Corrupted !! Click Ok to Continue Builing New File");
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return players;
        //These lines catch any exceptions that might be thrown while trying to read from the file or deserialize the Player objects.
        // If a FileNotFoundException is caught, players is cleared and returned.
        // If an IOException is caught, an error message is displayed and players is returned.
        // If a ClassNotFoundException is caught, an error message is displayed and players is returned.
        // If any other exception is caught, an error message is displayed and the exception is printed to the console.
        // Finally, players is returned regardless of whether any exceptions were caught.
    }

    public void Update_Player()            //Function to update the statistics of a player
    {
        ObjectInputStream input = null;
        ObjectOutputStream output = null;
        Player temp_player;
        //This declares a temporary player object for reading from the input file.
        File inputfile=null;
        File outputfile=null;
        try
        {
            inputfile = new File(System.getProperty("user.dir")+ File.separator + "chessgamedata.dat");
            outputfile = new File(System.getProperty("user.dir")+ File.separator + "tempfile.dat");
        } catch (SecurityException e)
        {
            JOptionPane.showMessageDialog(null, "Read-Write Permission Denied !! Program Cannot Start");
            System.exit(0);
        }
        //These lines define the input and output files. The try block attempts to create a new file object for each file.
        // If the security manager denies read-write permission for the files, a message is displayed and the program exits.

        boolean playerdonotexist;
        //This initializes a boolean variable for tracking whether the player to be updated already exists in the file.
        try
        {
            if(outputfile.exists()==false)
                outputfile.createNewFile();
            if(inputfile.exists()==false)
            {
                output = new ObjectOutputStream(new java.io.FileOutputStream(outputfile,true));
                output.writeObject(this);
            }
            //These lines check if the output file exists. If it does not, a new file is created. If the input file does not exist,
            // a new output stream is created and the player object is written to the file.

            else
            {
                input = new ObjectInputStream(new FileInputStream(inputfile));
                output = new ObjectOutputStream(new FileOutputStream(outputfile));
                playerdonotexist=true;
                try
                {
                    while(true)
                    {
                        temp_player = (Player)input.readObject();
                        if (temp_player.name().equals(name()))
                        {
                            output.writeObject(this);
                            playerdonotexist = false;
                        }
                        else
                            output.writeObject(temp_player);
                    }
                }
                catch(EOFException e){
                    input.close();
                }
                if(playerdonotexist)
                    output.writeObject(this);
            }
            //These lines are executed if the input file exists.
            // An input stream is created to read from the input file and an output stream is created to write to the output file.
            // A loop is started that reads each player object in the input file.
            // If the player name matches the name of the player to be updated,
            // the updated player object is written to the output file. Otherwise,
            // the original player object is written to the output file.
            // If the player to be updated does not exist in the input file, it is written to the output file.

            inputfile.delete();
            output.close();
            File newf = new File(System.getProperty("user.dir")+ File.separator + "chessgamedata.dat");
            if(outputfile.renameTo(newf)==false)
                System.out.println("File Renameing Unsuccessful");
            //These lines delete the original input file and close the output file.
            // Then, the output file is renamed to the original input file name.

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Unable to read/write the required Game files !! Press ok to continue");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Game Data File Corrupted !! Click Ok to Continue Builing New File");
        }
        catch (Exception e)
        {

        }
    }
}

