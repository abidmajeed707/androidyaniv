package com.andro.yaniv.game;

import java.util.ArrayList;

import android.content.Context;

public class ScoreKeeper {
	private final int scoreLimit = 200;
	Game game = null;
	Context mContext = null;
	static ArrayList<RoundScore> scores = new ArrayList<RoundScore>();
	int[] totals = new int[4];
	
	public ScoreKeeper(Context ctx, Game curGame){
		mContext = ctx;
		game = curGame;
	}
	public void addRound(Player yanivCaller, Player assafCaller, Player players[]){
		int[] roundScores = new int[4];
		
		for (int x = 0; x < players.length; x++){
			
			if (assafCaller==null?players[x].equals(yanivCaller):players[x].equals(assafCaller)){
				roundScores[x] = 0;
			}else{
				roundScores[x] = players[x].getCurrentScore();
				if (players[x].equals(yanivCaller) && assafCaller != null){
					roundScores[x] = players[x].getCurrentScore()+30;
				}
			}
		}
		scores.add(new RoundScore(roundScores));
		updateScores();
	}
	private void updateScores(){
		totals[0] = 0;
		totals[1] = 0;
		totals[2] = 0;
		totals[3] = 0;
		for (int x = 0; x < scores.size(); x++){
			RoundScore curRound = scores.get(x);
			for (int y =0; y < 4; y++){
				totals[y] += curRound.getPlayerScore(y);
			}
		}
		boolean dealNext = true;
		for (int x = 0; x < 4; x++){
			if (totals[x] > scoreLimit){
				dealNext = false;
			}
		}
		game.endGame(dealNext);
	}
	
	public void showScores(){
		updateScores();
	}
	
	public int[] getRoundScores(int roundNumber){
		RoundScore temp = scores.get(roundNumber);
		return temp.scores;
	}
	
	public int[] getLastRoundScores(){
		return getRoundScores(scores.size()-1);
	}
	
	public static ArrayList<RoundScore> getScores(){
		ArrayList<RoundScore> withTotal = new ArrayList<RoundScore>();
		for (int x = 0; x < scores.size(); x++){
			withTotal.add(scores.get(x));
		}
		
		int[] totals = new int[4];
		for (int x = 0; x < withTotal.size(); x++){
			for (int y = 0; y < 4; y++){
				totals[y] += withTotal.get(x).getPlayerScore(y);
			}
		}
		withTotal.add(new RoundScore(totals));
		return withTotal;
	}
	public void clear() {
		scores.clear();
		
	}
}
