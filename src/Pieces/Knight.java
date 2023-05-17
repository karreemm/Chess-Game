package Pieces;

import java.util.ArrayList;

import Chess.Cell;

/**
 * This is the Knight Class inherited from the Piece abstract class
 *
 */
public class Knight extends Piece{

    //Constructor
    public Knight(String i,String p,int c)
    {
        setId(i);
        setPath(p);
        setColor(c);

        super.BlackPath ="Black_Knight.png";
        super.WhitePath ="White_Knight.png";
    }

    //Move Function overridden
    //There are at max 8 possible moves for a knight at any point of time.
    //Knight moves only 2(1/2) steps
    public ArrayList<Cell> move(Cell[][] state, int x, int y)
    {
        possibleMoves.clear();
        int[] posX ={x+2,x+2,x+3,x+3,x-2,x-2,x-3,x-3};
        int[] posy ={y-3,y+3,y-2,y+2,y-3,y+3,y-2,y+2};
        for(int i=0;i<8;i++)
            if((posX[i]>=0&&posX[i]<8&&posy[i]>=0&&posy[i]<8))
                if((state[posX[i]][posy[i]].getpiece()==null||state[posX[i]][posy[i]].getpiece().getColor()!=this.getColor()))
                {
                    possibleMoves.add(state[posX[i]][posy[i]]);
                }
        return possibleMoves;
    }
}