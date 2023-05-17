package Chess;

import Chess.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


/**
 * This is the Time Class.
 * It contains all the required variables and functions related to the timer on the Main GUI
 * It uses a Timer Class
 *
 */


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Time {
    public JLabel label;
    public Timer countdownTimer;
    private int timeRemaining;
    public static Time instance;
    public static Time instance2;

    //private boolean isPlayer1Turn;

    private Time(JLabel passedLabel) {
        countdownTimer = new Timer(1000, new CountdownTimerListener());
        this.label = passedLabel;
        timeRemaining = Game.timeRemaining;

        // isPlayer1Turn = true;
    }

    public static Time  getInstance(JLabel label) {
        if (instance == null)
            instance = new Time(label);
        return instance;
    }

    public static Time  getInstance2(JLabel label2) {
        if (instance2 == null)
            instance2 = new Time(label2);
        return instance2;
    }

    // A function that starts the timer
    public void start() {
        countdownTimer.start();
    }

    // A function that stops the timer
    public void stop() {
        countdownTimer.stop();

    }

    // A function that resets the timer
    public void reset() {
        timeRemaining = Game.timeRemaining;
    }

    // A function that is called after every second. It updates the timer and takes other necessary actions
    class CountdownTimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int min, sec;

            if (timeRemaining > 0) {
                min = timeRemaining / 60;
                sec = timeRemaining % 60;
                label.setText(String.valueOf(min) + ":" + (sec >= 10 ? String.valueOf(sec) : "0" + String.valueOf(sec)));
                timeRemaining--;
            } else {
               if (Game.Mainboard != null) {
                   Game.Mainboard.timerEnd();
                }

            }
        }
    }
    public JLabel getTime (){
        return this.label;
    }

}
