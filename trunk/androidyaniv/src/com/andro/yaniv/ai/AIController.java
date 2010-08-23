package com.andro.yaniv.ai;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.andro.yaniv.game.Game;
import com.andro.yaniv.game.Player;
import com.andro.yaniv.game.PlayerHand;
import com.andro.yaniv.game.PlayingCard;

public class AIController {
	private static SharedPreferences prefs = null;
	public static PlayingCard[] getBestDrop(Game game, Player curPlayer) {
		// TODO Auto-generated method stub
		YanivAI compAI = null;
		prefs = PreferenceManager.getDefaultSharedPreferences(game.getContext());
		String level = prefs.getString("AI_Level", "easy");
		
		if (level.equals("easy")){
			compAI= new EasyAI();
			return compAI.getBestDrop(curPlayer);
		}else{
			compAI= new ModerateAI();
			return compAI.getBestDrop(curPlayer);
		}
		
	}

	public static int getBestPickup(Game game, PlayerHand discardHand) {
		// TODO Auto-generated method stub
		prefs = PreferenceManager.getDefaultSharedPreferences(game.getContext());
		String level = prefs.getString("AI_Level", "easy");
		
		if (level.equals("easy")){
			EasyAI compAI = null;
			compAI= new EasyAI();
			return compAI.getBestPickup(discardHand);
		}else{
			ModerateAI compAI = null;
			compAI= new ModerateAI();
			return compAI.getBestPickup(game.getPlayer(game.getCurrentPlayer()), discardHand);
		}
	}

}
