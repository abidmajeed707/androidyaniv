package com.andro.yaniv.ai;

import java.util.Arrays;

import com.andro.yaniv.game.Player;
import com.andro.yaniv.game.PlayerHand;
import com.andro.yaniv.game.PlayingCard;
import com.andro.yaniv.game.PlayingCard.SortMethod;

public abstract class YanivAI {

	public abstract int getBestPickup(Player currentPlayer, PlayerHand discardHand);
	public abstract PlayingCard[] getBestDrop(Player curPlayer);
	
	public static PlayingCard[] sortCardsForDrop(Player player,PlayingCard[] originalSelected){
		
		int dropType = player.validateDrop(originalSelected);		
		
		switch(dropType){
		case Player.DROPPAIR:
			// sort for pair
			PlayingCard.sortType = SortMethod.FACE;
			Arrays.sort(originalSelected);
			break;
		case Player.DROPSAME:
			// sort for same
			PlayingCard.sortType = SortMethod.FACE;
			Arrays.sort(originalSelected);
			break;
		case Player.DROPRUN:
			// sort for run

		}
		
		return originalSelected;
	}
	
}
