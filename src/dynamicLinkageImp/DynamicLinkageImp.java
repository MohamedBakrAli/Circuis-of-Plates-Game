package dynamicLinkageImp;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import dynamicLinkageIF.DynamicLinkageIF;
import plateIF.PlateIF;

public class DynamicLinkageImp implements DynamicLinkageIF {

	private static DynamicLinkageImp instance;
	private DynamicLinkageImp() {
		
	}
	
	public ArrayList<Constructor<PlateIF>> invokeClassMethod() {
		String[] classesNames = names();
		ArrayList<Constructor<PlateIF>> constructors = new ArrayList<Constructor<PlateIF>>();
		for (int i = 0; i < classesNames.length; i++) {
			Class<PlateIF> plate;
			try {
				plate = (Class<PlateIF>) Class.forName("plateImp." + classesNames[i]);
				Constructor<PlateIF> constructor = plate.getConstructor(Color.class);
				constructors.add(constructor);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return constructors;
	}

	public String[] names() {
		File folderOfPlates = new File("C:\\Users\\amrmh_000\\Desktop\\BeforeColor\\src\\plateImp");
		String[] classes = folderOfPlates.list();
		List<String> editedClasses = new ArrayList<String>();
		for (int i = 0; i < classes.length; i++) {
			if (!classes[i].toLowerCase().contains(".java"))
				continue;
			classes[i] = classes[i].substring(0, classes[i].length() - 5);
			editedClasses.add(classes[i]);
		}
		String editClasses[] = new String[editedClasses.size()];
		int ind = 0;
		for (String str : editedClasses)
			editClasses[ind++] = str;
		return editClasses;
	}
	
	public static DynamicLinkageImp getInstance(){
		if(instance == null){
			instance = new DynamicLinkageImp();
		}
		return instance;
	}
}
