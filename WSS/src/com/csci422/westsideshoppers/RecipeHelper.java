package com.csci422.westsideshoppers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class RecipeHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "recipes.db";
	private static final int SCHEMA_VERSION = 1;


	public RecipeHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE recipes (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, type TEXT, ingredient1 TEXT, ingredient2 TEXT, ingredient3 TEXT, direction1 TEXT, direction2 TEXT, direction3 TEXT);");
	}

	public Cursor getAll() {
		return getReadableDatabase().rawQuery("SELECT _id, name, type, ingredient1, ingredient2, ingredient3 FROM recipes", null);
	}

	public Cursor getById(String id) {
		String[] args = {id};

		return getReadableDatabase().rawQuery("SELECT _id, name, type, ingredient1, ingredient2, ingredient3, direction1, direction2, direction3 FROM recipes WHERE _ID=?", args);
	}

	public void insert(String name, String type, String ingredient1, String ingredient2, String ingredient3, String direction1, String direction2, String direction3) {
		ContentValues cv=new ContentValues();

		cv.put("name", name);
		cv.put("type", type);
		cv.put("ingredient1", ingredient1);
		cv.put("ingredient2", ingredient2);
		cv.put("ingredient3", ingredient3);
		cv.put("direction1", direction1);
		cv.put("direction2", direction2);
		cv.put("direction3", direction3);

		getWritableDatabase().insert("recipes", "name", cv);
	}

	public void update(String id, String name, String type, String ingredient1, String ingredient2, String ingredient3, String direction1, String direction2, String direction3) {
		ContentValues cv=new ContentValues();
		String[] args={id};

		cv.put("name", name);
		cv.put("type", type);
		cv.put("ingredient1", ingredient1);
		cv.put("ingredient2", ingredient2);
		cv.put("ingredient3", ingredient3);
		cv.put("direction1", direction1);
		cv.put("direction2", direction2);
		cv.put("direction3", direction3);
		
		getWritableDatabase().update("recipes", cv, "_ID=?", args);
	}

	public String getName(Cursor c) {
		return c.getString(1);
	}
	
	public String getType(Cursor c){
		return c.getString(2);
	}
	
	public String getIngredient(Cursor c, int ingredient){
		return c.getString(ingredient+2);
	}
	
	public String getDirection(Cursor c, int direction){
		return c.getString(direction+5);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Nothing to do here, on db scheme 1
	}

}