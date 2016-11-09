import java.util.Random;

public class TimeSpawnItems extends Thread implements Runnable {
	public static boolean isSpawn = false;

	@Override
	public void run() {
		if(Player.isAlive){
			while(true){
				try {
					Thread.sleep((long)(Math.random() * 15000 + 25000));
					isSpawn = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
