package com.csci422.westsideshoppers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class IngredientHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "ingredients.db";
	private static final int SCHEMA_VERSION = 1;


	public IngredientHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE ingredients (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, type TEXT);");
	}

	public Cursor getAll() {
		return getReadableDatabase().rawQuery("SELECT _id, name, type FROM ingredients", null);
	}

	public Cursor getById(String id) {
		String[] args = {id};

		return getReadableDatabase().rawQuery("SELECT _id, name, type FROM ingredients WHERE _ID=?", args);
	}

	public void insert(String name, String type) {
		ContentValues cv=new ContentValues();

		cv.put("name", name);
		cv.put("type", type);

		getWritableDatabase().insert("ingredients", "name", cv);
	}

	public void update(String id, String name, String type) {
		ContentValues cv=new ContentValues();
		String[] args={id};

		cv.put("name", name);
		cv.put("type", type);

		getWritableDatabase().update("ingredients", cv, "_ID=?", args);
	}

	public String getName(Cursor c) {
		return c.getString(1);
	}
	
	public String getType(Cursor c) {
		return c.getString(2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Nothing to do here, on db scheme 1
	}

}