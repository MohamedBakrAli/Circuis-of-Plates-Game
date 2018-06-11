package snapShot;

import java.util.ArrayList;
import java.util.List;

import plateIF.PlateIF;

public class FallingPlatesSnapShot {

	private List<PlateIF> plates;

	public FallingPlatesSnapShot(List<PlateIF> plates) {
		//plates form thread.
		this.plates = clone(plates);
	}

	public List<PlateIF> getPlates() {
		return this.plates;
	}

	public List<PlateIF> clone(List<PlateIF> plates) {
		List<PlateIF> newPlates = new ArrayList<PlateIF>();
		for (int i = 0; i < plates.size(); i++) {
			newPlates.add(plates.get(i).clone());
		}
		return newPlates;
	}
}
