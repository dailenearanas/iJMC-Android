package com.android.ijmc;

import java.io.File;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.android.ijmc.LoginActivity.ResponseReceiver;
import com.android.ijmc.config.Config;
import com.android.ijmc.helpers.DatabaseHandler;
import com.android.ijmc.helpers.Queries;
import com.android.ijmc.services.ContentGrabberService;
import com.android.ijmc.utilities.Utilities;

public class UpdateActivity extends Activity{

	UpdateContent responseReceiver;
	
	public UpdateActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		
		IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESPONSE);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		responseReceiver = new UpdateContent();
		registerReceiver(responseReceiver, filter);
		
		DatabaseHandler handler = new DatabaseHandler(this);
		SQLiteDatabase sqliteDB = handler.getReadableDatabase();
		
		Utilities.recursiveDelete(new File(Config.EXTERNAL_FOLDER));
		
		Queries.TruncateTables(sqliteDB, handler);
		
		Intent intent = new Intent(UpdateActivity.this, ContentGrabberService.class);
		intent.putExtra(ContentGrabberService.PARAM_SRC, Config.getRequests());
		startService(intent);
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//this will disable back press...
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(responseReceiver);
	}



	public class UpdateContent extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.e("UPDATE CONTENT", "onReceive");
			if(intent.getAction().equals(LoginActivity.ResponseReceiver.ACTION_RESPONSE)) {
				Intent backToMain = new Intent(UpdateActivity.this, MainActivity.class);
				startActivity(backToMain);
				finish();
			}
		}
		
	}

}
