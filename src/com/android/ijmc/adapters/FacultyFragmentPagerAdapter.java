package com.android.ijmc.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.ijmc.fragments.FacultyCollegeFragment;
import com.android.ijmc.fragments.FacultyGradeSchoolFragment;
import com.android.ijmc.fragments.FacultyHighSchoolFragment;

public class FacultyFragmentPagerAdapter extends FragmentStatePagerAdapter{

	private static int PAGES = 3;
	
	public FacultyFragmentPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return FacultyCollegeFragment.newInstance();
		case 1:
			return FacultyHighSchoolFragment.newInstance();
		case 2:
			return FacultyGradeSchoolFragment.newInstance();
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return PAGES;
	}
	
	public static String[] getTitles() {
		return new String[]{"College Department","HighSchool Department", "GradeSchool Department"};
	}
	
	
}
