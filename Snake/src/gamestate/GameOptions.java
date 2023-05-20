package src.gamestate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import src.main.Game;
import src.ui.AudioOptions;
import src.ui.AudioPlayer;
import src.ui.PauseButton;
import src.ui.UrmButton;
import src.utilz.LoadSave;

public class GameOptions extends State implements Statemethod {

	private AudioOptions audioOptions;
	private BufferedImage backgroundImg, optionsBackgroundImg;
	private UrmButton menuB;

	public GameOptions(Game game) {
		super(game);
		loadImgs();
		loadButton();
		audioOptions = game.getAudioOptions();
	}

	private void loadButton() {
		menuB = new UrmButton(363, 465, 75, 75,2);
	}

	private void loadImgs() {
		backgroundImg = LoadSave.LoadImage(LoadSave.MENU_BACKGROUND);
		optionsBackgroundImg = LoadSave.LoadImage(LoadSave.OPTIONS_MENU);
	}

	@Override
	public void update() {
		menuB.update();
		audioOptions.update();

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, 800, 600, null);
		g.setColor(new Color(0, 0, 0 ,200));
		g.fillRect(0, 0, 800, 600);
		g.drawImage(optionsBackgroundImg, 200, 30, 400, 565, null);

		menuB.draw(g);
		audioOptions.draw(g);

	}

	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuB)) {
			menuB.setMousePressed(true);
			game.getAudioPlayer().playEffect(AudioPlayer.Click);
		} else {
			audioOptions.mousePressed(e);
			game.getAudioPlayer().playEffect(AudioPlayer.Click);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuB)) {
			if (menuB.isMousePressed())
				Gamestate.state = Gamestate.Menu;
		} else
			audioOptions.mouseReleased(e);

		menuB.resetBools();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);

		if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else
			audioOptions.mouseMoved(e);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			Gamestate.state = Gamestate.Menu;

	}

	@Override
	public void KeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	private boolean isIn(MouseEvent e, PauseButton b) {
			return b.getBounds().contains(e.getX(), e.getY());
		}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
