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
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + contentsTbl);
        db.execSQL("DROP TABLE IF EXISTS " + departmentsTbl);
        db.execSQL("DROP TABLE IF EXISTS " + studentsTbl);

		onCreate(db);
	}

}
