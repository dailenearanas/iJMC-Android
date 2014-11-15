package com.android.ijmc.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.ijmc.fragments.SSGCollegeFragment;
import com.android.ijmc.fragments.SSGHighSchoolFragment;

public class SSGFragmentPagerAdapter extends FragmentStatePagerAdapter {

	private static int PAGES = 2;

	public SSGFragmentPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return SSGCollegeFragment.newInstance();
		case 1:
			return SSGHighSchoolFragment.newInstance();
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
		return new String[] { "College SSG", "High School SSG",
				"Grade School SSG" };
	}

}
