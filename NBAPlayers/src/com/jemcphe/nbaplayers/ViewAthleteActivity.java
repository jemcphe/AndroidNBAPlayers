package com.jemcphe.nbaplayers;

import java.util.List;

import com.jemcphe.nbaplayers.SQLPack.SQLDataSource;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ViewAthleteActivity extends Activity implements OnClickListener {

	// Declare TextViews
	TextView name;
	TextView team;
	TextView jerseyNumber;
	TextView height;
	TextView weight;

	// Declare Strings
	String objectIdString;
	String nameString;
	String teamString;
	String jerseyString;
	String heightString;
	String weightString;

	// Declare Buttons
	Button editButton;
	Button deleteButton;
	
	SQLDataSource dataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_athlete);

		dataSource = new SQLDataSource(this);
		dataSource.open();
		
		// Locate TextViews
		name = (TextView) this.findViewById(R.id.view_name);
		team = (TextView) this.findViewById(R.id.view_team);
		jerseyNumber = (TextView) this.findViewById(R.id.view_jerseyNumber);
		height = (TextView) this.findViewById(R.id.view_height);
		weight = (TextView) this.findViewById(R.id.view_weight);

		// retrieve data from MainActivity Item Selection
		Intent intent = getIntent();
		// Set Strings with data pulled
		objectIdString = intent.getStringExtra("objectId");
		nameString = intent.getStringExtra("displayName");
		teamString = intent.getStringExtra("team");
		jerseyString = intent.getStringExtra("jerseyNumber");
		heightString = intent.getStringExtra("height");
		weightString = intent.getStringExtra("weight");
		
		// Set TextViews with pulled data
		name.setText(nameString);
		team.setText(teamString);
		jerseyNumber.setText(jerseyString);
		height.setText(heightString);
		weight.setText(weightString);

		// Locate editButton, setId, & onClickListener
		editButton = (Button) this.findViewById(R.id.view_edit_button);
		editButton.setId(0);
		editButton.setOnClickListener(this);

		// Locate deleteButton, setId, & onClickListener
		deleteButton = (Button) this.findViewById(R.id.view_delete_button);
		deleteButton.setId(1);
		deleteButton.setOnClickListener(this);
	}

	/*
	 * onDelete() method is for deleting individual objects. First we Query through
	 * the Athlete Database, looking for our objectId's to match.  If they do, the object
	 * gets deleted from the database.  If not, we get an error stating why.
	 */
	public void onDelete(){
		
		// Delete from SQL
		dataSource.delete("displayName = '" + nameString + "'");
		
		// try/catch to query through Athlete Database
		try {
			// query via Athlete Table in database
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Athlete");
			// filter objects where objectId == objectIdString
			query.whereEqualTo("objectId", objectIdString);
			// Create list of query results
			List<ParseObject> athlete = query.find();
			// Loop through query results
			for (ParseObject object : athlete) {
				// Create Log to see what Athlete has been filtered
				Log.i("Remote Data", object.getString("displayName").toString());
				// Delete the object, if offline, then eventually (when data is present, again)
				object.deleteEventually();
			}
		} catch (ParseException e) {
			// Log Errors
			e.printStackTrace();
		}

	}
	
	/*
	 * onEdit() method allows user to edit an Athlete object.  Method is designed
	 * to create an intent that we put key/value data into and pass to our
	 * receiving activity. In this case, EditActivity.class.
	 */
	public void onEdit(){
		//Create an intent that captures data and sends to EditActivity
		Intent intent = new Intent(ViewAthleteActivity.this, EditActivity.class);
		// Placing values into intent using key/value pairs
		intent.putExtra("objectId", objectIdString);
		intent.putExtra("name", nameString);
		intent.putExtra("team", team.getText().toString());
		intent.putExtra("jerseyNumber", jerseyNumber.getText().toString());
		intent.putExtra("height", height.getText().toString());
		intent.putExtra("weight", weight.getText().toString());
		// Start the intent to launch EditActivity & pass data
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// Switch using button id's created earlier
		switch (v.getId()) {
		case 0: // Edit Button
			// Launch onEdit
			onEdit();
			// Resign current activity when finished
			finish();
			break;
		case 1: // Delete Button
			// Launch onDelete
			onDelete();
			// Resign current activity when finished
			finish();
			break;
		default:
			break;
		}
	}

}
