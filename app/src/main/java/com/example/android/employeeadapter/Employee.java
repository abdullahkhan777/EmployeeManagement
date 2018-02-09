package com.example.android.employeeadapter;
/**
 * Created by Musharif.ahmed on 8/1/2017.
 */
public class Employee {
    private long Id;
    private String fName;
    private String lName;

    public Employee(){}

    public Employee(String firstName, String lastName){fName = firstName; lName = lastName;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    @Override
    public String toString() {return Id + " "+fName + " " + lName;
    }
}
