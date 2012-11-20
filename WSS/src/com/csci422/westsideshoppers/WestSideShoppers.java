package com.csci422.westsideshoppers;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
        tabHost.addTab(tabHost.newTabSpec("Shopping").setIndicator("Shopping").setContent(new Intent(this, Shopping.class)));
        tabHost.addTab(tabHost.newTabSpec("Recipes").setIndicator("Recipes").setContent(new Intent(this, Recipes.class)));
        tabHost.addTab(tabHost.newTabSpec("Calendar").setIndicator("Calendar").setContent(new Intent(this, Calendar.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_west_side_shoppers, menu);
        return true;
    }
}