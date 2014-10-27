package com.android.ijmc.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.ijmc.config.Config;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	protected static final String DATABASE_NAME = "iJMC_db";

    final static String contentsTbl = Config.CONTENT_TABLE;
    final static String departmentsTbl = Config.DEPARTMENT_TABLE;
    final static String studentsTbl = Config.STUDENT_TABLE;
    final static String facultyTbl = Config.FACULTY_TABLE;
    final static String sealTbl = Config.SEAL_TABLE;
    final static String courseTbl = Config.COURSE_TABLE;
    final static String positionTbl = Config.POSITION_TABLE;
    final static String ssgTbl = Config.SSG_TABLE;
    final static String musicTbl = Config.MUSIC_TABLE;

    public DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ contentsTbl +" " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "content_id INTEGER, " +
                "content_type TEXT, " +
                "content_body TEXT ) ");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ departmentsTbl +" " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dept_id INTEGER, " +
                "dept_title TEXT, " +
                "dept_desc TEXT ) ");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ studentsTbl +" " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "stud_idnum TEXT, " +
                "stud_fname TEXT, " +
                "stud_mname TEXT, " +
                "stud_lname TEXT, " +
                "dept_id INTEGER ) ");
        
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ facultyTbl +" " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fc_idn TEXT, " +
                "fc_name TEXT, " +
                "fc_mname TEXT, " +
                "fc_lname TEXT, " +
                "fc_suffix TEXT, " +
                "fc_gender TEXT, " +
                "image_path TEXT, " +
                "dept_id TEXT, " +
                "pos TEXT ) ");
        
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ sealTbl +" " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "img_path TEXT, " +
                "js_name TEXT, " +               
                "js_desc TEXT ) ");
        
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ courseTbl +" " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "crs_id INTEGER, " +
                "crs_title TEXT, " +
                "crs_desc TEXT, " +               
                "dept_id INTEGER ) ");
        
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ positionTbl +" " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pos_id INTEGER, " +             
                "pos_title TEXT ) ");
        
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ ssgTbl +" " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "firstname TEXT, " +
                "middlename TEXT, " +
                "lastname TEXT, " +
                "img_path TEXT, " +
                "level TEXT, " +  
                "crs_id INTEGER, " +             
                "pos_id INTEGER ) ");
        
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ musicTbl +" " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "music_name TEXT, " +             
                "music_path TEXT ) ");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + contentsTbl);
        db.execSQL("DROP TABLE IF EXISTS " + departmentsTbl);
        db.execSQL("DROP TABLE IF EXISTS " + studentsTbl);
        db.execSQL("DROP TABLE IF EXISTS " + sealTbl);
        db.execSQL("DROP TABLE IF EXISTS " + courseTbl);
        db.execSQL("DROP TABLE IF EXISTS " + positionTbl);
        db.execSQL("DROP TABLE IF EXISTS " + ssgTbl);
        db.execSQL("DROP TABLE IF EXISTS " + musicTbl);

		onCreate(db);
	}

}
