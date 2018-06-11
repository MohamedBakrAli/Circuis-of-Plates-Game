package saveAndLoad.Imp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import plateIF.PlateIF;
import player.Player;
import snapShot.FallingPlatesSnapShot;
import snapShot.PlayerSnapShot;

public class SaveImp implements saveAndLoad.IF.SaveIF {

	private XStream xstream;
	private String path;
	private FileWriter writer = null;
	private Saving save;

	@Override
	public void saveAsXml() {
		// TODO Auto-generated method stub
		xstream = new XStream(new StaxDriver());
		File file = new File(path);
		try {
			if (!file.exists())
				file.createNewFile();
			writer = new FileWriter(file, false);
			writer.write(xstream.toXML(save));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void read(LinkedList<PlayerSnapShot> players, FallingPlatesSnapShot plates ,String path) {
		this.path = path;
		save = new Saving(players, plates);
		saveAsXml();
	}

}
