import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

public class BossLv1 extends Enemy {
	
	private boolean isVisible;
	private int score;
	private int maxHeight;
	private int minHeight;
	private int wallUp = 60;
	private int wallDown = 300;
	private int offset = 70;
	private int tSpeedY;
	private int maxHealth;
	private Random rand;
	private int timeIdle = 100;
	private int timeShoot;
	
	private static ImageIcon iiBossLv1 = new ImageIcon("BossLv1.png");
	private static ImageIcon iiBossLv1_2 = new ImageIcon("BossLv1_2.png");
	
	private static Image BossLv1 = iiBossLv1.getImage();
	private static Image BossLv1_2 = iiBossLv1_2.getImage();
	
	private Animation bossLv1Anim, explosionAnim;
	private boolean isKilled;
	private static Background bg = GamePlay.getBg1();

	public BossLv1(int startHealth, int speedX, int speedY, int xPos, int yPos) {
		super(startHealth, speedX, speedY, xPos, yPos);
		isVisible = true;
		score = 100;
		maxHealth = startHealth;
		
		bossLv1Anim = new Animation();
		bossLv1Anim.addFrame(BossLv1, 100);
		bossLv1Anim.addFrame(BossLv1_2, 100);
		
		explosionAnim = new Animation();
		explosionAnim.addFrame(explosion, 100);
		explosionAnim.addFrame(explosion2, 100);
		explosionAnim.addFrame(explosion3, 100);
		explosionAnim.addFrame(explosion4, 100);
		explosionAnim.addFrame(explosion5, 100);
		explosionAnim.addFrame(explosion6, 100);
		
		rand = new Random();
		
		minHeight = yPos - offset;
		maxHeight = yPos + offset;
		tSpeedY = speedY;
		timeShoot = (int)(Math.random() * 200) + 150;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	@Override
	public void Update() {
		if(this.isVisible){
			if(xPosition > 600)
				xPosition -= speedX;
			else{
				yPosition += speedY;
				timeShoot--;
				if(timeShoot <= 0){
					Shoot1();
					timeShoot = (int)(Math.random() * 200) + 150;
				}
				if(yPosition >= maxHeight || yPosition <= minHeight){
					maxHeight += offset;
					minHeight -= offset;
					speedY = tSpeedY * (rand.nextInt(3) - 1);
				}
				if(yPosition <= wallUp){
					maxHeight = wallUp - offset;
					speedY = 1;
				}
				else if(yPosition >= wallDown){
					minHeight = wallDown - offset;
					speedY = -1;
				}
				if(speedY == 0){
					timeIdle--;
					if(timeIdle == 0){
						speedY = 1;
						timeIdle = 100;
					}
				}
			}
			bossLv1Anim.Update(20);
			rect.setBounds(xPosition + 5, yPosition, 150, 120);
			OnCollision();
		}
		else{
			if(explosionAnim.getCurrentFrame() == 5){
				explosionAnim.setFinish(true);
				this.xPosition = -300;	
			}
			explosionAnim.Update(10);
		}
		
	}
	
	public void Shoot1(){
		enemyBullet.SpawnBullet(xPosition + 5, yPosition + 5);
		enemyBullet.SpawnBullet(xPosition + 5, yPosition + 75);
		enemyBullet.SpawnBullet(xPosition + 5, yPosition + 145);
	}
	
	public boolean isKilled() {
		return isKilled;
	}

	public void setKilled(boolean isKilled) {
		this.isKilled = isKilled;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Animation getBossLv1Anim() {
		return bossLv1Anim;
	}

	public void setBossLv1Anim(Animation bossLv1Anim) {
		this.bossLv1Anim = bossLv1Anim;
	}

	public Animation getExplosionAnim() {
		return explosionAnim;
	}

	public void setExplosionAnim(Animation explosionAnim) {
		this.explosionAnim = explosionAnim;
	}

	public void ResetExposionAnim(){
		explosionAnim.setFinish(false);
		explosionAnim.setCurrentFrame(0);
		explosionAnim.setAnimTime(0);
	}
	
	public void Reset(){
		isVisible = false;
		xPosition = -300;
	}

	@Override
	public void Dead() {
		GamePlay.isWin = true;
		PlaySound();
		GamePlay.score.Increase(score);
		score = 0;
		this.isVisible = false;
	}

	@Override
	public void OnCollision() {
		if(this.isVisible){
			if(Bullet.bullets.size() > 0){
				for(Bullet b : Bullet.bullets){
					if(b.isVisible()){
						if(b.getRect().intersects(rect) && Player.isAlive){
							b.Remove();
							TakeDamage(Bullet.damage);
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
