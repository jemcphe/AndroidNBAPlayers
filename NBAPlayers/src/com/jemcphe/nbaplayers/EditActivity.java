package com.jemcphe.nbaplayers;

import com.jemcphe.nbaplayers.SQLPack.SQLDataSource;
import com.parse.GetCallback;
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
import android.widget.EditText;

public class EditActivity extends Activity implements OnClickListener{

	// Declare EditText Fields
	EditText editDisplayName;
	EditText editTeam;
	EditText editJerseyNumber;
	EditText editHeight;
	EditText editWeight;

	// Declare Button
	Button saveButton;

	// Declare Strings
	String objectIdString;
	String displayNameString;
	String teamString;
	String jerseyNumberString;
	String heightString;
	String weightString;

	SQLDataSource dataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_view);

		// Locate EditText fields
		editDisplayName = (EditText) this.findViewById(R.id.edit_displayName);
		editTeam = (EditText) this.findViewById(R.id.edit_team);
		editJerseyNumber = (EditText) this.findViewById(R.id.edit_jerseyNumber);
		editHeight = (EditText) this.findViewById(R.id.edit_height);
		editWeight = (EditText) this.findViewById(R.id.edit_weight);

		// Locate Button & setOnClickListener
		saveButton = (Button) this.findViewById(R.id.edit_save_button);
		saveButton.setOnClickListener(this);

		//Get intent from ViewAthleteActivity, grab values, and populate EditText Fields
		Intent intent = getIntent();
		objectIdString = intent.getStringExtra("objectId");
		displayNameString = intent.getStringExtra("displayName");
		teamString = intent.getStringExtra("team");
		jerseyNumberString = intent.getStringExtra("jerseyNumber");
		heightString = intent.getStringExtra("height");
		weightString = intent.getStringExtra("weight");

		editDisplayName.setText(displayNameString);
		editTeam.setText(teamString);
		editJerseyNumber.setText(jerseyNumberString);
		editHeight.setText(heightString);
		editWeight.setText(weightString);

		// Open Database
		dataSource = new SQLDataSource(this);
		dataSource.open();
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// Launch onSave Method
		onSave();
		// Resign EditActivity when finished
		finish();
	}

	public void onSave(){
		// Create query that uses Athlete Table from Remote Database
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Athlete");

		// Retrieve the object by id
		query.getInBackground(objectIdString, new GetCallback<ParseObject>() {
			public void done(ParseObject athlete, ParseException e) {
				if (e == null) {
					/*
					 * Place values from EditText field into their respective columns.
					 * saveEventually() called in case user edits offline.
					 */
					athlete.put("displayName", editDisplayName.getText().toString());
					athlete.put("team", editTeam.getText().toString());
					athlete.put("jerseyNumber", editJerseyNumber.getText().toString());
					athlete.put("height", editHeight.getText().toString());
					athlete.put("weight", editWeight.getText().toString());
					athlete.saveEventually();
					// update entry in SQL Database
					dataSource.updateEntry(editDisplayName.getText().toString(),
							editTeam.getText().toString(),
							editJerseyNumber.getText().toString(),
							editHeight.getText().toString(),
							editWeight.getText().toString());
				} else {
					// Log Any Errors
					Log.e("EDIT ERROR", e.getMessage());
				}
			}
		});
	}

}
