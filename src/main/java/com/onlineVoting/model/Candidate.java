package com.onlineVoting.model;

import java.sql.Date;

public class Candidate {
    private int candidateId;
    private String fullname;
    private int age;
    private String education;
    private String address;
    private Date dob;
    private String info;
    private byte[] image;  // for longblob
    private int electionId;

    // Constructors
    public Candidate() {}

    public Candidate(int candidateId, String fullname, int age, String education,
                     String address, Date dob, String info, byte[] image, int electionId) {
        this.candidateId = candidateId;
        this.fullname = fullname;
        this.age = age;
        this.education = education;
        this.address = address;
        this.dob = dob;
        this.info = info;
        this.image = image;
        this.electionId = electionId;
    }

    // Getters & Setters
    public int getCandidateId() {
        return candidateId;
    }
    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }

    public byte[] getPhoto() {
        return image;
    }
    public void setPhoto(byte[] image) {
        this.image = image;
    }

    public int getElectionId() {
        return electionId;
    }
    public void setElectionId(int electionId) {
        this.electionId = electionId;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "candidateId=" + candidateId +
                ", fullname='" + fullname + '\'' +
                ", age=" + age +
                ", education='" + education + '\'' +
                ", address='" + address + '\'' +
                ", dob=" + dob +
                ", info='" + info + '\'' +
                ", electionId=" + electionId +
                '}';
        // Not printing image (byte[]) in toString to avoid madness 🤯
    }
}
