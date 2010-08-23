package com.andro.yaniv.ui;

import java.util.List;

import com.andro.yaniv.game.RoundScore;
import com.andro.yaniv.R;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoreAdapter extends ArrayAdapter<RoundScore> {
    int resource;
        String response;
        Context context;
    public ScoreAdapter(Context context, int resource, List<RoundScore> items) {
        super(context, resource, items);
        this.resource=resource;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LinearLayout alertView;
		//Get the current alert object
		RoundScore rs = getItem(position);

		//Inflate the view
		if(convertView==null)
		{
			alertView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi;
			vi = (LayoutInflater)getContext().getSystemService(inflater);
			vi.inflate(resource, alertView, true);
		}
		else
		{
			alertView = (LinearLayout) convertView;
		}
		//Get the text boxes from the listitem.xml file
		TextView rowID =(TextView)alertView.findViewById(R.id.item1);
		TextView col1 =(TextView)alertView.findViewById(R.id.item2);
		TextView col2 =(TextView)alertView.findViewById(R.id.item3);
		TextView col3 =(TextView)alertView.findViewById(R.id.item4);
		TextView col4 =(TextView)alertView.findViewById(R.id.item5);

		
		//Assign the appropriate data from our alert object above
		rowID.setText(Integer.toString(position));
		col1.setText( Integer.toString(rs.getPlayerScore(0)));
		col2.setText( Integer.toString(rs.getPlayerScore(1)));
		col3.setText( Integer.toString(rs.getPlayerScore(2)));
		col4.setText( Integer.toString(rs.getPlayerScore(3)));
		
		if (position == this.getCount() -1){
			rowID.setText("Sum");
			col1.setTypeface(Typeface.DEFAULT_BOLD);
			col2.setTypeface(Typeface.DEFAULT_BOLD);
			col3.setTypeface(Typeface.DEFAULT_BOLD);
			col4.setTypeface(Typeface.DEFAULT_BOLD);
		}
		return alertView;
	}

    
    
    
}
