package com.Jessy;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Jessy on 11/21/2015.
 */

public class ControlForGame extends GameDisplay implements KeyListener {

    static int computerPaddleMaxSpeed = 3;   //Max number of pixels that computer paddle can move clock tick. Higher number = easier for computer
    static int humanPaddleMaxSpeed = 5;   //This doesn't quite do the same thing... this is how many pixels human moves per key press TODO use this in a better way


    static int computerPaddleSpeed = 0;   // same

    static int ballSize = 10;                //Diameter of ball


    static double ballSpeed = 5;   //Again, pixels moved per clock tick


    //An angle in radians (which range from 0 to 2xPI (0 to about 6.3).
    //This starts the ball moving down toward the human. Replace with some of the other
    //commented out versions for a different start angle
    //set this to whatever you want (but helps if you angle towards a player)
    static double ballDirection = Math.PI + 1;   //heading left and up
    //static double ballDirection = 1;
    //static double ballDirection = 0;   //heading right
    //static double ballDirection = Math.PI;   //heading left



    @Override
    public void keyReleased(KeyEvent e) {

    } //  Don't need this method, but it was required for implementing




    @Override
    public void keyTyped(KeyEvent e) {

        char keyPressed = e.getKeyChar();
        char q = 'q';
        char j = 'j';
        if (keyPressed == q) {
            System.exit(0);    //quit if user presses the q key.
        }
        if (gameOver && keyPressed == j) {
            ballX = 150;        //reset the ball
            removeGameOver = true;
            gameOver = false;
            scoreCounter = false;
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {

        removeInstructions = true;   //game has started
        removeGameOver = false;


        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            System.out.println("down key");
            moveDown();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("up key");
            moveUp();

        }

        //ev.getComponent() returns the GUI component that generated this event
        //In this case, it will be GameDisplay JPanel
        e.getComponent().repaint();   //This calls paintComponent(Graphics g) again

    }


    private void moveDown() {
        //Coordinates decrease as you go up the screen, that's why this looks backwards.
        if (humanPaddleY < screenSize - paddleSize) {
            humanPaddleY+=humanPaddleMaxSpeed;
        }
    }


    private void moveUp() {
        //Coordinates increase as you go down the screen, that's why this looks backwards.
        if (humanPaddleY > paddleSize) {
            humanPaddleY-=humanPaddleMaxSpeed;
        }
    }


    //Uses the current position of ball and paddle to move the computer paddle towards the ball
    protected static void moveComputerPaddle(){


        //if ballY = 100 and paddleY is 50, difference = 50, need to adjust
        //paddleY by up to the max speed (the minimum of difference and maxSpeed)


        //if ballY = 50 and paddleY = 100 then difference = -50
        //Need to move paddleY down by the max speed


        int ballPaddleDifference = computerPaddleY - (int)ballY;
        int distanceToMove = Math.min(Math.abs(ballPaddleDifference), computerPaddleMaxSpeed);


        System.out.println("computer paddle speed = " + computerPaddleSpeed);


        if (ballPaddleDifference > 0 ) {   //Difference is positive - paddle is below ball on screen
            computerPaddleY -= distanceToMove;


        } else if (ballPaddleDifference < 0){
            computerPaddleY += distanceToMove;


        } else {
            //Ball and paddle are aligned. Don't need to move!
            computerPaddleSpeed = 0;
        }
    }




    //Checks to see if the ball has hit a wall or paddle
    // If so, bounce off the wall/paddle
    // And then move ball in the correct direction
    protected static void moveBall() {
        System.out.println("move ball");
        //move in current direction
        //bounce off walls and paddle
        //TODO Take into account speed of paddles


        // Initializing boolean value to variables
        boolean hitWall = false;
        boolean hitHumanPaddle = false;
        boolean hitComputerPaddle = false;


        if (ballX <= 0 || ballX >= screenSize ) {
            gameOver = true;
            //return;
        }


        if (ballY <= 0 || ballY >= screenSize-ballSize) {
            hitWall = true;
        }


        //If ballX is at a paddle AND ballY is within the paddle size.
        //Hit human paddle?
        if (ballX >= screenSize-(paddleDistanceFromSide+(ballSize)) && (ballY > humanPaddleY-paddleSize && ballY < humanPaddleY+paddleSize))
            hitHumanPaddle = true;


        //Hit computer paddle?
        if (ballX <= paddleDistanceFromSide && (ballY > computerPaddleY-paddleSize && ballY < computerPaddleY+paddleSize))
            hitComputerPaddle = true;



        if (hitWall) {
            //bounce
            ballDirection = ( (2 * Math.PI) - ballDirection );
            System.out.println("ball direction " + ballDirection);
        }


        //Bounce off computer paddle - just modify direction
        if (hitComputerPaddle) {
            ballDirection = (Math.PI) - ballDirection;
            //TODO factor in speed into new direction
            //TODO So if paddle is moving down quickly, the ball will angle more down too


        }


        //Bounce off computer paddle - just modify direction
        if (hitHumanPaddle) {
            ballDirection = (Math.PI) - ballDirection;
            //TODO consider speed of paddle
        }


        //Now, move ball correct distance in the correct direction


        // ** TRIGONOMETRY **


        //distance to move in the X direction is ballSpeed * cos(ballDirection)
        //distance to move in the Y direction is ballSpeed * sin(ballDirection)


        ballX = ballX + (ballSpeed * Math.cos(ballDirection));
        ballY = ballY + (ballSpeed * Math.sin(ballDirection));


        // ** TRIGONOMETRY END **


    }

}



