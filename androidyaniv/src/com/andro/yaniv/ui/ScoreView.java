package com.andro.yaniv.ui;

import java.util.ArrayList;

import com.andro.yaniv.game.RoundScore;
import com.andro.yaniv.game.ScoreKeeper;
import com.andro.yaniv.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ScoreView extends Activity {
        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.score);

            ListView lstScore = (ListView)findViewById(R.id.listview);
            
            ArrayList<RoundScore> scores = ScoreKeeper.getScores();

            ScoreAdapter adapter = new ScoreAdapter(getApplicationContext(), R.layout.list_item,scores);

            lstScore.setAdapter(adapter);

        }
    }