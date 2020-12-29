package View;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Handler.Controller;
import Handler.MyMouseListener;
import manager.ClientApp;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Room extends JPanel {

	/**
	 * Create the panel.
	 */
	private BoardPanel boardPanel;
	private ClientApp frameApp;
	Controller task;
	JButton startButton;
	JPanel player1;
	
	public Room(ClientApp frame) {
		
		frame.dangNhap.setVisible(false);
		frameApp = frame;
		setSize(900,710);
		setLayout(null);
		frameApp.getContentPane().add(this);
		frameApp.setSize(900, 710);
		task = new Controller(frameApp.player, frameApp.fromServer, frameApp.toServer);
		setup(task);
		boardPanel.setVisible(false);
		
		System.out.println("make room ok");
		
		startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					frameApp.toServer.writeBytes("503 "+frameApp.player.gameID+"\n");
					
					String giveString = frameApp.fromServer.readLine();
					String[] noi = giveString.split(" ", 2);
					int code = Integer.parseInt(noi[0].trim());
					if(code == 503) {
						frameApp.player.setPlayerID(1);
						startButton.setVisible(false);
						boardPanel.setVisible(true);
						new Thread(task).start();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		startButton.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 15));
		startButton.setBounds(400, 300, 200, 100);
		add(startButton);
		
		player1 = new JPanel();
		player1.setLayout(null);
		player1.setBounds(744, 158, 150, 300);
		JLabel name1 = new JLabel("name 1");
		name1.setBounds(0, 0, 100, 100);
		JButton kickButton = new JButton("Kich");
		kickButton.setBounds(10, 100, 100, 100);
		player1.add(name1);
		player1.add(kickButton);
		add(player1);
		
		
		
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				frameApp.dangNhap.setVisible(true);
			}
		});
		quitButton.setBounds(777, 636, 117, 50);
		add(quitButton);
		
		JLabel roomID = new JLabel(frameApp.player.getRoomID()+"");
		roomID.setBounds(746, 6, 148, 38);
		add(roomID);
		try {
			String give = frameApp.fromServer.readLine();
			frameApp.player.setPlayerID(2);
			startButton.setVisible(false);
			boardPanel.setVisible(true);
			new Thread(task).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setVisible(true);
		
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
