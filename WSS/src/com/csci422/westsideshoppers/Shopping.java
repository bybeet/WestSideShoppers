package com.csci422.westsideshoppers;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

public class Shopping extends ListActivity {
	
	public static String DATE_1 = "com.csci422.date1";
	public static String DATE_2 = "com.csci442.date2";
	
	private Cursor recipe;
	private Cursor calendar;
	
	private RecipeHelper recipeHelper;
	private CalendarHelper calHelper;
	
	private TextView dateRange;
	
	private DatePicker startSpinner;
	private DatePicker endSpinner;
	private Button setDate;
	
	private String beginDate;
	private String endDate;
	
	private ListView list;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle){
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.activity_shopping);
		
		dateRange = (TextView)findViewById(R.id.dateRange);
		
		list = (ListView)findViewById(R.id.list);
		
		recipeHelper = new RecipeHelper(this);
		calHelper = new CalendarHelper(this);
		
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
				if(startSpinner.getSpinnersShown()){
					setDate.setText(R.string.set_date_range);
					dateRange.setVisibility(View.GONE);
					list.setVisibility(View.GONE);
				}
				else {
					setDate.setText(R.string.show_date_pickers);
					setUpShoppingList();
				}
			}
			
		});

		

	}
	
	private void setUpShoppingList(){
		setPickerDate();
		dateRange.setText("From " + beginDate + " to " + endDate);
		dateRange.setVisibility(View.VISIBLE);
		
		if(calendar != null){
			stopManagingCursor(calendar);
			calendar.close();
		}
		
		calendar = calHelper.getById(beginDate);
		
		String[] from = {"recipe"};
		int[] to = {R.id.recipeName};
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row_check_box, calendar, from, to);
		
		list.setVisibility(View.VISIBLE);
		list.setAdapter(adapter);
	}
	
	private void setPickerDate(){
		beginDate = stringDate(startSpinner);
		endDate = stringDate(endSpinner);
	}
	
	private String stringDate(DatePicker picker){
		Date df = new Date(picker.getCalendarView().getDate());
		return new SimpleDateFormat("MM/dd/yy").format(df);
	}
}
