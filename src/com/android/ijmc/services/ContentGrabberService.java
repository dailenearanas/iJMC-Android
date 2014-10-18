package com.android.ijmc.services;

import java.util.ArrayList;

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
import com.android.ijmc.models.DepartmentModel;

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
				content.contentType = obj.getString("content_type");
				content.contentBody = obj.getString("content_body");
				content.contentId = obj.getString("id");
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
}
