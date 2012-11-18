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
        
        // Recipe Button
        Button recipeButton = (Button)findViewById(R.id.buttonRecipes);
        recipeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(WestSideShoppers.this, com.csci422.westsideshoppers.Recipes.class);
				startActivity(i);
			}
		});
        
        // Shopping Button
        Button shoppingButton = (Button)findViewById(R.id.buttonShopping);
        shoppingButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(WestSideShoppers.this, com.csci422.westsideshoppers.Shopping.class));	
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_west_side_shoppers, menu);
        return true;
    }
}