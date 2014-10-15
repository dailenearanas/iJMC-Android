package com.android.ijmc.models;

/**
 * Created by user on 9/12/2014.
 */
public class StudentModel {
    public String studId;
    public String studentFname;
    public String studentMname;
    public String studentLname;
    public Integer deptId;

    public String getStudentId(){
        return studId;
    }

    public String getStudentFname(){
        return studentFname;
    }

    public String getStudentMname(){
        return studentMname;
    }

    public String getStudentLname(){
        return studentLname;
    }

    public Integer getDeptId(){
        return deptId;
    }
}
