package com.jemcphe.nbaplayers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.jemcphe.nbaplayers.SQLPack.SQLDataSource;
import com.jemcphe.nbaplayers.SQLPack.SqlDBOpenHelper;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends Activity implements OnClickListener{

	//Declare Local Variables
	ListView listview;
	Context context;
	ParseQuery<ParseObject> query;
	Athlete Athlete;
	CustomAdapter adapter;
	List<ParseObject> object;
	List<ParseObject> singleObject;
	List<ParseObject> teamObject;
	public static SQLDataSource dataSource;
	public static SqlDBOpenHelper dbHelper;

	private List<Athlete> athleteList = null;
	private List<Athlete> allAthleteList = null;
	private List<Athlete> singleAthleteList = null;
	private List<Athlete> teamList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Add your initialization code here
		Parse.initialize(this, "qSFj5qCyU0gGWmsFGyLIxXDrI6XL61gyqH7WCqc1", "FJY2bnB3hc8Wa5LDOLmCzEr2Jl4yrC1qMzGVWb4y");
		//Set Context
		context = this;

		listview = (ListView) this.findViewById(R.id.listView);

		//Create Buttons, setOnClickListener, & setId
		//Single Athlete Button
		Button singleAthleteButton = (Button) this.findViewById(R.id.single_athlete_button);
		singleAthleteButton.setOnClickListener(this);
		singleAthleteButton.setId(0);
		//All Athletes Button
		Button allAthletesButton = (Button) this.findViewById(R.id.all_athletes_button);
		allAthletesButton.setOnClickListener(this);
		allAthletesButton.setId(1);
		//Status Button
		Button teamButton = (Button) this.findViewById(R.id.team_button);
		teamButton.setOnClickListener(this);
		teamButton.setId(2);

		//Initiate NBA PLAYERS pull
		new RemoteDataTask().execute();


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		//Switch statement for menu items
		switch (item.getItemId()) {
		case R.id.action_add:  //Add Athlete Button
			//Create Intent to launch AddActivity class
			Intent addIntent = new Intent(context, AddActivity.class);
			startActivity(addIntent);
			return true;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	// RemoteDataTask AsyncTask
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// Create the array
			athleteList = new ArrayList<Athlete>();
			try {
				// Locate the class table named "Athlete" in Parse.com
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
						"Athlete");
				// Set Cache Policy for when device goes offline
				query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
				//populate List with query results
				object = query.find();

				//Create a File variable that will help check for a file's existence
				File dbFile = context.getDatabasePath("nba_players.db");
				if (dbFile.exists()){

					for (ParseObject Athlete : object) {
						//Create string values to hold object data
						String displayName = (String) Athlete.get("displayName");
						String team = (String) Athlete.get("team");
						String jerseyNumber = (String) Athlete.get("jerseyNumber");
						String height = (String) Athlete.get("height");
						String weight = (String) Athlete.get("weight");

						// declare the dataSource and open it
						dataSource = new SQLDataSource(context);
						dataSource.open();
						//Create new instance of Athlete
						Athlete map = new Athlete();
						//set items accordingly
						map.setId((String) Athlete.getObjectId());
						map.setDisplayName(displayName);
						map.setTeam(team);
						map.setJerseyNumber(jerseyNumber);
						map.setHeight(height);
						map.setWeight(weight);
						// Add items to athleteList
						athleteList.add(map);
					}
				} else {
					for (ParseObject Athlete : object) {
						//Create string values to hold object data
						String displayName = (String) Athlete.get("displayName");
						String team = (String) Athlete.get("team");
						String jerseyNumber = (String) Athlete.get("jerseyNumber");
						String height = (String) Athlete.get("height");
						String weight = (String) Athlete.get("weight");

						// declare the dataSource and open it
						dataSource = new SQLDataSource(context);
						dataSource.open();
						//Create new instance of Athlete
						Athlete map = new Athlete();
						//set items accordingly
						map.setId((String) Athlete.getObjectId());
						map.setDisplayName(displayName);
						map.setTeam(team);
						map.setJerseyNumber(jerseyNumber);
						map.setHeight(height);
						map.setWeight(weight);
						// Add items to athleteList
						athleteList.add(map);
						// add items to SQL database
						dataSource.insertEntry(displayName, team, jerseyNumber, height, weight);
					}
				}
			} catch (ParseException e) {
				// If there be errors, log'em
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case 0:
			//Log Button Selected
			Log.i("NBA PLAYERS", "Kevin Durant Selected");

			// Create a List for placing data
			singleAthleteList = dataSource.singleAthlete();
			// Set Custom Adapter
			CustomAdapter singleAdapter = new CustomAdapter(this, singleAthleteList);
			listview.setAdapter(singleAdapter);

			break;
		case 1:
			//Log Button Selected
			Log.i("NBA PLAYERS", "All Athletes Selected");
			// Create a List for placing data
			allAthleteList = dataSource.findAll();
			// Set Custom Adapter
			CustomAdapter allAthletesAdapter = new CustomAdapter(this, allAthleteList);
			listview.setAdapter(allAthletesAdapter);

			break;
		case 2:
			// Log Button Selected
			Log.i("NBA PLAYERS", "Houston Rockets Selected");

			// Create a List for placing data
			teamList = dataSource.teamIsHoustonRockets();
			// Set Custom Adapter
			CustomAdapter teamAdapter = new CustomAdapter(this, teamList);
			listview.setAdapter(teamAdapter);

			break;
		default:
			break;
		}
	}
}
