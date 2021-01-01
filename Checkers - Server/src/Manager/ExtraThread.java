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

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Session.HandleSession;


public class ExtraThread extends Thread {
	public int id;
	ArrayList<ExtraThread> list;
	public Socket socket;
	private Connection conn;
	public HandleSession handleSession = null ;
	//private Statement statement;
	public BufferedReader in;
	public DataOutputStream out;
	
	public ExtraThread(Socket socket, ArrayList<ExtraThread> list ) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.socket = socket;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		try {
			int i = 0;
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
			while(!socket.isClosed()) {
				String string = in.readLine();
				System.out.println("from client: "+string);
				String[] noi = string.split(" ");
				int code = Integer.parseInt(noi[0]);
				switch (code) {
				case 500: {
					System.out.println("500");
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
					String userNameString;
					if(roomid > 0) {
						userNameString = getName(roomid);
						rommString = "322 "+userNameString+" "+noi[1]+"\n";
						out.writeBytes(rommString);
						
						for(int index = 0; index < list.size(); index++){
							if(list.get(index).id == roomid) {
								userNameString = getName(Integer.parseInt(noi[2].trim()));
								list.get(index).comeRoom(userNameString, noi[1]);
								break;
							}
							
						}
					} else if(roomid == 0) {
						rommString = "232 Phong khong ton tai \n";
						out.writeBytes(rommString);
					} else {
						rommString = "232 Phong da du nguoi \n";
						out.writeBytes(rommString);
					}
				}
				break;
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
						String userNameString = getName(masterID);
						out.writeBytes("322 "+userNameString+" "+roomid+"\n");
						for(int index = 0; index < list.size(); index++){
							if(list.get(index).id == masterID) {
								userNameString = getName(masterID);
								list.get(index).comeRoom(userNameString, noi[1]);
								break;
							}
							
						}
					}
					break;
				
				case 503:
					int x = checkRoomMaster(noi[1]);
					if(x > 0) {
						out.writeBytes("503 1\n");
						for(int index = 0; index < list.size(); index++) {
							if(list.get(index).id == x) {								
								handleSession = new HandleSession(this, list.get(index));
								
								list.get(index).start(handleSession);
								
								new Thread(handleSession).start();
								
								break;
							}
						}
					}else if(x == 0) {
						out.writeBytes("232 wait to other player\n");
					}else {
						out.writeBytes("232 wait to room master click start\n");
					}
					break;
				case 505:
					int from = Integer.parseInt(noi[1].trim());
					int to = Integer.parseInt(noi[2].trim());
					handleSession.playerGo(from, to, Integer.parseInt(noi[3].trim()));
					break;
					
				case 369:
					int userID = checkRoomMaster(noi[2]);
					if(userID > 0) {
						kick(userID);
						out.writeBytes("369\n");
						if(handleSession!=null) {
							handleSession.wait();
							
						}
						for(int index = 0; index < list.size(); index++) {
							if(list.get(index).id == userID) {		
								list.get(index).out.writeBytes("369\n");
								break;
							}
						}
					}else {
						out.writeBytes("232\n");
					}
					break;
				
				case 0:
					quit();
					if(handleSession != null) {
						handleSession.wait();
						handleSession = null;
					}
					out.writeBytes("0\n");
					socket.close();
					list.remove(this);
					this.stop();
					break;
				default:
					
					break;
				}
				//conn.close();
				
			}
			//socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
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
	     String password = "123456";
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
				statement.executeUpdate("INSERT INTO Users VALUES ('"+name+"','"+password+"',0,1)");
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
			ResultSet rSet = statement.executeQuery("SELECT * FROM Users WHERE Name = '"+username+"' AND Pass = '"+password+"' AND Statuss = 1");
			
			if (rSet.next()) {
				id = rSet.getInt("UserID");
				statement.executeUpdate("UPDATE Users SET Statuss = 0 WHERE UserID = "+id);
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
			ResultSet rSet = statement.executeQuery("SELECT * FROM Room WHERE RoomID = "+roomid+" AND UserID > 0");
			if(rSet.next()) {
				return -2;
			}
			rSet = statement.executeQuery("SELECT * FROM Room WHERE RoomID = "+roomid);
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
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Room ");
			int roomid = 0;
			while(resultSet.next()) {
				System.out.println(resultSet.getInt("UserID"));
				if(resultSet.getInt("UserID") > 0) continue;
				roomid = resultSet.getInt("RoomID");
				return roomid;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public void comeRoom(String userName, String roomID) {
		try {
			
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			String sendString = "322 "+userName+" "+roomID+"\n";
			
			System.out.println(sendString);
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
				System.out.println("User ID : "+resultSet.getInt("UserID"));
				return resultSet.getInt("UserID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public void start(HandleSession handleSession) {
		try {
			this.handleSession = handleSession;
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			String sendString = "503 2\n";
			System.out.println(sendString);
			out.writeBytes(sendString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getName(int UserID) {
		Statement statement;
		try {
			statement = conn.createStatement();
			ResultSet rSet = statement.executeQuery("SELECT Name FROM Users WHERE UserID = "+UserID);
			rSet.next();
			return rSet.getString("Name");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public void kick(int userID) {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("UPDATE Room SET UserID = NULL WHERE UserID = "+userID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateScore() {
		Statement statement;
		try {
			statement = conn.createStatement();
			ResultSet rSet = statement.executeQuery("SELECT Score FROM Users WHERE UserID = "+id);
			rSet.next();
			
			int score = rSet.getInt("Score");
			score++;
			statement.executeUpdate("UPDATE Users SET Score = "+score+" WHERE UserID = "+id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void quit() {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("UPDATE Users SET Statuss = 1 WHERE UserID = "+id);
			if(handleSession!=null) {
				
				if(id == handleSession.pExtraThread1.id) {
					kick(handleSession.pExtraThread2.id);
					handleSession.pExtraThread2.out.writeBytes("369\n");
					statement.executeUpdate("DELETE Room Where RMaster = "+id);
					
				}else {
					handleSession.pExtraThread1.out.writeBytes("232 other user has out\n");
					statement.executeUpdate("UPDATE Room SET UserID = NULL Where UserID = "+id);
				}
				
			}else {
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

