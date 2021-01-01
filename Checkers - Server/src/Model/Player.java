package Model;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Server Application -> Player
 * @author Siyar
 * 
 * Player Model
 */
public class Player{
	private int PlayerID;
	private Socket socket;
	private BufferedReader fromPlayer;
	private DataOutputStream toPlayer;
	
	
	public Player(int ID, Socket s){
		this.PlayerID = ID;
		this.socket = s;
		
		try{
			fromPlayer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			toPlayer = new DataOutputStream(socket.getOutputStream());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public int sendData(int data){
//		try {
//			this.toPlayer.writeBytes(data+"\n");
//			return 1; //Successfull
//		} catch (IOException e) {
//			System.out.println("sending: Player not found");
//			//e.printStackTrace();
//			return 99;	//failure
//		}	
		return 1;
	}
	
	public int receiveData(){
		int data = 0;;
//		try{
//			String give = this.fromPlayer.readLine();
//			data = Integer.parseInt(give);
//			return data;
//		}catch (IOException e) {
//			System.out.println("Waiting: No respond from Player");
//			return 99;
//		}
		return data;
	}
	
	public void closeConnection(){
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isOnline(){
		return socket.isConnected();
	}
}
