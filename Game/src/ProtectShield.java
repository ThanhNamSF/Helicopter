import java.awt.Image;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.ImageIcon;

public class ProtectShield {
	private int xPosition;
	private int yPosition;
	private Rectangle rect;
	private boolean isVisible;
	
	private static ImageIcon iiShield1 = new ImageIcon("shield1.png");
	private static ImageIcon iiShield2 = new ImageIcon("shield2.png");
	private static ImageIcon iiShield3 = new ImageIcon("shield3.png");
	private static ImageIcon iiShield4 = new ImageIcon("shield4.png");
	private static ImageIcon iiShield5 = new ImageIcon("shield5.png");
	private static ImageIcon iiShield6 = new ImageIcon("shield6.png");
	private static ImageIcon iiShield7 = new ImageIcon("shield7.png");
	private static ImageIcon iiShield8 = new ImageIcon("shield8.png");
	
	private static Image shiled1 = iiShield1.getImage().getScaledInstance(72, 66, Image.SCALE_SMOOTH);
	private static Image shiled2 = iiShield2.getImage().getScaledInstance(72, 66, Image.SCALE_SMOOTH);
	private static Image shiled3 = iiShield3.getImage().getScaledInstance(72, 66, Image.SCALE_SMOOTH);
	private static Image shiled4 = iiShield4.getImage().getScaledInstance(72, 66, Image.SCALE_SMOOTH);
	private static Image shiled5 = iiShield5.getImage().getScaledInstance(72, 66, Image.SCALE_SMOOTH);
	private static Image shiled6 = iiShield6.getImage().getScaledInstance(72, 66, Image.SCALE_SMOOTH);
	private static Image shiled7 = iiShield7.getImage().getScaledInstance(72, 66, Image.SCALE_SMOOTH);
	private static Image shiled8 = iiShield8.getImage().getScaledInstance(72, 66, Image.SCALE_SMOOTH);
	
	private Animation anim;
	private int currentTime;
	private int time;
	public static ProtectShield Instance = null;
	
	public ProtectShield(int xPosition, int yPosition, int time){
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		rect = new Rectangle(0, 0, 0, 0);
		this.time = time;
		currentTime = time;
		isVisible = true;
		
		anim = new Animation();
		anim.addFrame(shiled1, 100);
		anim.addFrame(shiled2, 100);
		anim.addFrame(shiled3, 100);
		anim.addFrame(shiled4, 100);
		anim.addFrame(shiled5, 100);
		anim.addFrame(shiled6, 100);
		anim.addFrame(shiled7, 100);
		anim.addFrame(shiled8, 100);
		
		Player.isShield = true;
	}

	public void Update(){
		if(isVisible){
			currentTime--;
			System.out.println(currentTime);
			xPosition = Player.getxPosition() - 11;
			yPosition = Player.getyPosition() - 13;
			anim.Update(20);
			if(currentTime <= 0){
				isVisible = false;
				xPosition = -100;
				yPosition = -100;
				Player.isShield = false;
			}
			rect.setBounds(xPosition, yPosition, 72, 66);
		}
	}
	
	public static void Spawn(){
		if(Instance != null){
			if(Instance.isVisible == false){
				Instance.currentTime = Instance.time;
				Instance.setxPosition(Player.getxPosition() - 11);
				Instance.setyPosition(Player.getyPosition() - 13);
				Instance.isVisible = true;
			}
			else{
				Instance.currentTime = Instance.time;
			}
			Player.isShield = true;
			return;
		}
		Instance = new ProtectShield(Player.getxPosition() - 11, Player.getyPosition() - 13, 600);
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
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

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Animation getAnim() {
		return anim;
	}

	public void setAnim(Animation anim) {
		this.anim = anim;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
}
