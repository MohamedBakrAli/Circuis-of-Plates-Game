package state;

import javax.swing.JOptionPane;

public class WinState implements State{
	private String player;
	public WinState(String playerColor) {
		this.player = playerColor;
	}
	@Override
	public void doAction() {
		String infoMessage = player + " Player wins !";
		JOptionPane.showMessageDialog(null,infoMessage);
		System.exit(0);
	}

}
