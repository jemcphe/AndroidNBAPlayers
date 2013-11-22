package com.jemcphe.nbaplayers;

public class Athlete {

	// Create some useful string constants
	public static final String COLUMN_DISPLAYNAME = "displayName";
	public static final String COLUMN_TEAM = "team";
	public static final String COLUMN_JERSEYNUMBER = "jerseyNumber";
	public static final String COLUMN_HEIGHT = "height";
	public static final String COLUMN_WEIGHT = "weight";
	
	// Create Strings
	private String id;
	private String displayName;
	private String team;
	private String jerseyNumber;
	private String height;
	private String weight;
	
	/*
	 * Getters & Setters for the Associate Objects.  This Class
	 * will make it easier to assign/retrieve values within
	 * application.
	 */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getJerseyNumber() {
		return jerseyNumber;
	}
	public void setJerseyNumber(String jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		
		return displayName;
//		NumberFormat nf = NumberFormat.getCurrencyInstance();
//		return nf.format(total);
	}
	
}
