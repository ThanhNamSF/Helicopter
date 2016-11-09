import java.util.concurrent.ThreadLocalRandom;

import javax.swing.plaf.SliderUI;

public class TimeThread extends Thread implements Runnable {
	
	public static boolean isSpawn = true;

	@Override
	public void run() {
		if(Player.isAlive){
			while(true){
				try {
					Thread.sleep(500);
					isSpawn = true;
				} catch (InterruptedException e) {
						e.printStackTrace();
				}
			}
		}
	}

}
