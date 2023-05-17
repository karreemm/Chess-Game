package Pieces;

import Chess.Cell;

import java.util.ArrayList;


/**
 * CHECKMARK --
 * This is the Piece Class. It is an [[abstract]] class from which all the actual pieces are [[inherited]].
 * It defines [[all the function common to all the pieces]]
 * The [[move()]] function an abstract function that [[has to be overridden in all the inherited class]]
 * It(Piece class) implements [Cloneable] interface], as a copy of the piece is required very often
 * // very clear that cloneable interface make a copy/clone ..me
 */
public abstract class Piece implements Cloneable{

    //Member Variables
    private int color; //notice that it is an Integer
    private String id=null; //A String variable representing the unique identifier of the piece.
    private String path; //A String variable representing the [[path to the [[image or resource associated with the piece]].
    private boolean notMove ;
    protected ArrayList<Cell> possibleMoves = new ArrayList<Cell>(); /*An ArrayList of Cell objects representing the possible moves for the piece.
																	It is declared as protected, allowing access from child classes.*/
    //Protected (access from child classes)
    public abstract ArrayList<Cell> move(Cell pos[][],int x,int y);  //Abstract Function. [[Must be overridden]]
    protected String BlackPath;
    protected String WhitePath;
    protected String PieceName;

    //VERY IMPORTANT FUNCTION.. me

    /* move(): An abstract method that takes a 2D array of Cell objects (pos), and the
    coordinates (x and y) of the [[current]] position of the piece. This method should be
    [[overridden]] by the child classes [[to define the specific movement behavior]] for each
    type of piece. It returns an ArrayList of Cell objects representing the possible
    moves for the piece.*/
    //Id Setter
    public void setId(String id)
    {
        this.id=id;
    }

    //Path Setter
    public void setPath(String path)
    {
        this.path=path; //of the current object/ the object where the method is called
    }

    //Color Setter
    public void setColor(int c)
    {
        this.color=c; //same like above
    }
    //Move Setter
    public void setMove(boolean move) {
        this.notMove = move;
    }

    //Path getter
    public String getPath()
    {
        return this.path; //NOT THIS.PATH????			<-------
    }
    public String getWhitePath(){
        return WhitePath;
    }
    public String getBlackPath(){
        return BlackPath;
    }

    //Id getter
    public String getId()
    {
        return this.id; // NOT THIS.ID???				<-------
    }

    //Color Getter
    public int getColor()
    {
        return this.color;
    }
    //Move getter
    public boolean getMove(){
        return notMove;
    }


    //Function to return the a "shallow" copy of the object. The copy has exact same variable value but different reference
    public Piece getcopy() throws CloneNotSupportedException /*This method is used to create a copy of the Piece object.
															It uses the clone() method to create a shallow copy of the object.
															~ The clone() method is a protected method defined in the Object class,
															~ and it is overridden in this class due to the implementation of the Cloneable interface.
															The clone() method returns an Object type, so the code explicitly casts it to Piece type to
															match the return type of the method.
															Note that the CloneNotSupportedException is thrown in case the Piece class or any of its superclasses
															do not support cloning.
															A shallow copy creates a new object with the same variable values as the original object, but the two
															objects have different memory references. This means that changes made to one object will not affect
															the other. However, if the object contains reference types, the references will be shared between the
															original object and its copy, which means changes made to the referenced objects will be reflected in
															 both the original and the copied object.*/
    {
        return (Piece) this.clone(); //(Piece) --> Casting
    }
}