import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements Runnable, Commons {

	private Dimension d;
    private ArrayList<Alien> aliens;
    private Player player;
    private Shot shot;
    private Image background;
    private ImageIcon pleyer = new ImageIcon("images/spaceship_thrust.png");
    private ImageIcon aice = new ImageIcon("images/ice.png");
    private int ALIEN_INIT_X = 150;
    private int ALIEN_INIT_Y = 5;
    private int direction = -1;
    private int deaths = 0;
    private int delay = 0;
    private int DELAY = 15;
    private int vel = 1;
    private int row = 1;
    private int col = 1;
    private int level = 0;
    private int nivel = 1;
    private int velo = 0;
    private int vidas = 3;
    private int vidasA = 0;
    private int ue = 0;
    private int NUMBER_OF_ALIENS_TO_DESTROY = row * col;
    private int score = 0;
    private int gravidade = 0;
    private long lastSlow;
    private long slowInterval = 10000;
    private long ncubos = 0;
    private boolean alienlife = false;
    private boolean ingame = true;
    private boolean slow = false;
    private boolean bololohaha = false;
    private final String explImg = "images/explosion.png";

    private String message = "Game Over";

    private Thread animator;

    public Board() {
    		initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    	if(bololohaha == true)
	        gameInit();
    	ImageIcon image = new ImageIcon("images/space.jpg");
    	this.background = image.getImage(); 

    }
    @Override
    public void addNotify() {

        super.addNotify();
        gameInit();
    }
    public void spawnHeart(){
    	for(int i = 0; i < 3; ++i){
    		Player.Vida life = player.getVida();
    		
    	}
    }
    public void spawnAliens(){
    	for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
            	Alien xis = new Alien(0, 0, level);
                Alien alien = new Alien(ALIEN_INIT_X + (xis.width + 25) * j, ALIEN_INIT_Y + (xis.height + 25) * i, level);
                aliens.add(alien);
            }
        }
    }
    public void gameInit() {
        aliens = new ArrayList<>();
        player = new Player();
        shot = new Shot();
        spawnHeart();
        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void drawAliens(Graphics g) {

        Iterator it = aliens.iterator();

        for (Alien alien: aliens) {

            if (alien.isVisible()) {

                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {

                alien.die();
            }
        }
    }

    public void drawPlayer(Graphics g) {

        if (player.isVisible()) {
            
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {

            player.die();
            ingame = false;
        }
    }

    public void drawShot(Graphics g) {

        if (shot.isVisible()) {
            
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    public void drawHeart(Graphics g){
    	for(int i = 0; i < vidas; ++i){
       		
    		Player.Vida x = player.getVida();
    		g.drawImage(x.getImage(), 785 - 25 * i, 753, this);
       	
    	}
    }
    public void drawBombing(Graphics g) {

        for (Alien a : aliens) {
            
            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {
                
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }
    
    public void drawIce(Graphics g, int i) {
    	g.drawImage(aice.getImage(), 330 + i * 15, 753, this);
    }
    private void showIntroScreen(Graphics g) {

        g.setColor(new Color(0, 32, 48));
        g.fillRect(300, 250, 200, 200);
        g.setColor(Color.white);
        g.drawRect(300, 250, 200, 200);

        String s = "SELECT LEVEL";
        String p = "Press:";
        String es = "E - for EASY";
        String med = "M - for MEDIUM";
        String har = "H - for HARD";
        Font small = new Font("Arial", Font.ROMAN_BASELINE, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(s, (800 - metr.stringWidth(s)) / 2, 280);
        g.drawString(p, (800 - metr.stringWidth(p)) / 2, 300);
        g.drawString(es, (800 - metr.stringWidth(es)) / 2, 350);
        g.drawString(med, (800 - metr.stringWidth(med)) / 2, 380);
        g.drawString(har, (800 - metr.stringWidth(har)) / 2, 410);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background,0,0,null);
        g.setColor(Color.green);
        if(bololohaha == true){
	        if (ingame) {
	
	            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
	            drawAliens(g);
	            drawPlayer(g);
	            drawShot(g);
	            drawBombing(g);
	            drawHeart(g);
	            Font small = new Font("ArialBlack", Font.BOLD, 14);
	            g.setFont(small);
	            g.drawString("SCORE " + score, 10, 764);
	            g.drawString("LEVEL " + (level-1), 100, 764);
	            if(level >= 4){
	            	g.drawString("BOSS LIFE " + vidasA, 175, 764);
	            }

		        if(slow == false){
		        	long x = System.currentTimeMillis() - lastSlow;
		            if(x <= slowInterval){
		            	if(x >= 900+(1000*ue) && x <= 1200 + (1000*ue)){
		            		ue++;
		            		ncubos--;
		            	}
	           	 		for(int i = 0; i < ncubos; ++i){
	           	 			drawIce(g, i);
	           	 		}
		        	} else {
		        		ue = 0;
		        		ncubos = 10;
		        	}
	        	}
	        }
        } else {
        	showIntroScreen(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    public void gameOver() {

        Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);
    }

    public void animationCycle() {


    	switch(level){
        case 0:
	    		col += 2;
	        	row++;		        	
	        	aliens.clear();
	        	deaths = 0;
	        	NUMBER_OF_ALIENS_TO_DESTROY = col * row;
	        	spawnAliens();
	    		level++;
		    	break;
    	case 1: 
        		if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
        			player.setX(400);
		    		player.setY(400);
    				if(col + 2 <= 9){
		        		col += 2;
		        	} else			        	
		        	row++;	
		        	aliens.clear();
		        	deaths = 0;
		        	NUMBER_OF_ALIENS_TO_DESTROY = col * row;
		        	spawnAliens();
		    		level++;
			    }
	    		break;
    	case 2:
        		if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
        			player.setX(400);
		    		player.setY(400);
        			if(col + 2 <= 13){
		        		col += 2;
		        	}
		    		aliens.clear();
		        	deaths = 0;
		        	NUMBER_OF_ALIENS_TO_DESTROY = col*row;
		        	spawnAliens();
		    		level++;
        		}
		    	break;
    	case 3:
    			if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
    				player.setX(400);
		    		player.setY(400);
		        	aliens.clear();
		    		alienlife = true;	
		    		vidasA = 25 * nivel;
		        	velo = 1;
		        	vel = 3;
		        	gravidade = 10 * nivel;
		        	deaths = 0;
		        	spawnAliens();
		        	spawnAliens();
		    		level++;
		    		NUMBER_OF_ALIENS_TO_DESTROY = vidasA;
		    		
    			}
    			break;
    	case 4:
    			if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
    				player.setX(400);
		    		player.setY(400);
    				aliens.clear();
    				deaths = 0;
        			vidasA = 80 * (nivel - 1);
        			gravidade = 15 * nivel;
        			velo = 2;
        			vel = 5;
			        spawnAliens();
		    		level++;
		    		NUMBER_OF_ALIENS_TO_DESTROY = vidasA;

    			}
    			break;
    	case 5:
    			if(deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
	    				
	    			message = "GG WP";
	    			ingame = false;
    			}
    			break;
    }
        player.act();

        if (shot.isVisible()) {

            int shotX = shot.getX();
            int shotY = shot.getY();

            for (Alien alien: aliens) {

                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX) && shotX <= (alienX + alien.width) && shotY >= (alienY) 
                     && shotY <= (alienY + alien.height)) {
                    	
                    	long tmp = System.currentTimeMillis(); 
                        ImageIcon ii = new ImageIcon(explImg);
                        
                        if(alienlife == true){
                        	vidasA--;
                        	
                        	while(System.currentTimeMillis() - tmp <= 20){
                         		alien.setImage(ii.getImage());
                         	}
                         	Alien xis = new Alien(0, 0, level-1);
                        	alien.setImage(xis.getImage());
                        	if(vidasA == 0){
                        		alien.setImage(ii.getImage());
                            	alien.setDying(true);
                        	}
                        } else {
                        	alien.setImage(ii.getImage());
                        	alien.setDying(true);
                        }
                        
                        if(level <= 2)
                        	score += 20;
                        if(score % 200 == 0){
                        	vidas++;
                        }
                        deaths++;
                        shot.die();
                    }
                }
            }

            int y = shot.getY();
            y -= 4;

            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        for (Alien alien: aliens) {

            int x = alien.getX();

            if (x + alien.width + velo >= BOARD_WIDTH - BORDER_RIGHT && direction != -1 - velo) {

                direction = -1 - velo;
                Iterator i1 = aliens.iterator();

                while (i1.hasNext()) {

                    Alien a2 = (Alien) i1.next();
                    a2.setY(a2.getY() + GO_DOWN + gravidade);
                }
            }

            if (x - velo <= BORDER_LEFT && direction != 1 + velo) {

                direction = 1 + velo;

                Iterator i2 = aliens.iterator();

                while (i2.hasNext()) {

                    Alien a = (Alien) i2.next();
                    a.setY(a.getY() + GO_DOWN + gravidade);
                }
            }
        }

        Iterator it = aliens.iterator();

        while (it.hasNext()) {
            
            Alien alien = (Alien) it.next();
            
            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > GROUND - alien.height) {
                    ingame = false;
                    message = "Invasion!";
                }

                alien.act(direction);
            }
        }

        Rectangle r3 = player.getBounds();
        for (Alien alien: aliens) {
        	Rectangle r2 = alien.getBounds();

            if (r3.intersects(r2) && alien.isVisible()) {
            	 long tmp = System.currentTimeMillis(); 
            	 vidas--;  	 
            	 ImageIcon ii = new ImageIcon(explImg);
            	 
            	 if(alienlife == true){
                 	vidasA--;
                 	while(System.currentTimeMillis() - tmp <= 20){
                 		alien.setImage(ii.getImage());
                 	}
                 	Alien xis = new Alien(0, 0, level);
                	alien.setImage(xis.getImage());
                 	if(vidasA == 0){
                 		alien.setImage(ii.getImage());
                     	alien.setDying(true);
                 	}
                 } else {
                 	alien.setImage(ii.getImage());
                 	alien.setDying(true);
                 }
            	 
            	 while(System.currentTimeMillis() - tmp <= 20){
                	 player.setImage(ii.getImage());
            	 }
            	 player.setImage(pleyer.getImage());
                 deaths++;
                 if(vidas == 0){
                	player.setImage(ii.getImage());
                 	player.setDying(true);
                 }
            }
        	
        }

        // bombs
        Random generator = new Random();

        for (Alien alien: aliens) {

            int kk = generator.nextInt(15);
            Alien.Bomb b = alien.getBomb();
            if (kk == CHANCE && alien.isVisible() && b.isDestroyed()) {

                b.setDestroyed(false);
                b.setX(alien.getX() + alien.width / 2);
                b.setY(alien.getY() + alien.height);
            }


            int bombX = b.getX();
            int bombY = b.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !b.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + player.width)
                        && bombY >= (playerY)
                        && bombY <= (playerY + player.height)) {
                    ImageIcon ii = new ImageIcon(explImg);
                    vidas--;
                    long tmp = System.currentTimeMillis();
               	 	while(System.currentTimeMillis() - tmp <= 100){
               		 	player.setImage(ii.getImage());
               	 	}
               	 	player.setImage(pleyer.getImage());
                    if(vidas == 0){
                    	player.setImage(ii.getImage());
                    	player.setDying(true);
                    }
                    b.setDestroyed(true);
                }
            }

            if (!b.isDestroyed()) {
                
                b.setY(b.getY() + vel);
                
                if (b.getY() >= GROUND - BOMB_HEIGHT) {
                    b.setDestroyed(true);
                }
            }
            
            
        }
    }

    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (ingame) {
            repaint();
            animationCycle();
            if(slow){
            	delay = 15;
            } else {
            	delay = 0;
            }
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = (DELAY + delay) - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            
            beforeTime = System.currentTimeMillis();
        }

        gameOver();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_SHIFT || key == KeyEvent.VK_Z){
            	slow = false;
            	lastSlow = System.currentTimeMillis();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();
            long beforeTime, timeDiff, sleep;
            beforeTime = System.currentTimeMillis();
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {
                
                if (ingame) {
                    if (!shot.isVisible()) {
                        shot = new Shot(x, y);
                    }
                }
            }
            
           if(key == KeyEvent.VK_SHIFT || key == KeyEvent.VK_Z){
        	   if (System.currentTimeMillis() - lastSlow > slowInterval) {
        		   slow = true;
        		   ue = 0;
        		   ncubos = 10;
        		   lastSlow = System.currentTimeMillis();
        	   }
           }
           if(bololohaha == false){
	           if(key == KeyEvent.VK_E){
	        	   nivel = 1;
	        	   bololohaha = true;
	           }
	           
	           if(key == KeyEvent.VK_M){
	        	   nivel = 2;
	        	   DELAY = 13;
	        	   bololohaha = true;
	           }
	           
	           if(key == KeyEvent.VK_H){
	        	   nivel = 3;
	        	   DELAY = 10;
	        	   bololohaha = true;
	           }
           }
        }
        
    }
}