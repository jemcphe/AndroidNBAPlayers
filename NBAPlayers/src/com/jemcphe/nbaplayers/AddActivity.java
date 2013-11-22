package com.jemcphe.nbaplayers;

import com.jemcphe.nbaplayers.SQLPack.SQLDataSource;
import com.parse.ParseObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends Activity {
	// EditText Fields
	EditText editFirstName;
	EditText editLastName;
	EditText editTeam;
	EditText editJerseyNumber;
	EditText editHeight;
	EditText editWeight;
	// Button
	Button addButton;
	
	SQLDataSource dataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_view);
		
		// Declare EditText Fields
		editFirstName = (EditText) this.findViewById(R.id.add_firstName);
		editLastName = (EditText) this.findViewById(R.id.add_lastName);
		editTeam = (EditText) this.findViewById(R.id.add_team);
		editJerseyNumber = (EditText) this.findViewById(R.id.add_jerseyNumber);
		editHeight = (EditText) this.findViewById(R.id.add_height);
		editWeight = (EditText) this.findViewById(R.id.add_weight);
		// Declare addButton
		addButton = (Button) this.findViewById(R.id.add_save_button);
		
		// setOnClickListener for addButton
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Launch onSave Method
				onSave();
				// Resign this Activity when finished
				finish();
			}
		});
		
	}
	
	/*
	 * onSave is launched whenever Add Button is selected.  
	 * The method creates a new Athlete, grabs values from EditText fields, 
	 * and places them in their appropriate columns within the database.
	 * Also, saveEventually is used in case user decides to add athletes
	 * offline.
	 */
	public void onSave(){
		String displayName = editFirstName.getText().toString() + " " + editLastName.getText().toString();
		String team = editTeam.getText().toString();
		String jerseyNumber = editJerseyNumber.getText().toString();
		String height = editHeight.getText().toString();
		String weight = editWeight.getText().toString();
		
		ParseObject athlete = new ParseObject("Athlete");
		athlete.put("displayName", displayName);
		athlete.put("team", team);
		athlete.put("jerseyNumber", jerseyNumber);
		athlete.put("height", height);
		athlete.put("weight", weight);
		athlete.saveEventually();
		
		// Insert new Athlete into SQL
		dataSource = new SQLDataSource(this);
		dataSource.open();
		dataSource.insertEntry(displayName, team, jerseyNumber, height, weight);
	}

}
