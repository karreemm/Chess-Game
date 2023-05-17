package Pieces;

import java.util.ArrayList;

import Chess.Cell;
import Chess.Game;

public class King extends Piece {

    private int x, y; //Extra variables for King class to keep a track of king's position
    private boolean whiteKingCastle, blackKingCastle;
    int castleRight;
    int castleLeft;

    //King Constructor
    public King(String i, String p, int c, int x, int y) {
        setX(x);
        setY(y);
        setId(i);
        setPath(p);
        setColor(c);


    }

    //general value access functions
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public boolean notMoved() {


        if (getX() == 0 && getY() == 4 && getColor() == 1 && !blackKingCastle) {
            setMove(true);
        } else if (getX() == 7 && getY() == 4 && getColor() == 0 && !whiteKingCastle) {
            setMove(true);
        } else if ((getX() != 0 || this.getY() != 4) && getColor() == 1 && !blackKingCastle) {
            setMove(false);
            blackKingCastle = true;
            castleRight = getY() - 4;
            castleLeft = 4 - getY();
        } else if ((getX() != 7 || this.getY() != 4) && getColor() == 0 && !whiteKingCastle) {
            setMove(false);
            whiteKingCastle = true;
            castleRight = getY() - 4;
            castleLeft = 4 - getY();
        }

        return getMove();
    }


    public ArrayList<Cell> move(Cell[][] state, int x, int y) {
        //King can move only one step. So all the adjacent 8 cells have been considered.
        possibleMoves.clear();
        boolean castlingRight = false;
        boolean castlingLeft = false;
        int[] posX = {x, x, x + 1, x + 1, x + 1, x - 1, x - 1, x - 1};
        int[] posY = {y - 1, y + 1, y - 1, y, y + 1, y - 1, y, y + 1};
        int[] castlingY = {y - 1, y - 2, y - 3, y - 4, y + 1, y + 2, y + 3};
        for (int i = 0; i < 8; i++) {
            if ((posX[i] >= 0 && posX[i] < 8 && posY[i] >= 0 && posY[i] < 8))
                if ((state[posX[i]][posY[i]].getpiece() == null || state[posX[i]][posY[i]].getpiece().getColor() != this.getColor()))
                    possibleMoves.add(state[posX[i]][posY[i]]);
        }

        //statement to check the castling possible and added it to possibleMove
        if (notMoved() && castlingY[3] >= 0 && castlingY[6] < 8) //to check the move of king
        {
            //for black king
            if (getColor() == 1) {
                for (int i = 0; i < 4; i++) {
                    if (castlingY[i] != 0 && Game.br01.notMoved()) {
                        if (state[x][castlingY[i]].getpiece() == null)
                            castlingLeft = true;
                        else {
                            castlingLeft = false;
                            break;
                        }
                    }

                }

                if (castlingLeft) {
                    possibleMoves.add(state[x][castlingY[1]]);
                }

                for (int i = 4; i < 7; i++) {
                    if (castlingY[i] != 7 && Game.br02.notMoved()) {
                        if (state[x][castlingY[i]].getpiece() == null)
                            castlingRight = true;
                        else {
                            castlingRight = false;
                            break;
                        }
                    }
                }

                if (castlingRight) {
                    possibleMoves.add(state[x][castlingY[5]]);
                }
            }

            //for white king
            if (getColor() == 0) {
                for (int i = 0; i < 4; i++) {
                    if (castlingY[i] != 0 && Game.wr01.notMoved()) {
                        if (state[x][castlingY[i]].getpiece() == null)
                            castlingLeft = true;
                        else {
                            castlingLeft = false;
                            break;
                        }
                    }


                }

                if (castlingLeft) {
                    possibleMoves.add(state[x][castlingY[1]]);
                }

                for (int i = 4; i < 7; i++) {
                    if (castlingY[i] != 7 && Game.wr02.notMoved()) {
                        if (state[x][castlingY[i]].getpiece() == null)
                            castlingRight = true;
                        else {
                            castlingRight = false;
                            break;
                        }
                    }


                }

                if (castlingRight) {
                    possibleMoves.add(state[x][castlingY[5]]);
                }
            }
        }


        return possibleMoves;
    }


