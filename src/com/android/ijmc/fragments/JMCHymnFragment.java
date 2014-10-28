package com.android.ijmc.fragments;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.android.ijmc.R;
import com.android.ijmc.adapters.JMCHymnListAdapter;
import com.android.ijmc.helpers.DatabaseHandler;
import com.android.ijmc.helpers.Queries;
import com.android.ijmc.models.ContentModel;

public class JMCHymnFragment extends Fragment {
	Context context;
	View view;
	ArrayList<ContentModel> jmcHymn;
	private int lastTopValueAssigned = 0;
	public JMCHymnFragment() {
		// TODO Auto-generated constructor stub
		this.context = getActivity();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null){
			view = inflater.inflate(R.layout.fragment_common_parallax, container, false);
			ListView listView = (ListView)view.findViewById(R.id.listContent);
			ViewGroup header = (ViewGroup)inflater.inflate(R.layout.fragment_jmcprofile_header, listView, false);
			
			TextView sectionName = (TextView)header.findViewById(R.id.sectionTitle);
			sectionName.setText("JMC Hymn");
			
			final ImageView sectionBadge = (ImageView)header.findViewById(R.id.sectionBadge);
			sectionBadge.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.jmc_banner));
			
			listView.addHeaderView(header, null, false);
			
			DatabaseHandler handler = new DatabaseHandler(getActivity());
			SQLiteDatabase sqliteDb = handler.getReadableDatabase();
			
			jmcHymn = Queries.getJmcHymn(sqliteDb, handler);
			JMCHymnListAdapter adapter = new JMCHymnListAdapter(getActivity(), jmcHymn);
			listView.setAdapter(adapter);
			listView.setOnScrollListener(new OnScrollListener() {
				
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub
					parallaxImage(sectionBadge);
				}
				
				@TargetApi(Build.VERSION_CODES.HONEYCOMB)
				private void parallaxImage(View view){
					if(Build.VERSION.SDK_INT > 10) {
					Rect rect = new Rect();
						view.getLocalVisibleRect(rect);
						if(lastTopValueAssigned != rect.top){
							lastTopValueAssigned = rect.top;
							view.setY((float)(rect.top/2.5));
						}
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
	}
}
