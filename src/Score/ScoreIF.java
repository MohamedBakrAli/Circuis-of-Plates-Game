package Score;

import java.awt.Label;

import player.Player;

public interface ScoreIF {
	public void update(Player player);
	
	public Label getTextArea();
}
