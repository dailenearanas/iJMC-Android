package com.android.ijmc.fragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.ijmc.R;
import com.android.ijmc.adapters.SSGListAdapter;
import com.android.ijmc.helpers.DatabaseHandler;
import com.android.ijmc.helpers.Queries;
import com.android.ijmc.models.SSGModel;
import com.android.ijmc.utilities.Utilities;

public class SSGCollegeFragment extends Fragment {

	LayoutInflater inflater;
	ArrayList<SSGModel> ssgCollege;
	DatabaseHandler handler;
	SQLiteDatabase sqliteDb;
	ListView ssgListView;
	SSGListAdapter adapter;
	
	public SSGCollegeFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public static Fragment newInstance() {
		SSGCollegeFragment fragment = new SSGCollegeFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.inflater = inflater;
		View view = this.inflater.inflate(R.layout.fragment_ssg_college, container, false);
		
		ssgListView = (ListView)view.findViewById(R.id.ssgOfficerList);
		
		DatabaseHandler handler = new DatabaseHandler(getActivity());
		SQLiteDatabase sqliteDB = handler.getReadableDatabase();
		
		Log.e("onCreateView", "create");
		ssgCollege = Queries.getSSGListItems(sqliteDB, handler);
		adapter = new SSGListAdapter(getActivity(), ssgCollege);
		
		ssgListView.setAdapter(adapter);
		ssgListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View parent, int position,
					long id) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				AlertDialog dialog = builder.create();
				View view = Utilities.createViewForSSGInformation(SSGCollegeFragment.this.inflater, ssgCollege.get(position));
				dialog.setView(view);
				dialog.show();
			}
		});
		return view;
	}
	
	

}
