package Manager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import Session.HandleSession;


public class ExtraThread extends Thread {
	public int id;
	ArrayList<ExtraThread> list;
	private Socket socket;
	private Connection conn;
	//private Statement statement;

	public ExtraThread(Socket socket, ArrayList<ExtraThread> list ) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.socket = socket;
	}

	public void run() {
		try {
			int i = 0;
			Iterator<ExtraThread> iterator;
			
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			while(true) {
				String string = in.readLine();
				System.out.println("from client: "+string);
				String[] noi = string.split(" ");
				int code = Integer.parseInt(noi[0]);
				switch (code) {
				case 500: {
					int id = sigup(noi[1], noi[2]);
					if( id == -1) {
						String fString = "232 Username or Password are haven't \n";
						out.writeBytes(fString);
					}else {
						String fString = "500 "+id+" 0\n";
						this.id = id;
						out.writeBytes(fString);
					}
					break;
				}
				case 322: {
					int roomid = searchRoom(noi[1], noi[2]);
					String rommString;
					if(roomid > 0) {
						rommString = "322 "+roomid+" "+noi[1]+"\n";
						out.writeBytes(rommString);
						iterator = list.iterator();
						while(iterator.hasNext()) {
							ExtraThread extraThread = iterator.next();
							System.out.println("ohayou " +extraThread.id);
							if(extraThread.id == roomid) {
								
								extraThread.comeRoom(noi[2], noi[1]);
								
								break;
							}
							
						}
					} else {
						rommString = "232 Phong khong ton tai \n";
						out.writeBytes(rommString);
					}
				}
				case 501: {
					int id = login(noi[1], noi[2]);
					if( id == -1) {
						String fString = "232 Username or Password doesn't exit \n";
						out.writeBytes(fString);
					}else {
						String fString = "501 "+id+" "+getScore(id)+"\n";
						this.id = id;
						out.writeBytes(fString);
					}
					break;
				}
				
				case 223: {
					int id = makeRoom(noi[1]);
					String sendString = "223 "+id+"\n";
					out.writeBytes(sendString);
					break;
				}
				case 258:
					int roomid = randomRoom();
					if(roomid == 0) {
						roomid = makeRoom(noi[1]);
						String sendString = "223 "+roomid+"\n";
						out.writeBytes(sendString);
					}else {
						int masterID = searchRoom(roomid+"", noi[1]);
						out.writeBytes("322 "+masterID+" "+roomid+"\n");
					}
					break;
				
				case 503:
					int x = checkRoomMaster(noi[1]);
					if(x > 0) {
						out.writeBytes("503 1");
						for(int index = 0; index<list.size(); i++) {
							if(list.get(index).id == x) {
								list.get(index).start();
								HandleSession handleSession = new HandleSession(list.get(index).socket, this.socket);
								new Thread(handleSession).start();
								break;
							}
						}
					}
					break;
				default:
					break;
				}
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	public Connection Connect()  {
		// TODO Auto-generated constructor stub
		String hostName = "localhost";
	     String sqlInstanceName = "SQLEXPRESS";
	     String database = "Checker";
	     String userName = "SA";
	     String password = "do@1230.com";
	     String connectionURL = "jdbc:sqlserver://" + hostName + ":1433"
	             + ";instance=" + sqlInstanceName + ";databaseName=" + database;
	     Connection conn = null;
	     try {
			conn = DriverManager.getConnection(connectionURL, userName, password);
			//statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	     return conn;
	}
	public int sigup(String name, String password) {
		conn = Connect();
		try {
			Statement statement = conn.createStatement();
			ResultSet rSet = statement.executeQuery("SELECT * FROM Users WHERE Name = '"+name+"' AND Pass = '"+password+"'");
			int id = 0;
			if(rSet.next()) {
				id = rSet.getInt("UserID");
			}
			if(id == 0) {
				statement.executeUpdate("INSERT INTO Users VALUES ('"+name+"','"+password+"',0)");
				rSet = statement.executeQuery("SELECT * FROM Users WHERE Name = '"+name+"' AND Pass = '"+password+"'");
				rSet.next();
				id = rSet.getInt("UserID");

				return id;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public int login(String username, String password) {
		
		conn = Connect();
		int id = 0;
		
		try {
			Statement statement = conn.createStatement();
			ResultSet rSet = statement.executeQuery("SELECT * FROM Users WHERE Name = '"+username+"' AND Pass = '"+password+"'");
			
			
			if (rSet.next()) {
				id = rSet.getInt("UserID");
			}
			
			if (id > 0) {
				return id;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int getScore(int userID) {
		int score = 0;
		try {
			Statement statement = conn.createStatement();
			ResultSet rSet = statement.executeQuery("SELECT * FROM Users WHERE UserID = "+userID);
			
			if (rSet.next()) {
				score = rSet.getInt("Score");
			}
			
			if (score != 0) {
				return score;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int searchRoom(String roomid, String userid) {
		conn = Connect();
		try {
			Statement statement = conn.createStatement();
			ResultSet rSet = statement.executeQuery("SELECT * FROM Room WHERE RoomID = "+roomid);
			int room = 0;
			if(rSet.next()) {
				room = rSet.getInt("RoomID");
			}
			System.out.println(room);
			if(room == 0) {
				return 0;
			} else {
				statement.executeUpdate("UPDATE Room SET UserID = "+userid+" WHERE RoomID="+roomid);
				ResultSet rSet2 = statement.executeQuery("SELECT * FROM Room WHERE RoomID = "+roomid);
				rSet2.next();
				room = rSet2.getInt("RMaster");
				return room;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public int makeRoom(String masterid) {
		conn = Connect();
		try {
			Statement statement = conn.createStatement();
			
			statement.executeUpdate("INSERT INTO Room(RMaster) VALUES("+masterid+")");
			ResultSet rSet = statement.executeQuery("SELECT * FROM Room WHERE RMaster = "+masterid);
			rSet.next();
			int roomid = rSet.getInt("RoomID");
			return roomid;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int randomRoom() {
		conn = Connect();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Room WHERE UserID = NULL");
			int roomid = 0;
			resultSet.next();
			roomid = resultSet.getInt("RoomID");
			return roomid;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public void comeRoom(String userID, String roomID) {
		try {
			
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			String sendString = "322 "+userID+" "+roomID+"\n";
			out.writeBytes(sendString);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int checkRoomMaster(String userID) {
		conn = Connect();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Room WHERE RMaster = "+userID);
			if(resultSet.next()) {
				return resultSet.getInt("UserID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public void start() {
		try {
			
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			String sendString = "503 2\n";
			out.writeBytes(sendString);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

