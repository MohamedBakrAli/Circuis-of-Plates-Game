package plateFactoryIF;


import java.util.List;
import observer.IObserver;
import plateIF.PlateIF;

public interface PlateFactoryIF {
	public PlateIF getObject(List<IObserver> players);
}
