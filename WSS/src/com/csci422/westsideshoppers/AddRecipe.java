package com.csci422.westsideshoppers;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddRecipe extends Activity{

	RecipeHelper helper;

	TextView title;

	EditText recipeName;
	EditText ingredient1;
	EditText ingredient2;
	EditText ingredient3;
	EditText direction1;

	CheckBox breakfast;
	CheckBox lunch;
	CheckBox dinner;
	CheckBox dessert;
	CheckBox snack;

	String recipeId;

	ArrayList<String> ingredients;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_recipe);
		//Keep keyboard hidden initially.
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		helper = new RecipeHelper(this);

		Intent intent = getIntent();
		ingredients = intent.getStringArrayListExtra(Recipes.INGREDIENTS_LIST);
		if(ingredients != null){
			System.out.println(ingredients.toString());
		}	

		title = (TextView)findViewById(R.id.recipeCreatorTitle);
		recipeName = (EditText)findViewById(R.id.recipeName);
		ingredient1 = (EditText)findViewById(R.id.ingredient1);
		ingredient2 = (EditText)findViewById(R.id.ingredient2);
		ingredient3 = (EditText)findViewById(R.id.ingredient3);

		direction1 = (EditText)findViewById(R.id.directions);

		breakfast = (CheckBox)findViewById(R.id.breakfast);
		lunch = (CheckBox)findViewById(R.id.lunch);
		dinner = (CheckBox)findViewById(R.id.dinner);
		dessert = (CheckBox)findViewById(R.id.dessert);
		snack = (CheckBox)findViewById(R.id.snack);

		Button btn = (Button)findViewById(R.id.addRecipe);

		//Check to see if the recipe already exists, if so, then load the data into the form.
		recipeId = getIntent().getStringExtra(Recipes.ID_EXTRA);
		if(recipeId != null){
			title.setText(R.string.edit_recipe);
			btn.setText(R.string.save_edited_recipe);
			TextView cancelSave = new TextView(this);
			cancelSave.setText(R.string.cancel_edit_recipe);
			LinearLayout linearLayout = (LinearLayout)findViewById(R.id.recipe_create_layout);
			linearLayout.addView(cancelSave);
			load();
		}

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
		StringBuilder mealType = new StringBuilder("");
		if(breakfast.isChecked()){
			mealType.append(breakfast.getText().toString());
		}
		if(lunch.isChecked()){
			if(mealType.length() > 0){
				mealType.append(", ");
			}
			mealType.append(lunch.getText().toString());
		}
		if(dinner.isChecked()){
			if(mealType.length() > 0){
				mealType.append(", ");
			}
			mealType.append(dinner.getText().toString());
		}
		if(snack.isChecked()){
			if(mealType.length() > 0){
				mealType.append(", ");
			}
			mealType.append(snack.getText().toString());
		}
		if(dessert.isChecked()){
			if(mealType.length() > 0){
				mealType.append(", ");
			}
			mealType.append(dessert.getText().toString());
		}
		if(recipeId == null) {
			helper.insert(recipeName.getText().toString(), mealType.toString(), ingredient1.getText().toString(), ingredient2.getText().toString(), ingredient3.getText().toString(), direction1.getText().toString());
		}
		else {
			helper.update(recipeId, recipeName.getText().toString(), mealType.toString(), ingredient1.getText().toString(), ingredient2.getText().toString(), ingredient3.getText().toString(), direction1.getText().toString());			
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
		if( type.contains(breakfast.getText().toString())){
			breakfast.setChecked(true);
		}
		if( type.contains(lunch.getText().toString())){
			lunch.setChecked(true);
		}
		if(type.contains(dinner.getText().toString())){
			dinner.setChecked(true);
		}
		if(type.contains(snack.getText().toString())){
			snack.setChecked(true);
		}
		if(type.contains(dessert.getText().toString())){
			dessert.setChecked(true);
		}
		c.close();
	}

	public void noRecipeAdded(){
		StringBuilder error = new StringBuilder("This doesn't look like a valid recipe, you are missing:\n");
		if(recipeName.getText().toString().equals("")){
			error.append("\tThere is no recipe name.\n");
		}
		if(direction1.getText().toString().equals("")){
			error.append("\tThere is no first ingredient.\n");
		}
		if(ingredient1.getText().toString().equals("")){
			error.append("\tThere are no directions.\n");
		}

		error.append("\nTo cancel creating a recipe, press the back button.");

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setMessage(error.toString())
		.setPositiveButton("Return and fix.", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		})
		.setNegativeButton("Cancel recipe creation", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		})
		.show();
	}

}