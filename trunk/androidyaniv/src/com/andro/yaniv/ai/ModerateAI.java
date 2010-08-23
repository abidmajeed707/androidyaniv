package com.andro.yaniv.ai;

import java.util.ArrayList;

import com.andro.yaniv.game.Player;
import com.andro.yaniv.game.PlayerHand;
import com.andro.yaniv.game.PlayingCard;

public class ModerateAI extends EasyAI {

	public int getBestPickup(Player currentPlayer, PlayerHand discardHand) {
		
		PlayingCard[] cardsBeingDropped = getBestDrop(currentPlayer);
		PlayingCard[] currentHand = currentPlayer.getHand().getCards();
		ArrayList<PlayingCard> tempHand = new ArrayList<PlayingCard>();
		
		for (PlayingCard card : currentHand){
			tempHand.add(card);
		}
		
		for (PlayingCard card : cardsBeingDropped){
			tempHand.remove(card);
		}
		
		PlayingCard[] newHand = new PlayingCard[5];
		tempHand.toArray(newHand);
		
		PlayingCard[] bestBefore = getBestDrop(newHand);
		
		newHand[tempHand.size()] = discardHand.getCard(discardHand.getCardCount()-1);
		
		PlayingCard[] bestAfter = getBestDrop(newHand);
		
		if (bestAfter.length > bestBefore.length){
			return Player.DISCARD;
		}else{
			int discardValue = newHand[tempHand.size()].getFace().getFaceValue(); 
			if ( discardValue <= 3){
				return Player.DISCARD;
			}else{
				return Player.DECK;
			}
		}
	}

	public PlayingCard[] getBestDrop(PlayingCard[] cards){
		Player temp = new Player(null,"",Player.COMPUTER);
		PlayerHand playerHand = temp.getHand();
		playerHand.addSelection(cards);
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
                cardArray = sortCardsForDrop(temp,cardArray);
                
                if (cardArray[cardArray.length-1].getFace().getFaceValue() != 0){
                	if (temp.validateDrop(cardArray) != Player.DROPINVALID){
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
	
}
