package com.android.ijmc.fragments;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.ijmc.R;

public class JMCProfileFragment extends Fragment{

	Context context;
	View view;
	private int lastTopValueAssigned = 0;
	public JMCProfileFragment() {
		// TODO Auto-generated constructor stub
		this.context = getActivity();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null){
			view = inflater.inflate(R.layout.fragment_jmcprofile, container, false);
			ListView listView = (ListView)view.findViewById(R.id.jmcContent);
			ViewGroup header = (ViewGroup)inflater.inflate(R.layout.fragment_jmcprofile_header, listView, false);
			final ImageView badge = (ImageView)header.findViewById(R.id.sectionBadge);
			listView.addHeaderView(header, null, false);
			List<String> items = new ArrayList<String>();
			for(int i=0;i<100;i++){
				items.add(i+"");
			}
			listView.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, items));
			listView.setOnScrollListener(new OnScrollListener() {
				
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub
					parallaxImage(badge);
				}
				
				@TargetApi(Build.VERSION_CODES.HONEYCOMB)
				private void parallaxImage(View view){
					Rect rect = new Rect();
					view.getLocalVisibleRect(rect);
					if(lastTopValueAssigned != rect.top){
						lastTopValueAssigned = rect.top;
						view.setY((float)(rect.top/2.2));
					}
				}
			});
		}
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		FragmentManager fm = getFragmentManager();
		fm.popBackStack();
	}

}
