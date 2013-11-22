package com.jemcphe.nbaplayers.SQLPack;

import java.util.ArrayList;
import java.util.List;

import com.jemcphe.nbaplayers.Athlete;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLDataSource {

	private static final String LOGTAG = "NBA PLAYERS";


	SQLiteOpenHelper dbHelper;
	SQLiteDatabase dataBase;

	public SQLDataSource (Context context){

		//SQLite setup
		dbHelper = new SqlDBOpenHelper(context);

	}

	// function helper for inserting data into database
	public void insertEntry(String displayName, String team, String jerseyNumber, String height, String weight){
		// Create ContentValues to place into insert() method
		ContentValues values = new ContentValues();
		// place passed values
		values.put(SqlDBOpenHelper.COLUMN_DISPLAYNAME, displayName);
		values.put(SqlDBOpenHelper.COLUMN_TEAM, team);
		values.put(SqlDBOpenHelper.COLUMN_JERSEYNUMBER, jerseyNumber);
		values.put(SqlDBOpenHelper.COLUMN_HEIGHT, height);
		values.put(SqlDBOpenHelper.COLUMN_WEIGHT, weight);
		// Insert into Athlete Table
		dataBase.insert(SqlDBOpenHelper.TABLE_ATHLETE, null, values);
	}

	public void updateEntry(String displayName, String team, String jerseyNumber, String height, String weight){
		// Create WHERE Clause String
		String whereClause = "displayName = '" + displayName + "'";
		// Create ContentValues to place into insert() method
		ContentValues values = new ContentValues();
		// place passed values
		values.put(SqlDBOpenHelper.COLUMN_DISPLAYNAME, displayName);
		values.put(SqlDBOpenHelper.COLUMN_TEAM, team);
		values.put(SqlDBOpenHelper.COLUMN_JERSEYNUMBER, jerseyNumber);
		values.put(SqlDBOpenHelper.COLUMN_HEIGHT, height);
		values.put(SqlDBOpenHelper.COLUMN_WEIGHT, weight);
		// Update Athletes Table
		dataBase.update(SqlDBOpenHelper.TABLE_ATHLETE, values, whereClause , null);
	}
	// open database
	public void open () throws SQLException{
		Log.i(LOGTAG, "Database Opened");
		dataBase = dbHelper.getWritableDatabase();
	}
	// close database
	public void close () {
		Log.i(LOGTAG, "Database Closed");
		dbHelper.close();
	}
	// delete database... currently not implemented
	public void delete(String where) {
		dataBase.delete(SqlDBOpenHelper.TABLE_ATHLETE, where, null);
	}
	// showData... currently not implemented
	public String showData() {
		return null;
	}

	private Athlete cursorToAthlete(Cursor cursor){
		Athlete athlete = new Athlete();
		athlete.setDisplayName(cursor.getString(1));
		athlete.setTeam(cursor.getString(2));
		athlete.setJerseyNumber(cursor.getString(3));
		athlete.setHeight(cursor.getString(4));
		athlete.setWeight(cursor.getString(5));


		return athlete;
	}

	public List<Athlete> findAll(){

		//dataBase = dbHelper.getReadableDatabase();

		List<Athlete> athletes = new ArrayList<Athlete>();

		Cursor cursor = dataBase.rawQuery("SELECT * FROM athlete", null);

		//Loop through accessories
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			while (!cursor.isAfterLast()){
				Athlete athlete = cursorToAthlete(cursor);
				athletes.add(athlete);
				cursor.moveToNext();

				Log.i(LOGTAG, athletes.toString());
			}
		}
		cursor.close();
		return athletes;
	}

	public List<Athlete> singleAthlete(){

		List<Athlete> athletes = new ArrayList<Athlete>();

		Cursor cursor = dataBase.rawQuery("SELECT * FROM athlete WHERE displayName = 'Kevin Durant'", null);

		//Loop through accessories
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			while (!cursor.isAfterLast()){
				Athlete athlete = cursorToAthlete(cursor);
				athletes.add(athlete);
				cursor.moveToNext();

				Log.i(LOGTAG, athletes.toString());
			}
		}
		cursor.close();
		return athletes;
	}

	public List<Athlete> teamIsHoustonRockets(){
		// Create a list to hold queried athletes
		List<Athlete> athletes = new ArrayList<Athlete>();

		Cursor cursor = dataBase.rawQuery("SELECT * FROM athlete WHERE team = 'Houston Rockets'", null);

		//Loop through athletes
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			while (!cursor.isAfterLast()){
				Athlete athlete = cursorToAthlete(cursor);
				athletes.add(athlete);
				cursor.moveToNext();

				Log.i(LOGTAG, athletes.toString());
			}
		}
		cursor.close();
		return athletes;
	}

}
