package com.csci422.westsideshoppers;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class WestSideShoppers extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_west_side_shoppers);

		// Create tab host
		TabHost tabHost = getTabHost();

		// Add tabs
		tabHost.addTab(tabHost.newTabSpec("Meal Planner").setIndicator("Meal Planner").setContent(new Intent(this, Calendar.class)));
		tabHost.addTab(tabHost.newTabSpec("Shopping List").setIndicator("Shopping List").setContent(new Intent(this, Shopping.class)));
		tabHost.addTab(tabHost.newTabSpec("Recipe Editor").setIndicator("Recipe Editor").setContent(new Intent(this, Recipes.class)));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.clear();
		return true;
	}
}