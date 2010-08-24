package com.andro.yaniv.ai;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.andro.yaniv.game.Game;
import com.andro.yaniv.game.Player;
import com.andro.yaniv.game.PlayerHand;
import com.andro.yaniv.game.PlayingCard;

public class AIController {
	private static YanivAI currentAI = null;
	private static SharedPreferences prefs = null;
	public static PlayingCard[] getBestDrop(Game game, Player curPlayer) {
		// TODO Auto-generated method stub
		prefs = PreferenceManager.getDefaultSharedPreferences(game.getContext());
		String level = prefs.getString("AI_Level", "easy");
		
		if (level.equals("easy")){
			currentAI= new EasyAI();
			return currentAI.getBestDrop(curPlayer);
		}else{
			currentAI= new ModerateAI();
			return currentAI.getBestDrop(curPlayer);
		}
		
	}

	public static int getBestPickup(Game game, PlayerHand discardHand) {
		// TODO Auto-generated method stub
		prefs = PreferenceManager.getDefaultSharedPreferences(game.getContext());
		String level = prefs.getString("AI_Level", "easy");
		try{
			if (level.equals("easy")){
				currentAI= new EasyAI();
				return currentAI.getBestPickup(game.getPlayer(game.getCurrentPlayer()),discardHand);
			}else{
				currentAI= new ModerateAI();
				return currentAI.getBestPickup(game.getPlayer(game.getCurrentPlayer()), discardHand);
			}
		}catch(NullPointerException e){
			e.printStackTrace();
			return 1;
		}
	}

}
