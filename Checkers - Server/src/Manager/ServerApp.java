package Manager;
import java.awt.BorderLayout;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

import EnumConstants.Checkers;
import Session.HandleSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server Application -> ServerApp
 * @author Keerthikan
 * 
 * Server and establish Connection
 */
public class ServerApp extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public final int NUM_OF_THREAD = 4;
	//Frame components
	private JScrollPane scroll;
	private JTextArea information;
	private JLabel title;
	
	//Network properties
	private ServerSocket serverSocket;
	int sessionNo;
	ArrayList<ExtraThread> list;
	
	public ServerApp(){
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		
		title = new JLabel("Server");
		information = new JTextArea();
		scroll = new JScrollPane(information);
		
		add(title,BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		list = new ArrayList<ExtraThread>();
	}	
	
	//Establish connection and wait for Clients
	public void startRunning(){
		
		try{
			ExecutorService executor = Executors.newFixedThreadPool(NUM_OF_THREAD);
			
			PropertyManager pm = PropertyManager.getInstance();
			int port = pm.getPort();
			
			//Create a server socket
			serverSocket = new ServerSocket(port);
			information.append(serverSocket.getInetAddress().getHostAddress());
			information.append(new Date() + ":- Server start at port "+ port + " \n");
			sessionNo = 1;			
			
			while(true){
				
				information.append(new Date()+ ":- Session "+ sessionNo + " is started\n");
				//Wait for player 1
				Socket player1 = serverSocket.accept();
				information.append(new Date() + ":- player1 joined at");
				information.append(player1.getInetAddress().getHostAddress() + "\n");
				
				//Notification to player1 that's he's connected successfully
				//new DataOutputStream(player1.getOutputStream()).writeInt(Checkers.PLAYER_ONE.getValue());
				
				ExtraThread extraThread = new ExtraThread(player1, list);
				
				list.add(extraThread);
				executor.execute(extraThread);
				
				//Wait for player 2
//				Socket player2 = serverSocket.accept();
//				information.append(new Date() + ":- player2 joined at");
//				information.append(player2.getInetAddress().getHostAddress() +"\n");
//				
//				//Notification to player2 that's he's connected successfully
//				new DataOutputStream(player2.getOutputStream()).writeInt(Checkers.PLAYER_TWO.getValue());
//				
//				//Increase Session number
//				sessionNo++;
//				
//				// Create a new thread for this session of two players
//				HandleSession task = new HandleSession(player1, player2);
//				new Thread(task).start();
			}
		}catch(Exception ex){			
			ex.printStackTrace();
			System.exit(0);
		}				
	}
}
