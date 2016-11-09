import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePlay extends JPanel implements Runnable, KeyListener{
	
	private static final int DELAY = 10;
	private boolean pressKeyFly = false;
	private boolean pressKeyShoot = false;
	private Player player;
	private Image character, character2, character3, background, explosion, explosion2, explosion3, explosion4, explosion5, explosion6;
	private Image play, replay, quit;
	private static Background bg1, bg2;
	private static BossLv1 bossLv1 = null;
	private Animation playerAnim, explosionAnim;
	private ArrayList<Tile> tileArray = new ArrayList<Tile>();
	private String gameOverText, yourScoreText, highScoreText;
	private GameOver gameOver;
	private Font font1, font2;
	private ImageIcon iiPlay, iiReplay, iiQuit;
	private Thread thread;
	private Thread tEnemy;
	private Thread tItem;
	private boolean load = true;
	private String yourScore = "0", highScore = "0";
	private BackgroundAudio bgAudio;
	private int r = 50, g = 205, b = 50;
	
	private int level = 1;
	private boolean isStartGame = false;
	private boolean isSpawnBossLv1 = false;
	private Random rand;
	private int timeToChangeColor = 0;
	
	public static boolean isWin = false;
	public static Image rockUp, rockDown;
	public static ScoreManager score;
	
	public GamePlay(){
		Awake();
		Start();
	}
	
	public void Awake(){
		setLayout(null);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		setFocusable(true);
		addKeyListener(this);
		
		ImageIcon iiCharacter = new ImageIcon("planeRed1.png");
		character = iiCharacter.getImage().getScaledInstance(44, 37, Image.SCALE_DEFAULT);
		ImageIcon iiCharacter2 = new ImageIcon("planeRed2.png");
		character2 = iiCharacter2.getImage().getScaledInstance(44, 37, Image.SCALE_DEFAULT);
		ImageIcon iiCharacter3 = new ImageIcon("planeRed3.png");
		character3 = iiCharacter3.getImage().getScaledInstance(44, 37, Image.SCALE_DEFAULT);
		
		playerAnim = new Animation();
		playerAnim.addFrame(character, 100);
		playerAnim.addFrame(character2, 100);
		playerAnim.addFrame(character3, 100);
		
		ImageIcon iiBackground = new ImageIcon("background.png");
		background = iiBackground.getImage().getScaledInstance(2180, 480, Image.SCALE_DEFAULT);
		
		ImageIcon iiExplosion = new ImageIcon("Explosion1.png");
		explosion = iiExplosion.getImage().getScaledInstance(59, 59, Image.SCALE_DEFAULT);
		ImageIcon iiExplosion2 = new ImageIcon("Explosion2.png");
		explosion2 = iiExplosion2.getImage().getScaledInstance(59, 59, Image.SCALE_DEFAULT);
		ImageIcon iiExplosion3 = new ImageIcon("Explosion3.png");
		explosion3 = iiExplosion3.getImage().getScaledInstance(59, 59, Image.SCALE_DEFAULT);
		ImageIcon iiExplosion4 = new ImageIcon("Explosion4.png");
		explosion4 = iiExplosion4.getImage().getScaledInstance(59, 59, Image.SCALE_DEFAULT);
		ImageIcon iiExplosion5 = new ImageIcon("Explosion5.png");
		explosion5 = iiExplosion5.getImage().getScaledInstance(59, 59, Image.SCALE_DEFAULT);
		ImageIcon iiExplosion6 = new ImageIcon();
		explosion6 = iiExplosion6.getImage();
		
		
		explosionAnim = new Animation();
		explosionAnim.addFrame(explosion, 100);
		explosionAnim.addFrame(explosion2, 100);
		explosionAnim.addFrame(explosion3, 100);
		explosionAnim.addFrame(explosion4, 100);
		explosionAnim.addFrame(explosion5, 100);
		explosionAnim.addFrame(explosion6, 100);
		
		ImageIcon iiRockDown = new ImageIcon("rockDown.png");
		rockDown = iiRockDown.getImage().getScaledInstance(30, 60, Image.SCALE_DEFAULT);
		ImageIcon iiRockUp = new ImageIcon("rockGrass.png");
		rockUp = iiRockUp.getImage().getScaledInstance(35,  70, Image.SCALE_DEFAULT);
		
		iiReplay = new ImageIcon("replayButton.png");
		iiQuit = new ImageIcon("Quit.png");
		iiPlay = new ImageIcon("Play.png");
		replay = iiReplay.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		quit = iiQuit.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		play = iiPlay.getImage().getScaledInstance(140, 60, Image.SCALE_SMOOTH);
		
	}
	
	public void Start(){	
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!isStartGame){
					Point click = e.getPoint();
					Rectangle PlayBounds = new Rectangle(new Point(getWidth() / 2 - 70, getHeight() / 2 - 30),  new Dimension(play.getWidth(null), play.getHeight(null)));
					if(PlayBounds.contains(click)){
						thread.start();
						tEnemy.start();
						tItem.start();
						isStartGame = true;
						bgAudio = new BackgroundAudio();
					}
				}
				if(gameOver.isStop()){
					Point click = e.getPoint();
					Rectangle ReplayBounds = new Rectangle(new Point(200, gameOver.getyReplayPos()), new Dimension(replay.getWidth(null), replay.getWidth(null)));
					Rectangle QuitBounds = new Rectangle(new Point(500, gameOver.getyMenuPos()), new Dimension(quit.getWidth(null), quit.getHeight(null)));
					if(ReplayBounds.contains(click)){
						System.out.println("click Replay button");
						ReStartGame();
					}
					else if(QuitBounds.contains(click)){
						System.out.println("click quit button");
						System.exit(0);
					}
				}
				
			}
		});
		gameOverText = "GAME OVER";
		yourScoreText = "YOUR SCORE";
		highScoreText = "HIGH SCORE";
		rand = new Random();
		gameOver = new GameOver(480, 540, 540, 620, 620, 150);
		font1 = new Font("Dialog", Font.BOLD, 50);
		font2 = new Font("Dialog", Font.BOLD, 30);
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		player = new Player();
		score = new ScoreManager();
		InitTile();
		thread = new Thread(this);
		tEnemy = new TimeThread();
		tItem = new TimeSpawnItems();
		
	}
	
	public void ReStartGame(){
		isWin = false;
		bgAudio.StartAudio();
		bg1.setSpeedX(1);
		bg2.setSpeedX(1);
		level = 1;
		load = true;
		isSpawnBossLv1 = false;
		Bullet.Reset();
		Rocket.ResetRocket();
		SuperRocket.Reset();
		PlaneEnemy.Reset();
		enemyBluePlane.Reset();
		enemyBullet.Reset();
		if(bossLv1 != null)
			bossLv1.Reset();
		Player.isAlive = true;
		gameOver = new GameOver(480, 540, 540, 620, 620, 150);
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		player = new Player();
		score = new ScoreManager();
		explosionAnim.setFinish(false);
		explosionAnim.setCurrentFrame(0);
		explosionAnim.setAnimTime(0);
	}

	@Override
	public void run() {
		while(true){
			if(isStartGame){
				if(Player.isAlive){
					if(!isWin)
						player.Update();
					else
						player.UpdateWin();
					bg1.Update();
					bg2.Update();
					TileUpdate();
					SetLevel();
					SpawnObstacle();
					SpawnItems();
					ObstacleUpdate();
					ItemUpdate();
					EnemyBulletUpdate();
					PlayerBulletUpdate();
					playerAnim.Update(20);
					repaint();
					try{
						Thread.sleep(DELAY);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
				else{
					bgAudio.StopAudio();
					ObstacleUpdate();
					ItemUpdate();
					if(explosionAnim.getCurrentFrame() == 5)
						explosionAnim.setFinish(true);
					explosionAnim.Update(10);
					PlayerBulletUpdate();
					EnemyBulletUpdate();
					gameOver.Update();
					repaint();
					try{
						Thread.sleep(DELAY);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void PlayerBulletUpdate(){
		for(Bullet b : Bullet.bullets){
			b.Update();
		}
	}
	
	public void EnemyBulletUpdate(){
		for(enemyBullet b : enemyBullet.enemyBullets){
			if(b.isVisible()){
				b.Update();
			}
		}
	}
	
	public void SetLevel(){
		if(score.getScore() >= 200){
			level = 5;
		}
		else if(score.getScore() >= 150){
			level = 4;
		}
		else if(score.getScore() >= 100){
			level = 3;
		}
		else if(score.getScore() >= 50){
			bg1.setSpeedX(2);
			bg2.setSpeedX(2);
			level = 2;
		}
	}
	
	public void ObstacleUpdate(){
		RocketUpdate();
		PlaneEnemyUpdate();
		SuperRocketUpdate();
		enemyBluePlaneUpdate();
		if(bossLv1 != null){
			bossLv1.Update();
		}
	}
	
	public void ItemUpdate(){
		ShieldItemUpdate();
		ProtectShieldUpdate();
	}
	
	public void SpawnObstacle(){
		if(!isWin){
			if(level == 1){
				SpawnRocket();
			}
			else if(level == 2){
				SpawPlaneEnemy();
			}
			else if(level == 3){
				SpawnSuperRocket();
			}
			else if(level == 4){
				SpawBluePlaneEnemy();
			}
			else if(level == 5 && !isSpawnBossLv1){
				isSpawnBossLv1 = true;
				bossLv1 = new BossLv1(50, bg1.getSpeedX() + 2, 1, 1200, 200);
			}
		}
	}
	
	public void SpawnItems(){
		if(TimeSpawnItems.isSpawn){
			
			ShieldItem.SpawnShieldItem();
		}
	}
	
	public void SpawnRocket(){
		if(TimeThread.isSpawn){
			Rocket.SpawnRocket();
		}
	}
	
	public void SpawPlaneEnemy(){
		if(TimeThread.isSpawn)
			PlaneEnemy.SpawPlaneEnemy();
	}
	
	public void SpawnSuperRocket(){
		if(TimeThread.isSpawn){
			SuperRocket.SpawnSuperRocket();
		}
	}
	
	public void SpawBluePlaneEnemy(){
		if(TimeThread.isSpawn)
			enemyBluePlane.SpawPlaneEnemy();
	}
	
	public void RocketUpdate(){
		//checkRocket = true;
		for(Rocket r : Rocket.rockets){
			if(r.isVisible()){
				r.Update();
				//checkRocket = false;
			}
		}
	}
	
	public void SuperRocketUpdate(){
		for(SuperRocket r : SuperRocket.superRockets){
			if(r.isVisible()){
				r.Update();
			}
		}
	}
	
	public void PlaneEnemyUpdate(){
		for(PlaneEnemy p : PlaneEnemy.planeEnemies){
			p.Update();
		}
	}
	
	public void enemyBluePlaneUpdate(){
		for(enemyBluePlane p : enemyBluePlane.planeBlueEnemies){
			p.Update();
		}
	}
	
	public void ShieldItemUpdate(){
		for(ShieldItem i : ShieldItem.shieldItems){
			if(i.isVisible()){
				i.Update();
			}
		}
	}
	
	public void ProtectShieldUpdate(){
		if(ProtectShield.Instance != null){
			if(ProtectShield.Instance.isVisible()){
				ProtectShield.Instance.Update();
			}
		}
		
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		DrawBackground(g);
		DrawTile(g);
		if(!isStartGame){
			DrawPlayImage(g);
		}
		DrawRocket(g);
		DrawSuperRocket(g);
		DrawPlaneEnemy(g);
		DrawBluePlaneEnemy(g);
		DrawBossLv1(g);
		DrawHeathBackgroundLv1(g);
		DrawHealthBossLv1(g);
		DrawBullet(g);
		DrawShieldItem(g);
		DrawEnemyBullet(g);
		DrawScore(g);
		if(Player.isAlive){
			DrawPlayer(g);
		}
		else{
			DrawExplosion(g);
			DrawGameOver(g);
		}
		DrawProtectShield(g);
		if(isWin)
			DrawYouWin(g);
	    Toolkit.getDefaultToolkit().sync();
	    g.dispose();
	}
	
	public void DrawExplosion(Graphics g){
		g.drawImage(explosionAnim.getImage(), player.getxPosition(), player.getyPosition() , this);
	}
	
	public void DrawPlayer(Graphics g){
		g.drawImage(playerAnim.getImage(), player.getxPosition(), player.getyPosition(), this);
	}
	
	public void DrawBackground(Graphics g){
		g.drawImage(background, bg1.getxPosition(), bg2.getyPosition(), this);
		g.drawImage(background, bg2.getxPosition(), bg2.getyPosition(), this);
	}
	
	public void DrawRocket(Graphics g){
		for(Rocket r : Rocket.rockets){
			if(r.isVisible()){
				g.drawImage(r.getRocketAnim().getImage(), r.getxPosition(), r.getyPosition(), this);
			}
		}
	}
	
	public void DrawSuperRocket(Graphics g){
		for(SuperRocket r : SuperRocket.superRockets){
			if(r.isVisible()){
				g.drawImage(r.getSuperRocketAnim().getImage(), r.getxPosition(), r.getyPosition(), this);
			}
		}
	}
	
	public void DrawPlaneEnemy(Graphics g){
		for(PlaneEnemy p : PlaneEnemy.planeEnemies){
			if(p.isVisible()){
				g.drawImage(p.getEnemyPlaneAnim().getImage(), p.getxPosition(), p.getyPosition(), this);
			}
			else{
				g.drawImage(p.getExplosionAnim().getImage(), p.getxPosition(), p.getyPosition(), this);
			}
		}
	}
	
	public void DrawBossLv1(Graphics g){
		if(bossLv1 != null){
			if(bossLv1.isVisible()){
				g.drawImage(bossLv1.getBossLv1Anim().getImage(), bossLv1.getxPosition(), bossLv1.getyPosition() ,this);
			}
			else{
				g.drawImage(bossLv1.getExplosionAnim().getImage(), bossLv1.getxPosition(), bossLv1.getyPosition(), this);
			}
		}
	}
	
	public void DrawBluePlaneEnemy(Graphics g){
		for(enemyBluePlane p : enemyBluePlane.planeBlueEnemies){
			if(p.isVisible()){
				g.drawImage(p.getBluePlaneEnemyAnim().getImage(), p.getxPosition() , p.getyPosition(), this);
			}
			else{
				g.drawImage(p.getExplosionAnim().getImage(), p.getxPosition(), p.getyPosition(), this);
			}
		}
	}
	
	public void DrawBullet(Graphics g){
		for(Bullet b : Bullet.bullets){
			if(b.isVisible()){
				g.setColor(Color.red);
				g.fillRect(b.getxPosition(), b.getyPosition(), 15, 5);
			}
			else{
				g.drawImage(b.getHitExplosionAnim().getImage(), b.getxPosition(), b.getyPosition(), this);
			}
		}
	}
	
	public void DrawShieldItem(Graphics g){
		for(ShieldItem i : ShieldItem.shieldItems){
			if(i.isVisible()){
				g.drawImage(i.getAnim().getImage(), i.getxPosition(), i.getyPosition(), this);
			}
		}
	}
	
	public void DrawProtectShield(Graphics g){
		if(ProtectShield.Instance != null){
			if(ProtectShield.Instance.isVisible()){
				g.drawImage(ProtectShield.Instance.getAnim().getImage(), ProtectShield.Instance.getxPosition(), ProtectShield.Instance.getyPosition(), this);
			}
		}
		
	}
	
	public void DrawProtectShieldRect(Graphics g){
		if(ProtectShield.Instance != null){
			if(ProtectShield.Instance.isVisible()){
				g.drawRect(ProtectShield.Instance.getRect().x, ProtectShield.Instance.getRect().y, ProtectShield.Instance.getRect().width, ProtectShield.Instance.getRect().height);
			}
		}
	}
	
	public void DrawHeathBackgroundLv1(Graphics g){
		if(bossLv1 != null){
			if(bossLv1.isVisible()){
				g.setColor(Color.DARK_GRAY);
				g.fillRect(670, 20, 100, 15);
			}
		}
	}
	
	public void DrawHealthBossLv1(Graphics g){
		if(bossLv1 != null){
			if(bossLv1.isVisible()){
				if(bossLv1.getCurrentHealth() >= bossLv1.getMaxHealth() / 2){
					g.setColor(Color.green);
				}
				else{
					g.setColor(Color.red);
				}
				int value = (bossLv1.getMaxHealth() - bossLv1.getCurrentHealth()) * (100 / bossLv1.getMaxHealth());
				g.fillRect(670 + value, 20, 100 - value , 15);
			}
		}
		
	}
	
	public void DrawEnemyBullet(Graphics g){
		for(enemyBullet b : enemyBullet.enemyBullets){
			if(b.isVisible()){
				g.setColor(Color.blue);
				g.fillRect(b.getxPosition(), b.getyPosition(), 15, 5);
			}
		}
	}
	
	public void DrawPlayerRectangle(Graphics g){
		g.drawRect((int)player.rect.getX(), (int)player.rect.getY(), (int)player.rect.getWidth(), (int)player.rect.getHeight());
	}
	
	public void DrawRockRectangle(Graphics g){
		for(Tile t : tileArray){
			g.drawRect((int)t.getRect().getX(), (int)t.getRect().getY(), (int)t.getRect().getWidth(), (int)t.getRect().getHeight());
		}
	}
	
	public void DrawRocketRectangle(Graphics g){
		for(Rocket r : Rocket.rockets){
			g.drawRect((int)r.getRect().getX(), (int)r.getRect().getY(), (int)r.getRect().getWidth(), (int)r.getRect().getHeight());
		}
	}
	
	public void DrawBulletRectangle(Graphics g){
		for(Bullet b : Bullet.bullets){
			g.drawRect((int)b.getRect().getX(), (int)b.getRect().getY(), (int)b.getRect().getWidth(), (int)b.getRect().getHeight());
		}
	}
	
	public void DrawPlaneEnemyRectangle(Graphics g){
		for(PlaneEnemy p : PlaneEnemy.planeEnemies){
			g.drawRect((int)p.getRect().getX(), (int)p.getRect().getY(), (int)p.getRect().getWidth(), (int)p.getRect().getHeight());
		}
	}
	
	public void DrawTile(Graphics g){
		for(Tile t : tileArray){
			g.drawImage(t.getTileImage(), t.getxTile(), t.getyTile(), this);
		}
	}
	
	public void DrawScore(Graphics g){
		String s = Integer.toString(score.getScore());
		Font font = new Font("Dialog", Font.BOLD, 64);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(s, getWidth() / 2 - 4, 50 + 2);
		g.setColor(new Color(198, 226, 255));
		g.drawString(s, getWidth() / 2 - 6, 50);
	}
	
	public void DrawGameOver(Graphics g){
		if(load){
			yourScore = Integer.toString(score.getScore());
			highScore = score.CompareScore();
			load = false;
		}
		g.setFont(font1);
		g.setColor(Color.DARK_GRAY);
		g.drawString(gameOverText, 250, gameOver.getyGameOverPos());
		g.setFont(font2);
		g.setColor(Color.CYAN);
		g.drawString(yourScoreText, 150, gameOver.getyYourScorePos());
		g.drawString(yourScore, 230, gameOver.getyYourScorePos() + 40);
		g.setColor(Color.RED);
		g.drawString(highScoreText, 450, gameOver.getyHighScorePos());
		g.drawString(highScore, 500, gameOver.getyHighScorePos() + 40);
		if(gameOver.isStop()){
			DrawReplayAndQuitImage(g);
		}
	}
	
	public void DrawYouWin(Graphics gp){
		gp.setFont(font1);
		timeToChangeColor++;
		if(timeToChangeColor == 15){
			r = rand.nextInt(256);
			g = rand.nextInt(256);
			b = rand.nextInt(256);
			timeToChangeColor = 0;
		}
		gp.setColor(new Color(r % 256, g % 256, b % 256));
		gp.drawString("YOU WIN", 270, 215);
	}
	
	public void DrawReplayAndQuitImage(Graphics g){
		g.drawImage(replay, 200, gameOver.getyReplayPos(), this);
		g.drawImage(quit, 500, gameOver.getyMenuPos(), this);
	}
	
	public void DrawPlayImage(Graphics g){
		g.drawImage(play, getWidth() / 2 - 70, getHeight() / 2 - 30, this);
	}
	
	public void InitTile(){
		for(int i = 0; i < 41; i++){
			Tile tDown = new Tile(i, 0, 1);
			tileArray.add(tDown);
			Tile tUp = new Tile(i, 20, 2);
			tileArray.add(tUp);
		}
	}
	
	public void TileUpdate(){
		for(Tile t : tileArray){
			t.Update();
		}
	}
	
	public Image loadImage(String name){
		ImageIcon ii = new ImageIcon(name);
		return ii.getImage();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!isWin){
			if(e.getKeyCode() == KeyEvent.VK_UP){
				player.setSpeedY(-2);
				if(!pressKeyFly){
					pressKeyFly = true;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE && !pressKeyShoot){
				player.Shoot();
				pressKeyShoot = true;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!isWin){
			switch(e.getKeyCode()){
			case KeyEvent.VK_SPACE:
				pressKeyShoot = false;
				break;
			case KeyEvent.VK_UP:
				pressKeyFly = false;
				player.setSpeedY(1);
				break;
			}	
		}
	}
	
	public static Background getBg1(){
		return bg1;
	}
	
	public static Background getBg2(){
		return bg2;
	}
	
}
