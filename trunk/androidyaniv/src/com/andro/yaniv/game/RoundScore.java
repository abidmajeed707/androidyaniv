package com.andro.yaniv.game;

public class RoundScore {
	int [] scores = new int[4];
	
	
	public RoundScore(int[] pscores){
		scores[0] = pscores[0];
		scores[1] = pscores[1];
		scores[2] = pscores[2];
		scores[3] = pscores[3];
	}
	
	public int getPlayerScore(int player){
		return scores[player];
	}
}
