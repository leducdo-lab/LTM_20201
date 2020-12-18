package manager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import EnumConstants.Checkers;
import Handler.Controller;
import Handler.MyMouseListener;
import Model.Player;
import View.BoardPanel;
import View.GiaoDien;

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
	private Player player;

	// View
	private BoardPanel boardPanel;

	// Network properties
	private Socket connection;
	public DataInputStream fromServer;
	public DataOutputStream toServer;

	// Constructor
	public ClientApp() {

		try {
			PropertyManager pm = PropertyManager.getInstance();
			address = "172.18.91.113";
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
			fromServer = new DataInputStream(connection.getInputStream());
			toServer = new DataOutputStream(connection.getOutputStream());

			// First player get 1 and second player get 2
			player.setPlayerID(fromServer.readInt());
			
			Controller task = new Controller(player, fromServer, toServer);
			setup(task);
			new Thread(task).start();
			
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

	private void setup(Controller c) {
		MyMouseListener listener = new MyMouseListener();
		listener.setController(c);

		boardPanel = new BoardPanel(listener);
		c.setBoardPanel(boardPanel);
		boardPanel.setVisible(false);
		add(boardPanel);
	}
	
	public void waitRoom() {
		
		GiaoDien giaoDien = new GiaoDien(this);
		
		this.add(giaoDien);
	}
}
