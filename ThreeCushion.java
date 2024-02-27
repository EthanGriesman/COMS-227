package hw2;

import api.PlayerPosition;
import api.BallType;
import static api.PlayerPosition.*;
import static api.BallType.*;

/**
 * Class that models the game of three-cushion billiards.
 * @author Ethan Griesman 2/23/2023
 */
public class ThreeCushion {
	
	/*
	 * points of player A
	 */
	private int playerAPoints;
	/*
	 * points of player B
	 */
	private int playerBPoints;
	/*
	 * Indicates whether this shot is the first shot
	 */
	private boolean isFirstShot;
	/*
	 * Indicates whether or not the shot is foul
	 */
	private boolean isShotFoul;
	/*
	 * Indicates whether or not the shot is the valid
	 */
	private boolean isShotValid;
	/*
	 * Indicates whether the shot has started
	 */
	private boolean isShotStarted;
	/*
	 * Indicates whether the inning has started
	 */
	private boolean isInningStarted;
	/*
	 * Indicates whether the current shot is a bank shot
	 */
	private boolean isBankShot;
	/*
	 * Indicates whether or not the game is over
	 */
	private boolean isGameOver;
	/*
	 * Indicates whether cushion has been impacted 3 times to make a valid point
	 */
	private int strikedCushion;
	/*
	 * Indicates whether red ball has been struck
	 */
	private boolean strikedRed;
	/*
	 * Indicates whether other ball has been struck
	 */
	private boolean strikedOther;
	/*
	 * Total points required to win a game
	 */
	private int pointsToWin;
	/*
	 * The current inning number
	 */
	private int inning;
	/*
	 * Models the cueball from BallType
	 */
	private BallType cueBall;
	/*
	 * Models the other ball from BallType
	 */
	private BallType otherBall;
	/*
	 * Models the current player from PlayerPosition
	 */
	private PlayerPosition currentPlayer;
	/*
	 * Models the lag winner from PlayerPosition
	 */
	private PlayerPosition lagWinner;
	/**
	 * Creates a three cushion game with parameters lagWinner and pointsToWin
	 * @param lagWinner
	 * @param pointsToWin
	 */
	public ThreeCushion(PlayerPosition lagWinner, int pointsToWin) {
		//declare instance
		this.lagWinner = lagWinner;
		//
		pointsToWin = this.pointsToWin;
		//Both players begin game with zero points
		playerAPoints = 0;
		playerBPoints = 0;
		//initially, the cushion has not been struck
		strikedCushion = 0;
		//default inning is set to 1
		inning = 1;
		//by default, shot has not started
		isShotStarted = false;
		//by default, shot is not breakshot
		isFirstShot = false;
		// by default, the inning has not started
		isInningStarted = false;
	}
	/**
	 * Returns the current player
	 * @return currentPlayer
	 */
	public PlayerPosition getInningPlayer() {
		return currentPlayer;
	}
	/**
	 * Returns the current cueball
	 * @return cueBall
	 */
	public BallType getCueBall() {
		return cueBall;
	}
	/**
	 * Determines the cueball and current player based on the lag winner, and self break status
	 * @param selfBreak
	 * @param cueball
	 */
	public void lagWinnerChooses(boolean selfBreak, BallType cueball) {
		//if Player A wins lag, choses to shoot first, and picks the white cueball, then nothing changes
		if (selfBreak == true && lagWinner == PLAYER_A && cueball == WHITE) {
			currentPlayer = PLAYER_A;
			cueball = cueBall;
			cueBall = WHITE;
		}
		//if Player B wins lag, choses to shoot first, and picks the white cueball, then nothing changes
		else if (selfBreak == true && lagWinner == PLAYER_B && cueball == WHITE) {
			currentPlayer = PLAYER_B;
			cueball = cueBall;
			cueBall = WHITE;
		}
		//if Player A wins lag, choses to shoot first, and picks the yellow cueball, then nothing changes
		else if (selfBreak == true && lagWinner == PLAYER_A && cueball == YELLOW) {
			currentPlayer = PLAYER_A;
			cueball = cueBall;
			cueBall = YELLOW;
		}
		//if Player B wins lag, choses to shoot first, and picks the yellow cueball, then nothing changes
		else if (selfBreak == true && lagWinner == PLAYER_B && cueball == YELLOW) {
			currentPlayer = PLAYER_B;
			cueball = cueBall;
			cueBall = YELLOW;
		}
		//if Player A wins lag, choses NOT to shoot first, and picks the white cueball, 
		//current player switches to Player B, and the cueball must be yellow 
		else if (selfBreak == false && lagWinner == PLAYER_A && cueball == WHITE) {
			currentPlayer = PLAYER_B;
			cueball = cueBall;
			cueBall = YELLOW;
		}
		//if Player B wins lag, choses NOT to shoot first, and picks the white cueball, 
		//current player switches to Player A, and the cueball must be yellow 
		else if (selfBreak == false && lagWinner == PLAYER_B && cueball == WHITE) {
			currentPlayer = PLAYER_A;
			cueball = cueBall;
			cueBall = YELLOW;
		}
		//if Player A wins lag, choses NOT to shoot first, and picks the yellow cueball, 
		//current player switches to Player B, and the cueball must be white 
		else if (selfBreak == false && lagWinner == PLAYER_A && cueball == YELLOW) {
			currentPlayer = PLAYER_B;
			cueball = cueBall;
			cueBall = WHITE;
		}
		//if Player B wins lag, choses NOT to shoot first, and picks the yellow cueball, 
		//current player switches to Player A, and the cueball must be white 
		else if (selfBreak == false && lagWinner == PLAYER_B && cueball == YELLOW) {
			currentPlayer = PLAYER_A;
			cueball = cueBall;
			cueBall = WHITE;
		}
		isFirstShot = true;
	}
	/**
	 * Models the collison between the cue-stick and the cue ball to determine if the shot is started
	 * @param ball
	 */
	public void cueStickStrike(BallType ball) {
		   //if cue stick is used while a shot is started, it's an automatic foul
		   if (isShotStarted == true) {
	           foul();
	           isShotFoul = true;
	           return;
	        }
		   //if the cue stick hits a ball that isn't the chosen cue ball, it is considered a foul
		   if (ball != cueBall) {
	    	   foul();
	    	   isShotFoul = true;
	           return;
		   }	 
		   //if the ball is the cue ball, the shot begins, and the inning begins
	       if (ball == cueBall) {
	           isShotStarted = true;
	           isShotFoul = false;
	           isInningStarted = true;
	           return;
	       }
	    
	    }
	/**
	 * Models the cue ball striking a given ball
	 * @param ball
	 * 
	 */
	public void cueBallStrike(BallType ball) {
		//we can only move to cueball hitting the next ball if the shot has started, and the game hasn't ended
		if (isShotStarted == true && isGameOver == false) {
			//if the cueball is white and the ball striked is red, the other ball must be yellow
			if (cueBall == WHITE && ball == RED) {
				otherBall = YELLOW;
				isShotValid = true;
				strikedRed = true;
				return;
		    }
			//condition for cue ball white other ball struck
			else if (ball == otherBall) {
				isShotValid = true;
				strikedOther = true;
			}
			
			
			//if the cueball is yellow and the ball striked is red, the other ball must be white
			if (cueBall == YELLOW && ball == RED) {
				otherBall = WHITE;
				isShotValid = true;
				strikedRed = true;
				return;
		    }
			//condition for cue ball yellow other ball struck
			else if (ball == otherBall) {
				isShotValid = true;
				strikedOther = true;
			}
			//
			else if (ball != RED) {
				isShotValid = true;
				strikedOther = true;
				return;
		    }
			
			if (cueBall == YELLOW && strikedRed == true && strikedCushion >= 3 && strikedOther == true) {
				isShotValid = true;
				
			}
			if (cueBall == WHITE && strikedRed == true && strikedCushion >= 3 && strikedOther == true) {
				isShotValid = true;
				
			}
		 }
	}
	/**
	 * Method to implements a counter to keep track of cushion impacts, assuming shot is valid
	 */
	public void cueBallImpactCushion () {
		if (isShotValid == true && isGameOver == false) {
			strikedCushion++;
			
		}
	}
	/**
	 * Indicates cue ball has hit the given cushion,
	 */
	public void foul() {
		changeInning();
	}
	/**
	 * Method that changes the inning if the cue-stick doesn't hit the cue ball
	 */
	private void changeInning() {
		if (isShotStarted = true && isGameOver == false) {
			inning++;
			//players and cue balls swap when foul
			if (currentPlayer == PLAYER_A) {
	        	currentPlayer = PLAYER_B;
	        } else {
	        	currentPlayer = PLAYER_A;
	        }
	        if (cueBall == YELLOW) {
	        	cueBall = WHITE;
	        }
	        else {  
	        	cueBall = YELLOW;
	        }
		}
	}     
	     
