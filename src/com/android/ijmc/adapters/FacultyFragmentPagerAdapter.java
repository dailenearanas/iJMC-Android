package com.android.ijmc.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.ijmc.fragments.CollegeFragment;
import com.android.ijmc.fragments.GradeSchoolFragment;
import com.android.ijmc.fragments.HighSchoolFragment;

public class FacultyFragmentPagerAdapter extends FragmentStatePagerAdapter{

	private static int PAGES = 3;
	
	public FacultyFragmentPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			return CollegeFragment.newInstance();
		case 1:
			return HighSchoolFragment.newInstance();
		case 2:
			return GradeSchoolFragment.newInstance();
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return PAGES;
	}
	
	public String[] getTitles() {
		return new String[]{"College Department","HighSchool Department", "GradeSchool Department"};
	}
	
	
}
