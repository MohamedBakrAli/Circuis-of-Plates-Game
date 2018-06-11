package state;

import platesThread.PlatesThread;
import playerThread.PlayerThread;

public class PauseState implements State{
	private PlayerThread playerThread;
	private PlatesThread platesThread;
	private boolean pause;
	
	public PauseState(PlayerThread playerThread , PlatesThread platesThread) {
		this.platesThread = platesThread;
		this.playerThread = playerThread;
		pause = false;
	}
	
	@Override
	public synchronized void doAction() {
		pause = !pause;
		this.platesThread.setPause(pause);
		this.playerThread.setPause(pause);
	}
}
