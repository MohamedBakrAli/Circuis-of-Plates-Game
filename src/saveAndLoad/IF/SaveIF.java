package saveAndLoad.IF;

import java.util.LinkedList;

import plateIF.PlateIF;
import player.Player;
import snapShot.FallingPlatesSnapShot;
import snapShot.PlayerSnapShot;

public interface SaveIF {

	public void saveAsXml();

	public void read(LinkedList<PlayerSnapShot> players, FallingPlatesSnapShot plates, String path);

}
