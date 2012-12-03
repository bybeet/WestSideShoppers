package com.csci422.westsideshoppers;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Spinner;

import com.csci422.westsideshoppers.Recipes.RecipeAdapter;

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
	
	/*
	class RecipeAdapter extends CursorAdapter {
		RecipeAdapter(Cursor c){
			super(AddCalendarMeal.this, c);
		}
		
		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			RecipeHolder holder = (RecipeHolder)row.getTag();
			holder.populateFrom(c, recipeHelper);
		}
		
		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent){
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);
			RecipeHolder holder = new RecipeHolder(row);
			row.setTag(holder);
			return row;
		}
	}
	
	static class RecipeHolder {
		private TextView name;
		private TextView mealType;
		
		RecipeHolder (View row){
			name = (TextView)row.findViewById(R.id.recipeName);
			mealType = (TextView)row.findViewById(R.id.mealType);
		}
		
		void populateFrom(Cursor c, RecipeHelper helper){
			mealType.setText(helper.getType(c));
			name.setText(helper.getName(c));
		}
	}
	*/
}