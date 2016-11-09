import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public abstract class Enemy {
	
	protected int currentHealth;
	protected int speedX, speedY;
	protected int xPosition, yPosition;
	protected Rectangle rect;
	private static ImageIcon iiExplosion = new ImageIcon("Explosion1.png");
	private static ImageIcon iiExplosion2 = new ImageIcon("Explosion2.png");
	private static ImageIcon iiExplosion3 = new ImageIcon("Explosion3.png");
	private static ImageIcon iiExplosion4 = new ImageIcon("Explosion4.png");
	private static ImageIcon iiExplosion5 = new ImageIcon("Explosion5.png");
	private static ImageIcon iiExplosion6 = new ImageIcon();
	protected static Image explosion = iiExplosion.getImage().getScaledInstance(59, 59, Image.SCALE_DEFAULT);
	protected static Image explosion2 = iiExplosion2.getImage().getScaledInstance(59, 59, Image.SCALE_DEFAULT);
	protected static Image explosion3 = iiExplosion3.getImage().getScaledInstance(59, 59, Image.SCALE_DEFAULT);
	protected static Image explosion4 = iiExplosion4.getImage().getScaledInstance(59, 59, Image.SCALE_DEFAULT);
	protected static Image explosion5 = iiExplosion5.getImage().getScaledInstance(59, 59, Image.SCALE_DEFAULT);
	protected static Image explosion6 = iiExplosion6.getImage();
	
	public Enemy(int startHealth, int speedX, int speedY, int xPos, int yPos){
		currentHealth = startHealth;
		this.speedX = speedX;
		this.speedY = speedY;
		this.xPosition = xPos;
		this.yPosition = yPos;
		rect = new Rectangle(0, 0, 0, 0);
	}
	
	public void PlaySound(){
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("explosion.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public abstract void Update();
	public abstract void Dead();
	public abstract void OnCollision();
	public abstract void TakeDamage(int damage);

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
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

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
}
