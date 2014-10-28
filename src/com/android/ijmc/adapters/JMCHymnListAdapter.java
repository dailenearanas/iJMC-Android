package com.android.ijmc.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.android.ijmc.R;
import com.android.ijmc.adapters.JMCHymnListAdapter.ViewHolder;
import com.android.ijmc.models.ContentModel;

public class JMCHymnListAdapter extends BaseAdapter {
	ArrayList<ContentModel> items;
	Context context;
	LayoutInflater inflater;
	public JMCHymnListAdapter(Context context, ArrayList<ContentModel> items) {
		// TODO Auto-generated constructor stub
		this.items = items;
		this.context = context;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public ContentModel getItem(int arg0) {
		// TODO Auto-generated method stub
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.listview_jmcprofile_view, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView)convertView.findViewById(R.id.profileContentText);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		final SpannableString spannableString = new SpannableString(items.get(position).getContentBody());
		int x = 0;
		for (int i = 0, ei = items.get(position).getContentBody().length(); i < ei; i++) {
		    char c = items.get(position).getContentBody().charAt(i);
		    if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
		        x = i;
		        break;
		    }
		}
		spannableString.setSpan(new RelativeSizeSpan(3.0f), x, x + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		viewHolder.textView.setText(spannableString, BufferType.SPANNABLE);
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView textView;
	}
}
