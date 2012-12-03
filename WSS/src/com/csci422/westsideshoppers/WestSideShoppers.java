package com.csci422.westsideshoppers;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

@SuppressWarnings("deprecation")
public class WestSideShoppers extends TabActivity {

	private TabHost tabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_west_side_shoppers);

		// Create tab host
		tabHost = getTabHost();

		// Add tabs
		tabHost.addTab(tabHost.newTabSpec("Calendar").setIndicator("Calendar").setContent(new Intent(this, Calendar.class)));
		tabHost.addTab(tabHost.newTabSpec("Shopping").setIndicator("Shopping").setContent(new Intent(this, Shopping.class)));
		tabHost.addTab(tabHost.newTabSpec("Recipes").setIndicator("Recipes").setContent(new Intent(this, Recipes.class)));
		
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				invalidateOptionsMenu();
			}
		});

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		MenuInflater inflate = getMenuInflater();
		switch(tabHost.getCurrentTab()){
		case 0:
			System.out.println("cal");
			inflate.inflate(R.menu.menu_calendar, menu);
			return true;
		case 1:
			System.out.println("shop");
			inflate.inflate(R.menu.menu_shopping, menu);
			return true;
		}
		return false;
	}
	
}