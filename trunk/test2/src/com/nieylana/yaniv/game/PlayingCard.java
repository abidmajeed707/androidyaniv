package com.nieylana.yaniv.game;

import java.lang.reflect.Field;

import com.nieylana.yaniv.R;

public class PlayingCard implements Comparable<PlayingCard>{
	public enum Suit {HEART,SPADE,DIAMOND,CLUB};

	public enum Face {
		JOKER(0),ACE(1),TWO(2),THREE(3),FOUR(4),FIVE(5),SIX(6),SEVEN(7),EIGHT(8),NINE(9),TEN(10),JACK(11),QUEEN(12),KING(13);

		  private int faceValue;

		  private Face(int faceVal) {
		    this.faceValue = faceVal;
		  }

		  public int getFaceValue() {
		    return faceValue;
		  }
		}
	public enum SortMethod {FACE,SUIT,COUNT};
	
	public static SortMethod sortType = SortMethod.FACE;
	private Suit  cardSuit = null;
	private Face face = null;
	private int	  countValue = 0;
	private boolean isVisible = false;
	private boolean isSelected = false;
	public PlayingCard (Suit suit, Face value){
		cardSuit = suit;
		face = value;
		countValue = 0;		
		switch(face){
			case TEN:
			case JACK:
			case QUEEN:
			case KING:
				countValue++;
			case NINE:
				countValue++;			
			case EIGHT:
				countValue++;
			case SEVEN:
				countValue++;
			case SIX:
				countValue++;
			case FIVE:
				countValue++;
			case FOUR:
				countValue++;
			case THREE:
				countValue++;
			case TWO:
				countValue++;
			case ACE:
				countValue++;
			case JOKER:
				break;		
		}
		
	}
	public int getImageId(){
		char suitChar = ' ';
		String valString = null;
		
		switch (cardSuit){
		case HEART:
			suitChar = 'h';
			break;
		case SPADE:
			suitChar = 's';
			break;
		case DIAMOND:
			suitChar = 'd';
			break;
		case CLUB:
			suitChar = 'c';
			break;
		}
		int val = 0;
		switch (face){
		case KING:
			val++;
		case QUEEN:
			val++;
		case JACK:
			val++;
		case TEN:
			val++;
		case NINE:
			val++;
		case EIGHT:
			val++;
		case SEVEN:
			val++;
		case SIX:
			val++;
		case FIVE:
			val++;
		case FOUR:
			val++;
		case THREE:
			val++;
		case TWO:
			val++;
		case ACE:
			val++;
		case JOKER:
			
		}
		valString = Integer.toString(val);
		try{
			if (isSelected){
				valString += "s";
			}
			Field theField = R.drawable.class.getDeclaredField(suitChar + valString);
			return theField.getInt(null);
		}catch (NoSuchFieldException nsfe){nsfe.printStackTrace();}
		catch (IllegalAccessException iae){iae.printStackTrace();}
		return -1;
	}

	public Suit getSuit(){
		return cardSuit;
	}
	
	public Face getFace(){
		return face;
	}
	
	public void setVisible(boolean visibility){
		isVisible = visibility;
	}
	public boolean isVisible(){
		return isVisible;
	}
	
	public int compareTo(PlayingCard card){
		switch (sortType){
		case FACE:
			return face.compareTo(card.face);
		case COUNT:
			if (countValue > card.countValue){
				return 1;
			}else if (countValue < card.countValue){
				return -1;
			}else{
				return 0;
			} 
		case SUIT:
			return cardSuit.compareTo(card.cardSuit);
		default:
			return 0;
		}
	}
	
	public void toggleSelected (){
		isSelected = !isSelected;		
	}
	public boolean isSelected() {
		return isSelected;
	}
	
	public int getCountValue(){
		return countValue;
	}
}
