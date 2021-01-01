package Handler;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import EnumConstants.Checkers;
import Model.Player;
import Model.Square;
import View.BoardPanel;
import manager.ClientApp;

/**
 * Client Application -> Controller
 * @author DuongHo
 * 
 * ClientApp
 */
public class Controller implements Runnable {
	private boolean continueToPlay;
	private boolean waitingForAction;
	private boolean isOver;
	
	//Network
	private BufferedReader fromServer;
	private DataOutputStream toServer;
	
	private BoardPanel boardPanel;
	private Player player;
	
	//Data
	private LinkedList<Square> selectedSquares;
	private LinkedList<Square> playableSquares;
	//private LinkedList<Square> crossableSquares;
	
	private ClientApp clientApp;
	
	public Controller(Player player, BufferedReader fromServer2, DataOutputStream output, ClientApp clientApp){
		this.player = player;
		this.fromServer = fromServer2;
		this.toServer= output;
		
		selectedSquares = new LinkedList<Square>();
		playableSquares = new LinkedList<Square>();
		
		this.clientApp = clientApp;
	}
	
	public void setBoardPanel(BoardPanel panel){
		this.boardPanel = panel;
	}
	
	@Override
	public void run() {
		continueToPlay = true;
		waitingForAction = true;
		isOver=false;
		
		try {
			
			//Player One
			if(player.getPlayerID()==Checkers.PLAYER_ONE.getValue()){
				//wait for the notification to start
			    fromServer.read();
				player.setMyTurn(true);
			}
					
			while(continueToPlay && !isOver){
				if(player.getPlayerID()==Checkers.PLAYER_ONE.getValue()){
					waitForPlayerAction();
					if(!isOver)
						receiveInfoFromServer();
					
				}else if(player.getPlayerID()==Checkers.PLAYER_TWO.getValue()){
					receiveInfoFromServer();
					if(!isOver)
						waitForPlayerAction();

				}
			}
			
			if(isOver){
				JOptionPane.showMessageDialog(null, "Game is over",
						"Information", JOptionPane.INFORMATION_MESSAGE, null);
				clientApp.room.reset();
				
			}
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Connection lost",
					"Error", JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, "Connection interrupted",
					"Error", JOptionPane.ERROR_MESSAGE, null);
		}			
	}
	
	private void receiveInfoFromServer() throws IOException {
		player.setMyTurn(false);
		
		String give = "";
		//JOptionPane.showMessageDialog(new JFrame(), "Client give : " + give,"Inane error", JOptionPane.ERROR_MESSAGE);
		while(give.equals("")) {
			give = fromServer.readLine();
			
		}
		System.out.println("Give : "+give);
		String[] noi = give.split(" ");
		int code = Integer.parseInt(noi[0].trim());
		if(code == 505) {
			int from = Integer.parseInt(noi[1].trim());
			int to = Integer.parseInt(noi[2].trim());
			updateReceivedInfo(from, to);
			
		}else if(code == 369) {
			JOptionPane.showMessageDialog(new JFrame(), "You are kicked ","Inane error", JOptionPane.ERROR_MESSAGE);
			clientApp.room.setVisible(false);
			clientApp.dangNhap.setVisible(true);
			
		}else if(code == Checkers.YOU_LOSE.getValue()) {
			int from = Integer.parseInt(noi[1].trim());
			int to = Integer.parseInt(noi[2].trim());
			updateReceivedInfo(from, to);
			isOver=true;
			
		} else if(code == Checkers.YOU_WIN.getValue()) {
			isOver=true;
			clientApp.player.setSocre(clientApp.player.getSocre()+1);
			clientApp.dangNhap.lblim.setText(clientApp.player.getSocre()+"");
			continueToPlay=false;
		}
	}	

	private void sendMove(Square from, Square to) throws IOException {
		toServer.writeBytes("505 "+from.getSquareID()+" "+to.getSquareID()+" "+player.getPlayerID()+"\n");

	}

	private void waitForPlayerAction() throws InterruptedException {
		player.setMyTurn(true);
		while(waitingForAction){
			Thread.sleep(100);
		}
		waitingForAction = true;		
	}
	
	public void move(Square from, Square to){
		to.setPlayerID(from.getPlayerID());
		from.setPlayerID(Checkers.EMPTY_SQUARE.getValue());
		checkCrossJump(from, to);
		checkKing(from, to);
		squareDeselected();
		
		waitingForAction = false;
		try {
			sendMove(from, to);
		} catch (IOException e) {
			System.out.println("Sending failed");
		}		
	}
	
	//When a square is selected
	public void squareSelected(Square s) {
		
		if(selectedSquares.isEmpty()){
			addToSelected(s);
		}		
		//if one is already selected, check if it is possible move
		else if(selectedSquares.size()>=1){
			if(playableSquares.contains(s)){
				move(selectedSquares.getFirst(),s);
			}else{
				squareDeselected();
				addToSelected(s);
			}
		}
	}
	
	private void addToSelected(Square s){
		s.setSelected(true);
		selectedSquares.add(s);
		getPlayableSquares(s);
	}

	public void squareDeselected() {
		
		for(Square square:selectedSquares)
			square.setSelected(false);
		
		selectedSquares.clear();
		
		for(Square square:playableSquares){
			square.setPossibleToMove(false);
		}
		
		playableSquares.clear();
		boardPanel.repaintPanels();
	}
	
	
	private void getPlayableSquares(Square s){
		playableSquares.clear();		
		playableSquares = boardPanel.getPlayableSquares(s);
		
		for(Square square:playableSquares){
			System.out.println(square.getSquareID());
		}		
		boardPanel.repaintPanels();
	}
	
	public boolean isHisTurn(){
		return player.isMyTurn();
	}
	
	private void checkCrossJump(Square from, Square to){		
		if(Math.abs(from.getSquareRow()-to.getSquareRow())==2){		
			int middleRow = (from.getSquareRow() + to.getSquareRow())/2;
			int middleCol = (from.getSquareCol() + to.getSquareCol())/2;
			
			Square middleSquare = boardPanel.getSquare((middleRow*8)+middleCol+1);
			middleSquare.setPlayerID(Checkers.EMPTY_SQUARE.getValue());
			middleSquare.removeKing();
		}
	}
	
	private void checkKing(Square from, Square movedSquare){		
		if(from.isKing()){
			movedSquare.setKing();
			from.removeKing();
		}else if(movedSquare.getSquareRow()==7 && movedSquare.getPlayerID()==Checkers.PLAYER_ONE.getValue()){
			movedSquare.setKing();
		}else if(movedSquare.getSquareRow()==0 && movedSquare.getPlayerID()==Checkers.PLAYER_TWO.getValue()){
			movedSquare.setKing();
		}
	}
	
	private void updateReceivedInfo(int from, int to){
		Square fromSquare = boardPanel.getSquare(from);
		Square toSquare = boardPanel.getSquare(to);
		toSquare.setPlayerID(fromSquare.getPlayerID());
		fromSquare.setPlayerID(Checkers.EMPTY_SQUARE.getValue());
		checkCrossJump(fromSquare, toSquare);
		checkKing(fromSquare, toSquare);
		boardPanel.repaintPanels();
	}
}
