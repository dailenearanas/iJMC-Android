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

public class AdministrationFragment extends Fragment{
	
	View view;
	ListView adminListView;
	FacultyListAdapter listAdapter;
	ArrayList<FacultyModel> faculties;
	LayoutInflater inflater;
	FacultyCollectionComparator comparator;
	
	public AdministrationFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.inflater = inflater;
		
		DatabaseHandler handler = new DatabaseHandler(getActivity());
		SQLiteDatabase sqliteDB = handler.getReadableDatabase();
		
		view = inflater.inflate(R.layout.fragment_administration, container, false);
		adminListView = (ListView)view.findViewById(R.id.adminListView);
		
		faculties = Queries.getFacultyListItem(sqliteDB, handler, "administration");
		
		comparator = new FacultyCollectionComparator();
		
		Collections.sort(faculties, comparator);
		
		listAdapter = new FacultyListAdapter(getActivity(), faculties);
		adminListView.setAdapter(listAdapter);
		adminListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> object, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				AlertDialog dialog = builder.create();
				dialog.setTitle("Faculty Information");
				dialog.setIcon(android.R.drawable.ic_dialog_info);
				
				FacultyModel model = faculties.get(position);
				
				View dialogView = Utilities.createViewForFacultyInformation(AdministrationFragment.this.inflater, model, position);
				
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
