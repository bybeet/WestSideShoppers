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

		db.execSQL("CREATE TABLE recipes (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);");
	}

	public Cursor getAll() {
		return getReadableDatabase().rawQuery("SELECT _id, name FROM recipes", null);
	}

	public Cursor getById(String id) {
		String[] args = {id};

		return getReadableDatabase().rawQuery("SELECT _id, name FROM recipes WHERE _ID=?", args);
	}

	public void insert(String name) {
		ContentValues cv=new ContentValues();

		cv.put("name", name);

		getWritableDatabase().insert("recipes", "name", cv);
	}

	public void update(String id, String name) {
		ContentValues cv=new ContentValues();
		String[] args={id};

		cv.put("name", name);

		getWritableDatabase().update("recipes", cv, "_ID=?", args);
	}

	public String getName(Cursor c) {
		return c.getString(1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Nothing to do here, on db scheme 1
	}

}