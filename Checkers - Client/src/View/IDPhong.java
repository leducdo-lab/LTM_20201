package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class IDPhong extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IDPhong frame = new IDPhong();
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
	public IDPhong() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 230);
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
		btnJ.setBounds(132, 137, 117, 37);
		contentPane.add(btnJ);
	}

}
