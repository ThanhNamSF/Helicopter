import java.awt.Image;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;

public class PlaneEnemy extends Enemy {
	
	private boolean isVisible;
	private int score;
	private static ImageIcon iiEnemyPlane1 = new ImageIcon("enemyPlane1.png");
	private static ImageIcon iiEnemyPlane2 = new ImageIcon("enemyPlane2.png");
	private static ImageIcon iiEnemyPlane3 = new ImageIcon("enemyPlane3.png");
//	private static ImageIcon iiExplosion = new ImageIcon("Explosion1.png");
//	private static ImageIcon iiExplosion2 = new ImageIcon("Explosion2.png");
//	private static ImageIcon iiExplosion3 = new ImageIcon("Explosion3.png");
//	private static ImageIcon iiExplosion4 = new ImageIcon("Explosion4.png");
//	private static ImageIcon iiExplosion5 = new ImageIcon("Explosion5.png");
//	private static ImageIcon iiExplosion6 = new ImageIcon();
	private static Image enemyPlane = iiEnemyPlane1.getImage().getScaledInstance(44, 37, Image.SCALE_DEFAULT);
	private static Image enemyPlane2 = iiEnemyPlane2.getImage().getScaledInstance(44, 37, Image.SCALE_DEFAULT);
	private static Image enemyPlane3 = iiEnemyPlane3.getImage().getScaledInstance(44, 37, Image.SCALE_DEFAULT);
//	private static Image explosion = iiExplosion.getImage().getScaledInstance(59, 59, Image.SCALE_DEFAULT);
//	private static Image explosion2 = iiExplosion2.getImage().getScaledInstance(59, 59, Image.SCALE_DEFAULT);
//	private static Image explosion3 = iiExplosion3.getImage().getScaledInstance(59, 59, Image.SCALE_DEFAULT);
//	private static Image explosion4 = iiExplosion4.getImage().getScaledInstance(59, 59, Image.SCALE_DEFAULT);
//	private static Image explosion5 = iiExplosion5.getImage().getScaledInstance(59, 59, Image.SCALE_DEFAULT);
//	private static Image explosion6 = iiExplosion6.getImage();
	private Animation enemyPlaneAnim, explosionAnim;
	private boolean isKilled;
	private static Background bg = GamePlay.getBg1();
	
//	public static ArrayList<PlaneEnemy> planeEnemies = new ArrayList<PlaneEnemy>();
	public static Vector<PlaneEnemy> planeEnemies = new Vector<PlaneEnemy>();
	
	public PlaneEnemy(int startHealth, int speedX, int speedY, int xPos, int yPos) {
		super(startHealth, speedX, speedY, xPos, yPos);
		isVisible = true;
		score = 2;
		
		enemyPlaneAnim = new Animation();
		enemyPlaneAnim.addFrame(enemyPlane, 100);
		enemyPlaneAnim.addFrame(enemyPlane2, 100);
		enemyPlaneAnim.addFrame(enemyPlane3, 100);
		
		explosionAnim = new Animation();
		explosionAnim.addFrame(explosion, 100);
		explosionAnim.addFrame(explosion2, 100);
		explosionAnim.addFrame(explosion3, 100);
		explosionAnim.addFrame(explosion4, 100);
		explosionAnim.addFrame(explosion5, 100);
		explosionAnim.addFrame(explosion6, 100);
		planeEnemies.add(this);
	}

	@Override
	public void Update() {
		if(this.isVisible){
			xPosition -= speedX;
			enemyPlaneAnim.Update(20);
			if(xPosition < -70){
				this.isVisible = false;
			}
			rect.setBounds(xPosition + 5, yPosition, 38, 32);
			OnCollision();
		}
		else{
			if(explosionAnim.getCurrentFrame() == 5){
				explosionAnim.setFinish(true);
				this.xPosition = -70;	
			}
			explosionAnim.Update(10);
		}
	}
	
	public void ResetExposionAnim(){
		explosionAnim.setFinish(false);
		explosionAnim.setCurrentFrame(0);
		explosionAnim.setAnimTime(0);
	}
	
	public Animation getExplosionAnim() {
		return explosionAnim;
	}

	public void setExplosionAnim(Animation explosionAnim) {
		this.explosionAnim = explosionAnim;
	}

	public static void SpawPlaneEnemy(){
		if(planeEnemies.size() > 0){
			for(PlaneEnemy p : planeEnemies){
				if(p.isVisible == false && p.getExplosionAnim().isFinish()){
					p.ResetExposionAnim();
					p.setxPosition((int)(Math.random() * 10 + 41) * 20);
					p.setyPosition((int)(Math.random() * 16 + 3) * 20);
					p.score = 2;
					p.isVisible = true;
					TimeThread.isSpawn = false;
					return;
				}
			}
		}
		PlaneEnemy p = new PlaneEnemy(1, bg.getSpeedX() + 2, 0, (int)(Math.random() * 10 + 41) * 20, (int)(Math.random() * 16 + 3) * 20);
		TimeThread.isSpawn = false;
	}
	
	public static void Reset(){
		if(planeEnemies.size() > 0){
			for(PlaneEnemy p : planeEnemies){
				if(p.isVisible){
					p.isVisible = false;
					p.xPosition = -50;
				}
			}
		}
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

//	public int getPassScore() {
//		return passScore;
//	}
//
//	public void setPassScore(int passScore) {
//		this.passScore = passScore;
//	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Animation getEnemyPlaneAnim() {
		return enemyPlaneAnim;
	}

	public void setEnemyPlaneAnim(Animation enemyPlaneAnim) {
		this.enemyPlaneAnim = enemyPlaneAnim;
	}

	@Override
	public void Dead() {
		PlaySound();
		GamePlay.score.Increase(score);
		score = 0;
		this.isVisible = false;
	}

	@Override
	public void OnCollision() {
		if(this.isVisible){
			if(ProtectShield.Instance != null){
				if(ProtectShield.Instance.getRect().intersects(rect)){
					Dead();
					return;
				}
			}
			if(Player.rect.intersects(rect) && Player.isAlive){
				Player.Dead();
				this.isVisible = false;
			}
			if(Bullet.bullets.size() > 0){
				for(Bullet b : Bullet.bullets){
					if(b.isVisible()){
						if(b.getRect().intersects(rect) && Player.isAlive){
							TakeDamage(Bullet.damage);
							b.Remove();
						}
					}
				}
			}
		}
	}

	@Override
	public void TakeDamage(int damage) {
		currentHealth -= damage;
		if(currentHealth <= 0)
			Dead();
	}

}
