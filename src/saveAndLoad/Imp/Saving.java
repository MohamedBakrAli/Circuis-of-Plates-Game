package saveAndLoad.Imp;

import java.util.LinkedList;
import player.Player;
import snapShot.FallingPlatesSnapShot;
import snapShot.PlayerSnapShot;

public class Saving {

    private FallingPlatesSnapShot plates;
    private LinkedList<PlayerSnapShot> players;

    public Saving(LinkedList<PlayerSnapShot> players, FallingPlatesSnapShot plates) {
        this.plates = plates;
        this.players = players; 
    }

    public FallingPlatesSnapShot getShapes() {
        return plates;
    }
    
    public LinkedList<PlayerSnapShot> getPlayers() {
        return players;
    }

}
