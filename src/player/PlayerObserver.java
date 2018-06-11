package player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import observer.IObserver;
import observer.ObservableClass;
import plateIF.PlateIF;

public class PlayerObserver implements IObserver {

	private final int maxNumber;
	private boolean win;

	public PlayerObserver() {
		maxNumber = 3;
		win = false;
	}
	
	@Override
	public void update(ObservableClass player, Object obj) {
		checkLeft(obj);
		checkRight(obj);
	}

	private void checkLeft(Object arg) {
		List<PlateIF> list = ((Player) arg).getPlatesLeft();
		if (list.size() < 3) {
			win = false;
			return;
		}
		Color c = list.get(list.size() - 1).getColor();
		win = true;
		for (int i = 0; i < maxNumber; i++) {
			if (list.get(list.size() - 1 - i).getColor() != c) {
				win = false;
				break;
			}
		}
		if(win){
			((Player)arg).winPoint(list);
		}
	}

	private void checkRight(Object arg) {
		List<PlateIF> list = ((Player) arg).getPlatesRight();
		if (list.size() < 3) {
			win = false;
			return;
		}
		Color c = list.get(list.size() - 1).getColor();
		win = true;
		for (int i = 0; i < maxNumber; i++) {
			if (list.get(list.size() - 1 - i).getColor() != c) {
				win = false;
				break;
			}
		}
		if(win){
			((Player)arg).winPoint(list);
		}
	}
	
	public boolean checkWin() {
		return this.win;
	}
}