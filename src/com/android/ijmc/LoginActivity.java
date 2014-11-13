package com.android.ijmc;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.ijmc.config.Config;
import com.android.ijmc.helpers.ServiceHandler;
import com.android.ijmc.services.ContentGrabberService;
import com.android.ijmc.utilities.Utilities;

public class LoginActivity extends Activity implements OnClickListener{

	ServiceHandler serviceHandler;
	SharedPreferences sp;
	SharedPreferences.Editor spEditor;
	ResponseReceiver responseReceiver;
	
	ProgressDialog prepareDialog;
	
	//1 = STUDENT, DEFAULT;
	//2 = FACULTY;
	private int flagUserType = 1;
	
	public LoginActivity() {
		// TODO Auto-generated constructor stub
		this.serviceHandler = new ServiceHandler();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESPONSE);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		responseReceiver = new ResponseReceiver();
		registerReceiver(responseReceiver, filter);
		
		sp = getSharedPreferences(Config.SHA_NAME, MODE_PRIVATE);
		Button btnRegister = (Button)findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(this);
		
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(responseReceiver);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btnRegister:
			displayLoginDialog();
			break;
		}
	}
	
	private void displayLoginDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		AlertDialog dialog = builder.create();
		
		View view = createLoginView();
		dialog.setView(view);
		final EditText idField = (EditText)view.findViewById(R.id.idField);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Login", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String requestURL = "";
				Log.e("REQUEST", (idField.getText().toString().split("-"))[0].length()+"");
				if((idField.getText().toString().split("-"))[0].length() == 2){
					requestURL = Config.JSON_REQUEST_URL + "/" + Config.FACULTY_CONFIRM_JSON + "?id=" + idField.getText();
					flagUserType = 2;
				} else {
					requestURL = Config.JSON_REQUEST_URL + "/" + Config.STUDENT_CONFIRM_JSON + "?id=" + idField.getText();
				}
				Log.e("requestURL", requestURL);
				
				LoginAsyncTask login = new LoginAsyncTask(LoginActivity.this, dialog);
				login.execute(requestURL);
			}
		});
		dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new AlertDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	private View createLoginView(){
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View baseLayout = inflater.inflate(R.layout.activity_login_view, null);
		return baseLayout;
	}
	
	private void setPreferenceForStudent(JSONObject jsonObject) throws JSONException{
		
		Log.e("JSONObjectResponse", jsonObject + "");
		
		spEditor = sp.edit();
		spEditor.clear();
		
		spEditor.putString(Config.SHA_USR_ID, jsonObject.getString("stud_idnum"));
		spEditor.putString(Config.SHA_USR_FNAME, jsonObject.getString("stud_fname"));
		spEditor.putString(Config.SHA_USR_MNAME, jsonObject.getString("stud_mname"));
		spEditor.putString(Config.SHA_USR_LNAME, jsonObject.getString("stud_lname"));
		spEditor.putString(Config.SHA_USR_DEPT_ID, jsonObject.getString("dept_id"));
		String imageFile = jsonObject.getString("image_path");
		imageFile = imageFile.replace(" ", "_");
		spEditor.putString(Config.SHA_USR_IMAGE_FILE, imageFile);
		spEditor.putString(Config.SHA_USR_TYPE, "Student");
		spEditor.putBoolean(Config.SHA_LOGGED_IN, true);
		spEditor.commit();
	}
	
	private void setPreferenceForFaculty(JSONObject jsonObject) throws JSONException{
		spEditor = sp.edit();
		spEditor.clear();
		
		spEditor.putString(Config.SHA_USR_ID, jsonObject.getString("fc_idn"));
		spEditor.putString(Config.SHA_USR_FNAME, jsonObject.getString("fc_name"));
		spEditor.putString(Config.SHA_USR_MNAME, jsonObject.getString("fc_mname"));
		spEditor.putString(Config.SHA_USR_LNAME, jsonObject.getString("fc_lname"));
		spEditor.putString(Config.SHA_USR_SUFFIX, jsonObject.getString("fc_suffix"));
		spEditor.putString(Config.SHA_USR_DEPT_ID, jsonObject.getString("dept_id"));
		spEditor.putString(Config.SHA_USR_POS_ID, jsonObject.getString("pos"));
		String imageFile = jsonObject.getString("image_path");
		imageFile = imageFile.replace(" ", "_");
		spEditor.putString(Config.SHA_USR_IMAGE_FILE, imageFile);
		spEditor.putString(Config.SHA_USR_TYPE, "Faculty");
		spEditor.putBoolean(Config.SHA_LOGGED_IN, true);
		
		spEditor.commit();
	}
	
	class LoginAsyncTask extends AsyncTask<String, Void, String>{
		
		DialogInterface dialog;
		Context context;
		ProgressDialog progressDialog;
		long startTime;
		
		public LoginAsyncTask(Context context, DialogInterface dialog){
			this.dialog = dialog;
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			startTime = System.currentTimeMillis();
			
			prepareDialog = new ProgressDialog(context);
			prepareDialog.setIndeterminate(true);
			prepareDialog.setMessage("Preparing...");
			
			progressDialog = new ProgressDialog(this.context);
			progressDialog.setIndeterminate(true);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setMessage("Logging in...");
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String response = LoginActivity.this.serviceHandler.makeServiceCall(params[0], 1);
			
			/*if(response == null) {
				Log.e("LOGIN RESPONSE", "Error in Response");
			} else {
				Log.e("LOGIN RESPONSE", response.toString());
			}*/
			
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null) {
				try {
					JSONArray jsonArray = Utilities.makeJSONArrayFromString(result);
					if(jsonArray.length() > 0){
						JSONObject jsonObject = (JSONObject) jsonArray.get(0);
						if(flagUserType == 1){
							setPreferenceForStudent(jsonObject);
						} else {
							setPreferenceForFaculty(jsonObject);
						}
						
						progressDialog.dismiss();
						dialog.dismiss();
						
						prepareDialog.show();
						Intent contentGrabber = new Intent(LoginActivity.this, ContentGrabberService.class);
						ArrayList<String> urls = new ArrayList<String>();
						urls.add(Config.JSON_URL + "/" + Config.CONTENT_JSON);
						urls.add(Config.JSON_URL + "/" + Config.DEPARTMENT_JSON);
						urls.add(Config.JSON_URL + "/" + Config.COURSE_JSON);
						urls.add(Config.JSON_URL + "/" + Config.FACULTY_JSON);
						urls.add(Config.JSON_URL + "/" + Config.POSITION_JSON);
						urls.add(Config.JSON_URL + "/" + Config.SSG_JSON);
						urls.add(Config.JSON_URL + "/" + Config.SEALS_JSON);
						urls.add(Config.JSON_URL + "/" + Config.MUSIC_JSON);
						urls.add(Config.JSON_URL + "/" + Config.OTHER_JSON);
						
						contentGrabber.putExtra(ContentGrabberService.PARAM_SRC, urls);
						startService(contentGrabber);
						
					} else {
						Toast.makeText(getApplicationContext(), "Invalid ID", Toast.LENGTH_LONG).show();
						progressDialog.dismiss();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.e("JSONException LoginActivity.java", e.getMessage().toString());
				}
			} else {
				progressDialog.dismiss();
				Toast.makeText(LoginActivity.this, "Login failed after " + (System.currentTimeMillis()- startTime)/1000 + " seconds", Toast.LENGTH_LONG).show();
			}
			
		}
		
	}
	
	public class ResponseReceiver extends BroadcastReceiver{
		public static final String ACTION_RESPONSE = "com.android.ijmc.intent.action.PREPARE_DONE";
		
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			prepareDialog.dismiss();
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		
	}
	
}
