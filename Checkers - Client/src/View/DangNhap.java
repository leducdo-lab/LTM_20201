package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import manager.ClientApp;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DangNhap extends JPanel {



	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	private ClientApp frameApp;
	public DangNhap(ClientApp frame) {
		frameApp = frame;
		setSize(900,600);
		this.setLayout(null);
		this.setBackground(new Color(56,15,3));

		JButton btnTaoPhong = new JButton("Tạo phòng");
		btnTaoPhong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				SquarePanel square = new SquarePanel(frameApp);
				String sendString = "223 "+frameApp.player.getPlayerID()+"\n";
				try {
					
					frameApp.toServer.writeBytes(sendString);
					
					String giveString = frameApp.fromServer.readLine();

					String[] roomStrings = giveString.split(" ");
					frameApp.player.setRoomID(Integer.parseInt(roomStrings[1]));
					Room room = new Room(frameApp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnTaoPhong.setBounds(623, 235, 274, 50);
		this.add(btnTaoPhong);

		JButton btnGhep = new JButton("Gh\u00E9p ng\u1EABu nhi\u00EAn");
		btnGhep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String sendString = "258 "+frameApp.player.getPlayerID()+"\n";

					
					frameApp.toServer.writeBytes(sendString);
					
					String giveString = frameApp.fromServer.readLine();

					String[] noiStrings = giveString.split(" ", 2);
					int code = Integer.parseInt(noiStrings[0]);
					switch (code) {
					case 223:
						frameApp.player.setRoomID(Integer.parseInt(noiStrings[1]));
						break;
					case 322:
						// switch to play room
						break;

					default:
						break;
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGhep.setBounds(623, 309, 274, 50);
		this.add(btnGhep);

		JButton btnTimPhong = new JButton("T\u00ECm Ph\u00F2ng");
		btnTimPhong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IDPhong id = new IDPhong(frameApp);
			}
		});
		btnTimPhong.setBounds(623, 383, 274, 50);
		this.add(btnTimPhong);

		JLabel lblNamec = new JLabel(frameApp.player.getName());
		lblNamec.setFont(new Font("FreeSans", Font.BOLD | Font.ITALIC, 25));
		lblNamec.setForeground(Color.WHITE);
		lblNamec.setBounds(772, 11, 145, 50);
		this.add(lblNamec);

		JLabel lblim = new JLabel(frameApp.player.getSocre()+"");
		lblim.setFont(new Font("Dialog", Font.BOLD, 28));
		lblim.setForeground(Color.WHITE);
		lblim.setBounds(793, 58, 70, 50);
		this.add(lblim);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("src/View/master_checker.png"));
		lblNewLabel.setBounds(0, -15, 593, 605);
		this.add(lblNewLabel);
	}

}
