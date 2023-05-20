package src.utilz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadSave {

	public static final String PLAYER_ATLAS = "player_sprites.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public static final String LEVEL_ONE_DATA = "level_one_data.png";
	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String PAUSE_BACKGROUND = "pause_menu.png";
	public static final String SOUND_BUTTONS = "sound_button.png";
	public static final String URM_BUTTONS = "urm_buttons.png";
	public static final String SELECT_BUTTONS = "select_buttons.png";
	public static final String VOLUME_BUTTONS = "volume_buttons.png";
	public static final String MENU_BACKGROUND = "backgroundmenu.png";
	public static final String PLAY_BACKGROUND = "backgroundplay.png";
	public static final String OPTIONS_MENU = "options_background.png";
	public static final String DEATH_SCREEN = "death_screen.png";
	public static final String ball = "dot.png";
	public static final String apple = "myapple.png"; 
	public static final String head = "head.png";
	public static final String block = "block.png";
	public static final String ground = "ground.png";
	public static final String bapple = "myapple1.png";
	public static final String[] Imgmap = {"map1.png","map2.png","map3.png"};
	


	public static BufferedImage LoadImage(String fileName) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("Snake/res/" + fileName));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}
