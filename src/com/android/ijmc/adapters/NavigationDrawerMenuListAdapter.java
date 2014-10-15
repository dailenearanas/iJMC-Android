package com.android.ijmc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.ijmc.R;

public class NavigationDrawerMenuListAdapter extends BaseAdapter{

	String[] menus;
	LayoutInflater inflater;
	Context context;
	public NavigationDrawerMenuListAdapter(Context context, String[] menus) {
		// TODO Auto-generated constructor stub
		this.menus = menus;
		this.context = context;
		this.inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.menus.length;
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
		if(convertView == null){
			holder = new ViewHolder();
			
			convertView = this.inflater.inflate(R.layout.nav_menu_list_item, null);
			
			TextView menuItem = (TextView)convertView.findViewById(R.id.menuItem);
			holder.menuItem = menuItem;
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.menuItem.setText(this.menus[position]);
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView menuItem;
	}

}
