package Pieces;

import java.util.ArrayList;

import Chess.Cell;

/**
 * This is the Queen Class inherited from the abstract Piece class
 *
 */
public class Queen extends Piece{

    //Constructors
    public Queen(String i,String p,int c)
    {
        setId(i);
        setPath(p);
        setColor(c);

        super.BlackPath ="Black_Queen.png";
        super.WhitePath ="White_Queen.png";
    }

    //Move Function Defined
    public ArrayList<Cell> move(Cell[][] state, int x, int y)
    {
        //Queen can move any number of steps in all 8 direction
        //The possible moves of queen is a combination of Rook and Bishop
        possibleMoves.clear();

        //Checking possible moves in vertical direction
        int tempX=x-1;
        while(tempX>=0)
        {
            if(state[tempX][y].getpiece()==null)
                possibleMoves.add(state[tempX][y]);
            else if(state[tempX][y].getpiece().getColor()==this.getColor())
                break;
            else
            {
                possibleMoves.add(state[tempX][y]);
                break;
            }
            tempX--;
        }

        tempX=x+1;
        while(tempX<8)
        {
            if(state[tempX][y].getpiece()==null)
                possibleMoves.add(state[tempX][y]);
            else if(state[tempX][y].getpiece().getColor()==this.getColor())
                break;
            else
            {
                possibleMoves.add(state[tempX][y]);
                break;
            }
            tempX++;
        }


        //Checking possible moves in horizontal Direction
        int tempY=y-1;
        while(tempY>=0)
        {
            if(state[x][tempY].getpiece()==null)
                possibleMoves.add(state[x][tempY]);
            else if(state[x][tempY].getpiece().getColor()==this.getColor())
                break;
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
            else if(state[x][tempY].getpiece().getColor()==this.getColor())
                break;
            else
            {
                possibleMoves.add(state[x][tempY]);
                break;
            }
            tempY++;
        }

        //Checking for possible moves in diagonal direction
        tempX=x+1;tempY=y-1;
        while(tempX<8&&tempY>=0)
        {
            if(state[tempX][tempY].getpiece()==null)
                possibleMoves.add(state[tempX][tempY]);
            else if(state[tempX][tempY].getpiece().getColor()==this.getColor())
                break;
            else
            {
                possibleMoves.add(state[tempX][tempY]);
                break;
            }
            tempX++;
            tempY--;
        }
        tempX=x-1;tempY=y+1;
        while(tempX>=0&&tempY<8)
        {
            if(state[tempX][tempY].getpiece()==null)
                possibleMoves.add(state[tempX][tempY]);
            else if(state[tempX][tempY].getpiece().getColor()==this.getColor())
                break;
            else
            {
                possibleMoves.add(state[tempX][tempY]);
                break;
            }
            tempX--;
            tempY++;
        }
        tempX=x-1;tempY=y-1;
        while(tempX>=0&&tempY>=0)
        {
            if(state[tempX][tempY].getpiece()==null)
                possibleMoves.add(state[tempX][tempY]);
            else if(state[tempX][tempY].getpiece().getColor()==this.getColor())
                break;
            else
            {
                possibleMoves.add(state[tempX][tempY]);
                break;
            }
            tempX--;
            tempY--;
        }
        tempX=x+1;tempY=y+1;
        while(tempX<8&&tempY<8)
        {
            if(state[tempX][tempY].getpiece()==null)
                possibleMoves.add(state[tempX][tempY]);
            else if(state[tempX][tempY].getpiece().getColor()==this.getColor())
                break;
            else
            {
                possibleMoves.add(state[tempX][tempY]);
                break;
            }
            tempX++;
            tempY++;
        }
        return possibleMoves;
    }
}