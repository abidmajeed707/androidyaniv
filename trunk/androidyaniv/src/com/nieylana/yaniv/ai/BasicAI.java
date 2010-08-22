package com.nieylana.yaniv.ai;

import java.util.ArrayList;
import java.util.Arrays;

import com.nieylana.yaniv.game.Player;
import com.nieylana.yaniv.game.PlayerHand;
import com.nieylana.yaniv.game.PlayingCard;
import com.nieylana.yaniv.game.PlayingCard.SortMethod;

public class BasicAI {

	public static PlayingCard[] getBestDrop(Player curPlayer){
		PlayerHand playerHand = curPlayer.getHand();
		ArrayList<PlayingCard[]> listOfHands = new ArrayList<PlayingCard[]>();
		ArrayList<PlayingCard> possibleHand = new ArrayList<PlayingCard>();
		
		CombinationGenerator cg = null;
		int number = playerHand.getCardCount();

		
        for (int x = 0; x < number; x++){
            cg = new CombinationGenerator(number,x+1);
            int [] result;
            while (cg.hasMore()){
            	possibleHand.clear();
                result = cg.getNext();
                for (int y = 0; y < result.length; y++){
                	possibleHand.add(playerHand.getCard(result[y]));
                }
        		PlayingCard[] cardArray = new PlayingCard[possibleHand.size()];
                possibleHand.toArray(cardArray);
                cardArray = sortCardsForDrop(curPlayer,cardArray);
                
                if (cardArray[cardArray.length-1].getFace().getFaceValue() != 0){
                	if (curPlayer.validateDrop(cardArray) != Player.DROPINVALID){
                		listOfHands.add(cardArray);
                	}
                }
            }
        }
        
        PlayingCard[] highestHand = null;
        int highestSum = 0;
        int curSum = 0;
        
        for (int x = 0; x < listOfHands.size(); x++){
        	curSum = PlayerHand.getHandSum(listOfHands.get(x));
        	if (curSum > highestSum){
        		highestSum = curSum;
        		highestHand = listOfHands.get(x);
        		
        	}
        }
        return highestHand;
	}
	
	private static PlayingCard[] sortCardsForDrop(Player player,PlayingCard[] originalSelected){
		
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

	public static int getBestPickup(PlayerHand discardHand) {
		try{
			if (discardHand.getCard(discardHand.getCardCount()-1).getCountValue() > 5){
				return Player.DECK;
			}else{
				return Player.DISCARD;
			}
		}catch (NullPointerException e){return Player.DECK;}
	}
	
}
