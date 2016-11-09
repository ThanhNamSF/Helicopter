import java.awt.Rectangle;
import java.util.Vector;

public class enemyBullet {
	
	private int xPosition, yPosition, speedX;
	private boolean isVisible;
	private Rectangle rect;
	
	public static int damage = 1;
	public static Vector<enemyBullet> enemyBullets = new Vector<enemyBullet>();
	
	public enemyBullet(int xStart, int yStart){
		xPosition = xStart;
		yPosition = yStart;
		isVisible = true;
		speedX = 8;
		rect = new Rectangle(0, 0, 0, 0);
		enemyBullets.add(this);
	}
	
	public void Update(){
		if(this.isVisible){
			xPosition -= speedX;
			if(xPosition < -20){
				this.yPosition = 0;
				this.isVisible = false;
			}
			rect.setBounds(xPosition, yPosition, 15, 5);
			OnCollision();
		}
	}
	
	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public static void SpawnBullet(int xPos, int yPos){
		if(enemyBullets.size() > 0){
			for(enemyBullet b : enemyBullets){
				if(b.isVisible == false){
					b.setxPosition(xPos);
					b.setyPosition(yPos);
					b.isVisible = true;
					return;
				}
			}
		}
		enemyBullet b = new enemyBullet(xPos, yPos);
	}
	
	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

//	public static ArrayList<enemyBullet> getEnemyBullets() {
//		return enemyBullets;
//	}
//
//	public static void setEnemyBullets(ArrayList<enemyBullet> enemyBullets) {
//		enemyBullet.enemyBullets = enemyBullets;
//	}

	public static int getDamage() {
		return damage;
	}

	public static void setDamage(int damage) {
		enemyBullet.damage = damage;
	}

	public static void Reset(){
		if(enemyBullets.size() > 0){
			for(enemyBullet b : enemyBullets){
				if(b.isVisible){
					b.isVisible = false;
					b.yPosition = 0;
				}
			}
		}
	}
	
	public void Remove(){
		this.isVisible = false;
		this.xPosition = -20;
		rect.setBounds(xPosition, yPosition, 15, 5);
	}
	
	public void OnCollision(){
		if(this.isVisible){
			if(ProtectShield.Instance != null){
				if(ProtectShield.Instance.getRect().intersects(rect)){
					Remove();
					return;
				}
			}
			if(rect.intersects(Player.rect) && Player.isAlive){
				Player.Dead();
				Remove();
			}
		}
	}

}
