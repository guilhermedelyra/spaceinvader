import javax.swing.ImageIcon;

public class Alien extends Sprite {

    public static Bomb bomb;
    public final String alienlvl0 = "images/alien_EASY.png";
    public final String alienlvl1 = "images/alien_MEDIUM.png";
    public final String alienlvl2 = "images/alien_HARD.png";
    public final String alienlvl3 = "images/estialenEhtoper.png";
    public final String alienlvl4 = "images/aliendivedade.png";
    public int i = 0;
    public Alien(int x, int y, int i) {
        initAlien(x, y, i);
    }

    private void initAlien(int x, int y, int i) {

        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);
        if(i == 0){
        	ImageIcon ii = new ImageIcon(alienlvl0);
        	setImage(ii.getImage());

        }
        if(i == 1){
        	ImageIcon ii = new ImageIcon(alienlvl1);
        	setImage(ii.getImage());

        }
        if(i == 2){
        	ImageIcon ii = new ImageIcon(alienlvl2);
        	setImage(ii.getImage());

        }
        if(i == 3){
        	ImageIcon ii = new ImageIcon(alienlvl3);
        	setImage(ii.getImage());
        }
        if(i == 4){
        	ImageIcon ii = new ImageIcon(alienlvl4);
        	setImage(ii.getImage());
        }
        setWH();
        
        
    }

    public void act(int direction) {
    	this.x += direction;
    }

    public Bomb getBomb() {
        
        return bomb;
    }
    
    
    public class Bomb extends Sprite {

        private final String bombImg = "images/coco.png";
        private boolean destroyed;

        public Bomb(int x, int y) {

            initBomb(x, y);
        }

        private void initBomb(int x, int y) {

            setDestroyed(true);
            this.x = x;
            this.y = y;
            ImageIcon ii = new ImageIcon(bombImg);
            setImage(ii.getImage());

        }

        public void setDestroyed(boolean destroyed) {
        
            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {
        
            return destroyed;
        }
    }
}