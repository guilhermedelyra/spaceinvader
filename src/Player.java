import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;


public class Player extends Sprite implements Commons {

    public static Vida vida;
    private final int START_Y = 710;
    private final int START_X = 400;

    private final String playerImg = "images/spaceship_thrust.png";


    public Player() {

        initPlayer();
    }

    private void initPlayer() {
        vida = new Vida(x, y);
        ImageIcon ii = new ImageIcon(playerImg);
        setImage(ii.getImage());
        setX(START_X);
        setY(START_Y);
        setWH();
    }

    public void act() {
        
        x += dx;
        y += dy;
        if (y <= 2) {
        	y = 2;
        }
        if (x <= 2) {
            x = 2;
        }
        
        if (x >= BOARD_WIDTH - 2 * width) {
            x = BOARD_WIDTH - 2 * width;
        }
        
        if (y >= BOARD_HEIGHT - 2 * height) {
            y = BOARD_HEIGHT - 2 * height;
        }
    }

    public void keyPressed(KeyEvent e) {
        
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            
            dy = 2;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
        
            dy = -2;
        }
        
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
        
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
        
            dx = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            
            dx = 0;
        }
        
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_W || key == KeyEvent.VK_S) {
            
            dy = 0;
        }

    }
    
    public Vida getVida() {
        
        return vida;
    }
    public class Vida extends Sprite {

        private final String bombImg = "images/heart.gif";

        public Vida(int x, int y) {

            initVida(x, y);
        }

        private void initVida(int x, int y) {
            this.x = x;
            this.y = y;
            ImageIcon ii = new ImageIcon(bombImg);
            setImage(ii.getImage());

        }
    }
}