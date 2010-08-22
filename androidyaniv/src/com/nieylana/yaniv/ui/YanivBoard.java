package com.nieylana.yaniv.ui;

import java.util.ArrayList;
import com.nieylana.yaniv.R;
import com.nieylana.yaniv.game.Game;
import com.nieylana.yaniv.game.Player;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class YanivBoard extends Activity {
	class endTurnListener implements OnClickListener {

		public void onClick(View v) {
			if (v.getId() == R.id.btnNext){
				if (game.getCurrentPlayer() != 0){
					if (game.getPlayer(game.getCurrentPlayer()).canDrop()!=Player.DROPINVALID){
						game.endCurrentTurn();
					}else{
						Toast.makeText(v.getContext(), "Invalid Move", Toast.LENGTH_SHORT);
					}
					
				}
			}
			if (game.getCurrentPlayer() == 0){
				if (v.getId() == R.id.deck1){
					game.getPlayer(0).pickupLocation = Player.DECK;
				}else{
					game.getPlayer(0).pickupLocation = Player.DISCARD;
				}
				if (game.getPlayer(game.getCurrentPlayer()).canDrop()!=Player.DROPINVALID){
					game.endCurrentTurn();
				}else{
					Toast.makeText(v.getContext(), "Move is invalid", Toast.LENGTH_SHORT);
				}
				
			}

		}

	}
	class playerCardListener implements OnClickListener{
		public void onClick(View arg0) {
			if (game.getCurrentPlayer() == 0){
				int viewId = arg0.getId();
				int card = 0;
				switch (viewId){
				case R.id.p1c5:
					card++;
				case R.id.p1c4:
					card++;
				case R.id.p1c3:
					card++;
				case R.id.p1c2:
					card++;
				case R.id.p1c1:
					break;
				}
				game.getPlayer(0).getHand().toggleCardSelection(card);
				game.redrawHands();
			}
			
			
		}
	}
	class yanivListener implements OnClickListener{

		public void onClick(View v) {
			game.performYaniv(v.getContext(), game.getPlayer(0));
		}
		
	}
	
	static TextView status = null;
	public static Context mContext = null;
	Game game = null;
	playerCardListener pCardListener = new playerCardListener();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);
        
		if (game != null){
			game.getScoreKeeper().clear();
		}
        
        
        findViewById(R.id.deck1).setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				//
			}
		});
        
        findViewById(R.id.btnNext).setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Toast.makeText(arg0.getContext(), "" + game.getPlayer(0).canDrop(),Toast.LENGTH_SHORT).show();
			}
		});
        
        findViewById(R.id.p1c1).setOnClickListener(pCardListener);
        findViewById(R.id.p1c2).setOnClickListener(pCardListener);
        findViewById(R.id.p1c3).setOnClickListener(pCardListener);
        findViewById(R.id.p1c4).setOnClickListener(pCardListener);
        findViewById(R.id.p1c5).setOnClickListener(pCardListener);
        
        ArrayList<ImageView> pCards = new ArrayList<ImageView>();
        pCards.add((ImageView)findViewById(R.id.p1c1));
        pCards.add((ImageView)findViewById(R.id.p1c2));
        pCards.add((ImageView)findViewById(R.id.p1c3));
        pCards.add((ImageView)findViewById(R.id.p1c4));
        pCards.add((ImageView)findViewById(R.id.p1c5));
        ImageView[] p1CardArray = new ImageView[5]; 
        pCards.toArray(p1CardArray);
        
        pCards.clear();
        
        pCards.add((ImageView)findViewById(R.id.p2c1));
        pCards.add((ImageView)findViewById(R.id.p2c2));
        pCards.add((ImageView)findViewById(R.id.p2c3));
        pCards.add((ImageView)findViewById(R.id.p2c4));
        pCards.add((ImageView)findViewById(R.id.p2c5));
        ImageView[] p2CardArray = new ImageView[5]; 
        pCards.toArray(p2CardArray);
        
        pCards.clear();
        
        pCards.add((ImageView)findViewById(R.id.p3c1));
        pCards.add((ImageView)findViewById(R.id.p3c2));
        pCards.add((ImageView)findViewById(R.id.p3c3));
        pCards.add((ImageView)findViewById(R.id.p3c4));
        pCards.add((ImageView)findViewById(R.id.p3c5));
        ImageView[] p3CardArray = new ImageView[5]; 
        pCards.toArray(p3CardArray);
        
        pCards.clear();
        
        pCards.add((ImageView)findViewById(R.id.p4c1));
        pCards.add((ImageView)findViewById(R.id.p4c2));
        pCards.add((ImageView)findViewById(R.id.p4c3));
        pCards.add((ImageView)findViewById(R.id.p4c4));
        pCards.add((ImageView)findViewById(R.id.p4c5));
        ImageView[] p4CardArray = new ImageView[5]; 
        pCards.toArray(p4CardArray);
        
        pCards.clear();
        pCards.add((ImageView)findViewById(R.id.discard1));
		pCards.add((ImageView)findViewById(R.id.discard2));
		pCards.add((ImageView)findViewById(R.id.discard3));
		pCards.add((ImageView)findViewById(R.id.discard4));
		pCards.add((ImageView)findViewById(R.id.discard5));
        										
		ImageView[] discardArray = new ImageView[5];
		pCards.toArray(discardArray);
		pCards = null;
		
		discardArray[0].setOnClickListener(new endTurnListener());
		discardArray[1].setOnClickListener(new endTurnListener());
		discardArray[2].setOnClickListener(new endTurnListener());
		discardArray[3].setOnClickListener(new endTurnListener());
		discardArray[4].setOnClickListener(new endTurnListener());
		
		findViewById(R.id.btnNext).setOnClickListener(new endTurnListener());
		findViewById(R.id.deck1).setOnClickListener(new endTurnListener());
		findViewById(R.id.btnYaniv).setOnClickListener(new yanivListener());        
        
        status = (TextView)findViewById(R.id.txtStatus);
        mContext = getApplicationContext();
        game = new Game(findViewById(R.id.btnYaniv),findViewById(R.id.deck1),p1CardArray,p2CardArray,p3CardArray,p4CardArray,discardArray);
        game.PlayGame();
    }
    
    public boolean onCreateOptionsMenu(Menu menu){
    	
    	menu.add(0, 1, 0, "View Scores");
    	menu.add(0,2,0,"Settings");
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	
    	case 1:
    		Intent intent = new Intent(YanivBoard.this, ScoreView.class);
    		startActivityForResult(intent,0);
    		break;
    	case 2:
    		//settings menu
    		Intent intent2 = new Intent(YanivBoard.this, Settings.class);
    		startActivityForResult(intent2, 0);
    	}
    	
    	return true;
    } 
    public static void updateStatus(String msg){
    	if (status != null){
    		status.setText(msg);
    	}
    }
}