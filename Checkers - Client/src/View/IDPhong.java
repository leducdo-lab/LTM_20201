package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import manager.ClientApp;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class IDPhong extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	private ClientApp frameApp;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public IDPhong(ClientApp frame) {
		frameApp = frame;
		setVisible(true);
		setBounds(550, 200, 400, 230);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNhpIdPhng = new JLabel("Nh\u1EADp ID ph\u00F2ng");
		lblNhpIdPhng.setFont(new Font("Dialog", Font.BOLD, 22));
		lblNhpIdPhng.setBounds(95, 22, 225, 25);
		contentPane.add(lblNhpIdPhng);

		textField = new JTextField();
		textField.setBounds(78, 73, 225, 37);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnJ = new JButton("Join");
		btnJ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText() != null) {
					String roomID = textField.getText();
					if(!roomID.matches("(\\d+)?")) {
						JOptionPane.showMessageDialog(new JFrame(), "Khong ton tai room ID","Inane error", JOptionPane.ERROR_MESSAGE);
					}else {
						try {
							String sendString = "322 "+roomID+" "+frameApp.player.getPlayerID()+"\n";
							
							frameApp.toServer.writeBytes(sendString);
							
							String giveString = frameApp.fromServer.readLine();
							
							
							String noi[] = giveString.split(" ", 2);
							int code = Integer.parseInt(noi[0]);
							switch (code) {
							case 232:
								JOptionPane.showMessageDialog(new JFrame(), noi[1],"Inane error", JOptionPane.ERROR_MESSAGE);
								break;
							case 322:
								Room room = new Room(frameApp);
								//frameApp.player.setRoomID(Integer.parseInt(noi[1].split(" ")[1]));
								dispose();
								break;

							default:
								break;
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// switch to room
					}
				}
			}
		});
		btnJ.setBounds(132, 137, 117, 37);
		contentPane.add(btnJ);
	}

}
