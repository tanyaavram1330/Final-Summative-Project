package game;

import jaco.mp3.player.MP3Player;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author anisoaraavram
 */
public class GameAssignment extends JComponent {

    // Height and Width of our game
    static final int WIDTH = 1200;
    static final int HEIGHT = 600;

    //Title of the window
    String title = "My Game";

    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;

    // YOUR GAME VARIABLES WOULD GO HERE
    //allowing music to pay
    MP3Player backgroundmusic = new MP3Player(ClassLoader.getSystemResource("NetMusic/COTS.MP3"));

    //player variables
    Rectangle player = new Rectangle(40, 250, WIDTH / 2, HEIGHT / 2);

    //creating the invisable death box
    Rectangle deathBox = new Rectangle(200, 380, 1, 20);

    // The arrays are to store all of the animation frames - 18 frames per running animation
    BufferedImage[] runRight = new BufferedImage[18];

    //player scores
    int playerScore = 0;
    int deathscore = 0;

    //the amount of boxes there middle top & bottom
    int[] xBox = new int[2];
    int[] xBox2 = new int[2];
    int[] xBox3 = new int[2];

    //creating a new box variable
    Rectangle Boxy = new Rectangle(xBox[1], 380, 20, 20);
    Rectangle Boxy2 = new Rectangle(xBox2[1], 330, 20, 20);
    Rectangle Boxy3 = new Rectangle(xBox3[1], 430, 20, 20);

    // variables that change frames
    int frame = 0; // which frame of animation am I drawing
    int frameDelay = 30; // number of milliseconds between frames
    long nextFrame = 0; // what time we next change the frame. i.e. now + delay

    // control variables
    boolean right = false;
    boolean up = false;
    boolean down = false;

    //deathscreen
    boolean death = false;

    //new colours
    Color BackGreen = new Color(30, 110, 15);
    Color MidGreen = new Color(21, 77, 11);
    Color DEATHbox = new Color (255, 0, 25);
    Color sunSet = new Color (255, 255, 205);

