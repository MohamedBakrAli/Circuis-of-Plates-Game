package playerIF;

import java.util.List;
import java.util.Observer;

import javax.swing.JLabel;

import Score.ScoreIF;
import plateIF.PlateIF;
import platesThread.PlatesThread;

public interface PlayerIF extends Observer{
	
	public JLabel getPlayerLabel();
	
	public List<PlateIF> getPlatesLeft();
	
	public List<PlateIF> getPlatesRight();
	
	public void winPoint(List<PlateIF> platesStack);
	
	public void setPlatesThread(PlatesThread platesThread);
	
	public Integer getScore();
	
	public void setScoreObject(ScoreIF scoreObject);
	
	public void setRightPlates(List<PlateIF> rightPlates);
	
	public void setLeftPlates(List<PlateIF> leftPlates);
}
