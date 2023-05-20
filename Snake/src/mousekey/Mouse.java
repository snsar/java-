package src.mousekey;

import java.awt.event.MouseEvent;

import src.gamestate.Gamestate;
import src.main.GamePanel;

import java.awt.event.MouseAdapter;

public class Mouse extends MouseAdapter{
    private GamePanel gamePanel;
    public Mouse(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void mouseDragged(MouseEvent e){
        switch(Gamestate.state){
            case Menu:
            gamePanel.getGame().getMenu().mouseDragged(e);
                break;
            case Playing:
            gamePanel.getGame().getPlaying().mouseDragged(e);
                break;
            case Options:
            gamePanel.getGame().getGameOptions().mouseDragged(e);
                break;
            default:
                break;
        }
    }
    @Override
    public void mouseClicked(MouseEvent e){
        switch(Gamestate.state){
            case Menu:
            gamePanel.getGame().getMenu().mouseClicked(e);
                break;
            case Playing:
            gamePanel.getGame().getPlaying().mouseClicked(e);
                break;
            case Options:
            gamePanel.getGame().getGameOptions().mouseClicked(e);
                break;
            default:
                break;
        }
    }
    @Override
    public void mousePressed(MouseEvent e){
        switch(Gamestate.state){
            case Menu:
            gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case Playing:
            gamePanel.getGame().getPlaying().mousePressed(e);
                break;
            case Options:
            gamePanel.getGame().getGameOptions().mousePressed(e);
                break;
            default:
                break;
        }
    }
    @Override
    public void mouseReleased(MouseEvent e){
        switch(Gamestate.state){
            case Menu:
            gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            case Playing:
            gamePanel.getGame().getPlaying().mouseReleased(e);
                break;
            case Options:
            gamePanel.getGame().getGameOptions().mouseReleased(e);
                break;
            default:
                break;
        }
    }
    @Override
    public void mouseMoved(MouseEvent e){
        switch(Gamestate.state){
            case Menu:
            gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            case Playing:
            gamePanel.getGame().getPlaying().mouseMoved(e);
                break;
            case Options:
            gamePanel.getGame().getGameOptions().mouseMoved(e);
                break;
            default:
                break;
        }
    }
}
