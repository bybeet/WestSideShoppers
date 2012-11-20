package com.csci422.westsideshoppers;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class AddCalendarMeal extends Activity{
	
	private Cursor recipes;
	private Cursor calCursor;
	
	private RecipeHelper recipeHelper;
	private CalendarHelper calHelper;
	
	private Spinner spinner;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_calendar_meal);
		
		recipeHelper = new RecipeHelper(this);
		calHelper = new  CalendarHelper(this);
		spinner = (Spinner)findViewById(R.id.spinner);
		
		populateSpinner();
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
		int[] to = new int[] {android.R.id.text1 , android.R.id.text2};
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, recipes, columns, to);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
}