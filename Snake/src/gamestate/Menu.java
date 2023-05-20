package src.gamestate;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import src.main.Game;
import src.ui.AudioPlayer;
import src.ui.MenuButton;
import src.utilz.LoadSave;

public class Menu extends State implements Statemethod{
    
    private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage bg;

    public Menu(Game game){
        super(game);
        loadButtons();
		loadBackground();
    }
    
    private void loadBackground() {
		bg = LoadSave.LoadImage(LoadSave.MENU_BACKGROUND);
	}

    private void loadButtons() {
		buttons[0] = new MenuButton(525, 100, 0, Gamestate.Playing);
		buttons[1] = new MenuButton(525, 250, 1, Gamestate.Options);
		buttons[2] = new MenuButton(525, 400, 2, Gamestate.Quit);
	}

	@Override
    public void update(){
        for (MenuButton mb : buttons)
			mb.update();
    }

	@Override
    public void draw(Graphics g){

        g.drawImage(bg, 0, 0, 800, 600, null);

		for (MenuButton mb : buttons)
			mb.draw(g);
    }

	@Override
	public void mouseDragged(MouseEvent e){

	}

	@Override
    public void mouseClicked(MouseEvent e){
    }

    @Override
	public void mousePressed(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMousePressed(true);
				game.getAudioPlayer().playEffect(AudioPlayer.Click);
			}
		}

	}
    @Override
	public void mouseReleased(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				if (mb.isMousePressed())
					mb.applyGamestate();
				if (mb.getState() == Gamestate.Playing)
					game.getAudioPlayer().playSong(AudioPlayer.BackGround);
				break;
			}
		}

		resetButtons();

	}

	private void resetButtons() {
		for (MenuButton mb : buttons)
			mb.resetBools();

	}

    @Override
	public void mouseMoved(MouseEvent e) {
		for (MenuButton mb : buttons)
			mb.setMouseOver(false);

		for (MenuButton mb : buttons)
			if (isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}

	}

	@Override
    public void keyPressed(KeyEvent e){
        
    }

	@Override
    public void KeyReleased(KeyEvent e){
        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}