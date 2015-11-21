package com.Jessy;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jessy on 11/21/2015.
 */
public class GameDisplay extends JPanel {

    static int screenSize = 300;    //and width - screen is square
    static int paddleSize = 25;     //Actually half the paddle size - how much to draw on each side of center
    static int paddleDistanceFromSide = 10;  //How much space between each paddle and side of screen


    static int computerPaddleY = screenSize / 2;    //location of the center of the paddles on the Y-axis of the screen
    static int humanPaddleY = screenSize / 2;


    static double ballX = screenSize / 2;   //Imagine the ball is in a square box. These are the coordinates of the top of that box.
    static double ballY = screenSize / 2;   //So this starts the ball in (roughly) the center of the screen
    static int ballSize = 10;                //Diameter of ball


    static boolean gameOver;      //Used to work out what message, if any, to display on the screen
    static boolean removeInstructions = false;  // initializing boolean value of false
    static boolean removeGameOver = false;  // initializing boolean value of false
    static boolean scoreCounter = false;    // initializing boolean value of false

    static int computerWin = 0;   // counter for, how many time computer and human win the game
    static int humanWin = 0;


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Checking if human or computer won the game
        if (gameOver && !scoreCounter) {
            if (ballX <= 0) {
                humanWin++;
            }

            else {
                computerWin++;
            }
        }

        if (gameOver && !removeGameOver) {
            //see the color for the Game over text
            g.setColor(Color.red);
            g.drawString("Game over!", 20, 30);
            g.drawString("Score: " + "Player Won =" + humanWin + " Computer Won =" + computerWin, 20, 60);
            g.drawString("Press r to restart the game", 20, 90);


            scoreCounter = true;


            return;
        }


        if (!removeInstructions) {
            //set the color for the beginning text
            g.setColor(Color.black);
            g.drawString("Pong! Press up or down to move", 20, 30);
            g.drawString("Press q to quit", 20, 60);
        }



        //While game is playing, these methods draw the ball, paddles, using the global variables
        //Other parts of the code will modify these variables

        //Ball - a circle is just an oval with the height equal to the width

        // Set the color of the ball to cyan
        g.setColor(Color.cyan);
        g.fillOval((int) ballX, (int) ballY, ballSize, ballSize);


        //Set Computer paddle color to blue
        g.setColor(Color.blue);
        g.drawLine(paddleDistanceFromSide, computerPaddleY - paddleSize, paddleDistanceFromSide, computerPaddleY + paddleSize);

        //Set Human paddle color to green
        g.setColor(Color.green);
        g.drawLine(screenSize - paddleDistanceFromSide, humanPaddleY - paddleSize, screenSize - paddleDistanceFromSide, humanPaddleY + paddleSize);

    }
}




