package manager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import EnumConstants.Checkers;
import Handler.Controller;
import Handler.MyMouseListener;
import Model.Player;
import View.BoardPanel;
import View.DangNhap;
import View.GiaoDien;
import View.Room;

import java.io.*;
import java.net.*;
import java.util.Random;

/**
 * Client Application -> ClientApp
 * 
 * @author 
 * 
 *         ClientApp
 */
public class ClientApp extends JFrame {

	private static final long serialVersionUID = 1L;

	private String address;
	private int port;

	// Model
	public Player player;

	// View
	private BoardPanel boardPanel;

	// Network properties
	private Socket connection;
	public BufferedReader fromServer;
	public DataOutputStream toServer;
	
	GiaoDien giaoDien;
	public DangNhap dangNhap;
	public Room room;

	// Constructor
	public ClientApp() {

		try {
			PropertyManager pm = PropertyManager.getInstance();
			address = "10.90.128.209";
			port = 50800;

			String name = "50800";

			
			
			if (name != null && name.length() > 0) {
				player = new Player(name);
				connect();
			} else {
				JOptionPane.showMessageDialog(null, "Please enter valid name", "Error", JOptionPane.ERROR_MESSAGE,
						null);
				System.exit(0);
			}
		} catch (Exception ex) {
//			JOptionPane.showMessageDialog(null, "Please enter valid IPv4-Address", "Error", JOptionPane.ERROR_MESSAGE,
//					null);
//			System.exit(0);
		}

	}

	private void connect() {

		try {
			connection = new Socket(address, port);

			// Should error occurs, handle it in a seperate thread (under try)
			fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			//fromServer = new DataInputStream(connection.getInputStream());
			toServer = new DataOutputStream(connection.getOutputStream());

			this.addWindowListener(new WindowAdapter() {
				@Override
	            public void windowClosed(WindowEvent e) {
	                super.windowClosed(e);

	                //do something
	            }

	            @Override
	            public void windowClosing(WindowEvent e) {
	                super.windowClosing(e);
	                try {
	                	System.out.println("closing");
						toServer.writeBytes("0\n");
						connection.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                //do something
	            }
			});

			//Vao Trang dang ky/ dang nhap
			waitRoom();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Internal Server Error - Check your IPv4-Address", "Error",
					JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't connect to Server. Please try again", "Error",
					JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		}
	}
	
	public void waitRoom() {
		
		giaoDien = new GiaoDien(this);

		this.add(giaoDien);
	}
	
	public void logined() {
		giaoDien.setVisible(false);
		dangNhap = new DangNhap(this);
		this.add(dangNhap);
	}
}
