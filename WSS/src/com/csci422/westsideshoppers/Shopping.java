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
	
	private TextView startLabel;
	private TextView endLabel;
	
	private DatePicker startSpinner;
	private DatePicker endSpinner;
	private Button setDate;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle){
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.activity_shopping);
		
		startLabel = (TextView)findViewById(R.id.begin_date_label);
		endLabel = (TextView)findViewById(R.id.end_date_label);
		
		startLabel.setText("Start list with:");
		endLabel.setText("End list with:");
		
		startSpinner = (DatePicker)findViewById(R.id.begin_date);
		startSpinner.setCalendarViewShown(false);
		endSpinner = (DatePicker)findViewById(R.id.end_date);
		endSpinner.setCalendarViewShown(false);
		
		setDate = (Button)findViewById(R.id.setList);	
		setDate.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				startSpinner.setSpinnersShown ( !startSpinner.getSpinnersShown());
				endSpinner.setSpinnersShown ( !endSpinner.getSpinnersShown());
				if(startSpinner.getSpinnersShown())
					setDate.setText(R.string.set_date_range);
				else
					setDate.setText(R.string.show_date_pickers);
			}
			
		});
		endSpinner.getDayOfMonth();

	}
}
