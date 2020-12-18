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

public class GiaoDien extends JPanel {


	private ClientApp frameApp;
	public GiaoDien(ClientApp frame) {
		frameApp = frame;
		setSize(900,600);
		this.setLayout(null);
		this.setBackground(new Color(56,15,3));

		JButton btnChiNguNhin = new JButton("\u0111\u0103ng nh\u1EADp");
		btnChiNguNhin.setBounds(599, 220, 274, 50);
		this.add(btnChiNguNhin);

		JButton btnChiCngBn = new JButton("\u0111\u0103ng k\u00FD");
		btnChiCngBn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Signup signup = new Signup(frameApp);
				
			}
		});
		btnChiCngBn.setBounds(599, 294, 274, 50);
		this.add(btnChiCngBn);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("src/View/master_checker.png"));
		lblNewLabel.setBounds(0, -15, 593, 605);
		this.add(lblNewLabel);
	}

}
