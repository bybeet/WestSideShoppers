package com.csci422.westsideshoppers;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Spinner;

public class AddCalendarMeal extends ListActivity{
	
	private Cursor recipes;
	private Cursor calCursor;
	
	private RecipeHelper recipeHelper;
	private CalendarHelper calHelper;
	
	private Spinner spinner;
	private CalendarView calendar;
	
	private boolean somethingSelected;
	private String recipeName;
	private String date;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_calendar_meal);
		
		somethingSelected = false;
		Intent i = getIntent();
		date = i.getStringExtra(Calendar.DATE_ID);
		
		recipeHelper = new RecipeHelper(this);
		calHelper = new  CalendarHelper(this);
		
		initRecipeList();

	}
	
	@Override
	public void onDestroy () {
		super.onDestroy();
		recipeHelper.close();
		calHelper.close();
	}
	
	@Override
	public void onListItemClick(ListView list, View view, int position, long id) {
		if(recipes != null){
			stopManagingCursor(recipes);
			recipes.close();
		}
		
		recipes = recipeHelper.getById(String.valueOf(id));
		startManagingCursor(recipes);
		recipes.moveToFirst();
		
		calHelper.insert(date, recipeHelper.getName(recipes));
		finish();
	}
	
	@SuppressWarnings("deprecation")
	private void initRecipeList(){
		if(recipes != null){
			stopManagingCursor(recipes);
			recipes.close();
		}
		
		recipes = recipeHelper.getAll();
		startManagingCursor(recipes);
		
		String[] from = new String[] {"name", "type"};
		int[] to = new int[] {R.id.recipeName, R.id.mealType};
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row, recipes, from, to);
		setListAdapter(adapter);
	}
	
}