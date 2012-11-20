package com.csci422.westsideshoppers;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddRecipe extends Activity{

	RecipeHelper helper;

	TextView title;
	
	EditText recipeName;
	EditText ingredient1;
	EditText ingredient2;
	EditText ingredient3;
	EditText direction1;
	
	RadioGroup buttonType;
	RadioButton breakfast;
	RadioButton lunch;
	RadioButton dinner;

	String recipeId;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_recipe);
		//Keep keyboard hidden initially.
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		helper = new RecipeHelper(this);
		
		title = (TextView)findViewById(R.id.recipeCreatorTitle);
		recipeName = (EditText)findViewById(R.id.recipeName);
		ingredient1 = (EditText)findViewById(R.id.ingredient1);
		ingredient2 = (EditText)findViewById(R.id.ingredient2);
		ingredient3 = (EditText)findViewById(R.id.ingredient3);
		
		direction1 = (EditText)findViewById(R.id.directions);
		
		buttonType = (RadioGroup)findViewById(R.id.buttonGroupType);
		breakfast = (RadioButton)findViewById(R.id.breakfast);
		lunch = (RadioButton)findViewById(R.id.lunch);
		dinner = (RadioButton)findViewById(R.id.dinner);

		//Check to see if the recipe already exists, if so, then load the data into the form.
		recipeId = getIntent().getStringExtra(Recipes.ID_EXTRA);
		if(recipeId != null){
			title.setText("Recipe Editor");
			load();
		}
		else{
			breakfast.setChecked(true);
		}

		Button btn = (Button)findViewById(R.id.addRecipe);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view){
				if(recipeName.getText().toString().equals("") || ingredient1.getText().toString().equals("") || direction1.getText().toString().equals("")){
					noRecipeAdded();
				}
				else{
					save();
				}

			}
		});
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		helper.close();
	}

	private void save() {
		RadioButton b = (RadioButton)findViewById(buttonType.getCheckedRadioButtonId());
		if(recipeId == null) {
			helper.insert(recipeName.getText().toString(), b.getText().toString(), ingredient1.getText().toString(), ingredient2.getText().toString(), ingredient3.getText().toString(), direction1.getText().toString());
		}
		else {
			helper.update(recipeId, recipeName.getText().toString(), b.getText().toString(), ingredient1.getText().toString(), ingredient2.getText().toString(), ingredient3.getText().toString(), direction1.getText().toString());			
		}
		finish();
	}


	private void load() {
		Cursor c = helper.getById(recipeId);

		c.moveToFirst();
		recipeName.setText(helper.getName(c));
		ingredient1.setText(helper.getIngredient(c, 1));
		ingredient2.setText(helper.getIngredient(c, 2));
		ingredient3.setText(helper.getIngredient(c, 3));
		direction1.setText(helper.getDirections(c));
		
		String type = helper.getType(c);
		Log.e("Test", helper.getType(c));
		if( type.equals("Breakfast")){
			breakfast.setChecked(true);
		}
		else if( type.equals("Lunch")){
			lunch.setChecked(true);
		}
		else if( type.equals("Dinner")){
			dinner.setChecked(true);
		}
		c.close();
	}

	public void noRecipeAdded(){
		Toast.makeText(this, R.string.no_recipe, Toast.LENGTH_SHORT).show();
	}

}