package com.nieylana.yaniv.game;

import java.util.ArrayList;
import java.util.Random;

import com.nieylana.yaniv.game.PlayingCard.Suit;
import com.nieylana.yaniv.game.PlayingCard.Face;

public class CardDeck {

	ArrayList<PlayingCard> cards = new ArrayList<PlayingCard>();
	
	public CardDeck(){
		populateDeck();
	}
	private void populateDeck(){
		for (PlayingCard.Suit suit : PlayingCard.Suit.values()){
			for (PlayingCard.Face value : PlayingCard.Face.values()){
				if (value == Face.JOKER && (suit == Suit.CLUB || suit == Suit.SPADE)){
					
				}else{
					cards.add(new PlayingCard(suit,value));
				}
			}
		}
	}
	public void reset(){
		for (Object o : cards.toArray()){
			cards.remove(o);
		}
		
		populateDeck();
	}
	
	public PlayingCard popCard(){
		if (cards.size() == 0){
			populateDeck();
		}
		Random rand = new Random();
		PlayingCard chosenCard = cards.get(rand.nextInt(cards.size()));
		cards.remove(chosenCard);
		return chosenCard;
	}
}
