import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

public class Rocket {
	private int xPosition;
	private int yPosition;
	private static int speedX;
	private boolean isVisible;
	private Rectangle rect;
	private int score;
	private static ImageIcon iiRocket = new ImageIcon("rocket1.png");
	private static ImageIcon iiRocket2 = new ImageIcon("rocket2.png");
	private Image rocket = iiRocket.getImage();
	private Image rocket2 = iiRocket2.getImage();
	private Animation rocketAnim;
	private Background bg = GamePlay.getBg1();
	
//	public static ArrayList<Rocket> rockets = new ArrayList<Rocket>();
	public static Vector<Rocket> rockets = new Vector<Rocket>();
	
	
	public Rocket(int x, int y){
		rect = new Rectangle();
		xPosition = x;
		yPosition = y;
		score = 1;
		this.isVisible = true;
		rocketAnim = new Animation();
		rocketAnim.addFrame(rocket, 100);
		rocketAnim.addFrame(rocket2, 100);
		speedX = bg.getSpeedX() + 3;
		rockets.add(this);
	}
	
	public void Update(){
		if(this.isVisible){
			xPosition -= speedX;
			rocketAnim.Update(20);
			if(xPosition < Player.getxPosition() && Player.isAlive){
				GamePlay.score.Increase(score);
				score = 0;
			}
			if(xPosition < -20){
				this.isVisible = false;
				score = 1;
			}
			rect.setBounds(xPosition + 5, yPosition + 3, 34, 18);
			OnCollision(Player.rect);
		}
	}

	public static void SpawnRocket(){
		if(rockets.size() > 0){
			for(Rocket r : rockets){
				if(r.isVisible == false){
					r.setxPosition((int)(Math.random() * 10 + 41) * 20);
					r.setyPosition((int)(Math.random() * 16 + 3) * 20);
					r.isVisible = true;
					TimeThread.isSpawn = false;
					return;
				}
			}
		}
		Rocket r = new Rocket((int)((Math.random() * 10 + 41) * 20), (int)((Math.random() * 16 + 3) * 20));
		TimeThread.isSpawn = false;
	}
	
	public void OnCollision(Rectangle player){
		if(this.isVisible){
			if(ProtectShield.Instance != null){
				if(ProtectShield.Instance.getRect().intersects(rect)){
					this.xPosition = -20;
					this.isVisible = false;
					GamePlay.score.Increase(score);
					return;
				}
			}
			if(player.intersects(rect) && Player.isAlive){
				Player.Dead();
				this.xPosition = -20;
				this.isVisible = false;
			}
		}
	}
	
	public Animation getRocketAnim() {
		return rocketAnim;
	}

	public void setRocketAnim(Animation rocketAnim) {
		this.rocketAnim = rocketAnim;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

//	public static ArrayList<Rocket> getRockets() {
//		return rockets;
//	}
//
//	public static void setRockets(ArrayList<Rocket> rockets) {
//		Rocket.rockets = rockets;
//	}
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
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

	public static int getSpeedX() {
		return speedX;
	}

	public static void setSpeedX(int speed) {
		speedX = speed;
	}
	
	public static void ResetRocket(){
		if(rockets.size() > 0){
			for(Rocket r : rockets){
				if(r.isVisible){
					r.isVisible = false;
					r.xPosition = -20;
				}
			}
		}
	}
}
