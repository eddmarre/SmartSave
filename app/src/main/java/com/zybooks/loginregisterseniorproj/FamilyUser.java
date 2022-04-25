package com.zybooks.loginregisterseniorproj;

public class FamilyUser
{
    String UserId;
    String FirstName;
    String LastName;
    String DOB;
    String RelationToOwner;

    public FamilyUser(String userId, String firstName, String lastName, String dob, String relationToOwner)
    {
        UserId=userId;
        FirstName=firstName;
        LastName=lastName;
        DOB=dob;
        RelationToOwner=relationToOwner;
    }

    public String getDOB() {
        return DOB;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getUserId() {
        return UserId;
    }

    public String getRelationToOwner() {
        return RelationToOwner;
    }
}