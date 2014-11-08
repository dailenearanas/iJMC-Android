package com.android.ijmc.models;

/**
 * Created by user on 9/9/2014.
 */
public class DepartmentModel {
    public int deptId;
    public String deptTitle;
    public String deptDesc;
    public String img_path;

    public int getDeptId(){
        return deptId;
    }

    public String getDeptTitle(){
        return deptTitle;
    }

    public String getDeptDesc(){
        return deptDesc;
    }
    
    public String getImagePath() {
    	return img_path;
    }
}
