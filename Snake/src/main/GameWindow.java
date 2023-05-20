package src.main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Graphics;
import src.utilz.LoadSave;
import java.awt.image.BufferedImage;

public class GameWindow {
    private JFrame window;
    private BufferedImage bg;
    private Graphics g;
    public GameWindow(GamePanel gamepanel){
        window = new JFrame();
        window.add(gamepanel);
        window.setTitle("Snake");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
        ImageIcon snake = new ImageIcon("Snake/res/snake.png");
        window.setIconImage(snake.getImage());
    }
}
