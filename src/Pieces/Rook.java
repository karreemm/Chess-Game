package Pieces;

import java.util.ArrayList;

import Chess.Game;
import Chess.Cell;

/**
 * This is the Rook class inherited from abstract Piece class
 *
 */
public class Rook extends Piece{

    private static final boolean[] CheckMove={true,true,true,true};

    //Constructor
    public Rook(String i,String p,int c )
    {

        setId(i);
        setPath(p);
        setColor(c);

        super.BlackPath ="Black_Rook.png";
        super.WhitePath ="White_Rook.png";
    }


    //Move function defined
    public ArrayList<Cell> move(Cell[][] state, int x, int y)
    {
        //Rook can move only horizontally or vertically
        possibleMoves.clear();
        int tempX=x-1;
        while(tempX>=0)
        {
            if(state[tempX][y].getpiece()==null)
                possibleMoves.add(state[tempX][y]);
            else if(state[tempX][y].getpiece().getColor()==this.getColor()) {
                break;
            }
            else
            {
                possibleMoves.add(state[tempX][y]);
                break;
            }
            tempX--;
        }
        tempX=x+1;
        while(tempX<8) {
            if (state[tempX][y].getpiece() == null)
                possibleMoves.add(state[tempX][y]);
            else if (state[tempX][y].getpiece().getColor() == this.getColor())
            {
                break;
            }

            else
            {
                possibleMoves.add(state[tempX][y]);
                break;
            }
            tempX++;
        }
        int tempY=y-1;
        while(tempY>=0)
        {
            if(state[x][tempY].getpiece()==null)
                possibleMoves.add(state[x][tempY]);

            else if(state[x][tempY].getpiece().getColor()==this.getColor()) {
                break;
            }
            else
            {
                possibleMoves.add(state[x][tempY]);
                break;
            }
            tempY--;
        }
        tempY=y+1;
        while(tempY<8)
        {
            if(state[x][tempY].getpiece()==null)
                possibleMoves.add(state[x][tempY]);
            else if(state[x][tempY].getpiece().getColor()==this.getColor()) {
                break;
            }
            else
            {
                possibleMoves.add(state[x][tempY]);
                break;
            }
            tempY++;
        }
        notMoved();

        return possibleMoves;
    }
    public boolean notMoved() {

        Cell [][] state= Game.Mainboard.boardState;
        int [] posX={0,0,7,7};
        int [] posY={0,7,0,7};
        String [] Id={"BR01","BR02","WR01","WR02"};
        for (int i =0  ; i<4 ; i++ ){
            if (state[posX[i]][posY[i]].getpiece() == this && getId().equals(Id[i]) && CheckMove[i]) {
                setMove(true);
            }
            else if (state[posX[i]][posY[i]].getpiece() != this && getId().equals(Id[i]) && CheckMove[i]) {
                setMove(false);
                CheckMove[i]=false;
            }

        }

        return getMove();
    }

}
