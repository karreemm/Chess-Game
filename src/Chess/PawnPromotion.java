package Chess;

import Chess.Cell;
import Pieces.*;
import Chess.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;


public class PawnPromotion extends JFrame implements ActionListener {
    JButton[] button = new JButton[4];
    public static Piece[] piece = new Piece[4];
    //removepiece
    JPanel p = new JPanel();
    JLabel label = new JLabel("Choose a Piece");

    Cell[][] state;

    int selectedX, selectedY;
    int colorZ;

    public PawnPromotion(Cell[][] board, int X, int Y, int color) {
        super();
        state = board;
        selectedX = X;
        selectedY = Y;
        setTitle("Pawn Promotion");
        setSize(600, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLocation(380, 250);
        colorZ = color;

        addelemnts();
        AddPieces(color);
    }

    public void addelemnts() {
        // setting up label
        label.setFont(new Font("Cooper Black", 0, 30));
        label.setForeground(new Color(51, 0, 25));
        label.setBounds(175, 10, 300, 40);

        // setting up buttons
        for (int i = 0; i < 4; i++) {
            button[i] = new JButton();
            button[i].setBackground(new Color(255, 255, 255));
            button[i].addActionListener(this);
        }

        button[0].setBounds(70, 65, 80, 80);
        button[1].setBounds(190, 65, 80, 80);
        button[2].setBounds(310, 65, 80, 80);
        button[3].setBounds(430, 65, 80, 80);


        // adjusting the panel color , layout and adding it to frame (153, 51, 255)
        p.setBackground(new Color(209, 138, 41));
        add(p);
        p.setLayout(null);

        // adding label & buttons
        p.add(label);
        for (int i = 0; i < 4; i++) {
            p.add(button[i]);
        }

    }


    public void AddPieces(int color) {
        String path = "null";

        if(color == 0) {
            piece[0] = Game.wr01;
            piece[0].setId("PromotedWR");
        }
        else {
            piece[0] = Game.br01;
            piece[0].setId("PromotedBR");
        }

        if(color == 0) {
            piece[1] = Game.wb01;
            piece[1].setId("PromotedWB");
        }
        else{
            piece[1] = Game.bb01;
            piece[1].setId("PromotedBB");
        }

        if(color == 0) {
            piece[2] = Game.wk01;
            piece[2].setId("PromotedWK");
        }
        else{
            piece[2] = Game.bk01;
            piece[2].setId("PromotedBK");
        }

        if(color == 0) {
            piece[3] = Game.wq;
            piece[3].setId("PromotedWQ");
        }
        else{
            piece[3] = Game.bq;
            piece[3].setId("PromotedBQ");
        }


        for (int i = 0; i < 4; i++) {

            //path = piece[i].getPath();

            if (color == 0) {
                path = piece[i].getWhitePath();
            } else if (color == 1) {
                path = piece[i].getBlackPath();
            }

            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image bf = icon.getImage();
            Image af = bf.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            icon = new ImageIcon(af);
            button[i].setIcon(icon);
        }

    }

    public void close() {

        WindowEvent winClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);

    }



    @Override
    public void actionPerformed(ActionEvent ae) {
        for (int i = 0; i < 4; i++) {

            if (ae.getSource() == button[i]) {

                state[selectedX][selectedY].removePiece();
                Game.board.repaint();
                state[selectedX][selectedY].setPiecePromotion(piece[i], colorZ);
                close();

            }

        }

    }

}
