package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import manager.ClientApp;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Signup extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	private ClientApp frameApp;
	public Signup(ClientApp frame) {
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

		JLabel lblngK = new JLabel("\u0111\u0103ng k\u00FD");
		lblngK.setFont(new Font("Dialog", Font.BOLD, 18));
		lblngK.setBounds(163, 17, 138, 22);
		contentPane.add(lblngK);

		JLabel lblUsername = new JLabel("T\u00EAn :");
		lblUsername.setBounds(30, 84, 106, 15);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("M\u1EADt kh\u1EA9u :");
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

		JButton btnngK = new JButton("\u0110\u0103ng k\u00FD");
		btnngK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameString = textField.getText();
				String passwordString = textField_1.getText();
				String sendString = "500 " +nameString + " " + passwordString + "\n";
				try {
					frameApp.toServer.writeBytes(sendString);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnngK.setBounds(156, 196, 117, 25);
		contentPane.add(btnngK);
	}

}
