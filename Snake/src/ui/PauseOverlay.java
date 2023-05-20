package src.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import src.gamestate.Gamestate;
import src.gamestate.Playing;
import src.main.Game;
import src.utilz.LoadSave;

public class PauseOverlay {
	private Game game;
    private Playing playing;
	private BufferedImage backgroundImg;
	private AudioOptions audioOptions;
	private UrmButton menuB, replayB, unpauseB;

	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		audioOptions = playing.getGame().getAudioOptions();
		createUrmButtons();

	}

	private void createUrmButtons() {
		menuB = new UrmButton(250, 465, 75, 75, 2);
		replayB = new UrmButton(363, 465, 75, 75, 1);
		unpauseB = new UrmButton(475, 465, 75, 75, 0);

	}

	private void loadBackground() {
		backgroundImg = LoadSave.LoadImage(LoadSave.PAUSE_BACKGROUND);
	}

    public void update() {

		menuB.update();
		replayB.update();
		unpauseB.update();
		
		audioOptions.update();
	}

	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0 ,200));
		g.fillRect(0, 0, 800, 600);
		// Background
		g.drawImage(backgroundImg, 200, 10, 400, 580, null);

		// UrmButtons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
		
		audioOptions.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);

	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuB)){
			menuB.setMousePressed(true);
		}
		else if (isIn(e, replayB)){
			replayB.setMousePressed(true);
		}
		else if (isIn(e, unpauseB)){
			unpauseB.setMousePressed(true);
		}
		else {
			audioOptions.mousePressed(e);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuB)) {
			if (menuB.isMousePressed()) {
				playing.setGamestate(Gamestate.Menu);
				playing.resetAll();
			}
		} else if (isIn(e, replayB)) {
			if (replayB.isMousePressed())
				playing.resetAll();
		} else if (isIn(e, unpauseB)) {
			if (unpauseB.isMousePressed())
				playing.unpauseGame();
		} else
			audioOptions.mouseReleased(e);

		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();

	}

	public void mouseMoved(MouseEvent e) {

		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);

		if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else if (isIn(e, replayB))
			replayB.setMouseOver(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMouseOver(true);
		else
			audioOptions.mouseMoved(e);

	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

}
