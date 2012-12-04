package com.csci422.westsideshoppers;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Recipes extends ListActivity {

	public final static String ID_EXTRA = "com.csci422.westsideshoppers._ID";
	public final static String INGREDIENTS_LIST = "com.csci422.westsideshoppers._INGREDIENTS";

	private Cursor recipes;
	private RecipeHelper helper;

	private EditText search;

	private ArrayList<String> ingredients;
	private ArrayList<String> recipeNames;
	private ArrayAdapter<String> recipeNameAdapter;
	private SimpleCursorAdapter mAdapter;


	@Override
	public void onCreate(Bundle savedInstanceBundle){
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.activity_recipes);

		helper = new RecipeHelper(this);
		ingredients = new ArrayList<String>();
		recipeNames = new ArrayList<String>();

		Button btn = (Button)findViewById(R.id.addRecipe);
		btn.setOnClickListener(new OnClickListener() {

			//Call the add/create recipe screen.
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Recipes.this, com.csci422.westsideshoppers.AddRecipe.class);
				i.putStringArrayListExtra(INGREDIENTS_LIST, ingredients);
				if( ingredients != null)
					System.out.println(ingredients.toString());
				startActivityForResult(i, 100);
			}
		});

		ListView lv = getListView();
		lv.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
				AlertDialog.Builder diag = new AlertDialog.Builder(Recipes.this);
				final long test = id;
				diag.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						helper.delete(String.valueOf(test));
						initRecipeList();
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				})
				.setCancelable(true)
				.show();

				return true;

			}
		});

		search = (EditText) findViewById(R.id.search);

		initRecipeList();

		mAdapter.setFilterQueryProvider(new FilterQueryProvider() {

			public Cursor runQuery(CharSequence constraint) {

				recipes = helper.getByRecipeName(constraint.toString());

				return recipes;
			}

		});

		search.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				System.out.println("Error with onTextChanged");
				if(!mAdapter.getCursor().isClosed())
					mAdapter.getFilter().filter(s);
				//mAdapter.notifyDataSetChanged();
			}

		});
	}

	@Override
	public void onDestroy () {
		super.onDestroy();
		helper.close();
	}

	@Override
	public void onListItemClick(ListView list, View view, int position, long id) {
		Intent intent = new Intent(Recipes.this, AddRecipe.class);
		intent.putExtra(ID_EXTRA, String.valueOf(id));
		intent.putStringArrayListExtra(INGREDIENTS_LIST, ingredients);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 100){
			initRecipeList();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		if(getParent() != null) {
			return getParent().onPrepareOptionsMenu(menu);
		}
		return false;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if(getParent() != null) {
			return getParent().onPrepareOptionsMenu(menu);
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	private void initRecipeList(){
		if(recipes != null){
			stopManagingCursor(recipes);
			recipes.close();
		}

		recipes = helper.getAll();
		//startManagingCursor(recipes);

		String[] from = new String[] {"name", "type"};
		int[] to = new int[] {R.id.recipeName, R.id.mealType};
		mAdapter = new SimpleCursorAdapter(this, R.layout.row, recipes, from, to);

		ListView lv = getListView();
		lv.setAdapter(mAdapter);
		lv.setTextFilterEnabled(true);

		ingredients = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>();

		recipes.moveToFirst();
		for (int i = 0; i < recipes.getCount(); i++) {
			names.add(helper.getName(recipes));
			for( int j = 1; j < 4; j++ ){
				if(helper.getIngredient(recipes, j).length() > 0 && !ingredients.contains(helper.getIngredient(recipes, j))){
					//ingredients.add(helper.getIngredient(c));
					System.out.println(helper.getIngredient(recipes, j));
					ingredients.add(helper.getIngredient(recipes, j));
				}
			}
			recipes.moveToNext();
		}
	}

}