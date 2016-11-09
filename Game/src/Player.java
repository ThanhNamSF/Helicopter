import java.awt.Rectangle;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Player {
	
	private static int xPosition = 100;
	private static int yPosition = 100;
	private int speedY = 1;
	private int speedX = 1;
	
	public static boolean isAlive = true;
	public static boolean isTapFly = false;
	public static boolean isShield = false;
	public static Rectangle rect = new Rectangle(0, 0, 0, 0);
	
	public Player(){
		isAlive = true;
		xPosition = 100;
		yPosition = 100; 
	}
	
	public void Update(){
		if(isAlive){
			yPosition += speedY;
			if(yPosition > 370)
				yPosition = 370;
			else if(yPosition < 58)
				yPosition = 58;
			rect.setBounds(xPosition + 5, yPosition, 38, 32);
		}
	}
	
	public void UpdateWin(){
		xPosition += speedX;
	}
	
	public void Shoot(){
		if(isAlive){
			PlayShootSound();
			Bullet.SpawnBullet(xPosition + 10, yPosition + 20);
		}
	}
	
//	public void Fall(){
//		yPosition += speedFall;
//	}

	public static int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public static int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

//	public int getSpeedFall() {
//		return speedFall;
//	}
//
//	public void setSpeedFall(int speedFall) {
//		this.speedFall = speedFall;
//	}

	public boolean isAlive() {
		return isAlive;
	}

	public static boolean isTapFly() {
		return isTapFly;
	}

	public static void setTapFly(boolean isTapFly) {
		Player.isTapFly = isTapFly;
	}
	
	public static void Dead(){
		if(!GamePlay.isWin){
			isAlive = false;
			PlayDeadSound();
		}
	}
	
	public static void PlayDeadSound(){
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("explosion.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void PlayShootSound(){
		try{
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("Shoot.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
