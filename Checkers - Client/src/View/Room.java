package View;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Handler.Controller;
import Handler.MyMouseListener;
import manager.ClientApp;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Room extends JPanel {

	/**
	 * Create the panel.
	 */
	private BoardPanel boardPanel;
	private ClientApp frameApp;
	Controller task;
	JButton startButton;
	JPanel player1, player2;
	
	public Room(ClientApp frame) {
		frameApp = frame;
		setSize(900,710);
		setLayout(null);
		frameApp.getContentPane().add(this);
		frameApp.setSize(900, 710);
		task = new Controller(frameApp.player, frameApp.fromServer, frameApp.toServer);
		setup(task);
		boardPanel.setVisible(false);
		frameApp.dangNhap.setVisible(false);
		
		startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startButton.setVisible(false);
				boardPanel.setVisible(true);
				new Thread(task).start();
			}
		});
		startButton.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 15));
		startButton.setBounds(400, 300, 200, 100);
		add(startButton);
		
		player1 = new JPanel();
		player1.setLayout(null);
		player1.setBounds(750, 100, 150, 300);
		JLabel name1 = new JLabel("name 1");
		name1.setBounds(0, 0, 100, 100);
		JButton kickButton = new JButton("Kich");
		kickButton.setBounds(10, 100, 100, 100);
		player1.add(name1);
		player1.add(kickButton);
		add(player1);
		
		player2 = new JPanel();
		player2.setLayout(null);
		player2.setBounds(750, 520, 250, 300);
		JLabel name2 = new JLabel(frameApp.player.getName());
		name2.setBounds(0, 0, 200, 200);
		JLabel scoreJLabel = new JLabel(frameApp.player.getSocre()+"");
		player2.add(name2);
		player2.add(scoreJLabel);
		add(player2);
		
		
		
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				frameApp.dangNhap.setVisible(true);
			}
		});
		quitButton.setBounds(771, 12, 117, 50);
		add(quitButton);
	}
	private void setup(Controller c) {
		MyMouseListener listener = new MyMouseListener();
		listener.setController(c);

		boardPanel = new BoardPanel(listener);
		boardPanel.setSize(720, 730);
		c.setBoardPanel(boardPanel);
		this.add(boardPanel);
		
	}
}
