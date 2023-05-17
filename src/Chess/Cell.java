package Chess;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import javax.swing.*;

import Pieces.Piece;
import Pieces.*;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * This is the Cell Class. It is the token class of our GUI.
 * There are total of 64 cells that together makes up the Chess Board
 *
 */
public class Cell extends JPanel implements Cloneable {

    private static HashMap<String, ImageIcon> imageCache = new HashMap<>();
    //Member Variables

    private static final long serialVersionUID = 1L;
    private boolean isPossibleDestination;
    private JLabel content;
    private Piece piece;
    int x, y;                             //is public because this is to be accessed by all the other class
    private boolean isSelected = false;
    private boolean isCheck = false;

    //Constructors
    public Cell(int x, int y, Piece p) {
        this.x = x;
        this.y = y;

        setLayout(new BorderLayout());

        if ((x + y) % 2 == 0)
            setBackground(new Color(255, 204, 153));

        else
            setBackground(new Color(153, 77, 0));

        if (p != null)
            setPiece(p);
    }
    public Cell(final int x, final int y)
    {
        super();
        this.x = x;
        this.y = y;

        if((x+y)%2==0)
        {
            setBackground(new Color(255, 204, 153));
        }
        else
        {
            setBackground(new Color(153, 77, 0));
        }

        setVisible(true);

    }

    //A constructor that takes a cell as argument and returns a new cell will the same data but different reference
    public Cell(Cell cell) throws CloneNotSupportedException {
        this.x = cell.x;
        this.y = cell.y;
        setLayout(new BorderLayout());
        if ((x + y) % 2 == 0)
            setBackground(new Color(255, 204, 153));
        else
            setBackground(new Color(153, 77, 0));
        if (cell.getpiece() != null) {
            setPiece(cell.getpiece().getcopy());
        } else
            piece = null;
    }

    public void setPiece(Piece p)    //Function to inflate a cell with a piece
    {
        piece = p;
        ImageIcon img = imageCache.computeIfAbsent(p.getPath(), k -> new ImageIcon(this.getClass().getResource(p.getPath())));
        // check if the photo is already in the cache or not
        // if it is already in the cache then call it to add in the label
        // if not then add new image from its path to add in the label
        content = new JLabel(img); // add photo to the label
        this.add(content); // add the label to the cell
    }
    public void setPiecePromotion(Piece p, int color)    //Function to inflate a cell with a piece
    {
        piece = p;
        ImageIcon img = null;
        if(color == 0)
            img = imageCache.computeIfAbsent(p.getWhitePath(), k -> new ImageIcon(this.getClass().getResource(p.getWhitePath())));
        else if(color == 1)
            img = imageCache.computeIfAbsent(p.getBlackPath(), k -> new ImageIcon(this.getClass().getResource(p.getBlackPath())));
        // check if the photo is already in the cache or not
        // if it is already in the cache then call it to add in the label
        // if not then add new image from its path to add in the label
        content = new JLabel(img); // add photo to the label
        this.add(content); // add the label to the cell
    }

    public void removePiece()      //Function to remove a piece from the cell
    {

        piece = null;
        this.remove(content);

    }


    public Piece getpiece()    //Function to access piece of a particular cell
    {
        return this.piece;
    }

    public void select()       //Function to mark a cell indicating its selection
    {
        this.setBorder(BorderFactory.createLineBorder(Color.green, 6));
        this.isSelected = true;
    }

    public boolean isSelected()   //Function to return if the cell is under selection
    {
        return this.isSelected;
    }

    public void deselect()      //Function to deselect the cell
    {
        this.setBorder(null);
        this.isSelected = false;
    }

    public void setPossibleDestination()     //Function to highlight a cell to indicate that it is a possible valid move
    {
        this.setBorder(BorderFactory.createLineBorder(Color.green, 4));
        this.isPossibleDestination = true;
    }

    public void removePossibleDestination()      //Remove the cell from the list of possible moves
    {
        this.setBorder(null);
        this.isPossibleDestination = false;
    }


    public boolean isPossibleDestination()    //Function to check if the cell is a possible destination
    {
        return this.isPossibleDestination;
    }

    public void setCheck()     //Function to highlight the current cell as checked (For King)
    {
        this.setBackground(Color.RED);
        this.isCheck = true;
    }

    public void reMoveCheck()   //Function to deselect check
    {
        // REMOVE BORDER AND RETURN THE CELL TO ITS ORIGINAL COLOR
        this.setBorder(null);
        if ((x + y) % 2 == 0)
            setBackground(new Color(255, 204, 153));
        else
            setBackground(new Color(153, 77, 0));
        this.isCheck = false;
    }

    public boolean isCheck()    //Function to check if the current cell is in check
    {
        return isCheck;
    }
}