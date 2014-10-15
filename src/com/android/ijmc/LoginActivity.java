package com.android.ijmc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ijmc.config.Config;
import com.android.ijmc.helpers.ServiceHandler;
import com.android.ijmc.utilities.Utilities;

public class LoginActivity extends Activity implements OnClickListener{

	ServiceHandler serviceHandler;
	SharedPreferences sp;
	SharedPreferences.Editor spEditor;
	
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
		sp = getSharedPreferences(Config.SHA_NAME, MODE_PRIVATE);
		Button btnRegister = (Button)findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(this);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
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
		
		dialog.setTitle("Login");
		dialog.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		
		View view = createLoginView();
		final EditText idField = (EditText)view.findViewById(11);
		
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Login", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String requestURL = "";
				if((idField.getText().toString().split("-"))[0].length() == 2){
					requestURL = Config.JSON_REQUEST_URL + "/" + Config.FACULTY_VERIFY_JSON + "?id=" + idField.getText();
					flagUserType = 2;
				} else {
					requestURL = Config.JSON_REQUEST_URL + "/" + Config.STUDENT_VERIFY_JSON + "?id=" + idField.getText();
				}
				
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
		dialog.setView(view);
		dialog.show();
	}
	
	private View createLoginView(){
		LinearLayout baseLayout = new LinearLayout(this);
		baseLayout.setOrientation(LinearLayout.VERTICAL);
		EditText idField = new EditText(this);
		idField.setId(11);
		TextView label = new TextView(this);
		label.setId(12);
		
		label.setText("ID Number: ");
		label.setTextColor(Color.parseColor("#555555"));
		
		baseLayout.addView(label);
		baseLayout.addView(idField);
		
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
		spEditor.putString(Config.SHA_USR_TYPE, "Faculty");
		spEditor.putBoolean(Config.SHA_LOGGED_IN, true);
		
		spEditor.commit();
	}
	
	class LoginAsyncTask extends AsyncTask<String, Void, String>{
		
		DialogInterface dialog;
		Context context;
		ProgressDialog progressDialog;
		
		public LoginAsyncTask(Context context, DialogInterface dialog){
			this.dialog = dialog;
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(this.context);
			progressDialog.setIndeterminate(true);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage("Logging in...");
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String response = LoginActivity.this.serviceHandler.makeServiceCall(params[0], 1);
			Log.e("LOGIN RESPONSE", response);
			
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
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
					
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
					
				} else {
					Toast.makeText(getApplicationContext(), "Invalid ID", Toast.LENGTH_LONG).show();
					progressDialog.dismiss();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("JSONException LoginActivity.java", e.getMessage().toString());
			}
		}
		
	}
	
}
