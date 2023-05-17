package Chess;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class EatenPieces extends JPanel {

    public JFrame frame = new JFrame();
    JPanel panelFrame = new JPanel();
    JPanel panelWhite = new JPanel(new GridLayout(3, 5));
    JPanel panelBlack = new JPanel(new GridLayout(3, 5));
    JLabel[] labelWhite = new JLabel[15];
    JLabel[] labelBlack = new JLabel[15];
    ImageIcon[] imageWhite = new ImageIcon[15];
    ImageIcon[] imageBlack = new ImageIcon[15];
    public static ArrayList<String> eatenWhite = new ArrayList(Collections.nCopies(15, null));
    public static ArrayList<String> eatenBlack = new ArrayList(Collections.nCopies(15, null));


    EatenPieces() {
        frame.setSize(500, 700);
        frame.setResizable(false);
        frame.setLayout(new GridLayout(2, 1));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panelWhite.setBackground(Color.lightGray);
        panelBlack.setBackground(Color.darkGray);
        frame.add(panelWhite);
        frame.add(panelBlack);
        frame.setVisible(true);
    }

    public void showPieceWhite() {

        for (int i = 0; i < 15; i++) {
            if (eatenWhite.get(0) != null) {
                imageWhite[i] = new ImageIcon(this.getClass().getResource(eatenWhite.get(0)));
                labelWhite[i] = new JLabel();
                labelWhite[i].setIcon(imageWhite[i]);
                panelBlack.add(labelWhite[i]);
                // frame.add(panelBlack);
                labelWhite[i].repaint();
                eatenWhite.add(0, null);
                frame.revalidate();
                frame.repaint();
                break;

            }
        }

    }

    public void showPieceBlack() {

        for (int i = 0; i < 15; i++) {
            if (eatenBlack.get(0) != null) {
                imageBlack[i] = new ImageIcon(this.getClass().getResource(eatenBlack.get(0)));
                labelBlack[i] = new JLabel();
                labelBlack[i].setIcon(imageBlack[i]);
                panelWhite.add(labelBlack[i]);
                labelBlack[i].repaint();
                eatenBlack.add(0, null);
                frame.revalidate();
                frame.repaint();
                break;


            }

        }

    }
}


