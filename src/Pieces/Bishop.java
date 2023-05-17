package Pieces;

import java.util.ArrayList;

import Chess.Cell;


/**
 * This is the Bishop Class.
 * The Move Function defines the basic rules for movement of Bishop on a chess board
 *
 */
public class Bishop extends Piece{

    //Constructor
    public Bishop(String i,String p,int c)
    {
        setId(i);
        setPath(p);
        setColor(c);

        super.BlackPath ="Black_Bishop.png";
        super.WhitePath ="White_Bishop.png";
    }

    //move function defined. It returns a list of all the possible destinations of a Bishop
    //The basic principle of Bishop Movement on chess board has been implemented
    public ArrayList<Cell> move(Cell[][] state, int x, int y)
    {
        //Bishop can Move diagonally in all 4 direction (NW,NE,SW,SE)
        //This function defines that logic
        possibleMoves.clear();
        int i =1;
        int tempX = x + 1, tempY = y - 1;
        while ((tempX < 8 && tempY >= 0)&&  i<=3 ) {
            if (state[tempX][tempY].getpiece() == null) {
                possibleMoves.add(state[tempX][tempY]);
            } else if (state[tempX][tempY].getpiece().getColor() != this.getColor()) {
                possibleMoves.add(state[tempX][tempY]);
            }

            tempX++;
            tempY--;
            i++;
        }
        i=1;
        tempX = x - 1;
        tempY = y + 1;
        while (tempX >= 0 && tempY < 8 && i<=3 ) {
            if (state[tempX][tempY].getpiece() == null)
                possibleMoves.add(state[tempX][tempY]);

            else if (state[tempX][tempY].getpiece().getColor() != this.getColor()) {
                possibleMoves.add(state[tempX][tempY]);
            }

            tempX--;
            tempY++;
            i++;
        }
        i=1;
        tempX = x - 1;
        tempY = y - 1;
        while (tempX >= 0 && tempY >= 0 && i<=3 ) {
            if (state[tempX][tempY].getpiece() == null)
                possibleMoves.add(state[tempX][tempY]);
            else if (state[tempX][tempY].getpiece().getColor() != this.getColor()) {
                possibleMoves.add(state[tempX][tempY]);
            }

            tempX--;
            tempY--;
            i++;
        }
        i=1;
        tempX = x + 1;
        tempY = y + 1;
        while (tempX < 8 && tempY < 8 && i<=3 ) {
            if (state[tempX][tempY].getpiece() == null)
                possibleMoves.add(state[tempX][tempY]);

            else if (state[tempX][tempY].getpiece().getColor() != this.getColor()) {
                possibleMoves.add(state[tempX][tempY]);
            }
            tempX++;
            tempY++;
            i++;
        }
        if (y+1<8)
        {
            if (state[x][y+1].getpiece()==null || state[x][y+1].getpiece().getColor() != this.getColor())
                possibleMoves.add(state[x][y+1]);
        }

        if (y-1>=0)
        {
            if (state[x][y - 1].getpiece() == null || state[x][y - 1].getpiece().getColor() != this.getColor())
                possibleMoves.add(state[x][y - 1]);
        }

        return possibleMoves;
    }
}