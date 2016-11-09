import java.awt.Image;
import java.awt.Rectangle;

public class Tile {
	
	private int xTile, yTile, type;
	private Background bg = GamePlay.getBg1();
	private Rectangle rect;
	private static int speedX = 1;
	
	public Image tileImage;
	
	public Tile(int x, int y, int typeInt){
		rect = new Rectangle();
		xTile = x * 20;
		yTile = y * 20;
		
		type = typeInt;
		
		if(type == 1){
			tileImage = GamePlay.rockDown;
		}
		else if(type == 2){
			tileImage = GamePlay.rockUp;
		}
	}
	
	public void Update(){
		xTile -= speedX;
		rect.setBounds(xTile, yTile, 30, 60);
		if(xTile < -20)
			xTile = 40 * 20;
		OnCollision(Player.rect);
	}
	
	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public void OnCollision(Rectangle player){
		if(Player.isShield == false){
			if(player.intersects(rect) && Player.isAlive){
				Player.Dead();
			}
		}
	}

	public int getxTile() {
		return xTile;
	}

	public void setxTile(int xTile) {
		this.xTile = xTile;
	}

	public int getyTile() {
		return yTile;
	}

	public void setyTile(int yTile) {
		this.yTile = yTile;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Background getBg() {
		return bg;
	}

	public void setBg(Background bg) {
		this.bg = bg;
	}

	public Image getTileImage() {
		return tileImage;
	}

	public void setTileImage(Image tileImage) {
		this.tileImage = tileImage;
	}
	

}
