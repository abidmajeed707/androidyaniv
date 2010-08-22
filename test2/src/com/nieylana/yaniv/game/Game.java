package com.nieylana.yaniv.game;

import java.util.Arrays;

import com.nieylana.yaniv.ai.BasicAI;
import com.nieylana.yaniv.game.PlayingCard.SortMethod;
import com.nieylana.yaniv.ui.YanivBoard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Game {
	private ScoreKeeper scoreKeeper = null;
	private CardDeck deck = new CardDeck();
	private Player players[] = new Player[4];
	private PlayerHand discardHand = null;
	private View deckView = null;
	//private View discardView = null;
	private View yanivButton = null;
	int [] deckLocation = new int[2];
	int roundNumber = 0;
	
	private int currentPlayer = 0;
	
	
	public Game(View yanivBtn,View deck, ImageView[] p1Cards,ImageView[] p2Cards, ImageView[] p3Cards, ImageView[] p4Cards, ImageView[] discardArray){
		players[0] = new Player(p1Cards,"You",Player.HUMAN);
		players[1] = new Player(p2Cards,"Comp1",Player.COMPUTER);
		players[2] = new Player(p3Cards,"Comp2",Player.COMPUTER);
		players[3] = new Player(p4Cards,"Comp3",Player.COMPUTER);
		deckView = deck;
		yanivButton = yanivBtn;
		discardHand = new PlayerHand(discardArray,Player.HUMAN);
		scoreKeeper = new ScoreKeeper(deckView.getContext(),this);
		redrawHands();
	}
	
	public Player getPlayer(int playerNumber){
		return players[playerNumber];
	}
	
	public void redrawHands(){
		for (int x = 0; x < players.length; x++){
			players[x].getHand().redraw();
		}
	}
	
	private void dealCards(){
		deck.reset();
		players[0].getHand().clearHand();
		players[1].getHand().clearHand();
		players[2].getHand().clearHand();
		players[3].getHand().clearHand();
		discardHand.clearHand();
		discardHand.addCard(deck.popCard());
		discardHand.redraw();
		
		int startOffset = 0;
		for (int y = 0; y < 5; y++){
			for (int x = 0; x <players.length; x++){
				players[x].getHand().addCard(deck.popCard());
				if (x == 0){
					players[x].getHand().getCard(y).setVisible(true);
				}
				int [] cardLocation = new int[2];
				players[x].getHand().getCardView(y).getLocationInWindow(cardLocation);
				deckView.getLocationInWindow(deckLocation);
				players[x].getHand().getCardView(y).setVisibility(View.VISIBLE);
				
				cardLocation[0] = cardLocation[0] > deckLocation[0] ?  0-(cardLocation[0] - deckLocation[0]) : deckLocation[0] - cardLocation[0];
				cardLocation[1] = cardLocation[1] > deckLocation[1] ?  deckLocation[1] - cardLocation[1] : cardLocation[1] - deckLocation[1];
				
				TranslateAnimation ta = new TranslateAnimation(cardLocation[0],0,cardLocation[1],0);
				ta.setDuration(100);
				ta.setStartOffset(100 * startOffset++);
				players[x].getHand().getCardView(y).startAnimation(ta);
			}
		}
		
		players[0].getHand().redraw();
	}
	
	public void PlayGame(){
		
		if (roundNumber == 0 ){
			dealCards();
			discardHand.redraw();
		}
		
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void endCurrentTurn(){
		if (players[currentPlayer].getHand().getPlayerType() == Player.COMPUTER){
			if (players[currentPlayer].getCurrentScore() < 7){
				performYaniv(deckView.getContext(), players[currentPlayer]);
				return;
			}
		}
		if (players[currentPlayer].canDrop()!=Player.DROPINVALID){
			//perform dropping here
			PlayingCard [] cardsToRemove = players[currentPlayer].getHand().getSelectedCards();

			for (PlayingCard curCard : cardsToRemove){
				players[currentPlayer].getHand().removeCard(curCard);
				curCard.toggleSelected();
			}
			PlayingCard pickupCard = null;
			if (players[currentPlayer].pickupLocation == Player.DECK){
				pickupCard = deck.popCard();
			}else{
				pickupCard = discardHand.getCard(discardHand.getCardCount()-1);
				discardHand.removeCard(pickupCard);
			}
			players[currentPlayer].getHand().addCard(pickupCard);
			
			cardsToRemove = sortCardsForDrop(cardsToRemove);
			discardHand.addSelection(cardsToRemove);
			players[currentPlayer].getHand().redraw();
			discardHand.redraw();
		}

		currentPlayer++;
		
		if (currentPlayer == 4){
			currentPlayer = 0;
		}
		
		startNextTurn();
	}

	private void startNextTurn() {
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(deckView.getContext());
		if (sharedPrefs.getBoolean("sortHand",false)){
			SortMethod sm = null;
			if(sharedPrefs.getBoolean("sortValue", false)){
				sm = SortMethod.COUNT;
			}else if(sharedPrefs.getBoolean("sortFace", false)){
				sm = SortMethod.FACE;
			}else if(sharedPrefs.getBoolean("sortSuit", false)){
				sm = SortMethod.SUIT;
			}
			players[0].getHand().sortHand(sm);
		}
		
		if (currentPlayer == 0){
			YanivBoard.updateStatus("Your Turn!");
			if (players[currentPlayer].getHand().getHandSum() < 7){
				yanivButton.setVisibility(View.VISIBLE);
			}else{
				yanivButton.setVisibility(View.INVISIBLE);
			}
		}else{
			YanivBoard.updateStatus("Comp" + (currentPlayer+1) + "'s Turn");
		}
		players[currentPlayer].selectDropCards();
		
		if (currentPlayer != 0){
			players[currentPlayer].pickupLocation = BasicAI.getBestPickup(discardHand);
			//TODO: try adding Thread.sleep to automate computer players...
		}
		
	}
	public ScoreKeeper getScoreKeeper(){
		return scoreKeeper;
	}
	public PlayingCard[] sortCardsForDrop(PlayingCard[] originalSelected){
		
		int dropType = players[currentPlayer].validateDrop(originalSelected);		
		
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

	public void performYaniv(Context context, Player player) {

		AlertDialog.Builder adb = new AlertDialog.Builder(context);
		StringBuilder sb = new StringBuilder(player.toString() + " declared Yaniv with a score of ");
		sb.append(player.getCurrentScore());
		int yanivCallerScore = player.getCurrentScore();
		boolean assaf = false;
		Player assafCaller = null;
		for (int x = 0; x < players.length; x++){
			if (players[x].equals(player) == false){
				if (!assaf){
					if (players[x].getCurrentScore() < yanivCallerScore){
						assaf = true;
						assafCaller = players[x];
					}
				}else{
					if (players[x].getCurrentScore() < assafCaller.getCurrentScore()){
						assaf = true;
						assafCaller = players[x];
					}
				}
			}
		}
		if (assaf){
			sb.append(" but " + assafCaller.toString() + " called Assaf with a score of " + assafCaller.getCurrentScore());
		}
		if (!assaf){
			if (player.equals(players[0])){
				adb.setTitle("You Win!");
			}else{
				adb.setTitle("Sorry!");
			}
		}else{
			if (assafCaller.equals(players[0])){
				adb.setTitle("You Win!");
			}else{
				adb.setTitle("Sorry!");
			}
		}
		adb.setMessage(sb.toString());
		adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		adb.create().show();
		scoreKeeper.addRound(player, assafCaller, players);
		yanivButton.setVisibility(View.INVISIBLE);
	}

	public void endGame(boolean dealNext) {
		//TODO: add who won
		if (dealNext){
			this.dealCards();
			//find the winner
			int[] scores = scoreKeeper.getLastRoundScores();
			for (int x = 0; x < 4; x++){
				if (scores[x] == 0){
					currentPlayer = x;
				}
			}
			startNextTurn();
		}else{
			AlertDialog.Builder adb = new AlertDialog.Builder(deckView.getContext());
			adb.setMessage("Game Over!");
			adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					 
				}
			});
			adb.create().show();
			scoreKeeper.clear();
			dealCards();
			
		}
	}


}
