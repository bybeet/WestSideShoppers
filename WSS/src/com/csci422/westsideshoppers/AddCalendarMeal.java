package com.csci422.westsideshoppers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;

public class AddCalendarMeal extends Activity{
	
	private Cursor recipeCursor;
	private Cursor calCursor;
	
	private RecipeHelper recipeHelper;
	private CalendarHelper calHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_calendar_meal);
		
		
	}
	
}