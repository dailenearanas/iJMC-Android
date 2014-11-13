package com.android.ijmc.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.ijmc.R;
import com.android.ijmc.adapters.FacultyListAdapter;
import com.android.ijmc.helpers.DatabaseHandler;
import com.android.ijmc.helpers.Queries;

public class AdministrationFragment extends Fragment{
	
	View view;
	ListView adminListView;
	FacultyListAdapter listAdapter;
	
	public AdministrationFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		DatabaseHandler handler = new DatabaseHandler(getActivity());
		SQLiteDatabase sqliteDB = handler.getReadableDatabase();
		
		view = inflater.inflate(R.layout.fragment_administration, container, false);
		adminListView = (ListView)view.findViewById(R.id.adminListView);
		
		listAdapter = new FacultyListAdapter(getActivity(), Queries.getFacultyListItem(sqliteDB, handler, "administration"));
		adminListView.setAdapter(listAdapter);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
	
	

}
