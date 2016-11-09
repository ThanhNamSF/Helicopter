import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScoreManager {
	private int score;
	
	public ScoreManager(){
		score = 0;
	}
	
	public void Increase(int score){
		this.score += score;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void Decrease(int score){
		this.score -= score;
	}
	
	public String CompareScore(){
		String result = null;
		try{
			File file = new File("score/score.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			result = reader.readLine();
			int n = Integer.parseInt(result);
			reader.close();
			if(this.score > n){
				BufferedWriter output = new BufferedWriter(new FileWriter(file));
				result = Integer.toString(this.score);
				output.write(result);
				output.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}
}
