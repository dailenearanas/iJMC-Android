package com.android.ijmc.utilities;

import org.json.JSONArray;
import org.json.JSONException;

public class Utilities {

	public Utilities() {
		// TODO Auto-generated constructor stub
	}
	
	public static JSONArray makeJSONArrayFromString(String jsonStr) throws JSONException{
		JSONArray jsonArray = new JSONArray(jsonStr);
		return jsonArray;
	}

}
