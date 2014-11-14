package com.android.ijmc.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.ijmc.LoginActivity;
import com.android.ijmc.config.Config;
import com.android.ijmc.helpers.DatabaseHandler;
import com.android.ijmc.helpers.Queries;
import com.android.ijmc.helpers.ServiceHandler;
import com.android.ijmc.models.ContentModel;
import com.android.ijmc.models.CourseModel;
import com.android.ijmc.models.DepartmentModel;
import com.android.ijmc.models.FacultyModel;
import com.android.ijmc.models.MusicModel;
import com.android.ijmc.models.OtherModel;
import com.android.ijmc.models.PositionModel;
import com.android.ijmc.models.SSGModel;
import com.android.ijmc.models.SealsModel;

public class ContentGrabberService extends IntentService {

	public static final String PARAM_SRC = "url_src";
	public static final String PARAM_METHOD = "method";
	
	public ContentGrabberService() {
		// TODO Auto-generated constructor stub
		super("ContentGrabberService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		ArrayList<String> urls = intent.getStringArrayListExtra(PARAM_SRC);
		ServiceHandler requestHandler = new ServiceHandler();
		for (String url : urls) {
			String response = requestHandler.makeServiceCall(url,
					ServiceHandler.GET);
			String sourceFile = url.substring(url.lastIndexOf('/') + 1,
					url.length() - 5);
			if (sourceFile.equals(Config.CONTENT_JSON.substring(0,
					Config.CONTENT_JSON.length() - 5))) {
				Log.e("ContentGrabberService","Content");
				forContent(response);
			} else if (sourceFile.equals(Config.DEPARTMENT_JSON.substring(0,
					Config.DEPARTMENT_JSON.length() - 5))) {
				Log.e("ContentGrabberService","Department");
				forDepartment(response);
				obtainDepartmentImages();
			} else if (sourceFile.equals(Config.COURSE_JSON.substring(0,
					Config.COURSE_JSON.length() - 5))) {
				Log.e("ContentGrabberService","Course");
				forCourse(response);
			} else if (sourceFile.equals(Config.FACULTY_JSON.substring(0,
					Config.FACULTY_JSON.length() - 5))) {
				Log.e("ContentGrabberService","Faculty");
				forFaculty(response);
				obtainFacultyImages();
			} else if (sourceFile.equals(Config.POSITION_JSON.substring(0,
					Config.POSITION_JSON.length() - 5))) {
				Log.e("ContentGrabberService","Position");
				forPosition(response);
			} else if (sourceFile.equals(Config.SSG_JSON.substring(0,
					Config.SSG_JSON.length() - 5))) {
				Log.e("ContentGrabberService","SSG");
				forSSG(response);
			} else if (sourceFile.equals(Config.SEALS_JSON.substring(0,
					Config.SEALS_JSON.length() - 5))) {
				Log.e("ContentGrabberService","Seals");
				forSeals(response);
				obtainSealImages();
			} else if (sourceFile.equals(Config.MUSIC_JSON.substring(0,
					Config.MUSIC_JSON.length() - 5))) {
				Log.e("ContentGrabberService","Music");
				forMusic(response);
				obtainMusicFile();
			} else if (sourceFile.equals(Config.OTHER_JSON.substring(0,
					Config.OTHER_JSON.length() - 5))) {
				Log.e("ContentGrabberService","Other");
				forOther(response);
			}
		}
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(LoginActivity.ResponseReceiver.ACTION_RESPONSE);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		sendBroadcast(broadcastIntent);
	}

	private void forDepartment(String response) {
		try {
			DatabaseHandler handler = new DatabaseHandler(
					this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				DepartmentModel singleContent = new DepartmentModel();
				singleContent.deptId = Integer.parseInt(obj
						.getString(Config.TAG_DEPT_ID));
				singleContent.deptTitle = obj.getString(Config.TAG_DEPT_TITLE);
				singleContent.deptDesc = obj.getString(Config.TAG_DEPT_DESC);

				String imagePath = obj.getString(Config.TAG_DEPT_IMAGEPATH);

				if (imagePath == null) {
					imagePath = "";
				}

				singleContent.img_path = imagePath;

				Queries.InsertDepartment(sqliteDB, handler, singleContent);
			}

		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage()
					.toString());
		}
	}

