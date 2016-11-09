import java.awt.Color;
import java.awt.Font;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.omg.CORBA.portable.ServantObject;

public class GameOver{
	
	private int yGameOverPos;
	private int yYourScorePos;
	private int yHighScorePos;
	private int yReplayPos;
	private int yMenuPos;
	private int speedY;
	private int height;
	private boolean stop;
	
	public GameOver(int yGameOverPos, int yYourScorePos, int yHighScorePos, int yReplayPos, int yMenuPos, int height){
		this.yGameOverPos = yGameOverPos;
		this.yHighScorePos = yHighScorePos;
		this.yYourScorePos = yYourScorePos;
		this.yReplayPos = yReplayPos;
		this.yMenuPos = yMenuPos;
		this.height = height;
		speedY = 2;
		stop = false;
	}
	
	public void Update(){
		if(yGameOverPos > height){
			yGameOverPos -= speedY;
			yHighScorePos -= speedY;
			yYourScorePos -= speedY;
			yReplayPos -= speedY;
			yMenuPos -= speedY;
		}
		else{
			stop = true;
		}
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public int getyGameOverPos() {
		return yGameOverPos;
	}

	public void setyGameOverPos(int yGameOverPos) {
		this.yGameOverPos = yGameOverPos;
	}

	public int getyYourScorePos() {
		return yYourScorePos;
	}

	public void setyYourScorePos(int yYourScorePos) {
		this.yYourScorePos = yYourScorePos;
	}

	public int getyHighScorePos() {
		return yHighScorePos;
	}

	public void setyHighScorePos(int yHighScorePos) {
		this.yHighScorePos = yHighScorePos;
	}

	public int getyReplayPos() {
		return yReplayPos;
	}

	public void setyReplayPos(int yReplayPos) {
		this.yReplayPos = yReplayPos;
	}

	public int getyMenuPos() {
		return yMenuPos;
	}

	public void setyMenuPos(int yMenuPos) {
		this.yMenuPos = yMenuPos;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
	

}
