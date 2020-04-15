package com.datingapp.springjwt.models;

public enum Gender {
    Male("male"), Female("Female");

    String gender;

    private Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return this.gender;
    }

}