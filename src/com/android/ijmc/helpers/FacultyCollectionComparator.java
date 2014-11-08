package com.android.ijmc.helpers;

import java.util.Comparator;

import com.android.ijmc.models.FacultyModel;

/**
 * 
 * <p>Custom Comparator for FacultyModel sorting. Using Comparator to compare specific object fields.</p>
 * 
 * @author omiplekevin
 *
 */
public class FacultyCollectionComparator implements Comparator<FacultyModel>{

	private static boolean ORDER = false;
	
	public FacultyCollectionComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(FacultyModel lhd, FacultyModel rhd) {
		// TODO Auto-generated method stub
		return lhd.getFacultyLname().compareTo(rhd.getFacultyLname());
	}

}
