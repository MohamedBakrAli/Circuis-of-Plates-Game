package observer;

import java.util.ArrayList;

public interface ObservableClassIF {

	public void addObserver(IObserver observer);

	public void removeObserver(IObserver observer);

	public  void notifyAllObservers(Object obj);
}
