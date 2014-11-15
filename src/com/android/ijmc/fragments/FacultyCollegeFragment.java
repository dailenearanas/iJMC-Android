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

import com.android.ijmc.R;
import com.android.ijmc.adapters.FacultyCollegeListAdapter;
import com.android.ijmc.helpers.DatabaseHandler;
import com.android.ijmc.helpers.FacultyCollectionComparator;
import com.android.ijmc.helpers.Queries;
import com.android.ijmc.models.DepartmentModel;
import com.android.ijmc.models.FacultyModel;
import com.android.ijmc.utilities.Utilities;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;

public class FacultyCollegeFragment extends Fragment{
	
	LayoutInflater inflater;
	ArrayList<FacultyModel> faculties;
	DatabaseHandler handler;
	SQLiteDatabase sqliteDb;
	FacultyCollectionComparator comparator;
	
	public FacultyCollegeFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public static Fragment newInstance() {
		FacultyCollegeFragment fragment = new FacultyCollegeFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		this.inflater = inflater;
		View view = inflater.inflate(R.layout.fragment_faculty_college, container, false);
		
		handler = new DatabaseHandler(getActivity());
		sqliteDb = handler.getReadableDatabase();
		comparator = new FacultyCollectionComparator();
		
		faculties = Queries.getFacultyListItem(sqliteDb, handler, "college");
		
		cleanCollectionSort();
		
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
				
				View dialogView = Utilities.createViewForFacultyInformation(FacultyCollegeFragment.this.inflater, model);
				
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
	
	private void cleanCollectionSort() {
		ArrayList<DepartmentModel> depts;
		ArrayList<FacultyModel> newFacultyList = new ArrayList<FacultyModel>();
		depts = Queries.getDepartment(sqliteDb, handler);
		for(DepartmentModel item : depts) {
			
			ArrayList<FacultyModel> facultyList = new ArrayList<FacultyModel>();
			
			for(int i=0;i<faculties.size();i++) {
				String d = faculties.get(i).getFacultyDeptId();
				if(d.substring(0, d.lastIndexOf('-')).equals(item.deptTitle)) {
					facultyList.add(faculties.get(i));
				}
			}
			
			Collections.sort(facultyList, comparator);
			newFacultyList.addAll(facultyList);
		}
		
		faculties = newFacultyList;
		
	}

}
