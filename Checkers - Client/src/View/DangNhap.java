package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

public class DangNhap extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DangNhap frame = new DangNhap();
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
	public DangNhap() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 900, 600);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(56,15,3));

		JButton btnToPhng = new JButton("T\u1EA1o Ph\u00F2ng");
		btnToPhng.setBounds(623, 235, 274, 50);
		contentPane.add(btnToPhng);

		JButton btnChiNguNhin = new JButton("Gh\u00E9p ng\u1EABu nhi\u00EAn");
		btnChiNguNhin.setBounds(623, 309, 274, 50);
		contentPane.add(btnChiNguNhin);

		JButton btnChiCngBn = new JButton("T\u00ECm Ph\u00F2ng");
		btnChiCngBn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnChiCngBn.setBounds(623, 383, 274, 50);
		contentPane.add(btnChiCngBn);

		JLabel lblNamec = new JLabel("T\u00EAn");
		lblNamec.setFont(new Font("FreeSans", Font.BOLD | Font.ITALIC, 25));
		lblNamec.setForeground(Color.WHITE);
		lblNamec.setBounds(772, 11, 145, 50);
		contentPane.add(lblNamec);

		JLabel lblim = new JLabel("\u0110i\u1EC3m");
		lblim.setFont(new Font("Dialog", Font.BOLD, 28));
		lblim.setForeground(Color.WHITE);
		lblim.setBounds(793, 58, 70, 50);
		contentPane.add(lblim);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("src/View/master_checker.png"));
		lblNewLabel.setBounds(0, -15, 593, 605);
		contentPane.add(lblNewLabel);
	}

}
