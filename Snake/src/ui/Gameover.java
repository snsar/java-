package src.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.security.Principal;

import src.gamestate.Gamestate;
import src.gamestate.Playing;
import src.ingame.Player;
import static src.ingame.Player.*;
import src.main.Game;
import src.utilz.LoadSave;

public class Gameover {
	private Game game;
	private Playing playing;
	private BufferedImage img;
	private UrmButton menu, play;
	private Player player;
    private Font font;

	public Gameover(Playing playing) {
		this.playing = playing;
		createImg();
		createButtons();
	}

	private void createButtons() {
		play = new UrmButton(450, 375, 75, 75, 0);
		menu = new UrmButton(275, 375, 75, 75,  2);

	}

	private void createImg() {
		img = LoadSave.LoadImage(LoadSave.DEATH_SCREEN);
        font = new Font("Helvetica", Font.BOLD, 24);
	}

	public void update() {
		menu.update();
		play.update();
	}

	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, 800, 600);

		g.drawImage(img, 200, 75, 400, 450,null);

		menu.draw(g);
		play.draw(g);

        g.setColor(Color.black);
        g.setFont(font);
        g.drawString("Hight Score:  " + high_score,305,300);
		g.drawString("Score:  " + score,340,350);

	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {
		play.setMouseOver(false);
		menu.setMouseOver(false);

		if (isIn(menu, e))
			menu.setMouseOver(true);
		else if (isIn(play, e))
			play.setMouseOver(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(menu, e)) {
			if (menu.isMousePressed()){
				playing.setGamestate(Gamestate.Menu);
				Player.inGame = !Player.inGame;
				playing.resetAll();
			}
		} else if (isIn(play, e)){
			if (play.isMousePressed()){
				Player.inGame = !Player.inGame;
				playing.resetAll();
			}
		}
		menu.resetBools();
		play.resetBools();
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(menu, e)){
			menu.setMousePressed(true);
		}
		else if (isIn(play, e)){
			play.setMousePressed(true);
		}
	}
    
	private boolean isIn(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
}
