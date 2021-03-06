package com.android.ijmc.adapters;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ijmc.R;
import com.android.ijmc.config.Config;
import com.android.ijmc.models.FacultyModel;
import com.android.ijmc.utilities.Utilities;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;

public class FacultyCollegeListAdapter extends BaseAdapter implements StickyListHeadersAdapter{

	Context context;
	LayoutInflater inflater;
	ArrayList<FacultyModel> faculties;
	private int lastPosition = -1;
	
	public FacultyCollegeListAdapter(Context context, ArrayList<FacultyModel> faculties) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.faculties = faculties;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return faculties.size();
	}

	@Override
	public Object getItem(int position) {
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
				String imageSource = item.getFacultyImagePath();
				File imageFile = new File(Config.EXTERNAL_FOLDER + "/" + Config.EXTERNAL_FOLDER_FACULTY_IMAGE + "/" + imageSource);
				Log.e("IMAGE DIR", imageFile.getAbsolutePath());
				Bitmap bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
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

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HeaderViewHolder holder;
		if(convertView == null) {
			holder = new HeaderViewHolder();
			convertView = inflater.inflate(R.layout.fragment_college_headerview, parent, false);
			holder.headerText = (TextView)convertView.findViewById(R.id.pageLabel);
			holder.headerContainer = (LinearLayout)convertView.findViewById(R.id.headerContainer);
			
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder)convertView.getTag();
		}
		String deptName = faculties.get(position).getFacultyDeptId();
		holder.headerText.setText(deptName.substring(0, deptName.lastIndexOf("-")).toUpperCase(Locale.getDefault()));
		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		// TODO Auto-generated method stub
		String deptName = faculties.get(position).getFacultyDeptId();
		Long id = Long.parseLong(deptName.substring(deptName.lastIndexOf("-")+1,deptName.length())); 
		return id;
	}
	
	static class HeaderViewHolder{
		TextView headerText;
		LinearLayout headerContainer;
	}
	
	static class ViewHolder{
		TextView facultyName;
		TextView facultyPosition;
		ImageView facultyImage;
	}

}
