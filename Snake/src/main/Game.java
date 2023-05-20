package src.main;

import java.awt.Graphics;
import src.ui.AudioPlayer;
import src.gamestate.GameOptions;
import src.gamestate.Gamestate;
import src.gamestate.Menu;
import src.gamestate.Playing;
import src.ui.AudioOptions;
import src.ui.Gameover;

public class Game implements Runnable {
    private Thread gameThread;
    private final int FPS_SET = 120;
	private final int UPS_SET = 200;
    public final static float SCALE = 2f;
    private GameWindow gamewindow;
    private GamePanel gamepanel;
    private Menu menu;
    private Playing playing;
    private GameOptions gameOptions;
    private Gameover gameover;
    private AudioOptions audioOptions;
    private AudioPlayer audioPlayer;
    public Game(){
        initclass();
        gamepanel = new GamePanel(this);
        gamewindow = new GameWindow(gamepanel);
        gamepanel.requestFocus();
        startGameLoop();
        update();
    }
    private void initclass(){
        audioOptions = new AudioOptions(this);
        audioPlayer = new AudioPlayer();
        menu = new Menu(this);
        playing = new Playing(this);
        gameOptions = new GameOptions(this);
    }
    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void update(){
        switch(Gamestate.state){
            case Menu:
            menu.update();
                break;
            case Playing:
            playing.update();
                break;
            case Gameover:
            gameover.update();
                break;
            case Options:
            gameOptions.update();
                break;
            case Quit:
                System.exit(0);
                break;
            default:
                break;
        }
    }
    public void render(Graphics g){
        switch(Gamestate.state){
            case Menu:
            menu.draw(g);
                break;
            case Playing:
            playing.draw(g);
                break;
            case Gameover:
            gameover.draw(g);
                break;
            case Options:
            gameOptions.draw(g);
                break;
            default:
                break;
        }
    }
    public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				gamepanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;

			}
		}

	}
    public Menu getMenu(){
        return menu;
    }
    public Playing getPlaying(){
        return playing;
    }
    public Gameover getGameover(){
        return gameover;
    }
    public GameOptions getGameOptions(){
        return gameOptions;
    }
    public AudioOptions getAudioOptions() {
        return audioOptions;
    }
    public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}
}
