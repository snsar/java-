package src.ui;

import src.gamestate.Gamestate;
import src.utilz.LoadSave;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class MenuButton {
    private int xPos, yPos, rowIndex, index;
    private Gamestate state;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;
	private Rectangle bounds;
    public MenuButton(int xPos, int yPos, int rowIndex, Gamestate state){
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImage();
        initBounds();
    }
    private void initBounds() {
		bounds = new Rectangle(xPos, yPos, 200, 100);

	}

    private void loadImage() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.LoadImage(LoadSave.MENU_BUTTONS);
        for(int i=0;i<imgs.length;i++){
            imgs[i] = temp.getSubimage(i * 140, rowIndex * 56, 140, 56);
        }
    }

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, 200, 100, null);
	}

    public void update() {
		index = 0;
		if (mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	public Rectangle getBounds() {
		return bounds;
	}
    public void applyGamestate() {
		Gamestate.state = state;
	}

	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}
	public Gamestate getState(){
		return Gamestate.state;
	}
}
