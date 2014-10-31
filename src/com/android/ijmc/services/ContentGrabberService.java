package com.android.ijmc.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

public class ContentGrabberService extends IntentService{
	
	public static final String PARAM_SRC = "url_src"; 

	public ContentGrabberService() {
		// TODO Auto-generated constructor stub
		super("ContentGrabberService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		ArrayList<String> urls = intent.getStringArrayListExtra(PARAM_SRC);
		ServiceHandler requestHandler = new ServiceHandler();
		for(String url : urls){
			String response = requestHandler.makeServiceCall(url, ServiceHandler.GET);
			String sourceFile = url.substring(url.lastIndexOf('/')+1, url.length()-5);
			if(sourceFile.equals(Config.CONTENT_JSON.substring(0, Config.CONTENT_JSON.length()-5))) {
				forContent(response);
			} else if(sourceFile.equals(Config.DEPARTMENT_JSON.substring(0, Config.DEPARTMENT_JSON.length()-5))) {
				forDepartment(response);
			} else if(sourceFile.equals(Config.COURSE_JSON.substring(0, Config.COURSE_JSON.length()-5))) {
				forCourse(response);
			} else if(sourceFile.equals(Config.FACULTY_JSON.substring(0, Config.FACULTY_JSON.length()-5))) {
				forFaculty(response);
				obtainFacultyImages();
			} else if(sourceFile.equals(Config.POSITION_JSON.substring(0, Config.POSITION_JSON.length()-5))) {
				forPosition(response);
			} else if(sourceFile.equals(Config.SSG_JSON.substring(0, Config.SSG_JSON.length()-5))) {
				forSSG(response);
			} else if(sourceFile.equals(Config.SEALS_JSON.substring(0, Config.SEALS_JSON.length()-5))) {
				forSeals(response);
			} else if(sourceFile.equals(Config.MUSIC_JSON.substring(0, Config.MUSIC_JSON.length()-5))) {
				forMusic(response);
			} else if(sourceFile.equals(Config.OTHER_JSON.substring(0, Config.OTHER_JSON.length()-5))) {
				forOther(response);
			}
		}
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(LoginActivity.ResponseReceiver.ACTION_RESPONSE);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		sendBroadcast(broadcastIntent);
	}
	
	private void forDepartment(String response){
		try {
			DatabaseHandler handler = new DatabaseHandler(this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				DepartmentModel singleContent = new DepartmentModel();
				singleContent.deptId = Integer.parseInt(obj.getString(Config.TAG_DEPT_ID));
				singleContent.deptTitle = obj.getString(Config.TAG_DEPT_TITLE);
				singleContent.deptDesc = obj.getString(Config.TAG_DEPT_DESC);
				
				Queries.InsertDepartment(sqliteDB, handler, singleContent);
			}
			
			ArrayList<DepartmentModel> items = Queries.getDepartment(sqliteDB, handler);
			for(DepartmentModel model : items) {
				Log.e("DEPARTMENT", model.getDeptId()+"");
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage().toString());
		}
		
	}

	private void forContent(String response){
		try {
			DatabaseHandler handler = new DatabaseHandler(this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				ContentModel content = new ContentModel();
				content.contentId = obj.getString(Config.TAG_CONTENT_ID);
				content.contentType = obj.getString(Config.TAG_CONTENT_TYPE);
				content.contentBody = obj.getString(Config.TAG_CONTENT_BODY);				
				Queries.InsertContent(sqliteDB, handler, content);
			}
			
			ArrayList<ContentModel> contents = Queries.getContents(sqliteDB, handler);
			for(ContentModel item: contents) {
				Log.e("CONTENT", item.getContentType());
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage().toString());
		}
	}
	
	private void forCourse(String response){
		try {
			DatabaseHandler handler = new DatabaseHandler(this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				CourseModel course = new CourseModel();
				course.courseId = obj.getString(Config.TAG_COURSE_ID);
				course.courseTitle = obj.getString(Config.TAG_COURSE_TITLE);
				course.courseDesc = obj.getString(Config.TAG_COURSE_DESC);
				course.deptId = obj.getInt(Config.TAG_COURSE_DEPT_ID);
				Queries.InsertCourse(sqliteDB, handler, course);
			}
			
			ArrayList<CourseModel> course = Queries.getCourses(sqliteDB, handler);
			for(CourseModel item: course) {
				Log.e("COURSE", item.getCourseTitle());
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage().toString());
		}
	}
	
	private void forFaculty(String response){
		try {
			DatabaseHandler handler = new DatabaseHandler(this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				FacultyModel faculty = new FacultyModel();				
				faculty.facultyId = obj.getString(Config.TAG_FACULTY_ID);
				faculty.facultyFname = obj.getString(Config.TAG_FACULTY_FNAME);
				faculty.facultyMname = obj.getString(Config.TAG_FACULTY_MNAME);
				faculty.facultyLname = obj.getString(Config.TAG_FACULTY_LNAME);
				faculty.facultySfx = obj.getString(Config.TAG_FACULTY_SFX);
				faculty.facultyGender = obj.getString(Config.TAG_FACULTY_GENDER);
				faculty.facultyImagePath = obj.getString(Config.TAG_FACULTY_IMAGE);
				faculty.facultyDeptId = obj.getString(Config.TAG_FACULTY_DEPT);
				faculty.facultyPositionId = obj.getString(Config.TAG_FACULTY_POSITION);
				
				Queries.InsertFaculty(sqliteDB, handler, faculty);
			}
			
			ArrayList<FacultyModel> faculty = Queries.getFaculty(sqliteDB, handler);
			for(FacultyModel item: faculty) {
				Log.e("FACULTY", item.getFacultyId());
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage().toString());
		}
	}
	
	private void obtainFacultyImages() {
		DatabaseHandler handler = new DatabaseHandler(this.getApplicationContext());
		SQLiteDatabase sqliteDB = handler.getWritableDatabase();
		List<String> fimages = Queries.getFacultyImages(sqliteDB, handler);
		
		
		
		for(String image : fimages) {
			try {
				int count = 0;
				Log.e("SOURCE", Config.IMAGE_FACULTY_BASE_URL + "/" + image);
				URL url = new URL(Config.IMAGE_FACULTY_BASE_URL + "/" + image);
				
				URLConnection connection = url.openConnection();
				connection.connect();
				
				InputStream in = new BufferedInputStream(url.openStream());
				
				File imageDir = new File(Config.EXTERNAL_FOLDER + "/faculty_images");
				imageDir.mkdirs();
				
				OutputStream out = new FileOutputStream(Config.EXTERNAL_FOLDER + "/faculty_images/" + image);
				byte[] data = new byte[1024];
				while((count = in.read(data)) != -1) {
					out.write(data,0,count);
				}
				out.flush();
				out.close();
				in.close();
				
			} catch(IOException e) {
				Log.e("FACULTY IMAGES DOWNLOAD ERROR", e.getMessage().toString());
			}
		}
	}
	
	private void forPosition(String response){
		try {
			DatabaseHandler handler = new DatabaseHandler(this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				PositionModel position = new PositionModel();								
				position.positionId = obj.getString(Config.TAG_POSITION_ID);
				position.positionTitle = obj.getString(Config.TAG_POSITION_TITLE);
				
				Queries.InsertPosition(sqliteDB, handler, position);
			}
			
			ArrayList<PositionModel> position = Queries.getPositions(sqliteDB, handler);
			for(PositionModel item: position) {
				Log.e("POSITION", item.getPositionTitle());
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage().toString());
		}
	}
	
	private void forSSG(String response){
		try {
			DatabaseHandler handler = new DatabaseHandler(this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				SSGModel ssg = new SSGModel();												
				ssg.ssgFname = obj.getString(Config.TAG_SSG_FNAME);
				ssg.ssgMname = obj.getString(Config.TAG_SSG_MNAME);
				ssg.ssgLname = obj.getString(Config.TAG_SSG_LNAME);
				ssg.ssgImage = obj.getString(Config.TAG_SSG_FNAME);
				ssg.ssgLevel = obj.getString(Config.TAG_SSG_LEVEL);
				ssg.ssgCrsId = obj.getInt(Config.TAG_SSG_CRS_ID);
				ssg.ssgPosId = obj.getInt(Config.TAG_SSG_POS_ID);
				
				Queries.InsertSSG(sqliteDB, handler, ssg);
			}
			
			ArrayList<SSGModel> ssg = Queries.getSSG(sqliteDB, handler);
			for(SSGModel item: ssg) {
				Log.e("SSG", item.getSsgLname());
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage().toString());
		}
	}
	
	private void forSeals(String response){
		try {
			DatabaseHandler handler = new DatabaseHandler(this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				SealsModel seal = new SealsModel();
				seal.sealImg = obj.getString(Config.TAG_SEAL_IMAGE);
				seal.sealName = obj.getString(Config.TAG_SEAL_NAME);
				seal.sealDesc = obj.getString(Config.TAG_SEAL_DESC);
				
				Queries.InsertSeal(sqliteDB, handler, seal);
			}
			
			ArrayList<SealsModel> seal = Queries.getSeals(sqliteDB, handler);
			for(SealsModel item: seal) {
				Log.e("SEAL", item.getSealName());
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage().toString());
		}
	}
	
	private void forMusic(String response){
		try {
			DatabaseHandler handler = new DatabaseHandler(this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				MusicModel music = new MusicModel();
				music.musicName = obj.getString(Config.TAG_MUSIC_NAME);
				music.musicPath = obj.getString(Config.TAG_MUSIC_PATH);
				
				Queries.InsertMusic(sqliteDB, handler, music);
			}
			
			ArrayList<MusicModel> music = Queries.getMusic(sqliteDB, handler);
			for(MusicModel item: music) {
				Log.e("MUSIC", item.getMusicName());
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage().toString());
		}
	}
	
	private void forOther(String response){
		try {
			DatabaseHandler handler = new DatabaseHandler(this.getApplicationContext());
			SQLiteDatabase sqliteDB = handler.getWritableDatabase();
			JSONArray jsonArray = new JSONArray(response);
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				OtherModel other = new OtherModel();
				other.otherTitle = obj.getString(Config.TAG_OTHER_TITLE);
				other.otherBody = obj.getString(Config.TAG_OTHER_BODY);
				other.otherUsrId = obj.getString(Config.TAG_OTHER_USR_ID);
				
				Queries.InsertOther(sqliteDB, handler, other);
			}
			
			ArrayList<OtherModel> other = Queries.getOther(sqliteDB, handler);
			for(OtherModel item: other) {
				Log.e("OTHER", item.getOtherTitle());
			}
		} catch (Exception e) {
			Log.e(ContentGrabberService.class.toString(), e.getMessage().toString());
		}
	}
}
