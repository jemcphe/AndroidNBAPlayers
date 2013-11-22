package com.jemcphe.nbaplayers.SQLPack;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlDBOpenHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "NBA PLAYERS";

	// create database name & version constant
	private static final String DATABASE_NAME = "nba_players.db";
	private static final int DATABASE_VERSION = 1;
	// Create constant string values for SQL columns
	public static final String TABLE_ATHLETE = "athlete";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_DISPLAYNAME = "displayName";
	public static final String COLUMN_TEAM = "team";
	public static final String COLUMN_JERSEYNUMBER = "jerseyNumber";
	public static final String COLUMN_HEIGHT = "height";
	public static final String COLUMN_WEIGHT = "weight";
	// Constant string for Creating SQL Table
	private static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLE_ATHLETE + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_DISPLAYNAME + " TEXT, " +
			COLUMN_TEAM + " TEXT, " +
			COLUMN_JERSEYNUMBER + " TEXT, " +
			COLUMN_HEIGHT + " TEXT, " +
			COLUMN_WEIGHT + " TEXT " +
			")";
	
	public SqlDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(TABLE_CREATE);
		Log.i(LOGTAG, "Table Has been created");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_ATHLETE);
		onCreate(database);
	}

}
