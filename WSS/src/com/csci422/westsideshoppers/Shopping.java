package com.csci422.westsideshoppers;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Shopping extends Activity {
	
	public static String DATE_1 = "com.csci422.date1";
	public static String DATE_2 = "com.csci442.date2";
	
	long beginDate;
	long endDate;
	
	private Cursor recipe;
	private Cursor calendar;
	
	private RecipeHelper recipeHelper;
	private CalendarHelper calHelper;
	
	private TextView dateRange;

	@Override
	public void onCreate(Bundle savedInstanceBundle){
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.activity_shopping);
		
		dateRange = (TextView)findViewById(R.id.dateRange);
		
		Button btn = (Button)findViewById(R.id.datePicker);
		btn.setOnClickListener(new OnClickListener(){
		
			@Override
			public void onClick(View view){
				Intent i = new Intent(Shopping.this, SetDateRange.class);				
				startActivityForResult(i, Activity.RESULT_OK);
			}
			
		});
		
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		beginDate = data.getLongExtra(DATE_1, 0);
		endDate = data.getLongExtra(DATE_2, 0);
		setUpShoppingList();
	}
	
	private void setUpShoppingList(){
		Date df = new Date(beginDate);
		String begin = new SimpleDateFormat("EEEE, MM/dd").format(df);
		df = new Date(endDate);
		String end = new SimpleDateFormat("EEEE, MM/dd").format(df);
		dateRange.setText("From " + begin + " to " + end);
	}
}
