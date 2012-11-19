package com.csci422.westsideshoppers;

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

public class Recipes extends ListActivity {
	
	private Cursor recipes;
	private RecipeHelper helper;
	private RecipeAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle){
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.activity_recipes);
		
		helper = new RecipeHelper(this);
		
		Button btn = (Button)findViewById(R.id.addRecipe);
		btn.setOnClickListener(new OnClickListener() {
			
			//Call the add/create recipe screen.
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Recipes.this, com.csci422.westsideshoppers.AddRecipe.class);
				startActivity(i);
			}
		});
		
		initRecipeList();
	}
	
	private void initRecipeList(){
		if(recipes != null){
			stopManagingCursor(recipes);
			recipes.close();
		}
		
		recipes = helper.getAll();
		startManagingCursor(recipes);
		
		adapter = new RecipeAdapter(recipes);
		setListAdapter(adapter);
	}
	
	class RecipeAdapter extends CursorAdapter {
		RecipeAdapter(Cursor c){
			super(Recipes.this, c);
		}
		
		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			RecipeHolder holder = (RecipeHolder)row.getTag();
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
	
	static class RecipeHolder {
		private TextView name;
		
		RecipeHolder (View row){
			name = (TextView)row.findViewById(R.id.recipeName);
		}
		
		void populateFrom(Cursor c, RecipeHelper helper){
			name.setText(helper.getName(c));
		}
	}
	
	
}