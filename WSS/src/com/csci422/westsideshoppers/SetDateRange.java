package com.csci422.westsideshoppers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;

public class SetDateRange extends Activity{

	private CalendarView beginDate;
	private CalendarView endDate;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_date_range);
		
		beginDate = (CalendarView)findViewById(R.id.beginDate);
		endDate = (CalendarView)findViewById(R.id.endDate);
		
		Button btn = (Button)findViewById(R.id.setDateButton);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view){
				setIntentData();
			}
		});
	}
	
	private void setIntentData(){
		Intent result = new Intent();
		result.putExtra(Shopping.DATE_1, beginDate.getDate());
		result.putExtra(Shopping.DATE_2, endDate.getDate());
		this.setResult(Activity.RESULT_OK, result);
		Log.v("SetDateRange", "setIntentData");
		this.finish();
	}
}