package com.android.ijmc.fragments;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

import com.android.ijmc.LoginActivity;
import com.android.ijmc.R;
import com.android.ijmc.adapters.NavigationDrawerMenuListAdapter;
import com.android.ijmc.config.Config;
import com.android.ijmc.helpers.DatabaseHandler;
import com.android.ijmc.helpers.Queries;
import com.android.ijmc.utilities.Utilities;

/**
 * Fragment used for managing interactions for and presentation of a navigation
 * drawer. See the <a href=
 * "https://developer.android.com/design/patterns/navigation-drawer.html#Interaction"
 * > design guidelines</a> for a complete explanation of the behaviors
 * implemented here.
 */
public class NavigationDrawerFragment extends Fragment implements OnClickListener{

	/**
	 * Remember the position of the selected item.
	 */
	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

	/**
	 * Per the design guidelines, you should show the drawer on launch until the
	 * user manually expands it. This shared preference tracks this.
	 */
	private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

	/**
	 * A pointer to the current callbacks instance (the Activity).
	 */
	private NavigationDrawerCallbacks mCallbacks;

	/**
	 * Helper component that ties the action bar to the navigation drawer.
	 */
	private ActionBarDrawerToggle mDrawerToggle;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerListView;
	private View mFragmentContainerView;

	private int mCurrentSelectedPosition = 0;
	private boolean mFromSavedInstanceState;
	private boolean mUserLearnedDrawer;
	private String[] menus;

	private SharedPreferences sp;

	public NavigationDrawerFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Read in the flag indicating whether or not the user has demonstrated
		// awareness of the
		// drawer. See PREF_USER_LEARNED_DRAWER for details.
		SharedPreferences spLocal = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		mUserLearnedDrawer = spLocal
				.getBoolean(PREF_USER_LEARNED_DRAWER, false);

		if (savedInstanceState != null) {
			mCurrentSelectedPosition = savedInstanceState
					.getInt(STATE_SELECTED_POSITION);
			mFromSavedInstanceState = true;
		}

		// Select either the default item (0) or the last selected item.
		selectItem(mCurrentSelectedPosition);

		sp = getActivity().getSharedPreferences(Config.SHA_NAME,
				Activity.MODE_PRIVATE);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Indicate that this fragment would like to influence the set of
		// actions in the action bar.
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.menus = getResources().getStringArray(R.array.navigation_items);
		View view = inflater.inflate(R.layout.fragment_navigation_drawer,
				container, false);

		// TODO START set user profile summary for drawer
		TextView userName = (TextView) view.findViewById(R.id.userName);
		TextView userIDNumber = (TextView) view.findViewById(R.id.userTitle);
		Button logout = (Button) view.findViewById(R.id.button1);
		String fname = sp.getString(Config.SHA_USR_FNAME, "--");
		String mname = sp.getString(Config.SHA_USR_MNAME, "--");
		String lname = sp.getString(Config.SHA_USR_LNAME, "--");
		String suffix = sp.getString(Config.SHA_USR_SUFFIX, "");
		userName.setText(fname + " " + mname + " "  + lname);
		if(!suffix.equals("")) {
			userName.setText(userName.getText() + " " + suffix);
		}
		userIDNumber.setText(sp.getString(Config.SHA_USR_ID,""));

		File image = new File(getActivity().getCacheDir()+"/images/"+sp.getString(Config.SHA_USR_IMAGE_FILE, ""));
		if(!image.exists()){
			ProfileImageGrabber grabber = new ProfileImageGrabber(getActivity());
			grabber.execute(sp.getString(Config.SHA_USR_IMAGE_FILE, ""));
		} else {
			ImageView imageHolder = (ImageView)view.findViewById(R.id.profileImageHolder);
//			imageHolder.setScaleType(ScaleType.FIT_CENTER);
			Bitmap bmp = BitmapFactory.decodeFile(getActivity().getCacheDir() + "/images/" + sp.getString(Config.SHA_USR_IMAGE_FILE, ""));
			imageHolder.setImageBitmap(Utilities.getRoundedRectBitmap(bmp, 100));
			imageHolder.invalidate();
		}
		
		// TODO END set user profile summary for drawer
		NavigationDrawerMenuListAdapter navAdapter = new NavigationDrawerMenuListAdapter(
				getActivity(), this.menus);

