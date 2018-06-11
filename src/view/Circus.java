package view;
 
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import Score.Score;
import Score.ScoreIF;
import level.DifficulityLevel;
import observer.IObserver;
import plateIF.PlateIF;
import platesThread.PlatesThread;
import player.Player;
import playerThread.PlayerThread;
import saveAndLoad.Imp.LoadImp;
import saveAndLoad.Imp.SaveImp;
import snapShot.FallingPlatesSnapShot;
import snapShot.PlayerSnapShot;
import state.PauseState;
import state.State;
 
public class Circus {
    private ImageIcon circusIcon;
    private ImageIcon circusFirst;
    Image circus1 , circus2;
    JFrame startFrame;
    public Circus() {
        InitializeImages();
        circus1 = circusIcon.getImage();
        circus2 = circusFirst.getImage();
        Button decideButton1=new Button("Easy");
        Button decideButton2=new Button("Medium");
        Button decideButton3=new Button("Hard");
        decideButton1.setSize(100, 50);
        decideButton2.setSize(100, 50);
        decideButton3.setSize(100, 50);
        decideButton1.setLocation(450, 430);
        decideButton2.setLocation(650, 430);
        decideButton3.setLocation(850, 430);
 
        decideButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	startGame(DifficulityLevel.Easy);
            }
        });
        decideButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(DifficulityLevel.Meduim);
            }
        });
        decideButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	startGame(DifficulityLevel.Hard);
            }
        });
        startFrame = new JFrame ("Circus of plates");
        startFrame.setSize(startFrame.getMaximumSize());
        Background  firstView = new Background(circus2);
        firstView.setLayout(null);
        startFrame.getContentPane().add(firstView);
        firstView.repaint();
        firstView.add(decideButton1);
        firstView.add(decideButton2);
        firstView.add(decideButton3);
 
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setLocationRelativeTo(null);
        startFrame.setVisible(true);
        startFrame.setResizable(false);
    }
    private void InitializeImages() {
        circusIcon = new ImageIcon(getClass().getResource("circus.jpg"));
        circusFirst = new ImageIcon(getClass().getResource("circus.jpg"));
    }
    
    private void startGame(DifficulityLevel gameLevel){
    	PlayerThread playerThread = new PlayerThread();
        playerThread.start();
        
        ScoreIF score = new Score(playerThread.getPlayer1(), playerThread.getPlayer2());
        playerThread.getPlayer1().setScoreObject(score);
        playerThread.getPlayer2().setScoreObject(score);
        
        JFrame whole = new JFrame("Circus of plates");
        whole.setSize(whole.getMaximumSize());
        Background gameBackground = new Background(circus1);
       
        ArrayList<IObserver> players = new ArrayList<IObserver>();
        players.add(playerThread.getPlayer1());
        players.add(playerThread.getPlayer2());
		//System.out.println("in circuis startGame " + players);
        PlatesThread plateThread = new PlatesThread(gameLevel , gameBackground, players);
        playerThread.setPlatesThread(plateThread);
        plateThread.start();
        
        
        gameBackground.setLayout(null);
        gameBackground.add(playerThread.getPlayer1().getPlayerLabel());
        gameBackground.add(playerThread.getPlayer2().getPlayerLabel());
        
        //save and load
        JMenuBar menuBar = new JMenuBar();
		whole.setJMenuBar(menuBar);

		JMenu file = new JMenu("File");
		menuBar.add(file);

		JMenuItem save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				SaveImp saveObj = new SaveImp();
				LinkedList<PlayerSnapShot> snapShotPlayers = new LinkedList<PlayerSnapShot>();
				snapShotPlayers.add(new PlayerSnapShot(playerThread.getPlayer1()));
				snapShotPlayers.add(new PlayerSnapShot(playerThread.getPlayer2()));
				FallingPlatesSnapShot plates = new FallingPlatesSnapShot(plateThread.getPlates());
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					saveObj.read(snapShotPlayers, plates, fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		State pauseState  = new PauseState(playerThread, plateThread);
		JMenuItem pause = new JMenuItem("Pause");
		pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.out.println("pause");
				pauseState.doAction();
			}
		});

		JMenuItem load = new JMenuItem("Load");
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					LoadImp loadObj = new LoadImp(fileChooser.getSelectedFile().getAbsolutePath());
					loadObj.readAsXml();
					gameBackground.remove(playerThread.getPlayer1().getPlayerLabel());
					gameBackground.remove(playerThread.getPlayer2().getPlayerLabel());
					FallingPlatesSnapShot plates = loadObj.getPlates();
					LinkedList<PlayerSnapShot> snapShotPlayers = loadObj.getPlayer();
					ArrayList<PlateIF> list = (ArrayList<PlateIF>) plates.getPlates();
					Player player1 = snapShotPlayers.get(0).getPlayer();
					Player player2 = snapShotPlayers.get(1).getPlayer();
					list.get(0).removeObserver(player1);
					for (int i = 0; i < snapShotPlayers.get(0).getPlayer().getPlatesLeft().size(); i++) {
						gameBackground.add(snapShotPlayers.get(0).getPlayer().getPlatesLeft().get(i).getLabel());
					}

					for (int i = 0; i < snapShotPlayers.get(0).getPlayer().getPlatesRight().size(); i++) {
						gameBackground.add(snapShotPlayers.get(0).getPlayer().getPlatesRight().get(i).getLabel());
					}
					player1.setLeftPlates(snapShotPlayers.get(0).getPlayer().getPlatesLeft());
					player1.setRightPlates(snapShotPlayers.get(0).getPlayer().getPlatesRight());
					playerThread.setPlayer1(player1);
					gameBackground.add(player1.getPlayerLabel());
					for (int i = 0; i < snapShotPlayers.get(1).getPlayer().getPlatesLeft().size(); i++) {
						gameBackground.add(snapShotPlayers.get(1).getPlayer().getPlatesLeft().get(i).getLabel());
					}

					for (int i = 0; i < snapShotPlayers.get(1).getPlayer().getPlatesRight().size(); i++) {
						gameBackground.add(snapShotPlayers.get(1).getPlayer().getPlatesRight().get(i).getLabel());
					}
					player2 = snapShotPlayers.get(1).getPlayer();
					player2.setLeftPlates(snapShotPlayers.get(1).getPlayer().getPlatesLeft());
					player2.setRightPlates(snapShotPlayers.get(1).getPlayer().getPlatesRight());
					plateThread.setPlates(plates.getPlates(),player1,player2);
					playerThread.setPlayer2(player2);
					gameBackground.add(player2.getPlayerLabel());

					playerThread.run();
				}

			}
		});

		file.add(save);
		file.add(load);
		file.add(pause);
		
		
        whole.getContentPane().add(gameBackground);
        Label textArea = score.getTextArea();
        textArea.setSize(200, 50);
        gameBackground.repaint();
        gameBackground.add(textArea);
        whole.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        whole.setLocationRelativeTo(null);
        whole.setVisible(true);
        whole.setResizable(false);
        startFrame.setVisible(false);
    }
}