package com.csci422.westsideshoppers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Shopping extends Activity {

	public static String DATE_1 = "com.csci422.date1";
	public static String DATE_2 = "com.csci442.date2";

	private Cursor recipe;
	private Cursor calendar;

	private RecipeHelper recipeHelper;
	private CalendarHelper calHelper;

	private TextView dateRange;

	private DatePicker startSpinner;
	private DatePicker endSpinner;
	private Button setDate;

	private String beginDate;
	private String endDate;

	private ListView list;

	@Override
	public void onCreate(Bundle savedInstanceBundle){
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.activity_shopping);

		dateRange = (TextView)findViewById(R.id.dateRange);

		list = (ListView)findViewById(R.id.list);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);  

		recipeHelper = new RecipeHelper(this);
		calHelper = new CalendarHelper(this);

		startSpinner = (DatePicker)findViewById(R.id.begin_date);
		startSpinner.setCalendarViewShown(false);
		endSpinner = (DatePicker)findViewById(R.id.end_date);
		endSpinner.setCalendarViewShown(false);

		setDate = (Button)findViewById(R.id.setList);	
		setDate.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(endSpinner.getCalendarView().getDate() <= startSpinner.getCalendarView().getDate()){
					AlertDialog.Builder alert = new AlertDialog.Builder(Shopping.this);
					alert.setNegativeButton("Okay", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					})
					.setMessage(R.string.end_date_in_past)
					.setCancelable(true)
					.show();
					return;
				}
				
				startSpinner.setSpinnersShown ( !startSpinner.getSpinnersShown());
				endSpinner.setSpinnersShown ( !endSpinner.getSpinnersShown());
				
				if(startSpinner.getSpinnersShown()){
					setDate.setText(R.string.set_date_range);
					dateRange.setVisibility(View.GONE);
					list.setVisibility(View.GONE);
				}
				else {
					
					setDate.setText(R.string.show_date_pickers);
					setUpShoppingList();
				}
			}

		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    if(getParent() != null) {
	        return getParent().onCreateOptionsMenu(menu);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.clear) {
			Toast.makeText(this, "Clear", Toast.LENGTH_SHORT).show();
			return true;
		}
		if(item.getItemId() == R.id.addItem){
			Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
			return true;
		}

		return false;
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		calHelper.close();
		recipeHelper.close();
	}

	private void setUpShoppingList(){
		setPickerDate();
		dateRange.setText("From " + beginDate + " to " + endDate);
		dateRange.setVisibility(View.VISIBLE);

		if(calendar != null){
			stopManagingCursor(calendar);
			calendar.close();
		}
		if(recipe != null){
			stopManagingCursor(recipe);
			recipe.close();
		}

		calendar = calHelper.getAll();
		startManagingCursor(calendar);

		ArrayList<String> ingredient = new ArrayList<String>();

		if (calendar != null) {

			//more to the first row
			calendar.moveToFirst();

			//iterate over rows
			for (int i = 0; i < calendar.getCount(); i++) {
				if(calHelper.getDate(calendar).equals(beginDate) || calHelper.getDate(calendar).equals(endDate)){
					recipe = recipeHelper.getByRecipeName(calHelper.getRecipe(calendar));

					recipe.moveToFirst();

					for( int j = 1; j < 4; j++ ){
						if(recipeHelper.getIngredient(recipe, j).length() > 0){
							ingredient.add(recipeHelper.getIngredient(recipe, j));
						}
					}
				}

				calendar.moveToNext();
			}
		}


		String[] from = new String[] {"recipe"};
		int[] to = new int[] {R.id.recipeName};
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row_check_box, calendar, from, to);

		ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, ingredient);
		
		list.setVisibility(View.VISIBLE);
		list.setAdapter(aAdapter);
	}

	private void setPickerDate(){
		beginDate = stringDate(startSpinner);
		endDate = stringDate(endSpinner);
	}

	private String stringDate(DatePicker picker){
		Date df = new Date(picker.getCalendarView().getDate());
		return new SimpleDateFormat("MM/dd/yy").format(df);
	}
}
