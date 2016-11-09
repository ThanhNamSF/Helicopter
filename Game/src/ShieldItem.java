import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;

public class ShieldItem extends Item {
	
	private static ImageIcon iiShield1 = new ImageIcon("shield1.png");
	private static ImageIcon iiShield2 = new ImageIcon("shield2.png");
	private static ImageIcon iiShield3 = new ImageIcon("shield3.png");
	private static ImageIcon iiShield4 = new ImageIcon("shield4.png");
	private static ImageIcon iiShield5 = new ImageIcon("shield5.png");
	private static ImageIcon iiShield6 = new ImageIcon("shield6.png");
	private static ImageIcon iiShield7 = new ImageIcon("shield7.png");
	private static ImageIcon iiShield8 = new ImageIcon("shield8.png");
	
	private static Image shiled1 = iiShield1.getImage().getScaledInstance(36, 33, Image.SCALE_SMOOTH);
	private static Image shiled2 = iiShield2.getImage().getScaledInstance(36, 33, Image.SCALE_SMOOTH);
	private static Image shiled3 = iiShield3.getImage().getScaledInstance(36, 33, Image.SCALE_SMOOTH);
	private static Image shiled4 = iiShield4.getImage().getScaledInstance(36, 33, Image.SCALE_SMOOTH);
	private static Image shiled5 = iiShield5.getImage().getScaledInstance(36, 33, Image.SCALE_SMOOTH);
	private static Image shiled6 = iiShield6.getImage().getScaledInstance(36, 33, Image.SCALE_SMOOTH);
	private static Image shiled7 = iiShield7.getImage().getScaledInstance(36, 33, Image.SCALE_SMOOTH);
	private static Image shiled8 = iiShield8.getImage().getScaledInstance(36, 33, Image.SCALE_SMOOTH);
	
	private Animation anim;
	private static Background bg = GamePlay.getBg1();
	
	public static Vector<ShieldItem> shieldItems = new Vector<ShieldItem>();

	public ShieldItem(int xPosition, int yPosition, int speedX, int speedY) {
		super(xPosition, yPosition, speedX, speedY);
		
		anim = new Animation();
		anim.addFrame(shiled1, 100);
		anim.addFrame(shiled2, 100);
		anim.addFrame(shiled3, 100);
		anim.addFrame(shiled4, 100);
		anim.addFrame(shiled5, 100);
		anim.addFrame(shiled6, 100);
		anim.addFrame(shiled7, 100);
		anim.addFrame(shiled8, 100);
		
		shieldItems.add(this);
	}

	@Override
	public void Update() {
		if(isVisible()){
			xPosition -= speedX;
			if(xPosition < -100)
				setVisible(false);
			anim.Update(20);
			rect.setBounds(xPosition, yPosition, 70, 63);
			OnCollision();
		}
		
	}
	
	public static void SpawnShieldItem(){
		if(shieldItems.size() > 0){
			for(ShieldItem i : shieldItems){
				if(i.isVisible() == false){
					i.setxPosition((int)(Math.random() * 10 + 41) * 20);
					i.setyPosition((int)(Math.random() * 16 + 3) * 20);
					i.setVisible(true);
					TimeSpawnItems.isSpawn = false;
					return;
				}
			}
		}
		ShieldItem i = new ShieldItem((int)(Math.random() * 10 + 41) * 20, (int)(Math.random() * 16 + 3) * 20, bg.getSpeedX() + 2, 0);
		TimeSpawnItems.isSpawn = false;
	}

	@Override
	public void OnCollision() {
		if(rect.intersects(Player.rect) && Player.isAlive){
			setVisible(false);
			xPosition = -100;
			rect.setBounds(xPosition, yPosition, 70, 63);
			ProtectShield.Spawn();
		}
	}

	public Animation getAnim() {
		return anim;
	}

	public void setAnim(Animation anim) {
		this.anim = anim;
	}

	public static Background getBg() {
		return bg;
	}

	public static void setBg(Background bg) {
		ShieldItem.bg = bg;
	}

}
