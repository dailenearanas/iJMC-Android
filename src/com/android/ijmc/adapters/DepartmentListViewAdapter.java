package com.android.ijmc.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import com.android.ijmc.models.DepartmentModel;

public class DepartmentListViewAdapter extends BaseAdapter{

	ArrayList<DepartmentModel> departments;
	Context context;
	LayoutInflater inflater;
	private int lastPosition = -1;
	
	public DepartmentListViewAdapter(Context context, ArrayList<DepartmentModel> departments) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.departments = departments;
		this.inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.departments.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		final DepartmentModel item = departments.get(position);
		if(convertView == null) {
			holder = new ViewHolder();
			
			convertView = this.inflater.inflate(R.layout.fragment_department_list_item, null);
			holder.deptLogo = (ImageView)convertView.findViewById(R.id.deptLogo);
			holder.deptTitle = (TextView)convertView.findViewById(R.id.deptTitle);
			holder.deptDesc = (TextView)convertView.findViewById(R.id.deptDesc);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		new AsyncTask<ViewHolder, Void, Bitmap>(){
			private ViewHolder v;
			
			@Override
			protected Bitmap doInBackground(ViewHolder... arg0) {
				// TODO Auto-generated method stub
				v = arg0[0];
				Bitmap bmp = BitmapFactory.decodeFile(Config.EXTERNAL_FOLDER + "/dept_image/" + item.getImagePath());
				if(bmp == null) {
					bmp = BitmapFactory.decodeFile(Config.EXTERNAL_FOLDER + "/dept_image/jmc.png");
				}
				return bmp;
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				// TODO Auto-generated method stub
				v.deptLogo.setImageBitmap(result);
				v.deptTitle.setText(item.getDeptTitle());
				v.deptDesc.setText(item.getDeptDesc());
			}
		}.execute(holder);
		
		Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.animator.up_from_bottom : R.animator.down_from_top);
		convertView.startAnimation(animation);
		lastPosition = position;
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView deptLogo;
		TextView deptTitle;
		TextView deptDesc;
	}

}
