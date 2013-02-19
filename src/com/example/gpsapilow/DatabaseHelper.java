package com.example.gpsapilow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	static int DATABASE_VERSION = 4;
	static String DATABASE_NAME = "gpsApiLowDatabase.db";
	
	static String TABLE_NAME = "history";
	static String HISTORY_COLUMN_LNG = "lng";
	static String HISTORY_COLUMN_LAT = "lat";
	static String HISTORY_COLUMN_NAME = "name";
	static String HISTORY_COLUMN_ID = "_id";
	
	String TAG = DatabaseHelper.class.getCanonicalName();

	public DatabaseHelper(Context context) {
	
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.d(TAG, "DatabaseHelper contructor");
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		Log.d(TAG,"onCreate");
	database.execSQL("CREATE TABLE " + TABLE_NAME  +
	                 "( " + HISTORY_COLUMN_ID + " integer primary key, " + HISTORY_COLUMN_LNG + " text, " +
	                    HISTORY_COLUMN_LAT +" text, "+ HISTORY_COLUMN_NAME + " text)");
	
	}

	//on upgrade will be invoked if i change version of db in consturctor
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVer, int newVer) {
		Log.d(TAG,"onUpgrade");
		
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
		onCreate(database);
		
	}
	
	public void saveRecord(String lat, String lng, String name){
		Log.d(TAG,"saveRecord");
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(HISTORY_COLUMN_LAT, lat);
		contentValues.put(HISTORY_COLUMN_LNG, lng);
		contentValues.put(HISTORY_COLUMN_NAME, name);
		
		database.insert(TABLE_NAME , null, contentValues );
		
	}
	
	public Cursor getAllRecors(){
		SQLiteDatabase database = this.getReadableDatabase();
		return database.rawQuery("SELECT * FROM " + TABLE_NAME  , null);
	}

}
