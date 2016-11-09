import java.awt.Rectangle;

public abstract class Item {
	
	protected int xPosition;
	protected int yPosition;
	protected int speedX;
	protected int speedY;
	private boolean isVisible;
	protected Rectangle rect;
	
	public Item(int xPosition, int yPosition, int speedX, int speedY){
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.speedX = speedX;
		this.speedY = speedY;
		isVisible = true;
		rect = new Rectangle(0, 0, 0, 0);
	}
	
	public abstract void Update();
	public abstract void OnCollision();
	
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

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

}
