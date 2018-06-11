package player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Score.ScoreIF;
import container.Container;
import observer.IObserver;
import observer.ObservableClass;
import plateIF.PlateIF;
import platesThread.PlatesThread;
import state.State;
import state.WinState;

public class Player extends ObservableClass implements IObserver{
	
	private JLabel player;
	private List<PlateIF> rightPlates;
	private List<PlateIF> leftPlates;
	private final int stackWidth = 30;
	private List<Observer> observers;
	private int score;
	private PlatesThread platesThread;
	private ScoreIF scoreObject;
	private final int offsetX = 15;
	private final int offsetY = 7;
	
	//must have an observers
	public Player(ImageIcon clown , int x) {
		rightPlates = new ArrayList<PlateIF>();
		leftPlates = new ArrayList<PlateIF>();
		player = new JLabel(clown);
		player.setBounds(x, 560, clown.getIconWidth(), clown.getIconHeight());
		observers = new ArrayList<Observer>();
		addObserver(new PlayerObserver());
		score = 0;
	}
	
	public JLabel getPlayerLabel() {
		return player;
	}
	
	//stackwidth = 30
	//clownwidth = 120

	@Override
	public void update(ObservableClass o, Object arg) {
		if (arg == null) {
			return;
		}
		if (arg instanceof PlateIF) {
			if (checkPlate((PlateIF) arg)) {
				// mov plates using state design pattern
				notifyAllObservers(this);
			}
			return;
		}
		List<PlateIF> list = (List<PlateIF>) arg;
		for (int i = 0; i < list.size(); i++) {
			PlateIF plate = list.get(i);
			if (checkPlate((PlateIF) plate)) {
				// mov plates using state design pattern
				notifyAllObservers(this);
			}
		}
	}
	
	private boolean checkPlate(PlateIF plate) {
		if (plate.isOnStack()) {
			return false;
		}
		if (checkPlateXOnLeftStack(plate) && checkPlateYOnLeftStack(plate)) {
			plate.setMove(true);
			this.leftPlates.add(plate);
			System.out.println(true);
			return true;
		} else if (checkPlateXOnRightStack(plate) && checkPlateYOnRightStack(plate)) {
			plate.setMove(true);
			this.rightPlates.add(plate);
			System.out.println(true);
			return true;
		}
		return false;
	}
	
	private boolean checkPlateYOnLeftStack(PlateIF plate) {
		Point platePosition = plate.getCurrentPosition();
		Point playerPosition = this.getPlayerLabel().getLocation();
		int dis = (int) ((playerPosition.getY() - this.leftPlates.size() * (plate.getHeight() - 30))
				- (platePosition.getY() + plate.getHeight() - 10));
		if (dis <= this.offsetY && dis >= 0) {
			return true;
		}
		return false;
	}
	
	private boolean checkPlateYOnRightStack(PlateIF plate) {
		Point platePosition = plate.getCurrentPosition();
		Point playerPosition = this.getPlayerLabel().getLocation();
		int dis = (int) ((playerPosition.getY() - this.rightPlates.size() * (plate.getHeight() - 30))
				- (platePosition.getY() + plate.getHeight() - 10));
		if (dis <= this.offsetY && dis >= 0) {
			return true;
		}
		return false;
	}
	
	private boolean checkPlateXOnLeftStack(PlateIF plate) {
		Point platePosition = plate.getCurrentPosition();
		Point playerPosition = this.getPlayerLabel().getLocation();
		int playerWidth = this.getPlayerLabel().getWidth();
		if (
		// plate before left stack
		(platePosition.getX()  < playerPosition.getX()
				&& platePosition.getX() + plate.getWidth() / 2 + offsetX >= playerPosition.getX())
				// plate after left stack
				|| (platePosition.getX() > playerPosition.getX() + stackWidth
						&& platePosition.getX() +  plate.getWidth() / 2  - offsetX <= playerPosition.getX() + stackWidth)) {
			return true;
		}
		return false;
	}

	private boolean checkPlateXOnRightStack(PlateIF plate) {
		Point platePosition = plate.getCurrentPosition();
		Point playerPosition = this.getPlayerLabel().getLocation();
		int playerWidth = this.getPlayerLabel().getWidth();
		if (
		// plate before right stack
		(platePosition.getX() > playerPosition.getX() + playerWidth
				&& platePosition.getX() + plate.getWidth() / 2 - offsetX <= playerPosition.getX() + playerWidth)
				// plate after right stack
				|| (platePosition.getX() < playerPosition.getX() + playerWidth - stackWidth
						&& platePosition.getX()+  plate.getWidth() / 2 + offsetX >= playerPosition.getX() + playerWidth - stackWidth)) {
			return true;
		}
		return false;
	}

	public List<PlateIF> getPlatesLeft() {
		return this.leftPlates;
	}

	public List<PlateIF> getPlatesRight() {
		return this.rightPlates;
	}
	
	public void winPoint(List<PlateIF> platesStack){
		this.score++;
		this.platesThread.removePlateFromView(platesStack.get(platesStack.size()-1));
		platesStack.remove(platesStack.size()-1);
		this.platesThread.removePlateFromView(platesStack.get(platesStack.size()-1));
		platesStack.remove(platesStack.size()-1);
		this.platesThread.removePlateFromView(platesStack.get(platesStack.size()-1));
		platesStack.remove(platesStack.size()-1);
		this.scoreObject.update(this);
	}
	
	public void setPlatesThread(PlatesThread platesThread) {
		this.platesThread = platesThread;
	}
	
	public Integer getScore(){
		return new Integer(this.score);
	}
	
	public void setScoreObject(ScoreIF scoreObject){
		this.scoreObject = scoreObject;
	}
	
	public void setRightPlates(List<PlateIF> rightPlates) {
		this.rightPlates = rightPlates;
	}

	public void setLeftPlates(List<PlateIF> leftPlates) {
		this.leftPlates = leftPlates;
	}
}