
public class Background {
	private int xPosition;
	private int yPosition;
	private int speedX;
	private Thread thread;
	
	public Background(int x, int y){
		xPosition = x;
		yPosition = y;
		speedX = 1;
	}
	
	public void Update(){
		xPosition -= speedX;
		if(xPosition <= -2160){
			xPosition = 2160;
		}
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	
	
}
