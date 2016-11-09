import java.awt.Image;

public class AnimFrame {
	private Image image;
	private int endTime;
	
	public AnimFrame(Image image, int endTime){
		this.image = image;
		this.endTime = endTime;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
}
