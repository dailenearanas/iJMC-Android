package com.android.ijmc.utilities;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ijmc.R;
import com.android.ijmc.config.Config;
import com.android.ijmc.models.FacultyModel;

public class Utilities {
	
	static int CONST_IMAGE_HEIGHT_DST = 480;
	
	static int CONST_IMAGE_THUMB_HEIGHT_DST = 150;

	public Utilities() {
		// TODO Auto-generated constructor stub
	}
	
	public static JSONArray makeJSONArrayFromString(String jsonStr) throws JSONException{
		JSONArray jsonArray = new JSONArray(jsonStr);
		return jsonArray;
	}
	
	/*public static Bitmap createRoundedMaskImage(Context context, String filename){
		
		File checkImage = new File(context.getCacheDir() + "/images/" + filename);
		Bitmap myPic = BitmapFactory.decodeResource(context.getResources(), R.drawable.image_holder);
		if(checkImage.isFile()) {
			Log.e("CHECK IMAGE", "isFile");
			myPic = BitmapFactory.decodeFile(context.getCacheDir() + "/images/" + filename);
		}
		myPic = createScaledBitmapRatio(myPic, CONST_IMAGE_HEIGHT_DST);
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
	
	public static Bitmap createRoundedMaskImageThumb(Context context, String filename){
		
		File checkImage = new File(filename);
		Bitmap myPic = BitmapFactory.decodeResource(context.getResources(), R.drawable.image_holder);
		if(checkImage.isFile()) {
			myPic = BitmapFactory.decodeFile(filename);
		}
		myPic = createScaledBitmapRatio(myPic, CONST_IMAGE_THUMB_HEIGHT_DST);
		Bitmap mask = BitmapFactory.decodeResource(context.getResources(), R.drawable.oval_thumb);
		Bitmap maskMono = Bitmap.createBitmap(mask.getHeight(), mask.getWidth(), Config.ARGB_8888);
		Canvas canvas = new Canvas(maskMono);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		canvas.drawBitmap(myPic, 0, 0, null);
		canvas.drawBitmap(mask, 0, 0, paint);
		paint.setXfermode(null);
		
		return maskMono;
	}*/
	
	public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
		bitmap = createScaledBitmapRatio(bitmap, CONST_IMAGE_THUMB_HEIGHT_DST);
		Bitmap result = null;
		Canvas canvas;
		int color;
		Paint paint;
		Rect rect;
		RectF rectF;
		int roundPx;
		
		try {
			result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
			Bitmap.Config.ARGB_8888);
			canvas = new Canvas(result);
	
			color = 0xff424242;
			paint = new Paint();
			rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			rectF = new RectF(rect);
			roundPx = pixels;
	
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
	
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
		} catch (NullPointerException e) {
			// return bitmap;
		} catch (OutOfMemoryError o){}
			return result;
	}
	
	
	private static Bitmap createScaledBitmapRatio(Bitmap bmp, int desiredSize) {
		Bitmap scaledBitmap;
		
		float valuePercentage = ((float)desiredSize / (float)bmp.getWidth())*100;
		float distanceRatio = (valuePercentage - 100)/100;
		
		float newW = bmp.getWidth() * distanceRatio;
		newW = bmp.getWidth() + newW;
		
		float newH = bmp.getHeight() * distanceRatio;
		newH = bmp.getHeight() + newH;
		
		scaledBitmap = Bitmap.createScaledBitmap(bmp, (int)newW, (int)newH, false);
		
		return scaledBitmap;
	}
	
	public static View createViewForContextMenu(Context context, String flag, String content) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contextView = inflater.inflate(R.layout.context_menu_view, null);
		return contextView;
	}
	
	public static View createViewForFacultyInformation(LayoutInflater inflater, FacultyModel model, int position) {
		
		View view = inflater.inflate(R.layout.faculty_single_item_view, null);
		
		ImageView facultyBadge = (ImageView)view.findViewById(R.id.facultyBadge);
		TextView facultyName = (TextView)view.findViewById(R.id.fcltyName);
		TextView facultyId = (TextView)view.findViewById(R.id.idNum);
		TextView facultyDept = (TextView)view.findViewById(R.id.fcltyDept);
		TextView facultyPosition = (TextView)view.findViewById(R.id.fcltyPosition);
		
		Bitmap bmp = BitmapFactory.decodeFile(Config.EXTERNAL_FOLDER + "/faculty_images/" + model.getFacultyImagePath());
		facultyBadge.setImageBitmap(Utilities.getRoundedRectBitmap(bmp, 256));
		facultyName.setText(model.getFacultyFname() + " " + model.getFacultyMname() + " " + model.getFacultyLname() + ", " + model.getFacultySfx());
		facultyId.setText(model.getFacultyId());
		facultyDept.setText(model.getFacultyDeptId().substring(0, model.getFacultyDeptId().lastIndexOf('-')));
		facultyPosition.setText(model.getFacultyPositionId());
		
		return view;
	}

}
