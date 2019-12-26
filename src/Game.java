import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Fires {

    private int x;
    private int y;

    public Fires(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setX(int x){
        this.x = x;
    }
    public int getX(){
        return x;
    }

    public void setY(int y){
        this.y = y;
    }
    public int getY(){
        return y;
    }
}

public class Game extends JPanel implements KeyListener, ActionListener {

    Timer timer = new Timer(3, this);       // delay in miliseconds, Object of ActionListener
    private int time = 0;
    private int fire = 0;
    private BufferedImage image;
    private ArrayList<Fires> fires = new ArrayList<Fires>();
    private int firedirY = 1;
    private int targetX = 0;
    private int targetdirX = 2;
    private int spaceShipX = 0;
    private int dirSpaceX = 20;

    public boolean checkIt(){

        for(Fires temp: fires){
            if (new Rectangle(temp.getX(), temp.getY(), 10,20).intersects(new Rectangle(targetX, 0, 20, 20))){
                return true;
            }
        }
        return false;
    }

    public Game() {

        try {
            image = ImageIO.read(new FileImageInputStream(new File("spaceship.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setBackground(Color.BLACK);   // Background Color

        timer.start();                // When Game starts, timer starts
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        time += 5;              // paint() runs every 5ms so if we add 5, we can find total time in game
        g.setColor(Color.red);                              // Color of Target
        g.fillOval(targetX, 0, 20, 20);    // Size of Target
        g.drawImage(image, spaceShipX, 490, image.getWidth()/10, image.getHeight()/10, this);  // Size of Image

        for (Fires temp : fires){
            if (temp.getY() < 0){
                fires.remove(temp);
            }
        }

        g.setColor(Color.BLUE);

        for (Fires temp : fires){
            g.fillRect(temp.getX(), temp.getY(), 10, 20);
        }

        if (checkIt()){
            timer.stop();
            String message = "You Win!\n" +
                    "Total Fire: " + fire +
                    "\nTotal Time: " + time / 1000.0 + " seconds";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (Fires temp : fires){
            temp.setY(temp.getY() - firedirY);
        }

        targetX += targetdirX;
        if (targetX >= 765)
            targetdirX = -targetdirX;
        if (targetX <= 0)
            targetdirX = -targetdirX;

        repaint();      // When actionPerformed run, repaint should be run because paint need repaint
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int c = e.getKeyCode();

        if (c == KeyEvent.VK_LEFT){
            if (spaceShipX <= 0){
                spaceShipX = 0;
            } else {
                spaceShipX -= dirSpaceX;
            }
        } else if (c == KeyEvent.VK_RIGHT){
            if (spaceShipX >= 740){
                spaceShipX = 740;
            } else{
                spaceShipX += dirSpaceX;
            }
        } else if (c == KeyEvent.VK_CONTROL){
            fires.add(new Fires(spaceShipX+15, 470));   // added 15 because of top of the ship
            fire++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