		mDrawerListView = (ListView) view.findViewById(R.id.navListView);
		mDrawerListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						selectItem(position);
					}
				});
		mDrawerListView.setAdapter(navAdapter);
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);

		logout.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.button1:
			SharedPreferences.Editor spEditor = sp.edit();
			spEditor.clear();
			spEditor.commit();
			
			SharedPreferences spLocal = PreferenceManager.getDefaultSharedPreferences(getActivity());
			SharedPreferences.Editor editor = spLocal.edit();
			editor.putBoolean(PREF_USER_LEARNED_DRAWER, false);
			editor.commit();
			
			File imageCache = new File(getActivity().getCacheDir() + "/images");
			imageCache.delete();
			
			File externalFiles = new File(Config.EXTERNAL_FOLDER);
			Utilities.recursiveDelete(externalFiles);
			
			DatabaseHandler handler = new DatabaseHandler(getActivity());
			SQLiteDatabase sqliteDb = null;
			Queries.TruncateTables(sqliteDb, handler);
			
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
		break;
		}
	}

	public boolean isDrawerOpen() {
		return mDrawerLayout != null
				&& mDrawerLayout.isDrawerOpen(mFragmentContainerView);
	}

	/**
	 * Users of this fragment must call this method to set up the navigation
	 * drawer interactions.
	 * 
	 * @param fragmentId
	 *            The android:id of this fragment in its activity's layout.
	 * @param drawerLayout
	 *            The DrawerLayout containing this fragment's UI.
	 */
	public void setUp(int fragmentId, DrawerLayout drawerLayout) {
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the navigation drawer and the action bar app icon.
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.navigation_drawer_open, /*
										 * "open drawer" description for
										 * accessibility
										 */
		R.string.navigation_drawer_close /*
										 * "close drawer" description for
										 * accessibility
										 */
		) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (!isAdded()) {
					return;
				}

				getActivity().supportInvalidateOptionsMenu(); // calls
																// onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if (!isAdded()) {
					return;
				}

				if (!mUserLearnedDrawer) {
					// The user manually opened the drawer; store this flag to
					// prevent auto-showing
					// the navigation drawer automatically in the future.
					
					mUserLearnedDrawer = true;
					SharedPreferences sp = PreferenceManager
							.getDefaultSharedPreferences(getActivity());
					sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true)
							.apply();
				}

				getActivity().supportInvalidateOptionsMenu(); // calls
																// onPrepareOptionsMenu()
			}
		};

		// If the user hasn't 'learned' about the drawer, open it to introduce
		// them to the drawer,
		// per the navigation drawer design guidelines.
		if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
			mDrawerLayout.openDrawer(mFragmentContainerView);
		}

		// Defer code dependent on restoration of previous instance state.
		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	private void selectItem(int position) {
		mCurrentSelectedPosition = position;
		if (mDrawerListView != null) {
			mDrawerListView.setItemChecked(position, true);
		}
		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		if (mCallbacks != null) {
			mCallbacks.onNavigationDrawerItemSelected(position);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activity must implement NavigationDrawerCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Forward the new configuration the drawer toggle component.
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// If the drawer is open, show the global app actions in the action bar.
		// See also
		// showGlobalContextActionBar, which controls the top-left area of the
		// action bar.
		if (mDrawerLayout != null && isDrawerOpen()) {
			inflater.inflate(R.menu.global, menu);
			showGlobalContextActionBar();
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		/*
		 * if (item.getItemId() == R.id.action_example) {
		 * Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT)
		 * .show(); return true; }
		 */

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Per the navigation drawer design guidelines, updates the action bar to
	 * show the global app 'context', rather than just what's in the current
	 * screen.
	 */
	private void showGlobalContextActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setTitle(R.string.app_name);
	}

	private ActionBar getActionBar() {
		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	}

	/**
	 * Callbacks interface that all activities using this fragment must
	 * implement.
	 */
	public static interface NavigationDrawerCallbacks {
		/**
		 * Called when an item in the navigation drawer is selected.
		 */
		void onNavigationDrawerItemSelected(int position);
	}

	class ProfileImageGrabber extends AsyncTask<String, Void, Void> {
		
		Context context;
		String imageName;
		
		public ProfileImageGrabber(Context context)
		{
			this.context = context;
		}

		@Override
		protected Void doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			int count;
			try {
				imageName = arg0[0];
				arg0[0] = arg0[0].replace(" ", "%20");
				
				URL url = null;
				if(sp.getString(Config.SHA_USR_TYPE, "").equals("Student")) {
					url = new URL(Config.IMAGE_STUDENT_BASE_URL + "/" + arg0[0]);
					Log.e("URL PATH", Config.IMAGE_STUDENT_BASE_URL + "/" + arg0[0]);
				} else {
					url = new URL(Config.IMAGE_FACULTY_BASE_URL + "/thumb/" + arg0[0]);
					Log.e("URL PATH", Config.IMAGE_FACULTY_BASE_URL + "/thumb/" + arg0[0]);
				}
				URLConnection connection = url.openConnection();
				connection.connect();

				InputStream in = new BufferedInputStream(url.openStream());

				File imageCache = new File(getActivity().getCacheDir()
						+ "/images");
				imageCache.mkdir();

				OutputStream out = new FileOutputStream(getActivity()
						.getCacheDir() + "/images/" + imageName);
				
				Log.e("IMAGE CACHE PATH", getActivity()
						.getCacheDir() + "/images/" + imageName);

				byte data[] = new byte[2048];
				while ((count = in.read(data)) != -1) {
					out.write(data, 0, count);
				}

				out.flush();
				out.close();
				in.close();

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Bitmap bmp = BitmapFactory.decodeFile(getActivity().getCacheDir() + "/images/" + imageName);
			TransitionDrawable td = new TransitionDrawable(new Drawable[] {
				new ColorDrawable(Color.TRANSPARENT),
						new BitmapDrawable(Utilities.getRoundedRectBitmap(bmp, 90))	
			});
			ImageView imageView = (ImageView)((Activity)this.context).findViewById(R.id.profileImageHolder);
			imageView.setImageDrawable(td);
//			imageView.setScaleType(ScaleType.CENTER_INSIDE);
			td.startTransition(3000);
		}
	}
}
