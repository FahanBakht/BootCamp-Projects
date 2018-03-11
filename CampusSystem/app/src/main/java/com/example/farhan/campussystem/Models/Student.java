package com.example.farhan.campussystem.Models;

/**
 * Created by Farhan on 3/7/2018.
 */

public class Student {
    private String studentName;
    private String studentEmail;
    private String studentQualification;
    private String studentAge;
    private String studentCity;
    private String studentUid;

    public Student() {
    }

    public Student(String studentName, String studentEmail, String studentQualification, String studentAge, String studentCity, String studentUid) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentQualification = studentQualification;
        this.studentAge = studentAge;
        this.studentCity = studentCity;
        this.studentUid = studentUid;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentQualification() {
        return studentQualification;
    }

    public void setStudentQualification(String studentQualification) {
        this.studentQualification = studentQualification;
    }

    public String getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(String studentAge) {
        this.studentAge = studentAge;
    }

    public String getStudentCity() {
        return studentCity;
    }

    public void setStudentCity(String studentCity) {
        this.studentCity = studentCity;
    }

    public String getStudentUid() {
        return studentUid;
    }

    public void setStudentUid(String studentUid) {
        this.studentUid = studentUid;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentName='" + studentName + '\'' +
                ", studentEmail='" + studentEmail + '\'' +
                ", studentQualification='" + studentQualification + '\'' +
                ", studentAge='" + studentAge + '\'' +
                ", studentCity='" + studentCity + '\'' +
                ", studentUid='" + studentUid + '\'' +
                '}';
    }
}
