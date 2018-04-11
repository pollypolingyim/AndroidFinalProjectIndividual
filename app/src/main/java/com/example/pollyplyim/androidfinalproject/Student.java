package com.example.pollyplyim.androidfinalproject;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

    private int id;
    private String name;
    private double gpa;

    public Student() {
        super();
    }

    private Student(Parcel in) {
        super();
        this.id = in.readInt();
        this.name = in.readString();
        this.gpa = in.readDouble();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }


    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeString(getName());
        parcel.writeDouble(getGpa());
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}