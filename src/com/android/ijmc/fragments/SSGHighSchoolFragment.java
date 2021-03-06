package com.android.ijmc.fragments;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.ijmc.R;
import com.android.ijmc.helpers.DatabaseHandler;
import com.android.ijmc.models.SSGModel;

public class SSGHighSchoolFragment extends Fragment {
	
	LayoutInflater inflater;
	ArrayList<SSGModel> ssgHighSchool;
	DatabaseHandler handler;
	SQLiteDatabase sqliteDb;
	
	public SSGHighSchoolFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public static Fragment newInstance() {
		SSGHighSchoolFragment fragment = new SSGHighSchoolFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.inflater = inflater;
		View view = this.inflater.inflate(R.layout.fragment_ssg_highschool, container, false);
		return view;
	}

}
