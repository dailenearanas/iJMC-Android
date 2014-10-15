package com.android.ijmc.helpers;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.ijmc.models.ContentModel;
import com.android.ijmc.models.DepartmentModel;
import com.android.ijmc.models.FacultyModel;
import com.android.ijmc.models.StudentModel;

/**
 * Created by user on 9/7/2014.
 */
public class Queries {

    public static String getStudentId(SQLiteDatabase sqLiteDB, DatabaseHandler dbHandler)
    {
        String studentId = "";

        sqLiteDB = dbHandler.getReadableDatabase();
        Cursor mCursor = sqLiteDB.rawQuery("SELECT * FROM " + DatabaseHandler.studentsTbl.toString(), null);

        mCursor.moveToFirst();
        if(!mCursor.isAfterLast())
        {
            do {
                studentId = mCursor.getString(1);
            } while (mCursor.moveToNext());
        }

        return studentId;
    }

    public static StudentModel getStudent(SQLiteDatabase sqLiteDB, DatabaseHandler dbHandler)
    {
        StudentModel studentModel = null;

        sqLiteDB = dbHandler.getReadableDatabase();
        Cursor mCursor = sqLiteDB.rawQuery("SELECT * FROM " + DatabaseHandler.studentsTbl.toString(), null);

        mCursor.moveToFirst();
        if (!mCursor.isAfterLast())
        {
            do
            {
                studentModel = new StudentModel();

                studentModel.studId = mCursor.getString(1);
                studentModel.studentFname = mCursor.getString(2);
                studentModel.studentMname = mCursor.getString(3);
                studentModel.studentLname = mCursor.getString(4);
                studentModel.deptId = mCursor.getInt(5);

            } while (mCursor.moveToNext());
        }

        mCursor.close();
        dbHandler.close();

        return studentModel;
    }

    public static ArrayList<ContentModel> getJMCProf(SQLiteDatabase sqLiteDb, DatabaseHandler dbHandler)
    {
        ArrayList<ContentModel> models = new ArrayList<ContentModel>();
        ContentModel contentModel;

        sqLiteDb = dbHandler.getReadableDatabase();
        Cursor mCursor = sqLiteDb.rawQuery("SELECT * FROM `contents` WHERE `content_type` = 'JMC Profile'", null);

        mCursor.moveToFirst();
        if (!mCursor.isAfterLast())
        {
            do
            {
                contentModel = new ContentModel();

                contentModel.contentId = mCursor.getString(1);
                contentModel.contentType = mCursor.getString(2);
                contentModel.contentBody = mCursor.getString(3);

                models.add(contentModel);

            } while (mCursor.moveToNext());
        }

        mCursor.close();
        dbHandler.close();

        return models;
    }

    public static ArrayList<ContentModel> getContents(SQLiteDatabase sqliteDB, DatabaseHandler dbHandler)
    {

        ArrayList<ContentModel> models = new ArrayList<ContentModel>();
        ContentModel contentModel;

        sqliteDB = dbHandler.getReadableDatabase();
        Cursor mCursor = sqliteDB.rawQuery("SELECT * FROM " + DatabaseHandler.contentsTbl.toString(), null);

        mCursor.moveToFirst();
        if (!mCursor.isAfterLast())
        {
            do
            {
                contentModel = new ContentModel();

                contentModel.contentId = mCursor.getString(1);
                contentModel.contentType = mCursor.getString(2);
                contentModel.contentBody = mCursor.getString(3);

                models.add(contentModel);

            } while (mCursor.moveToNext());
        }

        mCursor.close();
        dbHandler.close();

        return models;

    }

    public static ArrayList<ContentModel> getVMG(SQLiteDatabase sqliteDB, DatabaseHandler dbHandler)
    {

        ArrayList<ContentModel> models = new ArrayList<ContentModel>();
        ContentModel contentModel;

        sqliteDB = dbHandler.getReadableDatabase();
        Cursor mCursor = sqliteDB.rawQuery("SELECT * from `contents` WHERE `content_type` IN ('Vision', 'Mission', 'Goal') ", null);

        mCursor.moveToFirst();
        if (!mCursor.isAfterLast())
        {
            do
            {
                contentModel = new ContentModel();

                contentModel.contentId = mCursor.getString(1);
                contentModel.contentType = mCursor.getString(2);
                contentModel.contentBody = mCursor.getString(3);

                models.add(contentModel);

            } while (mCursor.moveToNext());
        }

        mCursor.close();
        dbHandler.close();

        return models;
    }

    public static ArrayList<ContentModel> getJmcHymn(SQLiteDatabase sqliteDB, DatabaseHandler dbHandler)
    {

        ArrayList<ContentModel> models = new ArrayList<ContentModel>();
        ContentModel contentModel;

        sqliteDB = dbHandler.getReadableDatabase();
        Cursor mCursor = sqliteDB.rawQuery("SELECT * FROM `contents` WHERE `content_type` = 'JMC Hymn'", null);

        mCursor.moveToFirst();
        if (!mCursor.isAfterLast())
        {
            do
            {
                contentModel = new ContentModel();

                contentModel.contentId = mCursor.getString(1);
                contentModel.contentType = mCursor.getString(2);
                contentModel.contentBody = mCursor.getString(3);

                models.add(contentModel);

            } while (mCursor.moveToNext());
        }

        mCursor.close();
        dbHandler.close();

        return models;

    }

