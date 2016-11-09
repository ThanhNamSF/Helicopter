import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;

public class Bullet {
	private int xPosition, yPosition, speedX;
	private boolean isVisible;
	private Rectangle rect;
	private static ImageIcon iiHitExplosion1 = new ImageIcon("HitExplosion.png");
	private static ImageIcon iiHitExplosion2 = new ImageIcon("HitExplosion1.png");
	private static ImageIcon iiHitExplosion3 = new ImageIcon("HitExplosion2.png");
	private static ImageIcon iiHitExplosion4 = new ImageIcon("HitExplosion3.png");
	private static ImageIcon iiHitExplosion5 = new ImageIcon("HitExplosion4.png");
	private static ImageIcon iiHitExplosion6 = new ImageIcon("HitExplosion5.png");
	private static ImageIcon iiHitExplosion7 = new ImageIcon();
	
	private static Image hitExplosion1 = iiHitExplosion1.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	private static Image hitExplosion2 = iiHitExplosion2.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	private static Image hitExplosion3 = iiHitExplosion3.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	private static Image hitExplosion4 = iiHitExplosion4.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	private static Image hitExplosion5 = iiHitExplosion5.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	private static Image hitExplosion6 = iiHitExplosion6.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	private static Image hitExplosion7 = iiHitExplosion7.getImage();
	
	private Animation hitExplosionAnim;
	
	//public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public static Vector<Bullet> bullets = new Vector<Bullet>();
	public static int damage = 1;
	
	public Bullet(int xStart, int yStart){
		xPosition = xStart;
		yPosition = yStart;
		isVisible = true;
		speedX = 10;
		rect = new Rectangle(0, 0, 0, 0);
		
		hitExplosionAnim = new Animation();
		hitExplosionAnim.addFrame(hitExplosion1, 100);
		hitExplosionAnim.addFrame(hitExplosion2, 100);
		hitExplosionAnim.addFrame(hitExplosion3, 100);
		hitExplosionAnim.addFrame(hitExplosion4, 100);
		hitExplosionAnim.addFrame(hitExplosion5, 100);
		hitExplosionAnim.addFrame(hitExplosion6, 100);
		hitExplosionAnim.addFrame(hitExplosion7, 100);
		
		bullets.add(this);
	}
	
	public void Update(){
		if(this.isVisible){
			xPosition += speedX;
			if(xPosition > 820){
				this.yPosition = -20;
				this.isVisible = false;
			}
			rect.setBounds(xPosition, yPosition, 15, 5);
		}
		else{
			if(hitExplosionAnim.getCurrentFrame() == 6){
				hitExplosionAnim.setFinish(true);
				this.xPosition = -20;
			}
			hitExplosionAnim.Update(25);
		}
	}
	
	public void ResetHitExposionAnim(){
		hitExplosionAnim.setFinish(false);
		hitExplosionAnim.setCurrentFrame(0);
		hitExplosionAnim.setAnimTime(0);
	}
	
	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public static void SpawnBullet(int xPos, int yPos){
		if(bullets.size() > 0){
			for(Bullet b : bullets){
				if(b.isVisible == false){
					b.setxPosition(xPos);
					b.setyPosition(yPos);
					b.ResetHitExposionAnim();
					b.isVisible = true;
					return;
				}
			}
		}
		Bullet b = new Bullet(xPos, yPos);
	}
	
	public Animation getHitExplosionAnim() {
		return hitExplosionAnim;
	}

	public void setHitExplosionAnim(Animation hitExplosionAnim) {
		this.hitExplosionAnim = hitExplosionAnim;
	}

	public static void Reset(){
		if(bullets.size() > 0){
			for(Bullet b : bullets){
				if(b.isVisible){
					b.isVisible = false;
					b.yPosition = 0;
				}
			}
		}
	}
	
	public void Remove(){
		this.isVisible = false;
		//this.xPosition = -20;
		rect.setBounds(-100, -100, 15, 5);
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
}
