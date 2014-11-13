package com.android.ijmc.adapters;

import java.io.File;
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
import com.android.ijmc.models.SealsModel;

public class SealListAdapter extends BaseAdapter{
	ArrayList<SealsModel> items;
	Context context;
	LayoutInflater inflater;
	private int lastPosition = -1;
	
	public SealListAdapter(Context context, ArrayList<SealsModel> items) {
		this.items = items;
		this.context = context;
		this.inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.items.size();
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
		final int pos = position;
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = this.inflater.inflate(R.layout.fragment_department_list_item, null);
			holder.sealImage = (ImageView)convertView.findViewById(R.id.deptLogo);
			holder.sealName = (TextView)convertView.findViewById(R.id.deptTitle);
			holder.sealDescription = (TextView)convertView.findViewById(R.id.deptDesc);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		new AsyncTask<ViewHolder, Void, Bitmap>(){
			ViewHolder v;
			@Override
			protected Bitmap doInBackground(ViewHolder... params) {
				// TODO Auto-generated method stub
				v = params[0];
				File imageName = new File(Config.EXTERNAL_FOLDER + "/" + Config.EXTERNAL_FOLDER_SEAL_IMAGE + "/" + items.get(pos).getSealImg());
				Bitmap bmp = BitmapFactory.decodeFile(imageName.getAbsolutePath());
				return bmp;
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				// TODO Auto-generated method stub
				v.sealImage.setImageBitmap(result);
				v.sealName.setText(items.get(pos).getSealName());
				v.sealDescription.setText(items.get(pos).getSealDesc());
			}
		}.execute(holder);
		
		Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.animator.up_from_bottom : R.animator.down_from_top);
		convertView.startAnimation(animation);
		lastPosition = position;
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView sealImage;
		TextView sealName;
		TextView sealDescription;
	}
}
