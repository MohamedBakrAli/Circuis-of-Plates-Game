package controller;

import java.awt.*;

import java.awt.event.*;
import java.net.*;
import java.util.Map;
import java.util.HashMap;
import javax.swing.*;

import player.Player;

public class KeyboardAnimation implements ActionListener {
	private final String PRESSED = "pressed ";
	private final String RELEASED = "released ";
	private boolean pause;
	private JComponent component;
	private Timer timer;
	private Map<String, Integer> pressedKeys = new HashMap<String, Integer>();
	private Player player;

	public KeyboardAnimation(Player component, int delay) {
		this.player = component;
		this.component = component.getPlayerLabel();
		timer = new Timer(delay, this);
		timer.setInitialDelay(0);
		pause = false;
	}

	public void addAction(String keyStroke, int deltaX) {
		// Separate the key identifier from the modifiers of the KeyStroke

		int offset = keyStroke.lastIndexOf(" ");
		String key = offset == -1 ? keyStroke : keyStroke.substring(offset + 1);
		String modifiers = keyStroke.replace(key, "");
		System.out.println(modifiers);
		// Get the InputMap and ActionMap of the component

		InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = component.getActionMap();

		// Create Action and add binding for the pressed key

		Action pressedAction = new AnimationAction(key, deltaX);
		String pressedKey = modifiers + PRESSED + key;
		KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(pressedKey);
		inputMap.put(pressedKeyStroke, pressedKey);
		actionMap.put(pressedKey, pressedAction);

		// Create Action and add binding for the released key

		Action releasedAction = new AnimationAction(key, 0);
		String releasedKey = modifiers + RELEASED + key;
		KeyStroke releasedKeyStroke = KeyStroke.getKeyStroke(releasedKey);
		inputMap.put(releasedKeyStroke, releasedKey);
		actionMap.put(releasedKey, releasedAction);
	}

	// Invoked whenever a key is pressed or released

	private void handleKeyEvent(String key, int moveDelta) {
		// Keep track of which keys are pressed

		if (moveDelta == 0)
			pressedKeys.remove(key);
		else
			pressedKeys.put(key, moveDelta);

		// Start the Timer when the first key is pressed

		if (pressedKeys.size() == 1) {
			timer.start();
		}

		// Stop the Timer when all keys have been released

		if (pressedKeys.size() == 0) {
			timer.stop();
		}
	}

	// Invoked when the Timer fires

	public void actionPerformed(ActionEvent e) {
		moveComponent();
	}

	// Move the component to its new location

	private void moveComponent() {
		int componentWidth = component.getSize().width;

		Dimension parentSize = component.getParent().getSize();
		int parentWidth = parentSize.width;

		// Calculate new move

		int deltaX = 0;

		for (int delta : pressedKeys.values()) {
			deltaX += delta;
		}

		// Determine next X position

		int nextX = Math.max(component.getLocation().x + deltaX, 0);

		if (nextX + componentWidth > parentWidth) {
			nextX = parentWidth - componentWidth;
		}

		// Move the component
		//System.out.println(player.getPlatesLeft().size());
		//System.out.println(player.getPlatesRight().size());
		if(pause)return;
		component.setLocation(nextX, component.getY());
		if (player.getPlatesLeft().size() > 0) {
			for (int i = 0; i < player.getPlatesLeft().size(); i++) {
				player.getPlatesLeft().get(i).setPosition(
						(int) player.getPlatesLeft().get(i).getCurrentPosition().getX() + deltaX,
						(int) player.getPlatesLeft().get(i).getCurrentPosition().getY());
			}
		}
		if (player.getPlatesRight().size() > 0) {
			for (int i = 0; i < player.getPlatesRight().size(); i++) {
				player.getPlatesRight().get(i).setPosition(
						(int) player.getPlatesRight().get(i).getCurrentPosition().getX() + deltaX,
						(int) player.getPlatesRight().get(i).getCurrentPosition().getY());
			}
		}

	}

	public void setPause(boolean pause){
		this.pause = pause;
	}
	
	// Action to keep track of the key and a Point to represent the movement
	// of the component. A null Point is specified when the key is released.

	private class AnimationAction extends AbstractAction implements ActionListener {
		private int moveDelta;

		public AnimationAction(String key, int moveDelta) {
			super(key);

			this.moveDelta = moveDelta;
		}

		public void actionPerformed(ActionEvent e) {
			handleKeyEvent((String) getValue(NAME), moveDelta);
		}
	}
}