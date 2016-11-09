import java.io.File;
//gdgdf
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BackgroundAudio {
	
	private Clip clip;
	public BackgroundAudio(){
		try{
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("backgroundMusic.wav"));
	        clip = AudioSystem.getClip();
	        clip.open(inputStream);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void StopAudio(){
		if(clip.isRunning()){
			clip.stop();
		}
	}
	
	public void StartAudio(){
		clip.setMicrosecondPosition(0);
		clip.start();
	}
}