	private void obtainDepartmentImages() {
		DatabaseHandler handler = new DatabaseHandler(
				this.getApplicationContext());
		SQLiteDatabase sqliteDB = handler.getReadableDatabase();
		List<String> deptImages = Queries
				.getDepartmentImages(sqliteDB, handler);
		for (String image : deptImages) {
			int count = 0;
			URL url;
			try {
				String urlImage = image.replace(" ", "%20");
				url = new URL(Config.IMAGE_DEPARTMENT_BASE_URL + "/" + urlImage);
				URLConnection connection = url.openConnection();
				connection.connect();

				InputStream in = new BufferedInputStream(url.openStream());

				File imageDir = new File(Config.EXTERNAL_FOLDER + "/" + Config.EXTERNAL_FOLDER_DEPT_IMAGE);
				imageDir.mkdirs();

				OutputStream out = new FileOutputStream(Config.EXTERNAL_FOLDER
						+ "/" + Config.EXTERNAL_FOLDER_DEPT_IMAGE + "/" + image);
				byte[] data = new byte[1024];
				while ((count = in.read(data)) != -1) {
					out.write(data, 0, count);
				}
				out.flush();
				out.close();
				in.close();

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		handler.close();
		sqliteDB.close();
	}

	private void forContent(String response) {
		try {
			DatabaseHandler handler = new DatabaseHandler(
					this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				ContentModel content = new ContentModel();
				content.contentId = obj.getString(Config.TAG_CONTENT_ID);
				content.contentType = obj.getString(Config.TAG_CONTENT_TYPE);
				content.contentBody = obj.getString(Config.TAG_CONTENT_BODY);
				Queries.InsertContent(sqliteDB, handler, content);
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage()
					.toString());
		}
	}

	private void forCourse(String response) {
		try {
			DatabaseHandler handler = new DatabaseHandler(
					this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				CourseModel course = new CourseModel();
				course.courseId = obj.getString(Config.TAG_COURSE_ID);
				course.courseTitle = obj.getString(Config.TAG_COURSE_TITLE);
				course.courseDesc = obj.getString(Config.TAG_COURSE_DESC);
				course.deptId = obj.getInt(Config.TAG_COURSE_DEPT_ID);
				Queries.InsertCourse(sqliteDB, handler, course);
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage()
					.toString());
		}
	}

	private void forFaculty(String response) {
		try {
			DatabaseHandler handler = new DatabaseHandler(
					this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				FacultyModel faculty = new FacultyModel();
				faculty.facultyId = obj.getString(Config.TAG_FACULTY_ID);
				faculty.facultyFname = obj.getString(Config.TAG_FACULTY_FNAME);
				faculty.facultyMname = obj.getString(Config.TAG_FACULTY_MNAME);
				faculty.facultyLname = obj.getString(Config.TAG_FACULTY_LNAME);
				faculty.facultySfx = obj.getString(Config.TAG_FACULTY_SFX);
				faculty.facultyGender = obj
						.getString(Config.TAG_FACULTY_GENDER);
				String imageFilename = obj.getString(Config.TAG_FACULTY_IMAGE);
//				imageFilename = imageFilename.replace(" ", "_");
				faculty.facultyImagePath = imageFilename;
				faculty.facultyDeptId = obj.getString(Config.TAG_FACULTY_DEPT);
				faculty.facultyPositionId = obj
						.getString(Config.TAG_FACULTY_POSITION);

				Queries.InsertFaculty(sqliteDB, handler, faculty);
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage()
					.toString());
		}
	}

	private void obtainFacultyImages() {
		DatabaseHandler handler = new DatabaseHandler(
				this.getApplicationContext());
		SQLiteDatabase sqliteDB = handler.getWritableDatabase();
		List<String> fimages = Queries.getFacultyImages(sqliteDB, handler);

		for (String image : fimages) {
			try {
				int count = 0;
				String urlImage = image.replace(" ", "%20");
				Log.e("SOURCE", Config.IMAGE_FACULTY_BASE_URL + "/thumb/" + urlImage);
				URL url = new URL(Config.IMAGE_FACULTY_BASE_URL + "/thumb/"
						+ urlImage);

				URLConnection connection = url.openConnection();
				connection.connect();

				InputStream in = new BufferedInputStream(url.openStream());

				File imageDir = new File(Config.EXTERNAL_FOLDER
						+ "/" + Config.EXTERNAL_FOLDER_FACULTY_IMAGE);
				imageDir.mkdirs();

				OutputStream out = new FileOutputStream(Config.EXTERNAL_FOLDER
						+ "/" + Config.EXTERNAL_FOLDER_FACULTY_IMAGE + "/" + image);
				byte[] data = new byte[1024];
				while ((count = in.read(data)) != -1) {
					out.write(data, 0, count);
				}
				out.flush();
				out.close();
				in.close();

			} catch (IOException e) {
				Log.e("FACULTY IMAGES DOWNLOAD ERROR", e.getMessage()
						.toString());
			}
		}
	}

	private void forPosition(String response) {
		try {
			DatabaseHandler handler = new DatabaseHandler(
					this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				PositionModel position = new PositionModel();
				position.positionId = obj.getString(Config.TAG_POSITION_ID);
				position.positionTitle = obj
						.getString(Config.TAG_POSITION_TITLE);

				Queries.InsertPosition(sqliteDB, handler, position);
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage()
					.toString());
		}
	}

	private void forSSG(String response) {
		try {
			DatabaseHandler handler = new DatabaseHandler(
					this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				SSGModel ssg = new SSGModel();
				ssg.ssgFname = obj.getString(Config.TAG_SSG_FNAME);
				ssg.ssgMname = obj.getString(Config.TAG_SSG_MNAME);
				ssg.ssgLname = obj.getString(Config.TAG_SSG_LNAME);
				ssg.ssgImage = obj.getString(Config.TAG_SSG_IMAGE);
				ssg.ssgLevel = obj.getString(Config.TAG_SSG_LEVEL);
				ssg.ssgCrsId = obj.getInt(Config.TAG_SSG_CRS_ID);
				ssg.ssgPosId = obj.getInt(Config.TAG_SSG_POS_ID);

				Queries.InsertSSG(sqliteDB, handler, ssg);
			}

			ArrayList<SSGModel> ssg = Queries.getSSG(sqliteDB, handler);
			for (SSGModel item : ssg) {
				Log.e("SSG", item.getSsgLname());
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage()
					.toString());
		}
	}

	private void forSeals(String response) {
		try {
			DatabaseHandler handler = new DatabaseHandler(
					this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				SealsModel seal = new SealsModel();
				String imageFilename = obj.getString(Config.TAG_SEAL_IMAGE);
//				imageFilename = imageFilename.replace(" ", "_");
				seal.sealImg = imageFilename;
				seal.sealName = obj.getString(Config.TAG_SEAL_NAME);
				seal.sealDesc = obj.getString(Config.TAG_SEAL_DESC);

				Queries.InsertSeal(sqliteDB, handler, seal);
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage()
					.toString());
		}
	}

	private void obtainSealImages() {
		DatabaseHandler handler = new DatabaseHandler(
				this.getApplicationContext());
		SQLiteDatabase sqliteDB = handler.getReadableDatabase();
		List<String> simages = Queries.getSealImagePath(sqliteDB, handler);
		for (String image : simages) {
			try {
				int count = 0;
				String urlImage = image.replace(" ", "%20");
				URL url = new URL(Config.IMAGE_SEAL_BASE_URL + "/" + urlImage);

				URLConnection connection = url.openConnection();
				connection.connect();

				InputStream in = new BufferedInputStream(url.openStream());

				File imageDir = new File(Config.EXTERNAL_FOLDER
						+ "/" + Config.EXTERNAL_FOLDER_SEAL_IMAGE);
				imageDir.mkdirs();

				OutputStream out = new FileOutputStream(Config.EXTERNAL_FOLDER
						+ "/" + Config.EXTERNAL_FOLDER_SEAL_IMAGE + "/" + image);
				byte[] data = new byte[1024];
				while ((count = in.read(data)) != -1) {
					out.write(data, 0, count);
				}
				out.flush();
				out.close();
				in.close();

			} catch (IOException e) {
				Log.e("SEAL IMAGES DOWNLOAD ERROR", e.getMessage().toString());
			}
		}
	}

	private void forMusic(String response) {
		try {
			DatabaseHandler handler = new DatabaseHandler(
					this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				MusicModel music = new MusicModel();

				music.musicName = obj.getString(Config.TAG_MUSIC_NAME);
				String musicFilename = obj.getString(Config.TAG_MUSIC_PATH);
//				musicFilename = musicFilename.replace(" ", "_");
				music.musicPath = musicFilename;

				Queries.InsertMusic(sqliteDB, handler, music);
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage()
					.toString());
		}
	}

	private void obtainMusicFile() {

		try {
//			String urlMusic = Config.MUSIC_FILENAME.replace("_", "%20");
			URL url = new URL(Config.MUSIC_BASE_URL + "/"
					+ Config.MUSIC_FILENAME);
			URLConnection connection = url.openConnection();
			connection.connect();

			InputStream in = url.openStream();

			File file = new File(Config.EXTERNAL_FOLDER + "/" + Config.EXTERNAL_FOLDER_MUSIC);
			file.mkdirs();

			OutputStream out = new FileOutputStream(Config.EXTERNAL_FOLDER
					+ "/" + Config.EXTERNAL_FOLDER_MUSIC + "/" + Config.MUSIC_FILENAME);
			byte data[] = new byte[1024];
			int count = 0;
			while ((count = in.read(data)) != -1) {
				out.write(data, 0, count);
			}

			out.flush();
			out.close();
			in.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void forOther(String response) {
		try {
			DatabaseHandler handler = new DatabaseHandler(
					this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				OtherModel other = new OtherModel();
				other.otherTitle = obj.getString(Config.TAG_OTHER_TITLE);
				other.otherBody = obj.getString(Config.TAG_OTHER_BODY);
				other.otherUsrId = obj.getString(Config.TAG_OTHER_USR_ID);

				Queries.InsertOther(sqliteDB, handler, other);
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage()
					.toString());
		}
	}
}
