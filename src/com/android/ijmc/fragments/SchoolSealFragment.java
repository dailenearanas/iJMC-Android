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
import com.android.ijmc.adapters.SealListAdapter;
import com.android.ijmc.helpers.DatabaseHandler;
import com.android.ijmc.helpers.Queries;

public class SchoolSealFragment extends Fragment{

	ListView sealListView;
	SealListAdapter adapter;
	SQLiteDatabase sqliteDB;
	DatabaseHandler handler;
	
	public SchoolSealFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_school_seal, container, false);
		handler = new DatabaseHandler(getActivity());
		sealListView = (ListView)view.findViewById(R.id.sealList);
		adapter = new SealListAdapter(getActivity(), Queries.getSeals(sqliteDB, handler));
		sealListView.setAdapter(adapter);
		
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

}
