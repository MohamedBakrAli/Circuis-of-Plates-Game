package dynamicLinkageIF;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import plateIF.PlateIF;

public interface DynamicLinkageIF {
	
	public ArrayList<Constructor<PlateIF>> invokeClassMethod();
	
	public String[] names();
}
