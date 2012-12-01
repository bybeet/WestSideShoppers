package com.csci422.westsideshoppers;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Calendar extends Activity {

	private Cursor cursor;
	private RecipeHelper helper;
	private GestureDetector mGestureDetector;
	private View mAnotherView;
	private CalendarView calendar;
	private Button btn;
	private CalendarHelper calHelper;
	private ListView list;
	
	public final static String DATE_ID = "com.csci422.westsideshoppers_DATE";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
	
		helper = new  RecipeHelper(this);
		list = (ListView)findViewById(android.R.id.list);
		
		calendar = (CalendarView)findViewById(R.id.calendarMealView);
		/*
		btn = (Button)findViewById(R.id.addMeal);
		btn.append(" " + returnStringDate());
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Calendar.this, AddCalendarMeal.class);
				
				i.putExtra(DATE_ID, "" + calendar.getDate());
				startActivity(i);
			}
		});
		*/
		calendar.setOnDateChangeListener(new OnDateChangeListener() {

			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				Log.v("Calendar", ""+dayOfMonth);
				btn.setText("Add Meal for " + returnStringDate());
				updateMealList();

			}

		});
		
		initCalendarList();
	}
	
	private String returnStringDate(){
		Date df = new java.util.Date(calendar.getDate());
		String date = new SimpleDateFormat("EEEE, MM/dd").format(df);
		return date;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    if (getParent() != null) 
	    {
	        return getParent().onCreateOptionsMenu(menu);
	    }
		new MenuInflater(this).inflate(R.menu.menu_calendar, menu);
		return (super.onCreateOptionsMenu(menu));
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.add) {
			startActivity(new Intent(this, AddCalendarMeal.class));
			return true;
		}

		return (super.onOptionsItemSelected(item));

	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		helper.close();
	}
	
	private void updateMealList(){
		
	}

	@SuppressWarnings("deprecation")
	private void initCalendarList(){
		if(cursor != null){
			stopManagingCursor(cursor);
			cursor.close();
		}
		cursor = helper.getAll();
		startManagingCursor(cursor);

		String[] from = new String[] {"name", "type"};
	    int[] to = new int[] {R.id.recipeName, R.id.mealType};
		
		SimpleCursorAdapter testAdapter = new SimpleCursorAdapter(this, R.layout.row, cursor, from, to);
		list.setAdapter(testAdapter);
		Log.e("Calendar", "ListView Adapter = " + list.getAdapter());
		list.setOnItemClickListener( new OnItemClickListener() {
		
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id){
				Toast.makeText(getBaseContext(), "Click", Toast.LENGTH_LONG).show();
			}
		});
	}
}