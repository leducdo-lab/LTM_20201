package View;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	JLabel name1;
	Thread thread = null;
	
	public Room(ClientApp frame) {
		
		frame.dangNhap.setVisible(false);
		frameApp = frame;
		setSize(900,710);
		setLayout(null);
		frameApp.getContentPane().add(this);
		frameApp.setSize(900, 710);
		task = new Controller(frameApp.player, frameApp.fromServer, frameApp.toServer,frameApp);
		setup(task);
		boardPanel.setVisible(false);
				
		startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				try {
					frameApp.toServer.writeBytes("503 "+frameApp.player.gameID+"\n");
					while(true) {
						String giveString = frameApp.fromServer.readLine();
						String[] noi = giveString.split(" ", 2);
						int code = Integer.parseInt(noi[0].trim());
						if(code == 503) {
							startButton.setVisible(false);
							boardPanel.setVisible(true);
							
							if(thread != null) thread.stop();
							thread = new Thread(task);
							thread.start();
							break;
						}else if(code == 232) {
							JOptionPane.showMessageDialog(new JFrame(), noi[1]);
							break;
						}
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
		name1 = new JLabel("wait");
		name1.setBounds(0, 0, 100, 100);
		JButton kickButton = new JButton("Kich");
		kickButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				try {
					if(frameApp.player.getPlayerID() == 1) {
						frameApp.toServer.writeBytes("369 "+frameApp.player.getRoomID()+" "+frameApp.player.gameID+"\n");
						
						String give = "";
 						while(give.equals("")) give = frameApp.fromServer.readLine();
 						while(true) {
							if(Integer.parseInt(give.split(" ")[0].trim()) == 369) {
								name1.setText("wait");
								boardPanel.setVisible(false);
								startButton.setVisible(true);
								waitToUser();
								thread.stop();
								break;
							}else {
								give = frameApp.fromServer.readLine();
							}
 						}
					}else JOptionPane.showMessageDialog(new JFrame(), "You are not Room master","Inane error", JOptionPane.ERROR_MESSAGE);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		kickButton.setBounds(10, 100, 100, 100);
		player1.add(name1);
		player1.add(kickButton);
		add(player1);
		
		JLabel roomID = new JLabel(frameApp.player.getRoomID()+"");
		roomID.setBounds(801, 16, 111, 33);
		add(roomID);
		
		JLabel lblNewLabel = new JLabel("RoomID :");
		lblNewLabel.setBounds(727, 20, 64, 24);
		add(lblNewLabel);

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
	
	@SuppressWarnings("deprecation")
	public void waitToPlay() {
			JOptionPane.showMessageDialog(new JFrame(), "Wait to Room Master click start\n");
			try {
				while(true) {
					String give = frameApp.fromServer.readLine();
					System.out.println(give);
					String[] noiStrings =give.split(" ");
					int code = Integer.parseInt(noiStrings[0].trim());
					if(code == 503) {
						startButton.setVisible(false);
						boardPanel.setVisible(true);
						name1.setText(noiStrings[1]);
						if(thread != null) thread.stop();
						thread = new Thread(task);
						thread.start();
						break;
					} else if(code == 369) {
						JOptionPane.showMessageDialog(new JFrame(), "You are kicked\n");
						this.setVisible(false);
						frameApp.dangNhap.setVisible(true);
						break;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void waitToUser() {
		JOptionPane.showMessageDialog(new JFrame(), "Wait to other user\n");
		try {
			String give = frameApp.fromServer.readLine();
			String[] noiStrings =give.split(" ");
			name1.setText(noiStrings[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reset() {
		this.remove(boardPanel);
		setup(task);
		boardPanel.setVisible(false);
		startButton.setVisible(true);
		if(frameApp.player.getPlayerID() == 2) {
			waitToPlay();
		}
	}
}
