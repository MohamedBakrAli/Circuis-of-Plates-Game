package poolingImp;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Random;

import container.Container;
import level.DifficulityLevel;
import observer.IObserver;
import plateFactoryIF.PlateFactoryIF;
import plateFactoryImp.PlateFactory;
import plateIF.PlateIF;

public class PoolPlate{

	private List<PlateIF> plates;
	private final int size = 300;
	private PlateFactoryIF factory;
	private static PoolPlate instance;
	private DifficulityLevel level;
	private List<IObserver> players;
	
	private PoolPlate(DifficulityLevel level , ArrayList<IObserver> observers) {
		plates = new ArrayList<PlateIF>();
		factory = new PlateFactory(level);	
		this.level = level;
		players = observers;
		fillShapes(level);
	}
	
	private void fillShapes(DifficulityLevel level) {
		plates.clear();
		for (int i = 0; i < size; i++) {
			PlateIF plate = factory.getObject(players);
			plates.add(plate);
		}
	}
	
	public PlateIF acquire() {
		if (plates.size() == 0) {
			return null;
		}
		PlateIF plate = plates.get(plates.size() - 1);
		plates.remove(plates.size() - 1);
		return plate;
	}
	
	public void release(PlateIF plate) {
		plate.setPosition((int) plate.getPosition().getX(), (int) plate.getPosition().getY());
		plate.setMove(false);
		plates.add(plate);
	}
	
	public static PoolPlate getInstance(DifficulityLevel level , ArrayList<IObserver> observers) {
		if (instance == null) {
			instance = new PoolPlate(level,observers);
		}
		return instance;
	}
}
