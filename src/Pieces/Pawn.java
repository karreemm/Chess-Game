package Pieces;

import javax.swing.*; //
import java.awt.*; //
import java.util.ArrayList; //


import Chess.Cell; //


//above newly added


/** x are rows and y are columns, 0 and 8 both used as min and max !??
 * This is the Pawn Class inherited from the piece
 *
 */
public class Pawn extends Piece {

    JFrame myFrame = new JFrame();

    public Pawn() {

    }

    //Constructors
    public Pawn(String i, String p, int c) {
        setId(i);
        setPath(p);
        setColor(c);
    }

    //Move Function Overridden
    public ArrayList<Cell> move(Cell state[][], int x, int y) {


        //Pawn can move only one step except the first chance when it may move 2 steps
        //It can move in a diagonal fashion only for attacking a piece of opposite color
        //It cannot move backward or move forward to attact a piece

        possibleMoves.clear();

        if (getColor() == 0) //black pawn
        {
            if (x == 0) {
                return possibleMoves; //which is null(zero, no moves possible) at the moment
            }

            if (state[x - 1][y].getpiece() == null) {
                possibleMoves.add(state[x - 1][y]);
                if (x == 6) //at starting position, pawn not moved yet
                {
                    if (state[4][y].getpiece() == null)
                        possibleMoves.add(state[4][y]);
                }


            }

            if ((y > 0) && (state[x - 1][y - 1].getpiece()) != null) {
                if (state[x - 1][y - 1].getpiece().getColor() != this.getColor())
                    possibleMoves.add(state[x - 1][y - 1]);
            }
            if ((y < 7) && (state[x - 1][y + 1].getpiece()) != null) {
                if (state[x - 1][y + 1].getpiece().getColor() != this.getColor())
                    possibleMoves.add(state[x - 1][y + 1]);
            }
            if ((x > 0) && (state[x - 1][y].getpiece()) != null) {
                if (state[x - 1][y].getpiece().getColor() != this.getColor())
                    possibleMoves.add(state[x - 1][y]);
            }
        } else //white pawn
        {
            if (x == 7) //reached the top
            {
                return possibleMoves;
                //CALL PAWN PROMOTION
            }

            if (state[x + 1][y].getpiece() == null) {
                possibleMoves.add(state[x + 1][y]);
                if (x == 1) //at starting position, pawn not moved yet
                {
                    if (state[3][y].getpiece() == null)
                        possibleMoves.add(state[3][y]);
                }
            }

            if ((y > 0) && (state[x + 1][y - 1].getpiece()) != null) {
                if (state[x + 1][y - 1].getpiece().getColor() != this.getColor())
                    possibleMoves.add(state[x + 1][y - 1]);
            }

            if ((y < 7) && (state[x + 1][y + 1].getpiece()) != null) {
                if (state[x + 1][y + 1].getpiece().getColor() != this.getColor())
                    possibleMoves.add(state[x + 1][y + 1]);
            }

            if ((x < 7) && (state[x + 1][y].getpiece()) != null) {
                if (state[x + 1][y].getpiece().getColor() != this.getColor())
                    possibleMoves.add(state[x + 1][y]);
            }

        }
        return possibleMoves;
    }
}