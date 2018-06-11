package observer;

import java.util.ArrayList;

public class ObservableClass {
	public ArrayList<IObserver> observers;
	
	public ObservableClass() {
		observers = new ArrayList<IObserver>();
	}
	
	public synchronized void addObserver(IObserver observer){
		observers.add(observer);
	}
	//edited by me
	public synchronized void removeObserver(IObserver observer){
		observers.clear();
	}
	
	public synchronized void notifyAllObservers(Object obj){
		for(IObserver observer : observers){
			observer.update(this, obj);
		}
	}
}
