package com.android.ijmc.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.ijmc.R;
import com.android.ijmc.adapters.FacultyCollegeListAdapter;
import com.android.ijmc.helpers.DatabaseHandler;
import com.android.ijmc.helpers.Queries;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;

public class CollegeFragment extends Fragment{

	public CollegeFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public static Fragment newInstance() {
		CollegeFragment fragment = new CollegeFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_college, container, false);
		
		DatabaseHandler handler = new DatabaseHandler(getActivity());
		SQLiteDatabase sqliteDb = handler.getReadableDatabase();
		
		StickyListHeadersListView listView = (StickyListHeadersListView)view.findViewById(R.id.collegeFacultyList);
		FacultyCollegeListAdapter adapter = new FacultyCollegeListAdapter(getActivity(), Queries.getFacultyListItem(sqliteDb, handler));
		listView.setAdapter(adapter);
		
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
	
	

}
