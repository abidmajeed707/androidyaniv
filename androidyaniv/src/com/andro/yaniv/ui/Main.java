package com.andro.yaniv.ui;

import com.andro.yaniv.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button play = (Button)findViewById(R.id.btnPlay);
        play.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				startGame(); 
			}
		});
        
        Button exit = (Button)findViewById(R.id.btnExit);
        exit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish(); 
			}
		});
        
        
        Button issues = (Button)findViewById(R.id.btnIssues);
        issues.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startIssueIntent();
				
				
			}
		});
        
    }
    public void startIssueIntent(){
    	Intent issueIntent = new Intent(this,IssueReport.class);
    	startActivity(issueIntent);
    }
    public void startGame(){
    	Intent myIntent = new Intent(this,YanivBoard.class);
		startActivity(myIntent); 
    }
}
