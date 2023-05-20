package src.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Delayed;

import src.gamestate.Gamestate;
import src.gamestate.Playing;
import src.main.Game;
import src.utilz.LoadSave;
import static src.ingame.Player.*;
import static src.gamestate.Playing.*;

public class Select {
	private Game game;
    private Playing playing;
	private BufferedImage backgroundImg;
	private BufferedImage[] Imgmap;
	private VolumeButton selecButton;
	private SelectButton menuB, replayB, unpauseB;
	private String[] namemap = {"map1","map2","map3"};
	private Font font;
	private int code;

	public Select(Playing playing) {
		this.playing = playing;
		loadImg();
		createSelectButtons();

	}

	private void createSelectButtons() {
		menuB = new SelectButton(700, 500, 75, 75, 2);
		replayB = new SelectButton(250, 350, 75, 75, 1);
		unpauseB = new SelectButton(500, 350, 75, 75, 0);
		selecButton = new VolumeButton(260, 500, 275, 45);
	}

	private void loadImg() {
		backgroundImg = LoadSave.LoadImage(LoadSave.PAUSE_BACKGROUND);
		Imgmap = new BufferedImage[LoadSave.Imgmap.length];
		for(int i=0;i<LoadSave.Imgmap.length;i++) {
			Imgmap[i] = LoadSave.LoadImage(LoadSave.Imgmap[i]);
		}
		font = new Font("Helvetica", Font.BOLD, 48);
	}
	
    public void update() {

		menuB.update();
		replayB.update();
		unpauseB.update();
		code = mapcode-1;
		selecButton.update();
	}
    
	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0 ,200));
		g.fillRect(0, 0, 800, 600);
		// Background
		g.drawImage(backgroundImg, 200, 10, 400, 580, null);
		g.drawImage(Imgmap[code], 150, 150, 300, 200,null);
		
		g.setColor(Color.red);
        g.setFont(font);
        g.drawString(namemap[code],350,400);
		// UrmButtons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
		
		selecButton.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
		if (selecButton.isMousePressed()) {
			selecButton.changeX(e.getX());
			int valueAfter = selecButton.getDelayValue();
			DELAY=valueAfter;
		}
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
		else if(isIn(e, selecButton)){
			selecButton.setMousePressed(true);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuB)) {
			if (menuB.isMousePressed()) {
				playing.resetAll();
				selectingame = !selectingame;
			}
		} else if (isIn(e, replayB)) {
			if (replayB.isMousePressed()){
				if(mapcode == 1)mapcode=3;
				else mapcode--;
			}
				
		} else if (isIn(e, unpauseB)) {
			if (unpauseB.isMousePressed()){
				if(mapcode==3)mapcode=1;
				else mapcode++;
			}
				
		}

		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
		selecButton.resetBools();
	}

	public void mouseMoved(MouseEvent e) {

		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		selecButton.setMouseOver(false);


		if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else if (isIn(e, replayB))
			replayB.setMouseOver(true);
		else if (isIn(e, unpauseB))
			unpauseB.setMouseOver(true);
		else if (isIn(e, selecButton))
			selecButton.setMouseOver(true);

	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
	public int getCode() {
		return mapcode;
	}

}
