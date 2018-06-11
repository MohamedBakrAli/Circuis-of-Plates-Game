package saveAndLoad.IF;

import java.util.LinkedList;
import java.util.Stack;

import snapShot.FallingPlatesSnapShot;
import snapShot.PlayerSnapShot;

public interface LoadIF {

	public boolean readAsXml();

	public FallingPlatesSnapShot getPlates();

	public LinkedList<PlayerSnapShot> getPlayer();
}
