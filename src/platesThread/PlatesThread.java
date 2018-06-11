package platesThread;

import java.util.ArrayList;
import java.util.List;

import level.DifficulityLevel;
import move.strategy.IMoveStrategy;
import observer.IObserver;
import plateIF.PlateIF;
import player.Player;
import poolingImp.PoolPlate;
import strategy.move.factory.MoveStrategyFactory;

public class PlatesThread extends Thread {

	private PoolPlate poolPlate;
	private List<PlateIF> plates;
	private IMoveStrategy moveStrategy;
	private final int maxY = 750;
	private final int cnt = 20;
	private view.Background gameBackGround;
	private DifficulityLevel level;
	private boolean pause;
	
	
	public PlatesThread(DifficulityLevel level, view.Background gameBackGround, ArrayList<IObserver> players) {
		poolPlate = poolPlate.getInstance(level, players);
		plates = new ArrayList<PlateIF>();
		moveStrategy = MoveStrategyFactory.getMoveStrategy(level);
		this.gameBackGround = gameBackGround;
		this.level = level;
		this.pause = false;
	}
	
	@Override
	public void run() {
		int counter = 0;
		while (true) {
			if(pause)continue;
			try {
				drawBefore();
				sleep(20);
				PlateIF myPlate = null;
				if (counter == cnt) {
					myPlate = poolPlate.acquire();
					counter = 0;
				}
				counter++;
				if (myPlate != null) {
					this.gameBackGround.add(myPlate.getLabel());
					plates.add(myPlate);
					//sleep(10);
					myPlate.move(this.moveStrategy);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void drawBefore() throws InterruptedException {
		Iterator iterator = new Iterator();
		while (iterator.hasNext()) {
			PlateIF plate = iterator.next();
			if (plate != null) {
				if (checkBound(plate)) {
					plate.move(this.moveStrategy);
					plate.notifyAllObservers(plates);
				} else {
					removePlateFromView(plate);
				}
			}
		}
	}
	
	public void removePlateFromView(PlateIF plate){		
		poolPlate.release(plate);		
		this.gameBackGround.remove(plate.getLabel());			
		plates.remove(plate);		
	}

	private boolean checkBound(PlateIF plate) {
		int deltaY;
		if (level == DifficulityLevel.Easy)
			deltaY = 4;
		else if (level == DifficulityLevel.Meduim)
			deltaY = 12;
		else
			deltaY = 20;
		if (plate.getCurrentPosition().y + plate.getHeight() + deltaY <= maxY) {
			return true;
		}
		return false;
	}

	public List<PlateIF> getPlates() {
		return this.plates;
	}
	
	public void setPlates(List<PlateIF> plates, Player player1, Player player2) {
		for (int i = 0; i < this.plates.size(); i++) {
			this.gameBackGround.remove(this.plates.get(i).getLabel());
		}
		this.plates.clear();
		for (int i = 0; i < plates.size(); i++) {
			plates.get(i).addObserver(player1);
			plates.get(i).addObserver(player2);
		}
		this.plates = plates;
	}
	
	public void setPause(boolean pause){
		this.pause = pause;
	}
	
	private class Iterator {
		private int i;

		public Iterator() {
			i = -1;
		}

		public int size() {
			return plates.size();

		}

		public PlateIF next() {
			if (this.hasNext()) {
				i++;
				return plates.get(i);
			}
			return null;

		}

		public boolean hasNext() {
			if (i + 1 < this.size()) {
				return true;
			}
			return false;

		}
	}
}