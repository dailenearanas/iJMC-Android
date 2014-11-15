package com.android.ijmc.fragments;

import java.util.ArrayList;
import java.util.Collections;

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
import com.android.ijmc.adapters.FacultyListAdapter;
import com.android.ijmc.helpers.DatabaseHandler;
import com.android.ijmc.helpers.FacultyCollectionComparator;
import com.android.ijmc.helpers.Queries;
import com.android.ijmc.models.FacultyModel;
import com.android.ijmc.utilities.Utilities;

public class FacultyGradeSchoolFragment extends Fragment {

	ArrayList<FacultyModel> faculties;
	FacultyListAdapter adapter;
	ListView listView;
	LayoutInflater inflater;
	FacultyCollectionComparator comparator;
	
	public FacultyGradeSchoolFragment() {
		
	}

	public static Fragment newInstance() {
		FacultyGradeSchoolFragment fragment = new FacultyGradeSchoolFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		this.inflater = inflater;
		DatabaseHandler handler = new DatabaseHandler(getActivity());
		SQLiteDatabase sqliteDb = handler.getReadableDatabase();
		faculties = Queries.getFacultyListItem(sqliteDb, handler,"Grade School");
		
		comparator = new FacultyCollectionComparator();
		
		Collections.sort(faculties, comparator);
		
		View view = inflater.inflate(R.layout.fragment_faculty_gradeschool, container, false);
		
		listView = (ListView) view.findViewById(R.id.facultyGradeSchoolList);
		adapter = new FacultyListAdapter(getActivity(), faculties);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				//request for AlertDialog for item viewing.
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				AlertDialog dialog = builder.create();
				dialog.setTitle("Faculty Information");
				dialog.setIcon(android.R.drawable.ic_dialog_info);
				
				FacultyModel model = faculties.get(position);
				View dialogView = Utilities.createViewForFacultyInformation(FacultyGradeSchoolFragment.this.inflater, model);
				
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
