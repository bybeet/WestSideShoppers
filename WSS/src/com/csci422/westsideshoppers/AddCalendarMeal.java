package com.csci422.westsideshoppers;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

public class AddCalendarMeal extends Activity{
	
	private Cursor recipes;
	private Cursor calCursor;
	
	private RecipeHelper recipeHelper;
	private CalendarHelper calHelper;
	
	private Spinner spinner;
	private CalendarView calendar;
	
	private boolean somethingSelected;
	private String recipeName;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_calendar_meal);
		
		somethingSelected = false;
		
		recipeHelper = new RecipeHelper(this);
		calHelper = new  CalendarHelper(this);
		spinner = (Spinner)findViewById(R.id.spinner);
		calendar = (CalendarView)findViewById(R.id.calendarView);
		
		populateSpinner();
		
		Button btn = (Button)findViewById(R.id.addMealCal);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(!somethingSelected){				
					Toast.makeText(AddCalendarMeal.this, "No meal selected.", Toast.LENGTH_SHORT).show();
				}
				else{
					Log.v("Date", "Date is: " + calendar.getDate());
					calHelper.insert(calendar.getDate(), recipeName);
					finish();
				}
			}
			
		});
	}
	
	@Override
	public void onDestroy () {
		super.onDestroy();
		recipeHelper.close();
		calHelper.close();
	}
	
	private void populateSpinner(){
		if(recipes != null){
			stopManagingCursor(recipes);
			recipes.close();
		}
		
		recipes = recipeHelper.getAll();
		startManagingCursor(recipes);
		
		String[] columns = {"name", "type"};
		int[] to = new int[] {R.id.recipeName , R.id.mealType};
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row, recipes, columns, to);
		adapter.setDropDownViewResource(R.layout.row);
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			public void onNothingSelected(AdapterView<?> parent){
				
			}

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
				somethingSelected = true;
				recipes = (Cursor)spinner.getItemAtPosition(pos);
				recipeName = recipeHelper.getName(recipes);
				Log.v("Spinner", recipeHelper.getName(recipes));
			}
		
		});
	}
}