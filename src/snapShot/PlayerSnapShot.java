package snapShot;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import plateIF.PlateIF;
import player.Player;

public class PlayerSnapShot {

	private Player player;
	private List<PlateIF> platesLeft;
	private List<PlateIF> platesRight;

	public PlayerSnapShot(Player player) {
		this.platesLeft = clone(player.getPlatesLeft());
		this.platesRight = clone(player.getPlatesRight());
		this.player = clone(player);
	}

	public Player getPlayer() {
		return player;
	}

	public List<PlateIF> getLeftPlates() {
		return this.platesLeft;
	}

	public List<PlateIF> getRightPlates() {
		return this.platesRight;
	}

	public List<PlateIF> clone(List<PlateIF> plates) {
		List<PlateIF> newPlates = new ArrayList<PlateIF>();
		for (int i = 0; i < plates.size(); i++) {
			newPlates.add(plates.get(i).clone());
		}
		return newPlates;
	}

	public Player clone(Player player) {
		Player newPlayer = new Player((ImageIcon) player.getPlayerLabel().getIcon(),
				(int) player.getPlayerLabel().getX());
		newPlayer.setLeftPlates(this.platesLeft);
		newPlayer.setRightPlates(this.platesRight);
		return newPlayer;
	}
}
