package move.strategy.impl;

import java.awt.Point;
import java.util.Random;

import move.strategy.IMoveStrategy;
import plateIF.PlateIF;

public class VerticalMoveStrategy implements IMoveStrategy{
	private int deltaX, deltaY;
	
	public VerticalMoveStrategy(int deltaX ,int deltaY) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}
	
	@Override
	public void move(PlateIF plate) {
		Point location = plate.getLabel().getLocation();	
		plate.getLabel().setLocation(location.x + deltaX, location.y + deltaY);
	}
}
