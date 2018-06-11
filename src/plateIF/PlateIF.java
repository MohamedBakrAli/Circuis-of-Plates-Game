package plateIF;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;

import move.strategy.IMoveStrategy;
import observer.IObserver;
import observer.ObservableClassIF;

public interface PlateIF extends ObservableClassIF {

	// change the plate itself
	public void move(IMoveStrategy moveStrategy);

	public Color getColor();

	public PlateIF clone();

	public int getWidth();

	public int getHeight();

	public JLabel getLabel();

	public void setPosition(int x, int y);

	public Point getPosition();

	public Point getCurrentPosition();

	public void setMove(boolean bool);

	public void addPlayersObservers(IObserver player1, IObserver player2);

	public boolean isOnStack();

}
