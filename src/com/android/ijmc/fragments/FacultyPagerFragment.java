package com.android.ijmc.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.ijmc.R;
import com.android.ijmc.adapters.FacultyFragmentPagerAdapter;

public class FacultyPagerFragment extends Fragment{

	View view;
	ViewPager viewPager;
	FacultyFragmentPagerAdapter viewPagerAdapter;
	
	public FacultyPagerFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null) {
			view = inflater.inflate(R.layout.fragment_faculty_pager, container, false);
			viewPager = (ViewPager)view.findViewById(R.id.facultyPager);
			viewPagerAdapter = new FacultyFragmentPagerAdapter(getFragmentManager());
			viewPager.setAdapter(viewPagerAdapter);
		}
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

}
