import javax.swing.*;
import java.awt.*;

public class GameScreen extends JFrame {

    public GameScreen(String title) throws HeadlessException {
        super(title);
    }

    public static void main(String[] args) {

        GameScreen screen = new GameScreen("Space Game");

        screen.setResizable(false);
        screen.setFocusable(false);
        screen.setSize(800,600);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game();

        game.requestFocus();            // Need focus before addKeyListener
        game.addKeyListener(game);      // KeyListener should have implemented. (For using keyboard)
        game.setFocusable(true);        // Focus on the JPanel now!
        game.setFocusTraversalKeysEnabled(false);       // JPanel, keyboard,

        screen.add(game);               // add JPanel into JFrame
        screen.setVisible(true);        // if JPanel has been added, set JFrame

    }
}
