package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblngNhp = new JLabel("\u0110\u0103ng Nh\u1EADp");
		lblngNhp.setFont(new Font("Dialog", Font.BOLD, 22));
		lblngNhp.setBounds(161, 12, 148, 36);
		contentPane.add(lblngNhp);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(30, 84, 106, 15);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password: ");
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
		btnngK.setBounds(156, 196, 117, 25);
		contentPane.add(btnngK);
	}

}
