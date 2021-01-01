package Session;
import javax.swing.*;

import EnumConstants.Checkers;
import Manager.ExtraThread;
import Model.Game;
import Model.Player;
import Model.Square;

import java.io.*;
import java.net.*;
import java.awt.*;

/**
 * Server Application --> Handle Session
 * @author DuongHo
 * 
 * Handle Game Logic and Player requests
 */
public class HandleSession implements Runnable {
	
	private Game checkers;
	
	public ExtraThread pExtraThread1, pExtraThread2;
	
	private boolean continueToPlay = true;
	
	//Construct thread
	public HandleSession(ExtraThread p1, ExtraThread p2){
		
		pExtraThread1 = p1;
		pExtraThread2 = p2;
		
		checkers = new Game();
	}
	
	public void run() {		
		
		//Send Data back and forth		
		try{
				//notify Player 1 to start
			pExtraThread1.out.writeBytes("1\n");

		}catch(Exception ex){
			System.out.println("Connection is being closed");

			return;
		}
	}
	
	public void playerGo(int from, int to, int player) {
		try{
				
				if(continueToPlay){
					//wait for player 1's Action
					if(player == 1) {
						checkStatus(from, to);
						updateGameModel(from, to);
								
						//Send Data back to 2nd Player
						if(checkers.isOver()) {
							//Game Over notification
							pExtraThread2.out.writeBytes(Checkers.YOU_LOSE.getValue()+" "+from+" "+to+"\n");
						}

						pExtraThread2.out.writeBytes("505 "+from+" "+to+"\n");
												
						//IF game is over, break
						if(checkers.isOver()){
							pExtraThread1.out.writeBytes(Checkers.YOU_WIN.getValue()+" "+from+" "+to+"\n");
							pExtraThread1.updateScore();
							continueToPlay=false;
						}
						
						System.out.println("after break");
						
					}else if(player == 2) {				
						
						checkStatus(from, to);
					
						
						updateGameModel(from, to);					
						
						//Send Data back to 1st Player
						if(checkers.isOver()){
							pExtraThread1.out.writeBytes(Checkers.YOU_LOSE.getValue()+" "+from+" "+to+"\n");
						}
						
						System.out.println("player 2 : "+from);
						pExtraThread1.out.writeBytes("505 "+from+" "+to+"\n");
						
						//IF game is over, break
						if(checkers.isOver()){
							pExtraThread2.out.writeBytes(Checkers.YOU_WIN.getValue()+" "+from+" "+to+"\n");
							pExtraThread2.updateScore();
							continueToPlay=false;
						}
						
						System.out.println("second break");
					}
				}
		}catch(Exception ex){
			
			return;
		}
	}
	
	private void checkStatus(int status, int status2) throws Exception{
		if(status==99 || status2==99){
			throw new Exception("Connection is lost");
		}
	}
	
	private void updateGameModel(int from, int to){
		Square fromSquare = checkers.getSquare(from);
		Square toSquare = checkers.getSquare(to);
		toSquare.setPlayerID(fromSquare.getPlayerID());
		fromSquare.setPlayerID(Checkers.EMPTY_SQUARE.getValue());
		
		checkCrossJump(fromSquare, toSquare);
	}
	
	private void checkCrossJump(Square from, Square to){		
		if(Math.abs(from.getSquareRow()-to.getSquareRow())==2){		
			int middleRow = (from.getSquareRow() + to.getSquareRow())/2;
			int middleCol = (from.getSquareCol() + to.getSquareCol())/2;
			
			Square middleSquare = checkers.getSquare((middleRow*8)+middleCol+1);
			middleSquare.setPlayerID(Checkers.EMPTY_SQUARE.getValue());
		}
	}
}