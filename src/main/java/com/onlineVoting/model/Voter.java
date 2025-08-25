package com.onlineVoting.model;

import java.sql.Timestamp;

public class Voter {
    private int id;               // primary key in election_voters
    private int voterId;          // reference to users table
    private int electionId;       // reference to elections table
    private Timestamp registeredAt; // default current timestamp
    private String username;      // added: voter’s name for display

    // Constructors
    public Voter() {}

    public Voter(int id, int voterId, int electionId, Timestamp registeredAt, String username) {
        this.id = id;
        this.voterId = voterId;
        this.electionId = electionId;
        this.registeredAt = registeredAt;
        this.username = username;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getVoterId() {
        return voterId;
    }
    public void setVoterId(int voterId) {
        this.voterId = voterId;
    }

    public int getElectionId() {
        return electionId;
    }
    public void setElectionId(int electionId) {
        this.electionId = electionId;
    }

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }
    public void setRegisteredAt(Timestamp registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getUsername() {      // new
        return username;
    }
    public void setUsername(String username) {   // new
        this.username = username;
    }

    @Override
    public String toString() {
        return "Voter{" +
                "id=" + id +
                ", voterId=" + voterId +
                ", electionId=" + electionId +
                ", registeredAt=" + registeredAt +
                ", username='" + username + '\'' +
                '}';
    }
}
