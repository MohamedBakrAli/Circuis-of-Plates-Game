package plateImp;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import move.strategy.IMoveStrategy;
import observer.IObserver;
import observer.ObservableClass;
import plateIF.PlateIF;

public class OvalPlate extends ObservableClass implements PlateIF {
	private BufferedImage bufferedImage;
	private File imageFile;
	private Color color;
	private ImageIcon imageIcon;
	private JLabel plate;
	private Point position;
	private boolean onStack;

	private List<Observer> observers;

	public OvalPlate(Color color) {
		int x = new Random().nextInt(1500);
		plate = new JLabel();
		position = new Point(x, 0);

		String colorStr;
		if (color.equals(Color.RED)) {
			colorStr = new String("Red");
		} else if (color.equals(Color.BLUE)) {
			colorStr = new String("Blue");
		} else if (color.equals(Color.YELLOW)) {
			colorStr = new String("Yellow");
		} else if (color.equals(Color.ORANGE)) {
			colorStr = new String("Pink");
		} else {
			colorStr = new String("Green");
		}
		String path = "C:\\Users\\amrmh_000\\Desktop\\BeforeColor\\src\\plateImp";
		path += "\\OvalPlate" + colorStr + ".png";
		imageFile = new File(path);
		try {
			imageIcon = new ImageIcon(ImageIO.read(imageFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.color = color;
		plate = new JLabel(imageIcon);
		plate.setBounds(getPosition().x, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
		this.onStack = false;
	}

	@Override
	public void move(IMoveStrategy moveStrategy) {
		if (onStack)
			return;
		moveStrategy.move(this);
		this.notifyAllObservers((PlateIF) this);
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public PlateIF clone() {
		PlateIF newOval = new OvalPlate(this.color);
		newOval.setPosition((int) this.getCurrentPosition().getX(), (int) this.getCurrentPosition().getY());
		newOval.setMove(this.onStack);
		return newOval;
	}

	@Override
	public int getWidth() {
		return this.imageIcon.getIconWidth();
	}

	@Override
	public int getHeight() {
		return this.imageIcon.getIconHeight();
	}

	public JLabel getLabel() {
		return this.plate;
	}

	public void setPosition(int x, int y) {
		plate.setBounds(x, y, getWidth(), getHeight());
	}

	public Point getPosition() {
		return this.position;
	}

	public Point getCurrentPosition() {
		return this.getLabel().getLocation();
	}

	public void setMove(boolean bool) {
		this.onStack = bool;
	}

	public void addPlayersObservers(IObserver player1, IObserver player2) {
		this.addObserver(player1);
		this.addObserver(player2);
	}

	public boolean isOnStack() {
		return onStack;
	}
}