    //A function of castling to set the rook in the board
    public void castling() {
        Cell[][] state = Game.Mainboard.boardState;
        if (!notMoved()) {
            if (castleRight == 2) {
                if (getColor() == 0) {
                    state[7][5].setPiece(state[7][7].getpiece());
                    state[7][7].removePiece();
                    castleRight = 0;
                }
                if (getColor() == 1) {
                    state[0][5].setPiece(state[0][7].getpiece());
                    state[0][7].removePiece();
                    castleRight = 0;
                }
                Game.board.repaint();
            }
            if (castleLeft == 2) {
                if (getColor() == 0) {
                    state[7][3].setPiece(state[7][0].getpiece());
                    state[7][0].removePiece();
                    castleLeft = 0;
                }
                if (getColor() == 1) {
                    state[0][3].setPiece(state[0][0].getpiece());
                    state[0][0].removePiece();
                    castleLeft = 0;
                }
                Game.board.repaint();
            }
        }
    }


    //Function to check if king is under threat
    //It checks whether there is any piece of opposite color that can attack king for a given board state
    public boolean isInDanger(Cell[][] state) {

        //Checking for attack from left,right,up and down
        for (int i = x + 1; i < 8; i++) {
            if (state[i][y].getpiece() == null)
                continue;
            else if (state[i][y].getpiece().getColor() == this.getColor())
                break;
            else {
                if ((state[i][y].getpiece() instanceof Rook) || (state[i][y].getpiece() instanceof Queen))
                    return true;
                else
                    break;
            }
        }
        for (int i = x - 1; i >= 0; i--) {
            if (state[i][y].getpiece() == null)
                continue;
            else if (state[i][y].getpiece().getColor() == this.getColor())
                break;
            else {
                if ((state[i][y].getpiece() instanceof Rook) || (state[i][y].getpiece() instanceof Queen))
                    return true;
                else
                    break;
            }
        }
        for (int i = y + 1; i < 8; i++) {
            if (state[x][i].getpiece() == null)
                continue;
            else if (state[x][i].getpiece().getColor() == this.getColor())
                break;
            else {
                if ((state[x][i].getpiece() instanceof Rook) || (state[x][i].getpiece() instanceof Queen))
                    return true;
                else
                    break;
            }
        }
        for (int i = y - 1; i >= 0; i--) {
            if (state[x][i].getpiece() == null)
                continue;
            else if (state[x][i].getpiece().getColor() == this.getColor())
                break;
            else {
                if ((state[x][i].getpiece() instanceof Rook) || (state[x][i].getpiece() instanceof Queen))
                    return true;
                else
                    break;
            }
        }

        //checking for attack from diagonal direction
        int tempX = x + 1, tempY = y - 1;
        while (tempX < 8 && tempY >= 0) {
            if (state[tempX][tempY].getpiece() == null) {
                tempX++;
                tempY--;
            } else {
                if (state[tempX][tempY].getpiece() instanceof Queen && state[tempX][tempY].getpiece().getColor() != this.getColor())
                    return true;
                else
                    break;
            }
        }
        tempX = x - 1;
        tempY = y + 1;
        while (tempX >= 0 && tempY < 8) {
            if (state[tempX][tempY].getpiece() == null) {
                tempX--;
                tempY++;
            } else {
                if (state[tempX][tempY].getpiece() instanceof Queen && state[tempX][tempY].getpiece().getColor() != this.getColor())
                    return true;
                else
                    break;
            }
        }
        tempX = x - 1;
        tempY = y - 1;
        while (tempX >= 0 && tempY >= 0) {
            if (state[tempX][tempY].getpiece() == null) {
                tempX--;
                tempY--;
            } else {
                if (state[tempX][tempY].getpiece() instanceof Queen && state[tempX][tempY].getpiece().getColor() != this.getColor())
                    return true;
                else
                    break;
            }
        }
        tempX = x + 1;
        tempY = y + 1;
        while (tempX < 8 && tempY < 8) {
            if (state[tempX][tempY].getpiece() == null) {
                tempX++;
                tempY++;
            } else {
                if (state[tempX][tempY].getpiece() instanceof Queen && state[tempX][tempY].getpiece().getColor() != this.getColor())
                    return true;
                else
                    break;
            }
        }
        //Checking for attack from the Bishop of opposite color
        int[] posBx = {x + 3, x + 3, x - 3, x - 3, x + 2, x + 2, x - 2, x - 2, x + 1, x + 1, x - 1, x - 1};
        int[] posBy = {y + 3, y - 3, y + 3, y - 3, y + 2, y - 2, y + 2, y - 2, y + 1, y - 1, y + 1, y - 1};

        for (int i = 0; i < 12; i++)
            if ((posBx[i] >= 0 && posBx[i] < 8 && posBy[i] >= 0 && posBy[i] < 8))
                if (state[posBx[i]][posBy[i]].getpiece() != null && state[posBx[i]][posBy[i]].getpiece().getColor() != this.getColor() && (state[posBx[i]][posBy[i]].getpiece() instanceof Bishop)) {
                    return true;
                }

        //Checking for attack from the Knight of opposite color ( changed )
        int[] posKx = {x + 2, x + 2, x + 3, x + 3, x - 2, x - 2, x - 3, x - 3};
        int[] posKy = {y - 3, y + 3, y - 2, y + 2, y - 3, y + 3, y - 2, y + 2};
        for (int i = 0; i < 8; i++)
            if ((posKx[i] >= 0 && posKx[i] < 8 && posKy[i] >= 0 && posKy[i] < 8))
                if (state[posKx[i]][posKy[i]].getpiece() != null && state[posKx[i]][posKy[i]].getpiece().getColor() != this.getColor() && (state[posKx[i]][posKy[i]].getpiece() instanceof Knight)) {
                    return true;
                }


        //Checking for attack from the (Pawn,King,bishop) of opposite color
        int[] posX = {x + 1, x + 1, x + 1, x, x, x - 1, x - 1, x - 1};
        int[] posy = {y - 1, y + 1, y, y + 1, y - 1, y + 1, y - 1, y};
        {
            for (int i = 0; i < 8; i++)
                if ((posX[i] >= 0 && posX[i] < 8 && posy[i] >= 0 && posy[i] < 8))
                    if (state[posX[i]][posy[i]].getpiece() != null && state[posX[i]][posy[i]].getpiece().getColor() != this.getColor() && (state[posX[i]][posy[i]].getpiece() instanceof King)) {
                        return true;
                    }
        }
        if(getColor()==0)
        {
            if(x>0&&y>0&&state[x-1][y-1].getpiece()!=null&&state[x-1][y-1].getpiece().getColor()==1&&(state[x-1][y-1].getpiece() instanceof Pawn))
                return true;
            else if ( x > 0 && y < 7 && state[x - 1][y + 1].getpiece() != null && state[x - 1][y + 1].getpiece().getColor() == 1 && (state[x - 1][y + 1].getpiece() instanceof Pawn))
                return true;
            return x > 0 && y < 7 && state[x - 1][y].getpiece() != null && state[x - 1][y].getpiece().getColor() == 1 && (state[x - 1][y].getpiece() instanceof Pawn);
        }
        else
        {
            if(x<7&&y>0&&state[x+1][y-1].getpiece()!=null&&state[x+1][y-1].getpiece().getColor()==0&&(state[x+1][y-1].getpiece() instanceof Pawn))
                return true;
            else if (x < 7 && y < 7 && state[x + 1][y + 1].getpiece() != null && state[x + 1][y + 1].getpiece().getColor() == 0 && (state[x + 1][y + 1].getpiece() instanceof Pawn)) {
                return true;
            }
            return x < 7 && y < 7 && state[x + 1][y].getpiece() != null && state[x + 1][y].getpiece().getColor() == 0 && (state[x + 1][y].getpiece() instanceof Pawn);
}


    }
}
