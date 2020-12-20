package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import manager.ClientApp;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
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

		JButton btnTaoPhong = new JButton("T\u1EA1o Ph\u00F2ng");
		btnTaoPhong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//SquarePanel square = new SquarePanel(frameApp);
			}
		});
		btnTaoPhong.setBounds(623, 235, 274, 50);
		this.add(btnTaoPhong);

		JButton btnGhep = new JButton("Gh\u00E9p ng\u1EABu nhi\u00EAn");
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

		JLabel lblNamec = new JLabel("T\u00EAn");
		lblNamec.setFont(new Font("FreeSans", Font.BOLD | Font.ITALIC, 25));
		lblNamec.setForeground(Color.WHITE);
		lblNamec.setBounds(772, 11, 145, 50);
		this.add(lblNamec);

		JLabel lblim = new JLabel("\u0110i\u1EC3m");
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
