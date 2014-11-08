package com.android.ijmc.fragments;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.android.ijmc.R;
import com.android.ijmc.adapters.SSGFragmentPagerAdapter;
import com.android.ijmc.helpers.ZoomOutPageTransformer;

@SuppressLint("NewApi")
public class SSGPagerFragment extends Fragment{

	View view;
	SSGFragmentPagerAdapter viewPagerAdapater;
	ViewPager viewPager;
	String[] titles;
	
	public SSGPagerFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view  = inflater.inflate(R.layout.fragment_ssg_pager, container, false);
		viewPagerAdapater = new SSGFragmentPagerAdapter(getFragmentManager());
		viewPager = (ViewPager)view.findViewById(R.id.ssgPager);
		viewPager.setAdapter(viewPagerAdapater);
		viewPager.setPageTransformer(false, new ZoomOutPageTransformer(getActivity()));
		titles = viewPagerAdapater.getTitles();
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				updateActionBarTitle(position);
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
		Toast.makeText(getActivity(), "[REMOVE ME]  I NEED DATA!", Toast.LENGTH_LONG).show();
		return view;
	}
	
	private void updateActionBarTitle(int position) {
		if(Build.VERSION.SDK_INT > 11) {
			getActivity().getActionBar().setTitle(Html.fromHtml("<i>" + SSGFragmentPagerAdapter.getTitles()[position] + "</i>"));
		}
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
