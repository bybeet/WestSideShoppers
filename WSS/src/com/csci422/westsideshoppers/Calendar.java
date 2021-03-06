package com.csci422.westsideshoppers;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Calendar extends Activity {

	private Cursor cursor;
	private Cursor rc;
	private RecipeHelper recipeHelper;
	private CalendarHelper calHelper;
	private CalendarView calendar;
	private Button btn;
	private ListView list;

	public final static String DATE_ID = "com.csci422.westsideshoppers_DATE";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		invalidateOptionsMenu();

		LinearLayout layout = (LinearLayout)findViewById(R.id.calendar_layout);

		recipeHelper = new  RecipeHelper(this);
		calHelper = new CalendarHelper(this);

		calendar = (CalendarView)layout.findViewById(R.id.calendarMealView);
		btn = (Button)layout.findViewById(R.id.addMeal);
		btn.append(" " + returnStringDate());
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Calendar.this, AddCalendarMeal.class);

				i.putExtra(DATE_ID, currentDate());
				startActivity(i);
			}
		});

		calendar.setOnDateChangeListener(new OnDateChangeListener() {

			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				btn.setText("Add Meal for " + returnStringDate());
				
				initCalendarList();
			}

		});
		
		list = (ListView)findViewById(R.id.list);
		list.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
				AlertDialog.Builder diag = new AlertDialog.Builder(Calendar.this);
				final long test = id;
				diag.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						calHelper.delete(String.valueOf(test));
						initCalendarList();
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				})
				.setCancelable(true)
				.show();

				return true;

			}
		});

		initCalendarList();

	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		calHelper.close();
		recipeHelper.close();
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    if(getParent() != null) {
	        return getParent().onPrepareOptionsMenu(menu);
	    }
	    return false;
	}
	
	@SuppressWarnings("deprecation")
	private void initCalendarList(){
		if(cursor != null){
			stopManagingCursor(cursor);
			cursor.close();
		}

		cursor = calHelper.getByDate(currentDate());
		startManagingCursor(cursor);		

		String[] from = new String[] {"recipe"};
		int[] to = new int[] {android.R.id.text1};
		SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, from, to);

		list.setAdapter(mAdapter);
	}

	private String returnStringDate(){
		Date df = new java.util.Date(calendar.getDate());
		String date = new SimpleDateFormat("EEEE, MM/dd").format(df);
		return date;
	}
	
	private String currentDate(){
		Date df = new Date(calendar.getDate());
		return new SimpleDateFormat("MM/dd/yy").format(df);
	}
		
}