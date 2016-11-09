import java.awt.Color;

import javax.swing.JFrame;

public class GameStart extends JFrame {
	
	public GameStart(){
		add(new GamePlay());
		setSize(800, 480);
		setTitle("Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}

	public static void main(String[] args) {
		new GameStart();

	}

}
