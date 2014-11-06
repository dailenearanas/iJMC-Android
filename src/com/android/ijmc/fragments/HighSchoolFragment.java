package com.android.ijmc.fragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.ijmc.R;
import com.android.ijmc.adapters.FacultyHighSchoolListAdapter;
import com.android.ijmc.helpers.DatabaseHandler;
import com.android.ijmc.helpers.Queries;
import com.android.ijmc.models.FacultyModel;
import com.android.ijmc.utilities.Utilities;

public class HighSchoolFragment extends Fragment{
	
	ListView listView;
	FacultyHighSchoolListAdapter adapter;
	ArrayList<FacultyModel> faculties;
	LayoutInflater inflater;
	
	public HighSchoolFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public static Fragment newInstance() {
		HighSchoolFragment fragment = new HighSchoolFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		this.inflater = inflater;
		// TODO Auto-generated method stub
		DatabaseHandler handler = new DatabaseHandler(getActivity());
		SQLiteDatabase sqliteDb = handler.getReadableDatabase();
		View view = inflater.inflate(R.layout.fragment_highschool, container, false);
		faculties = Queries.getFacultyListItem(sqliteDb, handler,"High School");
		
		listView = (ListView)view.findViewById(R.id.facultyHighSchoolList);
		adapter = new FacultyHighSchoolListAdapter(getActivity(), Queries.getFacultyListItem(sqliteDb, handler, "high school"));
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				AlertDialog dialog = builder.create();
				dialog.setTitle("Faculty Information");
				dialog.setIcon(android.R.drawable.ic_dialog_info);
				
				FacultyModel model = faculties.get(position);
				View dialogView = Utilities.createViewForFacultyInformation(HighSchoolFragment.this.inflater, model, position);
				
				dialog.setView(dialogView);
				dialog.show();
			}
		});
		
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

}
