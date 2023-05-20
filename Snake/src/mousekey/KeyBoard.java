package src.mousekey;

import java.awt.event.KeyEvent;

import src.gamestate.Gamestate;
import src.main.GamePanel;

import java.awt.event.KeyAdapter;

public class KeyBoard extends KeyAdapter {
    private GamePanel gamePanel;
    public KeyBoard(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyPressed(KeyEvent e){
        switch(Gamestate.state){
            case Menu:
            gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case Playing:
            gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            case Options:
            gamePanel.getGame().getGameOptions().keyPressed(e);
                break;
            default:
                break;
        }

    }
    public void KeyReleased(KeyEvent e){
        switch(Gamestate.state){
            case Menu:
            gamePanel.getGame().getMenu().KeyReleased(e);
                break;
            case Playing:
            gamePanel.getGame().getPlaying().KeyReleased(e);
                break;
            case Options:
            gamePanel.getGame().getGameOptions().KeyReleased(e);
                break;
            default:
                break;
        }
    }
}
