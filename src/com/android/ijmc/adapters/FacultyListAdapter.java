package com.android.ijmc.adapters;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ijmc.R;
import com.android.ijmc.config.Config;
import com.android.ijmc.models.FacultyModel;
import com.android.ijmc.utilities.Utilities;

public class FacultyListAdapter extends BaseAdapter{
	
	Context context;
	LayoutInflater inflater;
	ArrayList<FacultyModel> faculties;
	private int lastPosition = -1;

	public FacultyListAdapter(Context context, ArrayList<FacultyModel> faculties) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.faculties = faculties;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return faculties.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		final FacultyModel item = faculties.get(position);
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.fragment_faculty_listview_item, parent, false);
			holder.facultyImage = (ImageView)convertView.findViewById(R.id.facultyThumb);
			holder.facultyName = (TextView)convertView.findViewById(R.id.facultyName);
			holder.facultyPosition = (TextView)convertView.findViewById(R.id.facultyPosition);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		new AsyncTask<ViewHolder, Void, Bitmap>(){
			private ViewHolder v;
			@Override
			protected Bitmap doInBackground(ViewHolder... params) {
				// TODO Auto-generated method stub
				v = params[0];
				File imageName = new File(Config.EXTERNAL_FOLDER + "/" + Config.EXTERNAL_FOLDER_FACULTY_IMAGE + "/" + item.getFacultyImagePath());
				Log.e("IMAGE DIR", imageName.getAbsolutePath());
				Bitmap bmp = BitmapFactory.decodeFile(imageName.getAbsolutePath());
				if(bmp == null) {
					bmp = BitmapFactory.decodeFile(Config.EXTERNAL_FOLDER + "/" + Config.EXTERNAL_FOLDER_FACULTY_IMAGE + "/user.png");
				}
				return Utilities.getRoundedRectBitmap(bmp, 100);
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				String name = item.getFacultyLname() + ", " + item.getFacultyFname() + " " + item.getFacultyMname();
				v.facultyName.setText(name);
				v.facultyPosition.setText(item.getFacultyPositionId());
				v.facultyImage.setImageBitmap(result);
			}
			
		}.execute(holder);
		
		Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.animator.up_from_bottom : R.animator.down_from_top);
		convertView.startAnimation(animation);
		lastPosition = position;
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView facultyName;
		TextView facultyPosition;
		ImageView facultyImage;
	}

}