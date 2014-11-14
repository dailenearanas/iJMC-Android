package com.android.ijmc.fragments;

import java.io.IOException;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.android.ijmc.R;
import com.android.ijmc.adapters.HymnListAdapter;
import com.android.ijmc.config.Config;
import com.android.ijmc.helpers.DatabaseHandler;
import com.android.ijmc.helpers.Queries;
import com.android.ijmc.models.ContentModel;

public class JMCHymnFragment extends Fragment {
	Context context;
	View view;
	ArrayList<ContentModel> jmcHymn;
	MediaPlayer player;
	SeekBar seekBar;
	Handler handler = new Handler();
	ListView listView;
	
	private int lastTopValueAssigned = 0;
	public JMCHymnFragment() {
		// TODO Auto-generated constructor stub
		this.context = getActivity();
		player = new MediaPlayer();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(view == null){
			view = inflater.inflate(R.layout.fragment_common_parallax, container, false);
			listView = (ListView)view.findViewById(R.id.listContent);
			ViewGroup header = (ViewGroup)inflater.inflate(R.layout.fragment_jmcprofile_header, listView, false);
			
			/*
			 * HEADER AREA
			 */
			TextView sectionName = (TextView)header.findViewById(R.id.sectionTitle);
			sectionName.setText("JMC Hymn");
			LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 2f);
			sectionName.setLayoutParams(textParams);
			
			final ImageView sectionBadge = (ImageView)header.findViewById(R.id.sectionBadge);
			sectionBadge.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.jmc_banner));
			
			LinearLayout sectionWrapper = (LinearLayout)header.findViewById(R.id.sectionWrapper);
			View playerView = inflater.inflate(R.layout.viewgroup_musicplayer, null);
			seekBar = (SeekBar)playerView.findViewById(R.id.seekBar1);
			seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					Log.e("STOP - SEEKED TO", seekBar.getProgress()+"");
					try {
						player.reset();
						player.setDataSource(Config.EXTERNAL_FOLDER + "/music_file/" + Config.MUSIC_FILENAME);
						player.setOnPreparedListener(new OnPreparedListener() {
							
							@Override
							public void onPrepared(MediaPlayer mp) {
								// TODO Auto-generated method stub
								mp.seekTo(JMCHymnFragment.this.seekBar.getProgress());
								mp.start();
							}
						});
						player.prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					Log.e("START SEEK", seekBar.getProgress()+"");
					if(player.isPlaying()) {
						player.pause();
					}
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					// TODO Auto-generated method stub
					if(fromUser) {
						player.seekTo(progress);
					}
					Log.e("SEEK PROGRESS", progress+"");
				}
			});
			
			Button playBtn = (Button)playerView.findViewById(R.id.button1);			
			playBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(player.isPlaying()) {
						v.setBackgroundResource(R.drawable.play);
						v.invalidate();
						handler.removeCallbacks(run);
						seekBar.setProgress(0);
						seekBar.invalidate();
						player.stop();
						player.reset();
					} else {
						//125 is point of reference for top allowance pixel at point listview.firstChild.getTop();
						listView.smoothScrollBy(listView.getChildAt(1).getTop()-125, 2000);
						v.setBackgroundResource(R.drawable.stop);
						v.invalidate();
						try {
							final View btnView = v;
							player.setDataSource(Config.EXTERNAL_FOLDER + "/music_file/" + Config.MUSIC_FILENAME);
							player.setOnErrorListener(new OnErrorListener() {
								
								@Override
								public boolean onError(MediaPlayer mp, int what, int extra) {
									// TODO Auto-generated method stub
									return true;
								}
							});
							player.setOnPreparedListener(new OnPreparedListener() {
								
								@Override
								public void onPrepared(MediaPlayer mp) {
									// TODO Auto-generated method stub
									Log.e("MUSIC LENGTH", player.getDuration()+"");
									seekBar.setMax(player.getDuration());
									updateSeekBar();
									mp.start();
								}
							});
							player.setOnCompletionListener(new OnCompletionListener() {
								
								@Override
								public void onCompletion(MediaPlayer mp) {
									// TODO Auto-generated method stub
									btnView.setBackgroundResource(R.drawable.play);
									mp.stop();
									mp.reset();
									handler.removeCallbacks(run);
									seekBar.setProgress(0);
									seekBar.invalidate();
								}
							});
							player.prepare();
							
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			
			sectionWrapper.addView(playerView);
			LinearLayout.LayoutParams playerParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f);
			playerView.setLayoutParams(playerParams);
			sectionWrapper.invalidate();
			/*
			 * END OF HEADER AREA
			 */
			
			listView.addHeaderView(header, null, false);
			
			DatabaseHandler handler = new DatabaseHandler(getActivity());
			SQLiteDatabase sqliteDb = handler.getReadableDatabase();
			
			jmcHymn = Queries.getJmcHymn(sqliteDb, handler);
			HymnListAdapter adapter = new HymnListAdapter(getActivity(), jmcHymn);
			listView.setAdapter(adapter);
			listView.setOnScrollListener(new OnScrollListener() {
				
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub
					parallaxImage(sectionBadge);
				}
				
				@TargetApi(Build.VERSION_CODES.HONEYCOMB)
				private void parallaxImage(View view){
					if(Build.VERSION.SDK_INT > 10) {
					Rect rect = new Rect();
						view.getLocalVisibleRect(rect);
						if(lastTopValueAssigned != rect.top){
							lastTopValueAssigned = rect.top;
							view.setY((float)(rect.top/2.5));
						}
					}
				}
			});
		}
		return view;
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
		player.stop();
	}
	
	Runnable run = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			updateSeekBar();
		}
	};
	
	private void updateSeekBar() {
		if(player != null) {
			seekBar.setProgress(player.getCurrentPosition());
			seekBar.invalidate();
			handler.postDelayed(run, 100);
		} else {
			seekBar.setProgress(0);
		}
	}
}
