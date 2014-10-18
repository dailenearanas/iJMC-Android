package com.android.ijmc.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.ijmc.R;
import com.android.ijmc.models.ContentModel;

public class JMCVisionMissionGoalListAdapter extends BaseAdapter{
	
	ArrayList<ContentModel> jmcVMGitems;
	Context context;
	LayoutInflater inflater;

	public JMCVisionMissionGoalListAdapter(Context context, ArrayList<ContentModel> jmcVMGitems) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.jmcVMGitems = jmcVMGitems;
		this.inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jmcVMGitems.size();
	}

	@Override
	public ContentModel getItem(int arg0) {
		// TODO Auto-generated method stub
		return jmcVMGitems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.listview_jmcvmg_view, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.header = (TextView)convertView.findViewById(R.id.header);
			viewHolder.contentView = (TextView)convertView.findViewById(R.id.VMGContentText);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.header.setText(jmcVMGitems.get(position).getContentType());
		
		final SpannableString spannableString = new SpannableString(jmcVMGitems.get(position).getContentBody());
		int x = 0;
		for (int i = 0, ei = jmcVMGitems.get(position).getContentBody().length(); i < ei; i++) {
		    char c = jmcVMGitems.get(position).getContentBody().charAt(i);
		    if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
		        x = i;
		        break;
		    }
		}
		spannableString.setSpan(new RelativeSizeSpan(3.0f), x, x + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		viewHolder.contentView.setText(spannableString);
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView header;
		TextView contentView;
	}

}
