package src.ingame;

import java.util.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

import src.gamestate.Playing;
import src.ui.AudioPlayer;
import src.ui.Gameover;
import src.utilz.LoadSave;
import static src.gamestate.Playing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;


public class Player implements ActionListener{
    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    private final int DOT_SIZE = 20;
    private final int ALL_DOTS = 900;


    public static int DELAY = 60;
    private Timer timer;


    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;
    private int bapple_x = 9999;
    private int bapple_y = 9999;
    public static int score = 0;
    public static int high_score = 0;
    private int tile[][] = new int[B_HEIGHT/DOT_SIZE ][B_WIDTH/DOT_SIZE];
    private int startpos;
    public static int mapcode = 1;
    private int applecount;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    public static boolean inGame = true;
    private boolean newhs = false;
    private boolean teleport=false;
    private BufferedImage ball;
    private BufferedImage apple;
    private BufferedImage head;
    private Playing playing;
    private Gameover gameOver;
    private BufferedImage block;
    private BufferedImage ground;
    private BufferedImage bapple;
    public String[] str = {"map1", "map2" ,"map3"};

    public Player(Playing playing) {
        this.playing = playing;
        loadImages();
        initGame();
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public void loadImages() {

        ball = LoadSave.LoadImage(LoadSave.ball);
        apple = LoadSave.LoadImage(LoadSave.apple);
        head = LoadSave.LoadImage(LoadSave.head);
        block = LoadSave.LoadImage(LoadSave.block);
        ground = LoadSave.LoadImage(LoadSave.ground);
        bapple = LoadSave.LoadImage(LoadSave.bapple);
    }

    public void map(int code)
    {
    	File file = new File("Snake/res/map/"+str[code-1]+".txt");
        try
        {
        Scanner read = new Scanner(file);
        
        int m = read.nextInt();
        while(m != code)
        {
        	for(int i = 1;i <= B_HEIGHT/DOT_SIZE;i++)
        	{
        		read.nextLine();
        	}
        	m = read.nextInt();
        }
        
        startpos = read.nextInt();
        
        for(int i = 0;i <= (B_HEIGHT - DOT_SIZE)/DOT_SIZE;i++)
        	for(int j = 0;j <= (B_WIDTH - DOT_SIZE)/DOT_SIZE;j++)
        		tile[i][j] = read.nextInt();
        }
        catch (FileNotFoundException e)
        {
        	
        }
    }

    public void initGame() {
    	
        newhs = false;
        dots = 3;
        applecount = 0;
        
        map(mapcode);
        
        for(int i=0;i<B_HEIGHT;i+=DOT_SIZE)
        {
            cantUse.put(i,new HashMap<Integer,Boolean>());
        }
        for(int i=0;i<B_HEIGHT/DOT_SIZE;i++)
        {
        	for(int j=0;j<B_WIDTH/DOT_SIZE;j++)
        	{
        		if(tile[i][j]==1)
        		{
        			cantUse.get(i * DOT_SIZE).put(j * DOT_SIZE, true);
        		}
        	}
        }
        
        for(int i=0;i<B_HEIGHT;i+=DOT_SIZE)
        {
        	cantUse2.put(i, new HashMap<Integer,Boolean>());
        }
        
        for(int i=0;i<B_HEIGHT/DOT_SIZE-1;i++)
        { 	
        	for(int j=0;j<B_WIDTH/DOT_SIZE-1;j++)
        	{
        		if(tile[i][j]==1 || tile[i+1][j]==1 || tile[i][j+1]==1 || tile[i+1][j+1]==1)
        		{
        			cantUse2.get(i*DOT_SIZE).put(j*DOT_SIZE, true);
        		}
        	}
        }

        for (int z = 0; z < dots; z+=1) {
            x[z] = 400 - z * DOT_SIZE;
            y[z] = startpos;
            cantUse.get(y[z]).put(x[z],true);
        }
        leftDirection = false;
        rightDirection = true;
        upDirection = false;
        downDirection = false;

        locateApple();

    }

    public void update(){
    }
    public void draw(Graphics g) {

        for(int i = 0;i <= (B_HEIGHT - 20)/DOT_SIZE; i++) {
            for (int j = 0; j <= (B_WIDTH - 20) / DOT_SIZE; j++) {
                if (tile[i][j] == 1) {
                    g.drawImage(block, j * DOT_SIZE, i * DOT_SIZE, null);
                } else if (tile[i][j] == 0) {
                    g.drawImage(ground, j * DOT_SIZE, i * DOT_SIZE, null);
                }
            }
        }



        for (int z = 0; z < dots; z++) {
            if (z == 0) {
                g.drawImage(head, x[z], y[z], 20, 20, null);
            } else {
                g.drawImage(ball, x[z], y[z], 20, 20,null);
            }
        }
        g.drawImage(apple, apple_x, apple_y, 20, 20, null);
        g.drawImage(bapple,bapple_x,bapple_y,20, 20, null);
        g.setColor(Color.black);
        Font small = new Font("Helvetica", Font.BOLD, 24);
        g.setFont(small);
        g.drawString("Score: " + score,5,25);   
    }

    public void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            score ++;
            applecount++;
            dots++;
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.Hit);
            cantUse.get(y[dots-1]).put(x[dots-1],true);
            if (applecount%3 == 0)
            	locateBApple();
            else
            	locateApple();
        }
        else if ( (x[0] == bapple_x && y[0] == bapple_y) || (x[0] == bapple_x+DOT_SIZE && y[0] == bapple_y) || (x[0] == bapple_x && y[0] == bapple_y+DOT_SIZE) || (x[0] == bapple_x+DOT_SIZE && y[0] == bapple_y+DOT_SIZE))
        {
        	dots++;
            score +=2;
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.Hit);
        	locateApple();
        }
    }

    public void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
        cantUse.get(y[dots]).remove(x[dots]);


        if (leftDirection && !paused && !selectingame) {
            x[0] -= DOT_SIZE;
            if (x[0] < 0) {
                x[0] = B_WIDTH-DOT_SIZE;
                leftDirection = true;
                upDirection = false;
                downDirection = false;
                rightDirection=false;

            }

        }

        if (rightDirection && !paused && !selectingame) {
            x[0] += DOT_SIZE;
            if (x[0] > B_WIDTH-DOT_SIZE) {
                x[0] = 0;
                leftDirection = false;
                upDirection = false;
                downDirection = false;
                rightDirection=true;

            }
        }

        if (upDirection && !paused && !selectingame) {
            y[0] -= DOT_SIZE;
            if (y[0] < 0 ) {
                y[0] = B_HEIGHT-DOT_SIZE;
                leftDirection = false;
                upDirection = true;
                downDirection = false;
                rightDirection=false;

            }
        }

        if (downDirection && !paused && !selectingame) {
            y[0] += DOT_SIZE;
            if (y[0] > B_HEIGHT-DOT_SIZE) {
                y[0] = 0;
                leftDirection = false;
                upDirection = false;
                downDirection = true;
                rightDirection=false;

            }
        }
        cantUse.get(y[0]).put(x[0],true);
    }
    
    public void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if (((z > 3) && (x[0] == x[z]) && (y[0] == y[z]))||(tile[y[0] / DOT_SIZE] [ x[0] /DOT_SIZE ] == 1)) {
                inGame = false;
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
                if(score > high_score)
                {
                    high_score = score;
                    newhs = true;
                }
                timer.stop();
            }
        }
    }

    class pair
    {
        private int a, b;
        public int getA()
        {
            return this.a;
        }
        public int getB()
        {
            return this.b;
        }

        pair(int a,int b)
        {
            this.a=a;
            this.b=b;
        }
    }
    private ArrayList<pair>emptySlot=new ArrayList<pair>();
    private HashMap<Integer,HashMap<Integer,Boolean>>cantUse=new HashMap<Integer,HashMap<Integer,Boolean>>();
    private HashMap<Integer,HashMap<Integer,Boolean>>cantUse2=new HashMap<Integer,HashMap<Integer,Boolean>>();
    
    public void locateApple()
    {
    	bapple_x = 9999;
    	bapple_y = 9999;
        emptySlot.clear();
        // need to reset apple_x and apple_y
        for(int i=0;i<B_HEIGHT;i+=DOT_SIZE)
        {
            for(int j=0;j<B_WIDTH;j+=DOT_SIZE)
            {

                if(cantUse.get(i).containsKey(j))
                {
                    continue;
                }

                emptySlot.add(new pair(i,j));
            }
        }
        int nextAppleLocation=ThreadLocalRandom.current().nextInt(0,emptySlot.size());
        apple_y=emptySlot.get(nextAppleLocation).getA();
        apple_x=emptySlot.get(nextAppleLocation).getB();
    }
    
    private void locateBApple()
    {
    	apple_x = 9999;
    	apple_y = 9999;
    	emptySlot.clear();
    	for(int i=0;i<B_HEIGHT-DOT_SIZE;i+=DOT_SIZE)
        {
            for(int j=0;j<B_WIDTH-DOT_SIZE;j+=DOT_SIZE)
            {
                    if(cantUse2.get(i).containsKey(j))
                    {
                        continue;
                    }
                emptySlot.add(new pair(i,j));
            }
        }
    	if (emptySlot.isEmpty())
    	{
    		locateApple();
    	}
    	else
    	{
    		int nextAppleLocation=ThreadLocalRandom.current().nextInt(0,emptySlot.size());
    		bapple_y=emptySlot.get(nextAppleLocation).getA();
    		bapple_x=emptySlot.get(nextAppleLocation).getB();
    	}
    }
    
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_LEFT) && (!rightDirection) && (Math.abs(y[1]-y[0])>0)) 
        {
            leftDirection = true;
            upDirection = false;
            downDirection = false;
        }

        if ((key == KeyEvent.VK_RIGHT) && (!leftDirection) && (Math.abs(y[1]-y[0])>0)) {
            rightDirection = true;
            upDirection = false;
            downDirection = false;
        }

        if ((key == KeyEvent.VK_UP) && (!downDirection) && (Math.abs(x[1]-x[0])>0)) {
            upDirection = true;
            rightDirection = false;
            leftDirection = false;
        }

        if ( (key == KeyEvent.VK_DOWN) && (!upDirection) && ((Math.abs(x[1]-x[0])>0))) {
            downDirection = true;
            rightDirection = false;
            leftDirection = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkCollision();
        checkApple();
        move();

    }
}