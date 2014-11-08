package com.android.ijmc.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.ijmc.R;
import com.android.ijmc.adapters.DepartmentListViewAdapter;
import com.android.ijmc.helpers.DatabaseHandler;
import com.android.ijmc.helpers.Queries;

public class DepartmentsFragment extends Fragment{

	LayoutInflater inflater;
	ListView deptListView;
	DepartmentListViewAdapter deptListAdapter;
	public DepartmentsFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		DatabaseHandler handler = new DatabaseHandler(getActivity());
		SQLiteDatabase sqliteDB = handler.getReadableDatabase();
		
		View view = inflater.inflate(R.layout.fragment_department, container, false);
		deptListView = (ListView)view.findViewById(R.id.deptList);
		deptListAdapter = new DepartmentListViewAdapter(getActivity(), Queries.getDepartment(sqliteDB, handler));
		deptListView.setAdapter(deptListAdapter);
		
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
	
	

}
