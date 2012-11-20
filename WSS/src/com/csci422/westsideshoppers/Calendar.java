package com.csci422.westsideshoppers;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.csci422.westsideshoppers.Recipes.RecipeHolder;

public class Calendar extends ListActivity {
	
	private Cursor calendar;
	private CalendarHelper helper;
	private CalendarAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		
		helper = new CalendarHelper(this);
		
		Button btn = (Button)findViewById(R.id.addMeal);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Calendar.this, AddCalendarMeal.class);
				startActivity(i);
			}
		});
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		helper.close();
	}
	
	@SuppressWarnings("deprecation")
	private void initRecipeList(){
		if(calendar != null){
			stopManagingCursor(calendar);
			calendar.close();
		}
		
		calendar = helper.getAll();
		startManagingCursor(calendar);
		
		adapter = new CalendarAdapter(calendar);
		setListAdapter(adapter);
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
			RecipeHolder holder = new RecipeHolder(row);
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
			String date = new SimpleDateFormat("MM dd").format(df);
			mealType.setText(date);
			name.setText(helper.getRecipe(c));
			//Log.e("Recipe List", helper.getType(c));
			
		}
	}
}