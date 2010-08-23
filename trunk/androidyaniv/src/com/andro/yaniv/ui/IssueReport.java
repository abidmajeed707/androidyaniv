package com.andro.yaniv.ui;

import com.andro.yaniv.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class IssueReport extends Activity {

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.issues);
		
		findViewById(R.id.btnSubmit).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
			    String[] mailto = { "tslater2006@gmail.com" };
			    // Create a new Intent to send messages
			    Intent sendIntent = new Intent(Intent.ACTION_SEND);
			    // Add attributes to the intent
			    sendIntent.putExtra(Intent.EXTRA_EMAIL, mailto);
			    sendIntent.putExtra(Intent.EXTRA_SUBJECT,
			        "Issue Report");
			    
			    EditText issue = (EditText)findViewById(R.id.txtIssue);
			    sendIntent.putExtra(Intent.EXTRA_TEXT,
			        issue.getText().toString()
			    );
			    sendIntent.setType("text/plain");
			    startActivity(Intent.createChooser(sendIntent, "Issue Reporter"));
				
			}
		});
		
		
	}
	
}
