package com.csci422.westsideshoppers;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class Shopping extends ListActivity {
	
	public static String DATE_1 = "com.csci422.date1";
	public static String DATE_2 = "com.csci442.date2";
	
	private int sYear;
	private int sMonth;
	private int sDay;
	
	private int endYear;
	private int endMonth;
	private int endDay;
	
	long beginDate;
	long endDate;
	
	private Cursor recipe;
	private Cursor calendar;
	
	private RecipeHelper recipeHelper;
	private CalendarHelper calHelper;
	
	private TextView dateRange;
	
	private DatePicker startSpinner;
	private DatePicker endSpinner;
	private Button setDate;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle){
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.activity_shopping);
		
		startSpinner = (DatePicker)findViewById(R.id.begin_date);
		startSpinner.setCalendarViewShown(false);
		endSpinner = (DatePicker)findViewById(R.id.end_date);
		endSpinner.setCalendarViewShown(false);
		
		setDate = (Button)findViewById(R.id.setList);
		setDate.setText("Set Date");		
		
		setDate.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				startSpinner.setSpinnersShown ( !startSpinner.getSpinnersShown());
				endSpinner.setSpinnersShown ( !endSpinner.getSpinnersShown());
				if(startSpinner.getSpinnersShown())
					setDate.setText("Set Date");
				else
					setDate.setText("Show Spinners");
			}
			
		});
		endSpinner.getDayOfMonth();
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		Log.v("ActivityResult", "Here");
		super.onActivityResult(requestCode, resultCode, data);
		Log.v("ActivityResult", "Here");
		beginDate = data.getLongExtra(DATE_1, 0);
		endDate = data.getLongExtra(DATE_2, 0);
		setUpShoppingList();
	}
	
	private void setUpShoppingList(){
		Log.v("Set up list", "Test");
		Date df = new Date(beginDate);
		String begin = new SimpleDateFormat("EEEE, MM/dd").format(df);
		df = new Date(endDate);
		String end = new SimpleDateFormat("EEEE, MM/dd").format(df);
		dateRange.setText("From " + begin + " to " + end);
	}
}
