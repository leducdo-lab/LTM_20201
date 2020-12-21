package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import manager.ClientApp;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextField;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	
	private ClientApp frameApp;

	public Login(ClientApp frame) {
		frameApp = frame;
		setVisible(true);
		setBounds(550, 200, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("");
		label.setBounds(5, 5, 430, 0);
		contentPane.add(label);
		
		JLabel lblngNhp = new JLabel("\u0110\u0103ng Nh\u1EADp");
		lblngNhp.setFont(new Font("Dialog", Font.BOLD, 22));
		lblngNhp.setBounds(161, 12, 148, 36);
		contentPane.add(lblngNhp);

		JLabel lblUsername = new JLabel("T\\u00EAn :");
		lblUsername.setBounds(30, 84, 106, 15);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("M\\u1EADt kh\\u1EA9u: ");
		lblPassword.setBounds(30, 137, 106, 15);
		contentPane.add(lblPassword);

		textField = new JTextField();
		textField.setBounds(163, 76, 196, 32);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(163, 129, 196, 32);
		contentPane.add(textField_1);

		JButton btnngK = new JButton("\u0110\u0103ng nh\u1EADp");
		btnngK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameString = textField.getText();
				String passwordString = textField_1.getText();
				if(nameString.indexOf(' ') != -1 || passwordString.indexOf(' ') != -1) {
					JOptionPane.showMessageDialog(new JFrame(), "Khong nhap space","Inane error", JOptionPane.ERROR_MESSAGE);
				}else {
					String sendString = "501 " +nameString + " " + passwordString+"\n";
					try {
						frameApp.toServer.writeBytes(sendString);
						
						String giveString = frameApp.fromServer.readLine();
						String[] noi = giveString.split(" ", 2);
						int code = Integer.parseInt(noi[0]);
						if(code == 232) 
							JOptionPane.showMessageDialog(new JFrame(), noi[1], "Inane error", JOptionPane.ERROR_MESSAGE);
						else {
							String[] player = noi[1].split(" ");
							frameApp.player.setPlayerID(Integer.parseInt(player[0]));
							frameApp.player.setName(nameString);
							frameApp.player.setSocre(Integer.parseInt(player[1]));
							frameApp.logined();
							dispose();
						}
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnngK.setBounds(156, 196, 117, 25);
		contentPane.add(btnngK);
	}

}
