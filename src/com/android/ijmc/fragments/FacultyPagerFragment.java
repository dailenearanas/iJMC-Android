package com.android.ijmc.fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.ijmc.R;
import com.android.ijmc.adapters.FacultyFragmentPagerAdapter;
import com.android.ijmc.helpers.ZoomOutPageTransformer;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
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
		view = inflater.inflate(R.layout.fragment_faculty_pager, container, false);
		viewPager = (ViewPager)view.findViewById(R.id.facultyPager);
		viewPagerAdapter = new FacultyFragmentPagerAdapter(getFragmentManager());
		viewPager.setPageTransformer(true, new ZoomOutPageTransformer(getActivity()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				updateActionBarTitle(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
		updateActionBarTitle(0);
		viewPager.setAdapter(viewPagerAdapter);
		return view;
	}
	
	private void updateActionBarTitle(int position) {
		if(Build.VERSION.SDK_INT > 11) {
			getActivity().getActionBar().setTitle(Html.fromHtml("<i>" + FacultyFragmentPagerAdapter.getTitles()[position] + "</i>"));
		}
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(Build.VERSION.SDK_INT > 11) {
			getActivity().getActionBar().setTitle("iJMC");
		}
	}

}