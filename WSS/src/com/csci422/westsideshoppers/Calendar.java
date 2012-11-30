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
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class Calendar extends Activity {

	private Cursor cursor;
	private CalendarHelper helper;
	private CalendarAdapter adapter;
	private GestureDetector mGestureDetector;
	private View mAnotherView;
	private CalendarView calendar;
	private Button btn;
	private CalendarHelper calHelper;
	
	public final static String DATE_ID = "com.csci422.westsideshoppers_DATE";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		
		calendar = (CalendarView)findViewById(R.id.calendarMealView);
		btn = (Button)findViewById(R.id.addMeal);
		calHelper = new  CalendarHelper(this);
		btn.append(" " + returnStringDate());


		/*		findViewById(R.id.frameView).setOnTouchListener(new View.OnTouchListener() {

		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        return mGestureDetector.onTouchEvent(event);
		    }
		});

		mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

		    @Override
		    public void onLongPress(MotionEvent e) {
		        // do your tasks here
		    	Log.v("Caledar", "Long press");
		    }
		});*/

		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Calendar.this, AddCalendarMeal.class);
				
				i.putExtra(DATE_ID, "" + calendar.getDate());
				startActivity(i);
			}
		});

		calendar.setOnDateChangeListener(new OnDateChangeListener() {

			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				Log.v("Calendar", ""+dayOfMonth);
				btn.setText("Add Meal for " + returnStringDate());
				updateMealList();

			}

		});


		helper = new CalendarHelper(this);

		//initCalendarList();
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
		adapter = new CalendarAdapter(cursor);

		//setListAdapter(adapter);

	}

	class CalendarAdapter extends CursorAdapter {
		CalendarAdapter(Cursor c){
			super(Calendar.this, c);
		}

		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			CalendarHolder holder = (CalendarHolder)row.getTag();
			holder.populateFrom(c, helper);
		}

		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent){
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);
			CalendarHolder holder = new CalendarHolder(row);
			row.setTag(holder);
			return row;
		}
	}

	static class CalendarHolder {
		private TextView name;
		private TextView mealType;

		CalendarHolder (View row){
			name = (TextView)row.findViewById(R.id.recipeName);
			mealType = (TextView)row.findViewById(R.id.mealType);
		}

		void populateFrom(Cursor c, CalendarHelper helper){
			Date df = new java.util.Date(helper.getDate(c));
			String date = new SimpleDateFormat("EEEE, MM/dd").format(df);
			mealType.setText(date);
			name.setText(helper.getRecipe(c));

		}
	}

}