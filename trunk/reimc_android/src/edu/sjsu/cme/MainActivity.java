package edu.sjsu.cme;

import edu.sjsu.cme.mobileclient.MobileLogger;
import edu.sjsu.cme.receivers.BatteryChangeReceiver;
import gash.indexing.Document;
import gash.indexing.inverted.Registry;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

/**
 * Main entry for the Android application
 * 
 * @author michael
 * 
 */
public class MainActivity extends Activity implements
		SearchView.OnQueryTextListener {
	private static final String TAG = "MainActivity"; // for debug

	private SearchView mSearchView;
	private TextView mStatusView;
	private ListView mResultsListView;
	private ResultsListViewAdapter mResultsListAdapter; 
	
	Registry data;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Logger.log(TAG, "onCreate");

		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		setContentView(R.layout.activity_main);

		mStatusView = (TextView) findViewById(R.id.status_text);

		mResultsListView = (ListView) findViewById(R.id.results_list_view);

		mResultsListAdapter = new ResultsListViewAdapter(getApplicationContext());
		mResultsListView.setAdapter(mResultsListAdapter);
		
//		findViewById(R.id.alarm_service_btn).setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(getApplicationContext(),LocalServiceActivities.Binding.class);
//				startActivity(intent);
//				
//			}
//			
//		});
		
		findViewById(R.id.real_time_graph_btn).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),GraphActivity.class);
				startActivity(intent);
			}
			
		});
		
//		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//		getApplicationContext().registerReceiver(new BatteryChangeReceiver(), ifilter);
		MobileLogger.log(null, null, null);
		registerLoad();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		mSearchView = (SearchView) searchItem.getActionView();
		setupSearchView(searchItem);

		return true;
	}

	private void setupSearchView(MenuItem searchItem) {

		if (isAlwaysExpanded()) {
			mSearchView.setIconifiedByDefault(false);
		} else {
			searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
					| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}

		// MH: don't use Android search... but its somewhat cool
		// SearchManager searchManager = (SearchManager)
		// getSystemService(Context.SEARCH_SERVICE);
		// if (searchManager != null) {
		// List<SearchableInfo> searchables =
		// searchManager.getSearchablesInGlobalSearch();
		//
		// // Try to use the "applications" global search provider
		// SearchableInfo info =
		// searchManager.getSearchableInfo(getComponentName());
		// for (SearchableInfo inf : searchables) {
		// if (inf.getSuggestAuthority() != null
		// && inf.getSuggestAuthority().startsWith("applications")) {
		// info = inf;
		// }
		// }
		// mSearchView.setSearchableInfo(info);
		// }

		mSearchView.setOnQueryTextListener(this);
	}

	public boolean onQueryTextChange(String newText) {
		mStatusView.setText("Query = " + newText);
		return false;
	}

	public boolean onQueryTextSubmit(String query) {
		mStatusView.setText("Query = " + query + " : submitted");
		
		mResultsListAdapter.updateData(searchWithQuery(query)); // update the results
		mResultsListAdapter.notifyDataSetChanged(); // inform list of changes
		
		return false;
	}
	
	private void registerLoad(){
		Properties conf = new Properties();
		conf.setProperty(Registry.sStopWords, "resources/stopwords-long.txt");
		conf.setProperty(Registry.sDataStorage, "TBD");
		conf.setProperty(Registry.sIndexStorage, "TBD");
		
		data = new Registry(conf,getApplicationContext());
//        getAssets().open("resources/stopwords-long.txt");
		// load test data
		//File dir = new File(URI.create("file:///android_asset/testdata/lyrics"));
		data.register("testdata/lyrics");
	}
	
	public List<Document> searchWithQuery(String query) {
		List<String> words = new ArrayList<String>();
		words.add(query);
		//words.add("franciso");

		long st = System.nanoTime();
		List<Document> found = data.query(words);
		long et = System.nanoTime();

		return found;
		//System.out.println("---> found " + found.size() + " documents in " + (et - st) + " ns");
		//for (Document d : found)
			//System.out.println(d + "\n");
	}

	public boolean onClose() {
		mStatusView.setText("Closed!");
		return false;
	}

	protected boolean isAlwaysExpanded() {
		return true;
	}
}
