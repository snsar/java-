package src.gamestate;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import src.ingame.Player;
import static src.ingame.Player.*;
import src.main.Game;
import src.ui.AudioPlayer;
import src.ui.Gameover;
import src.ui.PauseOverlay;
import src.ui.Select;
import src.ui.SelectButton;
import src.utilz.LoadSave;
import java.awt.image.BufferedImage;

public class Playing extends State implements Statemethod {
    private PauseOverlay pauseOverlay;
    private Select select;
    private Gameover gameOver;
    private Player player;
	public static boolean paused = false;
    public static boolean selectingame = true;
    private BufferedImage bg;
    private AudioPlayer audioPlayer;

    public Playing(Game game){
        super(game);
        initClasses();
    }

    private void initClasses(){
        pauseOverlay = new PauseOverlay(this);
        select = new Select(this);
        gameOver = new Gameover(this);
        player = new Player(this);
        bg = LoadSave.LoadImage(LoadSave.PLAY_BACKGROUND);
    }

    @Override
    public void update() {
        if (paused && !selectingame) {
			pauseOverlay.update();
		} else if (!player.inGame){
            gameOver.update();
        } else if(selectingame){
            select.update();
        } else player.update();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!paused && !selectingame && player.inGame){
             player.actionPerformed(e);  
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(bg, 0, 0, 800, 600, null);
        if (paused && !selectingame)
			pauseOverlay.draw(g);
        else if (!player.inGame){
            gameOver.draw(g);
        } else if(selectingame){
            select.draw(g);
        }else player.draw(g);
    }

    @Override
    public void mouseDragged(MouseEvent e){
        if (paused && !selectingame){
			pauseOverlay.mouseDragged(e);
        } else if(selectingame){
            select.mouseDragged(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused && !selectingame) {
			pauseOverlay.mousePressed(e);
			game.getAudioPlayer().playEffect(AudioPlayer.Click);
        }
        else if (!player.inGame){
            gameOver.mousePressed(e);
            game.getAudioPlayer().playEffect(AudioPlayer.Click);
        }
        else if(selectingame){
            select.mousePressed(e);
            game.getAudioPlayer().playEffect(AudioPlayer.Click);
        }
}

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused && !selectingame)
			pauseOverlay.mouseReleased(e);
        else if (!player.inGame){
            gameOver.mouseReleased(e);
        }
        else if(selectingame){
            select.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused && !selectingame)
			pauseOverlay.mouseMoved(e);
        else if (!player.inGame){
            gameOver.mouseMoved(e);
        }
        else if(selectingame){
            select.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                if(player.inGame)
                    paused = !paused;
                break;
            case KeyEvent.VK_ENTER:
                player.inGame = !player.inGame;
                break;
        }
        if(player.inGame)
            player.keyPressed(e);
        gameOver.keyPressed(e);
    }

    @Override
    public void KeyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void unpauseGame() {
		paused = false;
	}

    public void resetAll() {
		paused = false;
        selectingame = true;
        player = new Player(this);
        score = 0;
        player.initGame();
	}
    public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}
    
}