    // GAME VARIABLES END HERE   
    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)
    public GameAssignment() {
        // creates a windows to show my game
        JFrame frame = new JFrame(title);

        // sets the size of my game
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(this);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        // add listeners for keyboard and mouse
        frame.addKeyListener(new Keyboard());
        Mouse m = new Mouse();

        this.addMouseMotionListener(m);
        this.addMouseWheelListener(m);
        this.addMouseListener(m);

        backgroundmusic.setRepeat(true);
        backgroundmusic.play();
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE
        //drawing the background image & etting the colour to yellow
        g.setColor(sunSet);
        g.fillRect(0, 0, 1200, 600);
        
        //changing the colour to white & placing the sun into the background-
        g.setColor(Color.WHITE);
        g.fillOval(600, 200, 300, 300);
        
        //drawing the back floor section of the game & setting the colour to backgreen
        g.setColor(BackGreen);
        g.fillRect(0, 350, 1200, 300);
        
        //drawing the middle section of the floor & setting a new colour
        g.setColor(MidGreen);
        g.fillRect(0, 400, 1200, 300);
        
        //drawing the front section of the floor & changing the colour to black
        g.setColor(Color.BLACK);
        g.fillRect(0, 450, 1200, 300);

        //creating the obstical middle
        //changing the colour to white
        g.setColor(DEATHbox);
        for (int i = 0; i < xBox.length; i++) {
            g.fillRect(Boxy.x, Boxy.y, Boxy.width, Boxy.height);
            g.fillRect(Boxy2.x, Boxy2.y, Boxy2.width, Boxy2.height);
            g.fillRect(Boxy3.x, Boxy3.y, Boxy3.width, Boxy3.height);
        }

        //changing the colour back to black
        g.setColor(Color.BLACK);
        //drawing the font for player 1
        g.drawString("Score: " + playerScore, WIDTH / 2 + 450, 50);

        //setting the colour to red
        g.setColor(Color.RED);
        //drawing the font for player 1
        g.drawString("Deaths: " + deathscore, WIDTH / 2 + 450, 75);

        //drawing the background
        //g.drawImage()
        // draw player
        if (!up && !down) {    // need to line up the pictures properly from my character size
            int imageWidth = runRight[frame].getWidth();
            int imageDiff = imageWidth - player.width;
            int imageX = player.x - imageDiff / 2; // subtract half the difference
            
            //Getting the death box into the game with the running animation for the 'hit' box
//            int imageWid = deathBox.x;
//            int imageDif = imageWid = player.y;
//            int imageY = deathBox.x = imageDif/2;

            // draw the image
            g.drawImage(runRight[frame], imageX, player.y, null);
        }
        //am I moving up?
        if (up && !down) {
            int imageWidth2 = runRight[frame].getWidth();
            int imageDiff2 = imageWidth2 - player.width;
            int imageY = player.x - imageDiff2 / 2;

            //drawing the image
            g.drawImage(runRight[frame], imageY, player.y - 50, null);
        }
        //am I moving down?
        if (down && !up) {
            int imageWidth3 = runRight[frame].getWidth();
            int imageDiff3 = imageWidth3 - player.width;
            int imageY = player.x - imageDiff3 / 2;

            //drawing the image
            g.drawImage(runRight[frame], imageY, player.y + 50, null);
        }
        //A bonus in the game to get out if there are three boxes in a row and
        //you can't excape from the death
        //am I stuck?
        if (up && down) {
            int imageWidth = runRight[frame].getWidth();
            int imageDiff = imageWidth - player.width;
            int imageY = player.x - imageDiff / 2;

            //drawing the image
            g.drawImage(runRight[frame], imageY + 200, player.y, null);
        }

        // GAME DRAWING ENDS HERE
    }

    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void preSetup() {
        // Any of your pre setup before the loop starts should go here
        // load in animations
        for (int i = 0; i < 18; i++) {
            runRight[i] = loadImage("ImagesGame/Running" + i + ".png");
        }

        //setting the top boxes out of the screen for the game to start
        Boxy2.x = 3200;
        Boxy.x = 3800;
        Boxy3.x = 4400;

        //drawing the death box
        deathBox.x = 200;
    }

    //Randomly generating the boxes
    public void setBox() {
        //randomly generating the boxes
        Random rand = new Random();
        //setting the space between the boxes
        int space = rand.nextInt(1200) + 1;
        Boxy.x = Boxy.x;
        Boxy.x = 1200 + space;
    }

    //Randomly generating the boxes
    public void setBox2() {
        //randomly generating the boxes
        Random rand = new Random();
        //setting the space between the boxes
        int space = rand.nextInt(1000) + 1;
        Boxy2.x = Boxy2.x;
        Boxy2.x = 1200 + space;
    }

    //Randomly generating the boxes
    public void setBox3() {
        //randomly generating the boxes
        Random rand = new Random();
        //setting the space between the boxes
        int space = rand.nextInt(1000) + 1;
        Boxy3.x = Boxy3.x;
        Boxy3.x = 1200 + space;
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        preSetup();

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;

        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            collisions();
            if (death == true) {
                deathscore++;
                death = false;
            }

            // get the pipes moving
            if (up && right && down) {
                player.x = player.x;
                deathBox.x = deathBox.x;
            }

            // delay of when to change the frame
            if (startTime >= nextFrame) {
                frame = (frame + 1) % 18;
                nextFrame = startTime + frameDelay;
            }
            if (up && startTime >= nextFrame) {
                frame = (frame + 1) % 18;
                nextFrame = startTime + frameDelay;
            }
            if (down && startTime >= nextFrame) {
                frame = (frame + 1) % 18;
                nextFrame = startTime + frameDelay;
            }

            //allowing the boxes to move
            for (int i = 0; i < xBox.length; i++) {
                Boxy.x -= 8;
                Boxy2.x -= 10;
                Boxy3.x -= 14;
            }

            //restarting the boxes after it touches the end of the screen back to the start
            if (Boxy.x < 0) {
                setBox();
            }
            if (Boxy2.x < 0) {
                setBox2();
            }
            if (Boxy3.x < 0) {
                setBox3();
            }

            //score as the player plays
            playerScore++;
            // GAME LOGIC ENDS HERE 
            // update the drawing (calls paintComponent)
            repaint();

            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            try {
                if (deltaTime > desiredTime) {
                    //took too much time, don't wait
                    Thread.sleep(1);
                } else {
                    // sleep to make up the extra time
                    Thread.sleep(desiredTime - deltaTime);
                }
            } catch (Exception e) {
            };
        }
    }

    private void moveBox() {

    }

    // Used to implement any of the Mouse Actions
    private class Mouse extends MouseAdapter {

        // if a mouse button has been pressed down
        @Override
        public void mousePressed(MouseEvent e) {

        }

        // if a mouse button has been released
        @Override
        public void mouseReleased(MouseEvent e) {

        }

        // if the scroll wheel has been moved
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {

        }

        // if the mouse has moved positions
        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

    // Used to implements any of the Keyboard Actions
    private class Keyboard extends KeyAdapter {

        // if a key has been pressed down
        @Override
        //allows the character to jump when key is pressed
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_UP) {
                up = true;
            }
            if (key == KeyEvent.VK_DOWN) {
                down = true;
            }
            if (key == KeyEvent.VK_DOWN && key == KeyEvent.VK_UP){
                up = false;
                down = true;
            }
        }

        // if a key has been released
        @Override
        //when key isn't pressed the animation is moving
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_UP) {
                up = false;
            }
            if (key == KeyEvent.VK_DOWN) {
                down = false;
            }
            if (key == KeyEvent.VK_DOWN && key == KeyEvent.VK_UP){
                up = false;
                down = false;
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates an instance of my game
        GameAssignment game = new GameAssignment();

        // starts the game loop
        game.run();
    }

    public BufferedImage loadImage(String filename) {

        BufferedImage img = null;

        try {
            // use ImageIO to load in an Image
            // ClassLoader is loading all of the images from the file
            img = ImageIO.read(ClassLoader.getSystemResourceAsStream(filename));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        return img;
    }

    public void collisions() {
        //setting the system/code to whenever the bix hits the 'deathbox' the player dies
//        for (int i = 0; i < xBox.length; i++) {
//            if (player.intersects(Boxy)|| player.intersects(Boxy2)|| player.intersects(Boxy3)) {
//                death = true;
//            }
//        }
        for (int i = 0; i < xBox.length; i++) {
            if (deathBox.intersects(Boxy)) {
                death = true;
            }
        }
    }
}
