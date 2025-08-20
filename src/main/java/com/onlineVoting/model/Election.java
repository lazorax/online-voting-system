package com.onlineVoting.model;

import java.sql.Date;
import java.sql.Time;

public class Election {
	 	private int electionId;
	 	private int conductorId;
	 	private String title;
	    private String refCode;
	    private String description;
	    private String type;
	    private String status;
	    private Date electionDate;
	    private Time startTime;
	    private Time endTime;

	    // Getters & Setters
	    public int getElectionId() { return electionId; }
	    public void setElectionId(int electionId) { this.electionId = electionId; }

	    public int getConductorId() { return conductorId; }
	    public void setConductorId(int conductorId) { this.conductorId = conductorId; }

	    public String getTitle() { return title; }
	    public void setTitle(String title) { this.title = title; }

	    public String getRefCode() { return refCode; }
	    public void setRefCode(String refCode) { this.refCode = refCode; }

	    public String getDescription() { return description; }
	    public void setDescription(String description) { this.description = description; }

	    public String getType() { return type; }
	    public void setType(String type) { this.type = type; }

	    public String getStatus() { return status; }
	    public void setStatus(String status) { this.status = status; }

	    public Date getElectionDate() { return electionDate; }
	    public void setElectionDate(Date electionDate) { this.electionDate = electionDate; }

	    public Time getStartTime() { return startTime; }
	    public void setStartTime(Time startTime) { this.startTime = startTime; }

	    public Time getEndTime() { return endTime; }
	    public void setEndTime(Time endTime) { this.endTime = endTime; }
}
