package com.android.ijmc.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.android.ijmc.R;

public class MainMenuFragment extends Fragment{

	View view;
	String[] menuTitles;
	FragmentTransaction transaction;
	public MainMenuFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.menuTitles = getResources().getStringArray(R.array.main_menu_titles);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null){
			view = inflater.inflate(R.layout.fragment_main_menu, container, false);
			LinearLayout baseLayout = (LinearLayout)view.findViewById(R.id.mainMenuLayout);
			int menuTitlesndx = 0;
			
			for(int i=0;i<4;i++){
				LinearLayout rowLayout = new LinearLayout(this.getActivity());
				LayoutParams rowParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
				rowLayout.setLayoutParams(rowParams);
				for(int j=0;j<2;j++){
					View subView = inflater.inflate(R.layout.main_menu_single_item, baseLayout, false);
					LayoutParams subLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
					subView.setLayoutParams(subLayoutParams);
					ImageView imageView = (ImageView)subView.findViewById(R.id.menuBadge);
					switch(menuTitlesndx){
					case 0:
						imageView.setImageResource(R.drawable.jmcprof);
						subView.setBackgroundColor(Color.parseColor("#ff95bb"));
						imageView.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								JMCProfileFragment jmcProfile = new JMCProfileFragment();
								transaction = getFragmentManager().beginTransaction();
								transaction.replace(R.id.baseMainLayout, jmcProfile);
								transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
								transaction.addToBackStack(null);
								transaction.commit();
							}
						});
						break;
					case 1:
						imageView.setImageResource(R.drawable.vmg);
						subView.setBackgroundColor(Color.parseColor("#fc87b1"));
						imageView.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								JMCVisionMissionGoalFragment jmcVMG = new JMCVisionMissionGoalFragment();
								transaction = getFragmentManager().beginTransaction();
								transaction.replace(R.id.baseMainLayout, jmcVMG);
								transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
								transaction.addToBackStack(null);
								transaction.commit();
							}
						});
						break;
					case 2:
						imageView.setImageResource(R.drawable.admin);
						subView.setBackgroundColor(Color.parseColor("#ff6fa3"));
						imageView.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AdministrationFragment jmcSchoolSealFragment = new AdministrationFragment();
								transaction = getFragmentManager().beginTransaction();
								transaction.replace(R.id.baseMainLayout, jmcSchoolSealFragment);
								transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
								transaction.addToBackStack(null);
								transaction.commit();
							}
						});
						break;
					case 3:
						imageView.setImageResource(R.drawable.faculty);
						subView.setBackgroundColor(Color.parseColor("#fd659c"));
						imageView.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								FacultyPagerFragment jmcFaculty = new FacultyPagerFragment();
								transaction = getFragmentManager().beginTransaction();
								transaction.replace(R.id.baseMainLayout, jmcFaculty);
								transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
								transaction.addToBackStack(null);
								transaction.commit();
							}
						});
						break;
					case 4:
						imageView.setImageResource(R.drawable.ssg);
						subView.setBackgroundColor(Color.parseColor("#ff4e8e"));
						imageView.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								SSGPagerFragment jmcSSG = new SSGPagerFragment();
								transaction = getFragmentManager().beginTransaction();
								transaction.replace(R.id.baseMainLayout, jmcSSG);
								transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
								transaction.addToBackStack(null);
								transaction.commit();
							}
						});
						break;
					case 5:
						imageView.setImageResource(R.drawable.hymn);
						subView.setBackgroundColor(Color.parseColor("#fe4386"));
						imageView.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								JMCHymnFragment jmcHymnFragment = new JMCHymnFragment();
								transaction = getFragmentManager().beginTransaction();
								transaction.replace(R.id.baseMainLayout, jmcHymnFragment);
								transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
								transaction.addToBackStack(null);
								transaction.commit();
							}
						});
						break;
					case 6:
						imageView.setImageResource(R.drawable.dept);
						subView.setBackgroundColor(Color.parseColor("#fe3d82"));
						imageView.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								DepartmentsFragment jmcDepartmentsFragment = new DepartmentsFragment();
								transaction = getFragmentManager().beginTransaction();
								transaction.replace(R.id.baseMainLayout, jmcDepartmentsFragment);
								transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
								transaction.addToBackStack(null);
								transaction.commit();
							}
						});
						break;
					case 7:
						imageView.setImageResource(R.drawable.seal);
						subView.setBackgroundColor(Color.parseColor("#fe327b"));
						imageView.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								SchoolSealFragment jmcSchoolSealFragment = new SchoolSealFragment();
								transaction = getFragmentManager().beginTransaction();
								transaction.replace(R.id.baseMainLayout, jmcSchoolSealFragment);
								transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
								transaction.addToBackStack(null);
								transaction.commit();
							}
						});
						break;
					}
					((TextView)subView.findViewById(R.id.menuTitle)).setText(menuTitles[menuTitlesndx]);
					rowLayout.addView(subView);
					menuTitlesndx++;
				}
				baseLayout.addView(rowLayout);
			}
		}
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}
	
	

}
