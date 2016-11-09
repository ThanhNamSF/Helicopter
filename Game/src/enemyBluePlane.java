import java.awt.Image;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;

public class enemyBluePlane extends Enemy{
	
	private boolean isVisible;
	private int score;
	private static ImageIcon iiBluePlaneEnemy1 = new ImageIcon("enemyBluePlane1.png");
	private static ImageIcon iiBluePlaneEnemy2 = new ImageIcon("enemyBluePlane2.png");
	private static ImageIcon iiBluePlaneEnemy3 = new ImageIcon("enemyBluePlane3.png");
	private static Image BluePlaneEnemy = iiBluePlaneEnemy1.getImage().getScaledInstance(44, 37, Image.SCALE_DEFAULT);
	private static Image BluePlaneEnemy2 = iiBluePlaneEnemy2.getImage().getScaledInstance(44, 37, Image.SCALE_DEFAULT);
	private static Image BluePlaneEnemy3 = iiBluePlaneEnemy3.getImage().getScaledInstance(44, 37, Image.SCALE_DEFAULT);
	private Animation bluePlaneEnemyAnim, explosionAnim;
	private boolean isKilled;
	private static Background bg = GamePlay.getBg1();
	private int timeShoot = 1000;
	
	//public static ArrayList<enemyBluePlane> planeBlueEnemies = new ArrayList<enemyBluePlane>();
	public static Vector<enemyBluePlane> planeBlueEnemies = new Vector<enemyBluePlane>();

	public enemyBluePlane(int startHealth, int speedX, int speedY, int xPos, int yPos) {
		super(startHealth, speedX, speedY, xPos, yPos);
		isVisible = true;
		score = 3;
		
		bluePlaneEnemyAnim = new Animation();
		bluePlaneEnemyAnim.addFrame(BluePlaneEnemy, 100);
		bluePlaneEnemyAnim.addFrame(BluePlaneEnemy2, 100);
		bluePlaneEnemyAnim.addFrame(BluePlaneEnemy3, 100);
		
		explosionAnim = new Animation();
		explosionAnim.addFrame(explosion, 100);
		explosionAnim.addFrame(explosion2, 100);
		explosionAnim.addFrame(explosion3, 100);
		explosionAnim.addFrame(explosion4, 100);
		explosionAnim.addFrame(explosion5, 100);
		explosionAnim.addFrame(explosion6, 100);

		planeBlueEnemies.add(this);
		
	}

	@Override
	public void Update() {
		if(this.isVisible){
			timeShoot -= 5;
			if(timeShoot <= 0){
				Shoot();
				timeShoot = 1000;
			}
			xPosition -= speedX;
			bluePlaneEnemyAnim.Update(20);
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

	public static void SpawPlaneEnemy(){
		if(planeBlueEnemies.size() > 0){
			for(enemyBluePlane p : planeBlueEnemies){
				if(p.isVisible == false && p.getExplosionAnim().isFinish()){
					p.ResetExposionAnim();
					p.setxPosition((int)(Math.random() * 10 + 41) * 20);
					p.setyPosition((int)(Math.random() * 16 + 3) * 20);
					p.score = 3;
					p.isVisible = true;
					TimeThread.isSpawn = false;
					return;
				}
			}
		}
		enemyBluePlane p = new enemyBluePlane(1, bg.getSpeedX() + 2, 0, (int)(Math.random() * 10 + 41) * 20, (int)(Math.random() * 16 + 3) * 20);
		TimeThread.isSpawn = false;
	}
	
	public static void Reset(){
		if(planeBlueEnemies.size() > 0){
			for(enemyBluePlane p : planeBlueEnemies){
				if(p.isVisible){
					p.isVisible = false;
					p.xPosition = -50;
				}
			}
		}
	}
	
	public void Shoot(){
		if(this.isVisible){
			enemyBullet.SpawnBullet(xPosition + 5, yPosition + 20);
		}
	}
	
	public Animation getBluePlaneEnemyAnim() {
		return bluePlaneEnemyAnim;
	}

	public void setBluePlaneEnemyAnim(Animation bluePlaneEnemyAnim) {
		this.bluePlaneEnemyAnim = bluePlaneEnemyAnim;
	}

	public Animation getExplosionAnim() {
		return explosionAnim;
	}

	public void setExplosionAnim(Animation explosionAnim) {
		this.explosionAnim = explosionAnim;
	}


	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isKilled() {
		return isKilled;
	}

	public void setKilled(boolean isKilled) {
		this.isKilled = isKilled;
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
