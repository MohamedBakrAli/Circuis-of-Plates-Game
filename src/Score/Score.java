package Score;

import java.awt.Label;
import player.Player;
import state.State;
import state.WinState;

public class Score implements ScoreIF{
	private Player player1 , player2;
	//private JTextArea textArea;
	Label label;
	public Score(Player player1 , Player player2) {
		// blue
		this.player1 = player1;
		// green
		this.player2 = player2;
		String str = "Blue  " + player2.getScore().toString() + " :  Green   " + player1.getScore().toString(); ; 
		label = new Label(str);
		label.setBounds(50, 30, 20, 10);
	}
	
	@Override
	public void update(Player player) {
		if(player2.getScore() ==5){
			State winState = new WinState("Blue");
			winState.doAction();
		}
		else if(player1.getScore()==5){
			State winState = new WinState("Green");
			winState.doAction();
		}
		String score = "Blue  " + player2.getScore().toString() + " :  Green   " + player1.getScore().toString(); ; 
		label.setText(score);
	}
	
	public Label getTextArea(){
		return this.label;
	}
}
