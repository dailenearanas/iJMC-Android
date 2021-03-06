package com.android.ijmc.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.ijmc.models.ContentModel;
import com.android.ijmc.models.CourseModel;
import com.android.ijmc.models.DepartmentModel;
import com.android.ijmc.models.FacultyModel;
import com.android.ijmc.models.MusicModel;
import com.android.ijmc.models.OtherModel;
import com.android.ijmc.models.PositionModel;
import com.android.ijmc.models.SSGModel;
import com.android.ijmc.models.SealsModel;
import com.android.ijmc.models.StudentModel;

/**
 * Created by user on 9/7/2014.
 */
public class Queries {

	public static String getStudentId(SQLiteDatabase sqLiteDB,
			DatabaseHandler dbHandler) {
		String studentId = "";

		sqLiteDB = dbHandler.getReadableDatabase();
		Cursor mCursor = sqLiteDB.rawQuery("SELECT * FROM "
				+ DatabaseHandler.studentsTbl.toString(), null);

		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
				studentId = mCursor.getString(1);
			} while (mCursor.moveToNext());
		}

		mCursor.close();
		dbHandler.close();

		return studentId;
	}

	public static StudentModel getStudent(SQLiteDatabase sqLiteDB,
			DatabaseHandler dbHandler) {
		StudentModel studentModel = null;

		sqLiteDB = dbHandler.getReadableDatabase();
		Cursor mCursor = sqLiteDB.rawQuery("SELECT * FROM "
				+ DatabaseHandler.studentsTbl.toString(), null);

		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
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

	public static ArrayList<ContentModel> getJMCProf(SQLiteDatabase sqLiteDb,
			DatabaseHandler dbHandler) {
		ArrayList<ContentModel> models = new ArrayList<ContentModel>();
		ContentModel contentModel;

		sqLiteDb = dbHandler.getReadableDatabase();
		Cursor mCursor = sqLiteDb
				.rawQuery(
						"SELECT * FROM `contents` WHERE `content_type` = 'JMC Profile'",
						null);

		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
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

	public static ArrayList<ContentModel> getContents(SQLiteDatabase sqliteDB,
			DatabaseHandler dbHandler) {

		ArrayList<ContentModel> models = new ArrayList<ContentModel>();
		ContentModel contentModel;

		sqliteDB = dbHandler.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery("SELECT * FROM "
				+ DatabaseHandler.contentsTbl.toString(), null);

		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
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

	public static ArrayList<ContentModel> getVMG(SQLiteDatabase sqliteDB,
			DatabaseHandler dbHandler) {

		ArrayList<ContentModel> models = new ArrayList<ContentModel>();
		ContentModel contentModel;

		sqliteDB = dbHandler.getReadableDatabase();
		Cursor mCursor = sqliteDB
				.rawQuery(
						"SELECT * from `contents` WHERE `content_type` IN ('Vision', 'Mission', 'Goal') ",
						null);

		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
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

	public static ArrayList<ContentModel> getJmcHymn(SQLiteDatabase sqliteDB,
			DatabaseHandler dbHandler) {

		ArrayList<ContentModel> models = new ArrayList<ContentModel>();
		ContentModel contentModel;

		sqliteDB = dbHandler.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery(
				"SELECT * FROM `contents` WHERE `content_type` = 'JMC Hymn'",
				null);

		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
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

	public static ArrayList<FacultyModel> getFaculty(SQLiteDatabase sqLiteDb,
			DatabaseHandler dbHandler) {
		ArrayList<FacultyModel> models = new ArrayList<FacultyModel>();
		FacultyModel facultyModel;

		sqLiteDb = dbHandler.getReadableDatabase();
		Cursor mCursor = sqLiteDb.rawQuery("SELECT * FROM "
				+ DatabaseHandler.facultyTbl.toString(), null);

		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
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

	/**
	 * Queries items from the database that contains the information of every
	 * faculty registered on the database. this requires instances of
	 * SQLiteDatabase, DatabaseHandler, tag (faculty department).
	 * 
	 * @param sqliteDB
	 * @param dbHandler
	 * @param tag
	 * @return
	 */
	public static ArrayList<FacultyModel> getFacultyListItem(
			SQLiteDatabase sqliteDB, DatabaseHandler dbHandler, String tag) {

		ArrayList<FacultyModel> models = new ArrayList<FacultyModel>();
		FacultyModel facultyModel;

		sqliteDB = dbHandler.getReadableDatabase();
		// Cursor mCursor = sqliteDB.rawQuery("SELECT * FROM " +
		// DatabaseHandler.facultyTbl.toString(), null);
		String rawQuery = "select f.*, d.dept_title, p.pos_title FROM "
				+ DatabaseHandler.facultyTbl.toString() + " as f inner join "
				+ DatabaseHandler.departmentsTbl.toString()
				+ " as d on f.dept_id = d.id inner join "
				+ DatabaseHandler.positionTbl.toString()
				+ " as p on f.pos = p.id";
		Cursor mCursor = sqliteDB.rawQuery(rawQuery, null);
		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
				facultyModel = new FacultyModel();

				facultyModel.facultyId = mCursor.getString(1);
				facultyModel.facultyFname = mCursor.getString(2);
				facultyModel.facultyMname = mCursor.getString(3);
				facultyModel.facultyLname = mCursor.getString(4);
				facultyModel.facultySfx = mCursor.getString(5);
				facultyModel.facultyGender = mCursor.getString(6);
				facultyModel.facultyImagePath = mCursor.getString(7);
				facultyModel.facultyDeptId = mCursor.getString(10) + "-"
						+ mCursor.getString(8);
				facultyModel.facultyPositionId = mCursor.getString(11);

				if (!mCursor.getString(10).toLowerCase(Locale.getDefault())
						.equals("high school department")
						&& !mCursor.getString(10)
								.toLowerCase(Locale.getDefault())
								.equals("grade school department")
						&& !mCursor.getString(10)
								.toLowerCase(Locale.getDefault())
								.equals("administration")
						&& tag.toLowerCase(Locale.getDefault()).equals(
								"college")) {
					models.add(facultyModel);
				} else if (mCursor.getString(10)
						.toLowerCase(Locale.getDefault())
						.equals("high school department")
						&& tag.toLowerCase(Locale.getDefault()).equals(
								"high school")) {
					models.add(facultyModel);
				} else if (mCursor.getString(10)
						.toLowerCase(Locale.getDefault())
						.equals("grade school department")
						&& tag.toLowerCase(Locale.getDefault()).equals(
								"grade school")) {
					models.add(facultyModel);
				} else if (mCursor.getString(10)
						.toLowerCase(Locale.getDefault())
						.equals("administration")
						&& tag.toLowerCase(Locale.getDefault()).equals(
								"administration")) {
					models.add(facultyModel);
				}
			} while (mCursor.moveToNext());
		}

		mCursor.close();
		dbHandler.close();

		return models;
	}

	public static List<String> getFacultyImages(SQLiteDatabase sqliteDB,
			DatabaseHandler dbHandler) {
		List<String> fimages = new ArrayList<String>();
		sqliteDB = dbHandler.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery("select image_path from "
				+ DatabaseHandler.facultyTbl.toString(), null);
		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
				fimages.add(mCursor.getString(0));

			} while (mCursor.moveToNext());
		}

		mCursor.close();
		dbHandler.close();

		return fimages;
	}

	public static ArrayList<DepartmentModel> getDepartment(
			SQLiteDatabase sqliteDB, DatabaseHandler dbHandler) {

		ArrayList<DepartmentModel> models = new ArrayList<DepartmentModel>();
		DepartmentModel departmentModel;

		sqliteDB = dbHandler.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery("SELECT * FROM "
				+ DatabaseHandler.departmentsTbl.toString(), null);

		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
				departmentModel = new DepartmentModel();

				departmentModel.deptId = mCursor.getInt(1);
				departmentModel.deptTitle = mCursor.getString(2);
				departmentModel.deptDesc = mCursor.getString(3);
				departmentModel.img_path = mCursor.getString(4);

				models.add(departmentModel);

			} while (mCursor.moveToNext());
		}

		mCursor.close();
		dbHandler.close();

		return models;
	}

	public static List<String> getDepartmentImages(SQLiteDatabase sqliteDB,
			DatabaseHandler handler) {
		List<String> deptImages = new ArrayList<String>();
		String rawQuery = "SELECT img_path FROM "
				+ DatabaseHandler.departmentsTbl;
		// SOMETHING's WRONG WITH THE DATABASE
		sqliteDB = handler.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery(rawQuery, null);
		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
				String image_path = mCursor.getString(0);
				deptImages.add(image_path);
			} while (mCursor.moveToNext());
		}

		return deptImages;

	}

	public static ArrayList<CourseModel> getCourses(SQLiteDatabase sqliteDB,
			DatabaseHandler dbHandler) {
		ArrayList<CourseModel> models = new ArrayList<CourseModel>();
		CourseModel courseModel;

		sqliteDB = dbHandler.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery("SELECT * from "
				+ DatabaseHandler.courseTbl.toString(), null);

		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
				courseModel = new CourseModel();

				courseModel.courseId = mCursor.getString(1);
				courseModel.courseTitle = mCursor.getString(2);
				courseModel.courseDesc = mCursor.getString(3);
				courseModel.deptId = mCursor.getString(4);

				models.add(courseModel);

			} while (mCursor.moveToNext());
		}

		mCursor.close();
		dbHandler.close();

		return models;
	}

	public static ArrayList<PositionModel> getPositions(
			SQLiteDatabase sqliteDB, DatabaseHandler dbHandler) {
		ArrayList<PositionModel> models = new ArrayList<PositionModel>();
		PositionModel positionModel;

		sqliteDB = dbHandler.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery("SELECT * from "
				+ DatabaseHandler.positionTbl.toString(), null);

		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
				positionModel = new PositionModel();

				positionModel.positionId = mCursor.getString(1);
				positionModel.positionTitle = mCursor.getString(2);

				models.add(positionModel);

			} while (mCursor.moveToNext());
		}

		mCursor.close();
		dbHandler.close();

		return models;
	}

	public static ArrayList<SSGModel> getSSG(SQLiteDatabase sqliteDB,
			DatabaseHandler dbHandler) {
		ArrayList<SSGModel> models = new ArrayList<SSGModel>();
		SSGModel ssgModel;

		sqliteDB = dbHandler.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery("SELECT * from "
				+ DatabaseHandler.ssgTbl.toString(), null);

		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
				ssgModel = new SSGModel();

				ssgModel.ssgFname = mCursor.getString(1);
				ssgModel.ssgMname = mCursor.getString(2);
				ssgModel.ssgLname = mCursor.getString(3);
				ssgModel.ssgImage = mCursor.getString(4);
				ssgModel.ssgLevel = mCursor.getString(5);
				ssgModel.ssgCrsId = mCursor.getInt(6) + "";
				ssgModel.ssgDeptId = mCursor.getInt(7) + "";
				ssgModel.ssgPosId = mCursor.getInt(8) + "";

				models.add(ssgModel);

			} while (mCursor.moveToNext());
		}

		mCursor.close();
		dbHandler.close();

		return models;
	}

	public static ArrayList<SSGModel> getSSGListItems(SQLiteDatabase sqliteDB,
			DatabaseHandler handler) {

		ArrayList<SSGModel> ssgitems = new ArrayList<SSGModel>();
		SSGModel ssgModel;

		sqliteDB = handler.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery(
				"select s.firstname,s.middlename,s.lastname,s.img_path,s.level, d.dept_title, p.pos_title, (select c.crs_desc from "
						+ DatabaseHandler.courseTbl
						+ " as c where c.id = s.crs_id) as crs FROM "
						+ DatabaseHandler.ssgTbl + " as s  inner join "
						+ DatabaseHandler.departmentsTbl
						+ " as d on s.dept_id = d.id inner join "
						+ DatabaseHandler.positionTbl
						+ " as p on s.pos_id = p.id", null);

		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
				ssgModel = new SSGModel();

				ssgModel.ssgFname = mCursor.getString(0);
				ssgModel.ssgMname = mCursor.getString(1);
				ssgModel.ssgLname = mCursor.getString(2);
				ssgModel.ssgImage = mCursor.getString(3);
				ssgModel.ssgLevel = mCursor.getString(4);
				ssgModel.ssgDeptId = mCursor.getString(5);
				ssgModel.ssgPosId = mCursor.getString(6);
				ssgModel.ssgCrsId = mCursor.getString(7);
				ssgitems.add(ssgModel);

			} while (mCursor.moveToNext());
		}

		sqliteDB.close();
		handler.close();

		return ssgitems;

	}
	
	public static List<String> getSSGImages(SQLiteDatabase sqliteDB,
			DatabaseHandler handler) {
		List<String> deptImages = new ArrayList<String>();
		String rawQuery = "SELECT img_path FROM "
				+ DatabaseHandler.ssgTbl;
		sqliteDB = handler.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery(rawQuery, null);
		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
				String image_path = mCursor.getString(0);
				deptImages.add(image_path);
			} while (mCursor.moveToNext());
		}

		return deptImages;

	}

	public static ArrayList<SealsModel> getSeals(SQLiteDatabase sqliteDB,
			DatabaseHandler dbHandler) {
		ArrayList<SealsModel> models = new ArrayList<SealsModel>();
		SealsModel sealsModel;

		sqliteDB = dbHandler.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery("SELECT * from "
				+ DatabaseHandler.sealTbl.toString(), null);

		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
				sealsModel = new SealsModel();

				sealsModel.sealImg = mCursor.getString(1);
				sealsModel.sealName = mCursor.getString(2);
				sealsModel.sealDesc = mCursor.getString(3);

				models.add(sealsModel);

			} while (mCursor.moveToNext());
		}

		mCursor.close();
		dbHandler.close();

		return models;
	}

	public static List<String> getSealImagePath(SQLiteDatabase sqliteDB,
			DatabaseHandler dbHandler) {
		List<String> simages = new ArrayList<String>();
		sqliteDB = dbHandler.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery("select img_path from "
				+ DatabaseHandler.sealTbl.toString(), null);
		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
				simages.add(mCursor.getString(0));

			} while (mCursor.moveToNext());
		}

		mCursor.close();
		dbHandler.close();

		return simages;
	}

	public static ArrayList<MusicModel> getMusic(SQLiteDatabase sqliteDB,
			DatabaseHandler dbHandler) {
		ArrayList<MusicModel> models = new ArrayList<MusicModel>();
		MusicModel musicModel;

		sqliteDB = dbHandler.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery("SELECT * from "
				+ DatabaseHandler.musicTbl.toString(), null);

		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
				musicModel = new MusicModel();
				musicModel.musicName = mCursor.getString(1);
				musicModel.musicPath = mCursor.getString(2);

				models.add(musicModel);

			} while (mCursor.moveToNext());
		}

		mCursor.close();
		dbHandler.close();

		return models;
	}

	public static ArrayList<OtherModel> getOther(SQLiteDatabase sqliteDB,
			DatabaseHandler dbHandler) {
		ArrayList<OtherModel> models = new ArrayList<OtherModel>();
		OtherModel otherModel;

		sqliteDB = dbHandler.getReadableDatabase();
		Cursor mCursor = sqliteDB.rawQuery("SELECT * from "
				+ DatabaseHandler.otherTbl.toString(), null);

		mCursor.moveToFirst();
		if (!mCursor.isAfterLast()) {
			do {
				otherModel = new OtherModel();
				otherModel.otherTitle = mCursor.getString(1);
				otherModel.otherBody = mCursor.getString(2);
				otherModel.otherUsrId = mCursor.getString(3);

				models.add(otherModel);

			} while (mCursor.moveToNext());
		}

		mCursor.close();
		dbHandler.close();

		return models;
	}

	public static void InsertStudents(SQLiteDatabase sqLiteDB,
			DatabaseHandler dbHandler, StudentModel studentModel) {
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

	public static void InsertContent(SQLiteDatabase sqLiteDB,
			DatabaseHandler dbHandler, ContentModel content) {

		sqLiteDB = dbHandler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("content_id", content.getContentId());
		values.put("content_type", content.getContentType());
		values.put("content_body", content.getContentBody());

		sqLiteDB.insert(DatabaseHandler.contentsTbl, null, values);
		sqLiteDB.close();
	}

	public static void InsertFaculty(SQLiteDatabase sqLiteDB,
			DatabaseHandler dbHandler, FacultyModel faculty) {

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

	public static void InsertDepartment(SQLiteDatabase sqLiteDB,
			DatabaseHandler dbHandler, DepartmentModel department) {
		sqLiteDB = dbHandler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("dept_id", department.getDeptId());
		values.put("dept_title", department.getDeptTitle());
		values.put("dept_desc", department.getDeptDesc());
		values.put("img_path", department.getImagePath());

		sqLiteDB.insert(DatabaseHandler.departmentsTbl, null, values);
		sqLiteDB.close();
	}

	public static void InsertCourse(SQLiteDatabase sqLiteDB,
			DatabaseHandler dbHandler, CourseModel course) {
		sqLiteDB = dbHandler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("crs_id", course.getCourseId());
		values.put("crs_title", course.getCourseTitle());
		values.put("crs_desc", course.getCourseDesc());
		values.put("dept_id", course.getDeptId());

		sqLiteDB.insert(DatabaseHandler.courseTbl, null, values);
		sqLiteDB.close();
	}

	public static void InsertPosition(SQLiteDatabase sqLiteDB,
			DatabaseHandler dbHandler, PositionModel position) {
		sqLiteDB = dbHandler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("pos_id", position.getPositionId());
		values.put("pos_title", position.getPositionTitle());

		sqLiteDB.insert(DatabaseHandler.positionTbl, null, values);
		sqLiteDB.close();
	}

	public static void InsertSSG(SQLiteDatabase sqLiteDB,
			DatabaseHandler dbHandler, SSGModel ssg) {
		sqLiteDB = dbHandler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("firstname", ssg.getSsgFname());
		values.put("middlename", ssg.getSsgMname());
		values.put("lastname", ssg.getSsgLname());
		values.put("img_path", ssg.getSsgImage());
		values.put("level", ssg.getSsgLevel());
		values.put("dept_id", ssg.getSsgDeptId());
		values.put("crs_id", ssg.getSsgCrsId());
		values.put("pos_id", ssg.getSsgPosId());

		sqLiteDB.insert(DatabaseHandler.ssgTbl, null, values);
		sqLiteDB.close();
	}

	public static void InsertSeal(SQLiteDatabase sqLiteDB,
			DatabaseHandler dbHandler, SealsModel seal) {
		sqLiteDB = dbHandler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("img_path", seal.getSealImg());
		values.put("js_name", seal.getSealName());
		values.put("js_desc", seal.getSealDesc());

		sqLiteDB.insert(DatabaseHandler.sealTbl, null, values);
		sqLiteDB.close();
		dbHandler.close();
	}

	public static void InsertMusic(SQLiteDatabase sqLiteDB,
			DatabaseHandler dbHandler, MusicModel music) {
		sqLiteDB = dbHandler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("music_name", music.getMusicName());
		values.put("music_path", music.getMusicPath());

		sqLiteDB.insert(DatabaseHandler.musicTbl, null, values);
		sqLiteDB.close();
	}

	public static void InsertOther(SQLiteDatabase sqLiteDB,
			DatabaseHandler dbHandler, OtherModel other) {
		sqLiteDB = dbHandler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("o_title", other.getOtherTitle());
		values.put("o_body", other.getOtherBody());
		values.put("user_id", other.getOtherUsrId());

		sqLiteDB.insert(DatabaseHandler.otherTbl, null, values);
		sqLiteDB.close();
	}

	public static void TruncateTables(SQLiteDatabase db,
			DatabaseHandler dbHandler) {

		db = dbHandler.getWritableDatabase();

		db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.contentsTbl);
		db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.departmentsTbl);
		db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.studentsTbl);
		db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.facultyTbl);
		db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.courseTbl);
		db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.positionTbl);
		db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.ssgTbl);
		db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.sealTbl);
		db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.musicTbl);
		db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.otherTbl);

		db.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseHandler.contentsTbl
				+ " " + "( id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "content_id INTEGER, " + "content_type TEXT, "
				+ "content_body TEXT ) ");

		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ DatabaseHandler.departmentsTbl + " "
				+ "( id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "dept_id INTEGER, " + "dept_title TEXT, "
				+ "dept_desc TEXT, img_path TEXT ) ");

		db.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseHandler.studentsTbl
				+ " " + "( id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "stud_idnum TEXT, " + "stud_fname TEXT, "
				+ "stud_mname TEXT, " + "stud_lname TEXT, "
				+ "dept_id INTEGER ) ");

		db.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseHandler.facultyTbl
				+ " " + "( id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "fc_idn TEXT, " + "fc_name TEXT, " + "fc_mname TEXT, "
				+ "fc_lname TEXT, " + "fc_suffix TEXT, " + "fc_gender TEXT, "
				+ "image_path TEXT, " + "dept_id TEXT, " + "pos TEXT ) ");

		db.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseHandler.sealTbl
				+ " " + "( id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "img_path TEXT, " + "js_name TEXT, " + "js_desc TEXT ) ");

		db.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseHandler.courseTbl
				+ " " + "( id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "crs_id INTEGER, " + "crs_title TEXT, " + "crs_desc TEXT, "
				+ "dept_id INTEGER ) ");

		db.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseHandler.positionTbl
				+ " " + "( id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "pos_id INTEGER, " + "pos_title TEXT ) ");

		db.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseHandler.ssgTbl + " "
				+ "( id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "firstname TEXT, " + "middlename TEXT, " + "lastname TEXT, "
				+ "img_path TEXT, " + "level TEXT, " + "crs_id INTEGER, "
				+ "pos_id INTEGER ) ");

		db.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseHandler.musicTbl
				+ " " + "( id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "music_name TEXT, " + "music_path TEXT ) ");

		db.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseHandler.otherTbl
				+ " " + "( id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "o_title TEXT, " + "o_body TEXT, " + "user_id INTEGER ) ");

		db.close();
		dbHandler.close();
	}
}
