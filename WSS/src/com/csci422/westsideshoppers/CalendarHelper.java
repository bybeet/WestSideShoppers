package com.csci422.westsideshoppers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalendarHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "calendar.db";
	private static final int SCHEMA_VERSION = 1;


	public CalendarHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE calendar (_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, recipe TEXT);");
	}

	public Cursor getAll() {
		return getReadableDatabase().rawQuery("SELECT _id, date, recipe FROM calendar ORDER BY date ASC", null);
	}

	public Cursor getById(String id) {
		String[] args = {id}; 

		return getReadableDatabase().rawQuery("SELECT _id, date, recipe FROM calendar WHERE _ID=?", args);
	}
	
	public void delete(String id){		
		getWritableDatabase().delete("calendar", "_id" + "=" + id,null);
	}
	
	public Cursor getByDate(String date){
		String sql = " SELECT * FROM calendar WHERE date LIKE '%" + date + "%'";
		return getReadableDatabase().rawQuery(sql, null);
	}

	public void insert(String date, String recipe){
		ContentValues cv=new ContentValues();

		cv.put("date", date);
		cv.put("recipe", recipe);

		getWritableDatabase().insert("calendar", "date", cv);
	}

	public void update(String id, String date, String recipe){
		ContentValues cv=new ContentValues();
		String[] args={id};

		cv.put("date", date);
		cv.put("recipe", recipe);
		
		getWritableDatabase().update("calendar", cv, "_ID=?", args);
	}

	public String getDate(Cursor c) {
		return c.getString(1);
	}
	
	public String getRecipe(Cursor c){
		return c.getString(2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Nothing to do here, on db scheme 1
	}
}