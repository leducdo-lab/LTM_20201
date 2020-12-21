package Model;

import EnumConstants.SessionVariable;

/**
 * Client Application -> Player
 * @author  Siyar
 * 
 * Board Model
 */
public class Player {
	
	private String name;
	private int playerID;
	private boolean myTurn;
	private int socre;
	private int roomID;
	
	public Player(String name){
		this.name = name;
		
		setMyTurn(false);
	}

	public String getName(){
		return this.name;
	}
	
	public int getPlayerID() {
		return playerID;
	}


	public void setPlayerID(int playerID) {
		this.playerID = playerID;
		SessionVariable.myID.setValue(playerID);
	}


	public boolean isMyTurn() {
		return myTurn;
	}


	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}

	public int getSocre() {
		return socre;
	}

	public void setSocre(int socre) {
		this.socre = socre;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	
	
}
