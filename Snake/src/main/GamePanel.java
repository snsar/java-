package src.main;

import javax.swing.JPanel;
import java.awt.Graphics;
import src.mousekey.Mouse;
import src.mousekey.KeyBoard;
import java.awt.Dimension;

public class GamePanel extends JPanel{
    private Mouse mouse;
	private Game game;
    public GamePanel(Game game){
        mouse = new Mouse(this);
		this.game = game;
		setPanelSize();
		addKeyListener(new KeyBoard(this));
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
    }
    private void setPanelSize() {
        Dimension size = new Dimension(800,600);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size); 
    }
    public void updateGame() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}

	public Game getGame() {
		return game;
	}
}
