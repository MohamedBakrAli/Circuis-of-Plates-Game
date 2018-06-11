package player;

import javax.swing.ImageIcon;

public class PlayerFactory {

	private PlayerFactory() {

	}
	
	public static Player createPlayer(ImageIcon clown, int x) {
		return new Player(clown, x);
	}
}
