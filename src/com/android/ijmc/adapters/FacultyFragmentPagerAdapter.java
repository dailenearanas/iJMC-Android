package com.android.ijmc.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.ijmc.fragments.CollegeFragment;
import com.android.ijmc.fragments.GradeSchoolFragment;
import com.android.ijmc.fragments.HighSchoolFragment;

public class FacultyFragmentPagerAdapter extends FragmentPagerAdapter{

	private static int PAGES = 3;
	
	public FacultyFragmentPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			return new CollegeFragment();
		case 1:
			return new HighSchoolFragment();
		case 2:
			return new GradeSchoolFragment();
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return PAGES;
	}
}
