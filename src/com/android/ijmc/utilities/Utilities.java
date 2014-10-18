package com.android.ijmc.utilities;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.ijmc.R;

public class Utilities {

	public Utilities() {
		// TODO Auto-generated constructor stub
	}
	
	public static JSONArray makeJSONArrayFromString(String jsonStr) throws JSONException{
		JSONArray jsonArray = new JSONArray(jsonStr);
		return jsonArray;
	}
	
	public static Bitmap createRoundedMaskImage(Context context, String filename){
		
		File checkImage = new File(context.getCacheDir() + "/images/" + filename);
		Bitmap myPic = BitmapFactory.decodeResource(context.getResources(), R.drawable.image_holder);
		if(checkImage.isFile()) {
			Log.e("CHECK IMAGE", "isFile");
			myPic = BitmapFactory.decodeFile(context.getCacheDir() + "/images/" + filename);
		}
//		myPic = Bitmap.createScaledBitmap(myPic, 256, 256, false);
		Bitmap mask = BitmapFactory.decodeResource(context.getResources(), R.drawable.oval);
		Bitmap maskMono = Bitmap.createBitmap(mask.getHeight(), mask.getWidth(), Config.ARGB_8888);
		Canvas canvas = new Canvas(maskMono);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		canvas.drawBitmap(myPic, 0, 0, null);
		canvas.drawBitmap(mask, 0, 0, paint);
		paint.setXfermode(null);
		
		return maskMono;
	}
	
	public static View createViewForContextMenu(Context context, String flag, String content) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contextView = inflater.inflate(R.layout.context_menu_view, null);
		return contextView;
	}

}
