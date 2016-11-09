import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;

public class SuperRocket{
	private int xPosition;
	private int yPosition;
	private static int speedX;
	private static int offset = 70;
	private static int wallUp = 60;
	private static int wallDown = 420;
	private int speedY = 1;
	private int maxHeight;
	private int minHeight;
	private boolean isVisible;
	private Rectangle rect;
	private int score;
	private static ImageIcon iiRocket = new ImageIcon("superRocket1.png");
	private static ImageIcon iiRocket2 = new ImageIcon("superRocket2.png");
	private static ImageIcon iiRocket3 = new ImageIcon("superRocket3.png");
	private static ImageIcon iiRocket4 = new ImageIcon("superRocket4.png");
	private static ImageIcon iiRocket5 = new ImageIcon("superRocket5.png");
	private static ImageIcon iiRocket6 = new ImageIcon("superRocket6.png");
	private static ImageIcon iiRocket7 = new ImageIcon("superRocket7.png");
	private static ImageIcon iiRocket8 = new ImageIcon("superRocket8.png");
	private static ImageIcon iiRocket9 = new ImageIcon("superRocket9.png");
	private static ImageIcon iiRocket10 = new ImageIcon("superRocket10.png");
	private static ImageIcon iiRocket11 = new ImageIcon("superRocket11.png");
	private static ImageIcon iiRocket12 = new ImageIcon("superRocket12.png");
	private Image rocket = iiRocket.getImage().getScaledInstance(78, 35, Image.SCALE_SMOOTH);
	private Image rocket2 = iiRocket2.getImage().getScaledInstance(78, 35, Image.SCALE_SMOOTH);
	private Image rocket3 = iiRocket3.getImage().getScaledInstance(78, 35, Image.SCALE_SMOOTH);
	private Image rocket4 = iiRocket4.getImage().getScaledInstance(78, 35, Image.SCALE_SMOOTH);
	private Image rocket5 = iiRocket5.getImage().getScaledInstance(78, 35, Image.SCALE_SMOOTH);
	private Image rocket6 = iiRocket6.getImage().getScaledInstance(78, 35, Image.SCALE_SMOOTH);
	private Image rocket7 = iiRocket7.getImage().getScaledInstance(78, 35, Image.SCALE_SMOOTH);
	private Image rocket8 = iiRocket8.getImage().getScaledInstance(78, 35, Image.SCALE_SMOOTH);
	private Image rocket9 = iiRocket9.getImage().getScaledInstance(78, 35, Image.SCALE_SMOOTH);
	private Image rocket10 = iiRocket10.getImage().getScaledInstance(78, 35, Image.SCALE_SMOOTH);
	private Image rocket11 = iiRocket11.getImage().getScaledInstance(78, 35, Image.SCALE_SMOOTH);
	private Image rocket12 = iiRocket12.getImage().getScaledInstance(78, 35, Image.SCALE_SMOOTH);
	private Animation superRocketAnim;
	private Background bg = GamePlay.getBg1();
	
//	public static ArrayList<SuperRocket> superRockets = new ArrayList<SuperRocket>();
	public static Vector<SuperRocket> superRockets = new Vector	<SuperRocket>();
	
	public SuperRocket(int x, int y){
		rect = new Rectangle();
		xPosition = x;
		yPosition = y;
		maxHeight = y + offset;
		minHeight = y - offset;
		score = 2;
		
		this.isVisible = true;
		superRocketAnim = new Animation();
		superRocketAnim.addFrame(rocket, 100);
		superRocketAnim.addFrame(rocket2, 100);
		superRocketAnim.addFrame(rocket3, 100);
		superRocketAnim.addFrame(rocket4, 100);
		superRocketAnim.addFrame(rocket5, 100);
		superRocketAnim.addFrame(rocket6, 100);
		superRocketAnim.addFrame(rocket7, 100);
		superRocketAnim.addFrame(rocket8, 100);
		superRocketAnim.addFrame(rocket9, 100);
		superRocketAnim.addFrame(rocket10, 100);
		superRocketAnim.addFrame(rocket11, 100);
		superRocketAnim.addFrame(rocket12, 100);
		speedX = bg.getSpeedX() + 2;
		
		superRockets.add(this);
	}
	
	public void Update(){
		if(this.isVisible){
			xPosition -= speedX;
			yPosition += speedY;
			if(yPosition <= minHeight || yPosition >= maxHeight || yPosition <= wallUp || yPosition >= wallDown ){
				speedY = -speedY;
			}
			superRocketAnim.Update(20);
			if(xPosition < Player.getxPosition() && Player.isAlive){
				GamePlay.score.Increase(score);
				score = 0;
			}
			if(xPosition < -30){
				this.isVisible = false;
			}
			rect.setBounds(xPosition + 5, yPosition + 3, 34, 18);
			OnCollision(Player.rect);
		}
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
	
	public static void SpawnSuperRocket(){
		if(superRockets.size() > 0){
			for(SuperRocket r : superRockets){
				if(r.isVisible == false){
					r.setxPosition((int)(Math.random() * 10 + 41) * 20);
					r.setyPosition((int)(Math.random() * 16 + 4) * 20);
					r.maxHeight = r.yPosition + offset;
					r.minHeight = r.yPosition - offset;
					r.score = 2;
					r.isVisible = true;
					TimeThread.isSpawn = false;
					return;
				}
			}
		}
		SuperRocket r = new SuperRocket((int)((Math.random() * 10 + 41) * 20), (int)((Math.random() * 16 + 4) * 20));
		TimeThread.isSpawn = false;
	}
	
	public static void Reset(){
		if(superRockets.size() > 0){
			for(SuperRocket r : superRockets){
				if(r.isVisible){
					r.isVisible = false;
					r.xPosition = -50;
				}
			}
		}
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

	public static void setSpeedX(int speedX) {
		SuperRocket.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Animation getSuperRocketAnim() {
		return superRocketAnim;
	}

	public void setSuperRocketAnim(Animation rocketAnim) {
		this.superRocketAnim = rocketAnim;
	}
}
