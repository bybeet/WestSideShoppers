package com.csci422.westsideshoppers;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class WestSideShoppers extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_west_side_shoppers);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_west_side_shoppers, menu);
        return true;
    }
}
