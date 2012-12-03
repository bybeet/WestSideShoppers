package com.csci422.westsideshoppers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Shopping extends ListActivity {

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
		//list.setClickable(true);
		/*list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
				CheckBox box = (CheckBox)arg0.findViewById(R.id.row_checkbox);
				box.setChecked(!box.isChecked());
				System.out.println("CLICK");
			}

		});*/

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
