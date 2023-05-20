package src.gamestate;

import src.ui.AudioPlayer;
import java.awt.event.MouseEvent;
import src.ui.MenuButton;
import src.main.Game;

public class State {
    protected Game game;
    public State(Game game){
        this.game = game;
    }

    public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
	}
	
    public Game getGame(){
        return game;
    }

    public void setGamestate(Gamestate state) {
		switch (state) {
		case Menu -> game.getAudioPlayer().playSong(AudioPlayer.MENU);
		case Playing -> game.getAudioPlayer().playSong(AudioPlayer.BackGround);
		}

		Gamestate.state = state;
	}
}
