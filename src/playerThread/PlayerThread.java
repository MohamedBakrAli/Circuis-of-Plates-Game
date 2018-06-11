package playerThread;

import javax.swing.ImageIcon;

import controller.KeyboardAnimation;
import platesThread.PlatesThread;
import player.Player;
import player.PlayerFactory;

public class PlayerThread extends Thread {

	private final int delta = 3;
	private ImageIcon clown1;
	private ImageIcon clown2;
	private Player player1;
	private Player player2;
	private PlatesThread platesThread;
	private boolean pause;
	private KeyboardAnimation motion1,motion2;
	
	public PlayerThread() {
		// TODO Auto-generated constructor stub
		clown1 = new ImageIcon(getClass().getResource("clown1.png"));
		clown2 = new ImageIcon(getClass().getResource("clown2.png"));
		player1 = PlayerFactory.createPlayer(clown1, 1100);
		player2 = PlayerFactory.createPlayer(clown2, 200);
		pause = false;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		motion1 = new KeyboardAnimation(player1, 18);
		motion1.addAction("LEFT", -delta);
		motion1.addAction("RIGHT", delta);

		motion2 = new KeyboardAnimation(player2, 18);
		motion2.addAction("A", -delta);
		motion2.addAction("D", delta);

	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}
	
	public void setPlatesThread(PlatesThread platesThread) {
		this.platesThread = platesThread;
		this.player1.setPlatesThread(this.platesThread);
		this.player2.setPlatesThread(this.platesThread);
	}
	
	public void setPause(boolean pause){
		this.pause = pause;
		this.motion1.setPause(true);
		this.motion2.setPause(true);
	}
	
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

}
