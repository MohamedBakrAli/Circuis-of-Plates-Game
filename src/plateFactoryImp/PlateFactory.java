package plateFactoryImp;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import dynamicLinkageImp.DynamicLinkageImp;
import level.DifficulityLevel;
import observer.IObserver;
import plateFactoryIF.PlateFactoryIF;
import plateIF.PlateIF;

public class PlateFactory implements PlateFactoryIF {

	private DynamicLinkageImp dynamicLinkageImp;
	private ArrayList<Constructor<PlateIF>> constructors;
	private DifficulityLevel level;

	public PlateFactory(DifficulityLevel level) {
		this.level = level;
		dynamicLinkageImp = DynamicLinkageImp.getInstance();
		constructors = dynamicLinkageImp.invokeClassMethod();
	}

	public PlateIF getObject(List<IObserver> players) {
		Random random = new Random();
		int index = random.nextInt(constructors.size());

		Constructor<PlateIF> plateClass = constructors.get(index);
		List<Color> colors = getColors(level);
		int colorNum = random.nextInt(colors.size());
		PlateIF shape;
		try {
			shape = constructors.get(index).newInstance(colors.get(colorNum));
			shape.addPlayersObservers(players.get(0), players.get(1));
		} catch (Exception e) {
			throw new RuntimeException("Can't construct object in factory.");
		}
		return shape;
	}

	private List<Color> getColors(DifficulityLevel level) {
		List<Color> colors = new ArrayList<Color>();
		switch (level) {
		case Hard: {
			colors.add(Color.blue);
			colors.add(Color.green);
			colors.add(Color.red);
			colors.add(Color.orange);
			colors.add(Color.yellow);
			
			break;
		}
		case Meduim: {
			colors.add(Color.blue);
			colors.add(Color.green);
			//colors.add(Color.red);
			colors.add(Color.orange);
			break;
		}
		case Easy: {
			colors.add(Color.blue);
			colors.add(Color.green);
			//colors.add(Color.red);
			break;
		}
		default:
			throw new AssertionError("Unknown Level ");
		}
		return colors;
	}
}
