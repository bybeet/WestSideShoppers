package com.csci422.westsideshoppers;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

public class Calendar extends Activity {

	private Cursor cursor;
	private RecipeHelper helper;
	private CalendarHelper calHelper;
	private CalendarView calendar;
	private Button btn;
	private ListView list;

	public final static String DATE_ID = "com.csci422.westsideshoppers_DATE";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);

		LinearLayout layout = (LinearLayout)findViewById(R.id.calendar_layout);

		helper = new  RecipeHelper(this);
		calHelper = new CalendarHelper(this);

		calendar = (CalendarView)layout.findViewById(R.id.calendarMealView);
		btn = (Button)layout.findViewById(R.id.addMeal);
		btn.append(" " + returnStringDate());
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
				//btn.setText("Add Meal for " + returnStringDate());
			}

		});
		
		initCalendarList();

		list = (ListView)findViewById(R.id.list);
		list.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
				AlertDialog.Builder diag = new AlertDialog.Builder(Calendar.this);
				//final long test = id;
				diag.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(Calendar.this, "Delete", Toast.LENGTH_SHORT).show();
						//cursor = helper.getById(String.valueOf(test));
						//calHelper.delete(String.valueOf(test));
						//finish();
						//startActivity(getIntent());
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(Calendar.this, "Cancel", Toast.LENGTH_SHORT).show();
					}
				})
				.setCancelable(true)
				.show();

				return true;

			}
		});

		
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		helper.close();
	}

	@SuppressWarnings("deprecation")
	private void initCalendarList(){
		if(cursor != null){
			stopManagingCursor(cursor);
			cursor.close();
		}

		cursor = calHelper.getAll();
		startManagingCursor(cursor);

		String[] from = new String[] {"date", "recipe"};
		int[] to = new int[] {R.id.recipeName, R.id.mealType};
		SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.row, cursor, from, to);

		list.setAdapter(mAdapter);

		/*
		Log.e("Calendar", "ListView Adapter = " + list.getAdapter());
		 */
	}

	private String returnStringDate(){
		Date df = new java.util.Date(calendar.getDate());
		String date = new SimpleDateFormat("EEEE, MM/dd").format(df);
		return date;
	}

}

/*




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

list.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id){
				Toast.makeText(getBaseContext(), "Click", Toast.LENGTH_LONG).show();
			}
		});

 */