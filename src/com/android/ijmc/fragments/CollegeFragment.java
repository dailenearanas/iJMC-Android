package com.android.ijmc.fragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ijmc.R;
import com.android.ijmc.adapters.FacultyCollegeListAdapter;
import com.android.ijmc.config.Config;
import com.android.ijmc.helpers.DatabaseHandler;
import com.android.ijmc.helpers.Queries;
import com.android.ijmc.models.FacultyModel;
import com.android.ijmc.utilities.Utilities;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;

public class CollegeFragment extends Fragment{
	
	LayoutInflater inflater;
	ArrayList<FacultyModel> faculties;
	
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
		
		this.inflater = inflater;
		View view = inflater.inflate(R.layout.fragment_college, container, false);
		
		DatabaseHandler handler = new DatabaseHandler(getActivity());
		SQLiteDatabase sqliteDb = handler.getReadableDatabase();
		
		faculties = Queries.getFacultyListItem(sqliteDb, handler, "college");
		
		StickyListHeadersListView listView = (StickyListHeadersListView)view.findViewById(R.id.collegeFacultyList);
		FacultyCollegeListAdapter adapter = new FacultyCollegeListAdapter(getActivity(), faculties);
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
				
				View dialogView = Utilities.createViewForFacultyInformation(CollegeFragment.this.inflater, model, position);
				
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