    public static ArrayList<FacultyModel> getFaculty(SQLiteDatabase sqLiteDb, DatabaseHandler dbHandler)
    {
        ArrayList<FacultyModel> models = new ArrayList<FacultyModel>();
        FacultyModel facultyModel;

        sqLiteDb = dbHandler.getReadableDatabase();
        Cursor mCursor = sqLiteDb.rawQuery("SELECT * FROM `faculties`", null);

        mCursor.moveToFirst();
        if (!mCursor.isAfterLast())
        {
            do
            {
                facultyModel = new FacultyModel();

                facultyModel.facultyId = mCursor.getString(1);
                facultyModel.facultyFname = mCursor.getString(2);
                facultyModel.facultyMname = mCursor.getString(3);
                facultyModel.facultyLname = mCursor.getString(4);
                facultyModel.facultySfx = mCursor.getString(5);
                facultyModel.facultyGender = mCursor.getString(6);
                facultyModel.facultyImagePath = mCursor.getString(7);
                facultyModel.facultyDeptId = mCursor.getString(8);
                facultyModel.facultyPositionId = mCursor.getString(10);

                models.add(facultyModel);

            } while (mCursor.moveToNext());
        }

        mCursor.close();
        dbHandler.close();

        return models;
    }

    public static ArrayList<DepartmentModel> getDepartment(SQLiteDatabase sqliteDB, DatabaseHandler dbHandler)
    {

        ArrayList<DepartmentModel> models = new ArrayList<DepartmentModel>();
        DepartmentModel departmentModel;

        sqliteDB = dbHandler.getReadableDatabase();
        Cursor mCursor = sqliteDB.rawQuery("SELECT * FROM " + DatabaseHandler.departmentsTbl.toString(), null);

        mCursor.moveToFirst();
        if (!mCursor.isAfterLast())
        {
            do
            {
                departmentModel = new DepartmentModel();

                departmentModel.deptId = mCursor.getInt(0);
                departmentModel.deptTitle = mCursor.getString(1);
                departmentModel.deptDesc = mCursor.getString(2);

                models.add(departmentModel);

            } while (mCursor.moveToNext());
        }

        mCursor.close();
        dbHandler.close();

        return models;
    }

    public static void InsertStudents(SQLiteDatabase sqLiteDB, DatabaseHandler dbHandler, StudentModel studentModel) {
        Log.e("INSERTING STUDENTS", "##COMMENT##");
        sqLiteDB = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("stud_idnum", studentModel.getStudentId());
        values.put("stud_fname", studentModel.getStudentFname());
        values.put("stud_mname", studentModel.getStudentMname());
        values.put("stud_lname", studentModel.getStudentLname());
        values.put("dept_id", studentModel.getDeptId());

        sqLiteDB.insert(DatabaseHandler.studentsTbl, null, values);
        sqLiteDB.close();
    }

    public static void InsertContent(SQLiteDatabase sqLiteDB, DatabaseHandler dbHandler, ContentModel content) {
        Log.e("INSERTING CONTENT", "##COMMENT##");

        sqLiteDB = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("content_id", content.getContentId());
        values.put("content_type", content.getContentType());
        values.put("content_body", content.getContentBody());

        sqLiteDB.insert(DatabaseHandler.contentsTbl, null, values);
        sqLiteDB.close();
    }

    public static void InsertFaculty(SQLiteDatabase sqLiteDB, DatabaseHandler dbHandler, FacultyModel faculty) {
        Log.e("INSERTING FACULTY", "##COMMENT##");

        sqLiteDB = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("fc_idn", faculty.getFacultyId());
        values.put("fc_name", faculty.getFacultyFname());
        values.put("fc_mname", faculty.getFacultyMname());
        values.put("fc_lname", faculty.getFacultyLname());
        values.put("fc_suffix", faculty.getFacultySfx());
        values.put("fc_gender", faculty.getFacultyGender());
        values.put("image_path", faculty.getFacultyImagePath());
        values.put("dept_id", faculty.getFacultyDeptId());
        values.put("pos", faculty.getFacultyPositionId());

        sqLiteDB.insert(DatabaseHandler.facultyTbl, null, values);
        sqLiteDB.close();
    }

    public static void InsertDepartment(SQLiteDatabase sqLiteDB, DatabaseHandler dbHandler, DepartmentModel department) {
        Log.e("INSERTING DEPARTMENT", "##COMMENT##");
        sqLiteDB = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("dept_id", department.getDeptId());
        Log.e("DEPT", values.getAsString("dept_id"));
        values.put("dept_title", department.getDeptTitle());
        values.put("dept_desc", department.getDeptDesc());

        sqLiteDB.insert(DatabaseHandler.departmentsTbl, null, values);
        sqLiteDB.close();
    }

    public static void TruncateTables(SQLiteDatabase db, DatabaseHandler dbHandler) {
        Log.e("TRUNCATE TABLES", "");

        db = dbHandler.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.contentsTbl);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.departmentsTbl);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.studentsTbl);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.facultyTbl);

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ DatabaseHandler.contentsTbl +" " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "content_id INTEGER, " +
                "content_type TEXT, " +
                "content_body TEXT ) ");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ DatabaseHandler.departmentsTbl +" " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dept_id INTEGER, " +
                "dept_title TEXT, " +
                "dept_desc TEXT ) ");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ DatabaseHandler.studentsTbl +" " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "stud_idnum TEXT, " +
                "stud_fname TEXT, " +
                "stud_mname TEXT, " +
                "stud_lname TEXT, " +
                "dept_id INTEGER ) ");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ DatabaseHandler.facultyTbl +" " +
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

    }
}
