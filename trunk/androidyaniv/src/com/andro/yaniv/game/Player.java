package com.andro.yaniv.game;
import java.util.ArrayList;
import java.util.Arrays;

import com.andro.yaniv.ai.BasicAI;
import com.andro.yaniv.game.PlayingCard.Face;
import com.andro.yaniv.game.PlayingCard.SortMethod;

import android.widget.ImageView;

public class Player {
	public static final int HUMAN = 1;
	public static final int COMPUTER = 2;
	
	public static final int DECK =1;
	public static final int DISCARD = 2;
	
	public static final int DROPSINGLE = 0;
	public static final int DROPPAIR = 1;
	public static final int DROPSAME = 2;
	public static final int DROPRUN = 3;
	public static final int DROPINVALID = -1;
	
	private PlayerHand hand = null;
	private String playerName = null;
	public int pickupLocation = 0;
	
	public Player(ImageView[] cardViews,String pName, int playerType){
		hand = new PlayerHand(cardViews, playerType);
		playerName = pName;
	}
	
	
	public PlayerHand getHand(){
		return hand;
	}
	
	public int canDrop(){
		PlayingCard [] selectedCards = hand.getSelectedCards();
		if (selectedCards.length == 0){
			return DROPINVALID;
		}else{
			return validateDrop(selectedCards);
		}
	}

	public int validateDrop(PlayingCard[] selectedCards) {
		int retVal = DROPINVALID;
		//check for single card
		if (selectedCards.length == 1){
			retVal =DROPSINGLE;
		}else{
			//dealing with more than one card
			if (selectedCards.length == 2){
				//dealing with a pair both must have same face
				Face card1Face = selectedCards[0].getFace();
				Face card2Face = selectedCards[1].getFace(); 
				if ( (card1Face.equals(card2Face)) || (card1Face.equals(Face.JOKER) || card2Face.equals(Face.JOKER))){
					retVal =DROPPAIR;
				}
			}	
			if (retVal == DROPINVALID){
				if (selectedCards.length > 2){
					//dealing with multiple could be a Run or all same face
					//selectedCards[0] = new PlayingCard(Suit.HEART,Face.JOKER);
					//selectedCards[1] = new PlayingCard(Suit.DIAMOND,Face.THREE);
					//selectedCards[2] = new PlayingCard(Suit.DIAMOND,Face.FIVE);
					//check for a run
					PlayingCard.sortType = SortMethod.FACE;
					Arrays.sort(selectedCards);
					int jokerCount = 0;
					while (selectedCards[jokerCount].getFace() == Face.JOKER){
						jokerCount++;
					}
					if (jokerCount + 1 == selectedCards.length){
						retVal =DROPSAME;
					}
					if (retVal == DROPINVALID){
						int runStart = jokerCount;
						while (runStart < selectedCards.length -1){
							int card1Value = selectedCards[runStart].getFace().getFaceValue();
							int card2Value = selectedCards[runStart+1].getFace().getFaceValue();
							if (card1Value + 1 == card2Value){
								runStart++;
							}else{
								int difference = (card2Value - card1Value) -1;
								if (difference < 0) {
									break;
								}
								if (difference > jokerCount){
									retVal =DROPINVALID;
									break;
								}else{
									jokerCount -= difference;
									runStart++;
								}
							}
						}
						if (runStart == selectedCards.length-1){
							PlayingCard.sortType = SortMethod.SUIT;
							Arrays.sort(selectedCards);
							
							if (selectedCards[0].getSuit().compareTo(selectedCards[selectedCards.length -1].getSuit())==0){
								retVal =DROPRUN;
							}else{
								ArrayList <PlayingCard> temp = new ArrayList<PlayingCard>(Arrays.asList(selectedCards));
								for (Object cur: temp.toArray()){
									if ( ((PlayingCard)cur).getFace().equals(Face.JOKER) ){
										temp.remove(cur);
									}
								}
								PlayingCard[] filtered = new PlayingCard[temp.size()];
								temp.toArray(filtered);
								Arrays.sort(filtered);
								if (filtered[0].getSuit().compareTo(filtered[filtered.length -1].getSuit())==0){
									retVal =DROPRUN;
								}else{
									retVal =DROPINVALID;
								}
							}
						}
						//check for multiple same-face
						if (retVal == DROPINVALID){
							retVal = DROPSAME;
							int startVal = selectedCards[selectedCards.length-1].getFace().getFaceValue();
							for (int x = selectedCards.length-2; x >= 0; x--){
								int cardValue = selectedCards[x].getFace().getFaceValue();
								if (cardValue != startVal && cardValue != Face.JOKER.getFaceValue()){
									retVal =DROPINVALID;
								}
							}
						}

					}
				}
			}
		}
		return retVal;
	}
	
	public void selectDropCards(){
		if (hand.getPlayerType() == COMPUTER){
			//TODO do AI SHIT HERE
			PlayingCard[] bestDrop = BasicAI.getBestDrop(this);
			for (int x = 0; x < bestDrop.length; x++){
				bestDrop[x].toggleSelected();
			}
			
		}
		
	}
	public int getCurrentScore(){
		return hand.getHandSum();
	}
	
	public String toString(){
		return playerName;
	}
}
