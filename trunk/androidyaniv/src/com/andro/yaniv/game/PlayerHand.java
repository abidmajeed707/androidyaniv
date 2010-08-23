package com.andro.yaniv.game;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.andro.yaniv.game.PlayingCard.SortMethod;
import com.andro.yaniv.R;

import android.view.View;
import android.widget.ImageView;

public class PlayerHand {
	protected PlayingCard cards[] = new PlayingCard[5];
	protected ImageView views[] = null;
	private int cardCount = 0;
	protected int playerType = 2;
	
	public PlayerHand(ImageView[] imageViews, int playerType){
		views = imageViews;
		this.playerType = playerType;
	}
	
	public void clearHand(){
		cards = new PlayingCard[5];
	}
	
	public void addCard(PlayingCard newCard){
		compact();
		ArrayList <PlayingCard> al = new ArrayList<PlayingCard> (Arrays.asList(cards));
		al.add(newCard);
		al.removeAll(Collections.singleton(null));
		cards = new PlayingCard[5];
		al.toArray(cards);
		compact();
	}
	
	public void removeCard(PlayingCard cardToRemove){
		ArrayList <PlayingCard> al = new ArrayList<PlayingCard> (Arrays.asList(cards));
		al.remove(cardToRemove);
		cards = new PlayingCard[5];
		al.toArray(cards);
		compact();
	}
	public PlayingCard getCard(int cardNumber){
		return cards[cardNumber];
	}
	public void toggleCardSelection(int cardNumber){
		try{
			cards[cardNumber].toggleSelected();
		}catch(NullPointerException e){}
		
	}
	public View getCardView(int card){
		return views[card];
	}
	public void redraw(){
		for (int x = 0 ; x < cards.length; x++){
			if (cards[x] != null){
				if (views[x].getVisibility() == View.INVISIBLE){
					views[x].setVisibility(View.VISIBLE);
				}
				if (playerType == Player.HUMAN ){
					views[x].setBackgroundResource(cards[x].getImageId());
				}else{
					try{
						Field theField = R.drawable.class.getDeclaredField("cardback");
						views[x].setBackgroundResource(theField.getInt(null));
					}catch(NoSuchFieldException e){
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

				}
				
			}else{
				views[x].setVisibility(View.INVISIBLE);
			}
		}
	}
	
	public void sortHand(SortMethod method){
		
		ArrayList <PlayingCard> al = new ArrayList<PlayingCard> (Arrays.asList(cards));
		al.removeAll(Collections.singleton(null));
		PlayingCard temp[] = new PlayingCard[al.size()];
		al.toArray(temp);

		PlayingCard.sortType = method;
		Arrays.sort(temp);
		
		al = new ArrayList<PlayingCard> (Arrays.asList(temp));
		cards = new PlayingCard[5];
		al.toArray(cards);
		redraw();
	}
	
	public PlayingCard[] getSelectedCards(){
		ArrayList<PlayingCard> retList = new ArrayList<PlayingCard>();
		for (int x = 0; x < cards.length;x++){
			if (cards[x] != null){
				if (cards[x].isSelected()){
					retList.add(cards[x]);
				}
			}
		}
		PlayingCard retArray[] = new PlayingCard[retList.size()];
		retList.toArray(retArray);
		return retArray;
		
	}
	
	private void compact(){
		ArrayList <PlayingCard> al = new ArrayList<PlayingCard> (Arrays.asList(cards));
		al.removeAll(Collections.singleton(null));
		cards = new PlayingCard[5];
		al.toArray(cards);
		setCardCount(al.size());
	}

	private void setCardCount(int size) {
		// TODO Auto-generated method stub
		cardCount = size;
	}

	public int getPlayerType() {
		return playerType;
	}
	
	public void addSelection(PlayingCard[] cards){
		compact();
		ArrayList <PlayingCard> cardList = new ArrayList<PlayingCard>(Arrays.asList(this.cards));
		cardList.removeAll(Collections.singleton(null));
		if (cardList.size() + cards.length > 5){
			for ( int x = (cardList.size() + cards.length - 5); x > 0; x--){
				cardList.remove(0);
				cardList.trimToSize();
			}
		}
		for (PlayingCard curCard: cards){
			cardList.add(curCard);
		}
		cardList.toArray(this.cards);
		compact();
	}

	public int getCardCount() {
		compact();
		return cardCount;
	}
	
	public static int getHandSum(PlayingCard[] cards){
		int retVal = 0;
		for (int x = 0; x < cards.length; x++){
			if (cards[x] != null){
				retVal += cards[x].getCountValue();
			}
		}
		return retVal;
	}
	public int getHandSum(){
		return getHandSum(cards);
	}
}
