package com.csci422.westsideshoppers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class WestSideShoppers extends Activity {

	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_west_side_shoppers);
        
        Button btn = (Button)findViewById(R.id.buttonRecipes);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(WestSideShoppers.this, com.csci422.westsideshoppers.Recipes.class);
				startActivity(i);
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_west_side_shoppers, menu);
        return true;
    }
}