	/**
	 * indicates foul committed, inning ends, inning incremented, inning player switches 
	 * @return inning
	 */
	public int getInning() {
		return inning;
	}
	/**
	 * indicates player A's current score
	 * @return playerAPoints
	 */
	public int getPlayerAScore() {
		return playerAPoints;
	}
	/**
	 * indicates player B's current score
	 * @return playerBPoints
	 */
	public int getPlayerBScore() {
		return playerBPoints;
	}
	/**
	 * indicates whether current shot is a bank shot
	 * @return isBankShot
	 */
	public boolean isBankShot() {
		return isBankShot;
	}	
	/**
	 * indicates whether current shot is first shot of game
	 * @return isFirstShot
	 */
	public boolean isBreakShot() {
		return isFirstShot;
	}
	/**
	 * indicates whether game is over
	 * @return isGameOver
	 */
	public boolean isGameOver() {
		return isGameOver;
	}
	/**
	 * indicates whether inning has started
	 * @return isInningStarted
	 */
	public boolean isInningStarted() {
		return isInningStarted;
	}
	/**
	 * indicates whether current shot has begun
	 * @return isShotStarted
	 */
	public boolean isShotStarted() {
		return isShotStarted;
	}
	/**
	 *  Models end of each round
	 */
	public void endShot() {
		if (isShotStarted == true && isGameOver == false) {
		//when balls stop moving, shot will never be started, no longer first shot
		isShotStarted = false;
		isFirstShot = false;
		
		//increments points for players if a shot is valid
		if (isShotValid == true) {
			if (currentPlayer == PLAYER_A) {
				playerAPoints++;
				if (playerAPoints == pointsToWin) {
					isGameOver = true;
				}
			} else {
				playerBPoints++;
				if (playerBPoints == pointsToWin) {
					isGameOver = true;
				}
			}
		}
		//no point gains if invalid shot
		else if (isShotValid == false) {
			playerAPoints+=0;
			playerBPoints+=0;
		}
		
		//if shot isn't a foul, and the game isn't over, but the current player's shot has ended
		//increment the inning, swap out players and cue balls
		if (isShotFoul == false && isGameOver == false) {
			inning++;
			if (currentPlayer == PLAYER_A) {
				currentPlayer = PLAYER_B;
		    }
			else {
				currentPlayer = PLAYER_A;
		        }
		    if (cueBall == YELLOW) {
		    	cueBall = WHITE;
		    }
		    else {
		    	cueBall = YELLOW;
		        }
		    isInningStarted = false;
		}
		
	}
	}

