package src.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import src.utilz.LoadSave;

public class VolumeButton extends PauseButton {

	private BufferedImage[] imgs;
	private BufferedImage slider;
	private int index = 0;
	private boolean mouseOver, mousePressed;
	private int buttonX, minX, maxX;
	private float floatValue = 0f;
	private int DelayValue ;

	public VolumeButton(int x, int y, int width, int height) {
		super(x + width / 2, y, 56, height);
		bounds.x -= 28;
		buttonX = x + width / 2;
		this.x = x;
		this.width = width;
		minX = x;
		maxX = x + width;
		loadImgs();
	}

	private void loadImgs() {
		BufferedImage temp = LoadSave.LoadImage(LoadSave.VOLUME_BUTTONS);
		imgs = new BufferedImage[3];
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * 28, 0, 28, 44);

		slider = temp.getSubimage(3 * 28, 0, 215, 44);
		
	}

	public void update() {
		index = 0;
		if (mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;

	}

	public void draw(Graphics g) {

		g.drawImage(slider, x, y, width, height, null);
		g.drawImage(imgs[index], buttonX - 14, y, 28, height, null);

	}

	public void changeX(int x) {
		if (x - 14 < minX)
			buttonX = minX + 14;
		else if (x + 14 > maxX)
			buttonX = maxX - 14;
		else
			buttonX = x;
		updateFloatValue();
		updateDelay();
		bounds.x = buttonX - 14;

	}

	private void updateFloatValue() {
		float range = maxX - minX;
		float value = buttonX - minX;
		floatValue = value / range;
	}

	private void updateDelay(){
		DelayValue = (75000/buttonX)-113;
	}

	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
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

	public float getFloatValue() {
		return floatValue;
	}

	public int getDelayValue() {
		return DelayValue;
	}
}
