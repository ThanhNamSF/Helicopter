import java.awt.Image;
import java.util.ArrayList;

public class Animation {
	private ArrayList<AnimFrame> frames;
	private int currentFrame;
	private int animTime;
	private int totalDuaration;
	private boolean isFinish = false;
	
	public Animation(){
		frames = new ArrayList<AnimFrame>();
		totalDuaration = 0;
		animTime = 0;
		currentFrame = 0;
	}
	
	public void addFrame(Image image, int duaration){
		totalDuaration += duaration;
		frames.add(new AnimFrame(image, duaration));
	}
	
	public void Update(int elapsedTime){
		if(!isFinish){
			if(frames.size() > 1){
				animTime += elapsedTime;
				if(animTime >= totalDuaration){
					animTime = animTime % totalDuaration;
					currentFrame = 0;
				}
				while(animTime > getFrame(currentFrame).getEndTime() * (currentFrame + 1)){
					currentFrame++;
				}
			}
		}
	}
	
	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	public Image getImage(){
		if(frames.size() == 0)
			return null;
		return getFrame(currentFrame).getImage();
	}
	
	public AnimFrame getFrame(int i){
		return frames.get(i);
	}

	public ArrayList<AnimFrame> getFrames() {
		return frames;
	}

	public void setFrames(ArrayList<AnimFrame> frames) {
		this.frames = frames;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public int getAnimTime() {
		return animTime;
	}

	public void setAnimTime(int animTime) {
		this.animTime = animTime;
	}

	public int getTotalDuaration() {
		return totalDuaration;
	}

	public void setTotalDuaration(int totalDuaration) {
		this.totalDuaration = totalDuaration;
	}
	

}