	// The method below is provided for you and you should not modify it.
	// The compile errors will go away after you have written stubs for the
	// rest of the API methods.

	/**
	 * Returns a one-line string representation of the current game state. The
	 * format is:
	 * <p>
	 * <tt>Player A*: X Player B: Y, Inning: Z</tt>
	 * <p>
	 * The asterisks next to the player's name indicates which player is at the
	 * table this inning. The number after the player's name is their score. Z is
	 * the inning number. Other messages will appear at the end of the string.
	 * 
	 * @return one-line string representation of the game state
	 */
	public String toString() {
		String fmt = "Player A%s: %d, Player B%s: %d, Inning: %d %s%s";
		String playerATurn = "";
		String playerBTurn = "";
		String inningStatus = "";
		String gameStatus = "";
		
		if (getInningPlayer() == PLAYER_A) {
			playerATurn = "*";
		} else if (getInningPlayer() == PLAYER_B) {
			playerBTurn = "*";
		}
		if (isInningStarted()) {
			inningStatus = "started";
		} else {
			inningStatus = "not started";
		}
		if (isGameOver()) {
			gameStatus = ", game result final";
		}
		return String.format(fmt, playerATurn, getPlayerAScore(), playerBTurn, getPlayerBScore(), getInning(),
				inningStatus, gameStatus);
	}
}
