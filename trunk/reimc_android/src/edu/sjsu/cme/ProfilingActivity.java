package edu.sjsu.cme;

import org.json.JSONException;
import org.json.JSONObject;

import edu.sjsu.cme.models.LogData;
import edu.sjsu.cme.receivers.BatteryChangeReceiver;
import edu.sjsu.cme.receivers.BatteryChangeReceiverForGraph;
import edu.sjsu.cme.receivers.NetworkChangeReceiver;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v4.app.NavUtils;

public class ProfilingActivity extends Activity {

	public static final String LOG_BATTERY = "LogBattery";
	public static final String LOG_NETWORK = "LogNetwork";
	public static final String JSON_RESULTS = "jsonResults";

	BatteryChangeReceiver batteryChangeReceiver;
	NetworkChangeReceiver networkChangeReceiver;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profiling);
		findViewById(R.id.end_profiling_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
//						startActivity(new Intent(getApplicationContext(),
//								ResultsActivity.class));
						
						JSONObject jsonObject = new JSONObject();
						try {
							jsonObject.put("deviceId", Secure.getString(getApplicationContext().getContentResolver(),
							        Secure.ANDROID_ID));
							jsonObject.put("appName", "REIMC");
							jsonObject.put("data",LogData.jsonDataArray);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						Intent intent = new Intent();
						if (batteryChangeReceiver != null) {
							intent.putExtra(JSON_RESULTS,jsonObject.toString());
						}
						
						setResult(RESULT_OK, intent);
						finish();
					}

				});

		if (getIntent().getExtras().getBoolean(LOG_BATTERY, false)) {
			batteryChangeReceiver = new BatteryChangeReceiver();
			IntentFilter ifilter = new IntentFilter(
					Intent.ACTION_BATTERY_CHANGED);
			getApplicationContext().registerReceiver(batteryChangeReceiver,
					ifilter);
		}

		// MH: Decided it was easier to let the Android platform broadcast
		// battery changes

		if(getIntent().getExtras().getBoolean(LOG_NETWORK, false)){
			 // Registers BroadcastReceiver to track network connection changes.
			networkChangeReceiver = new NetworkChangeReceiver();
	        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
	        getApplicationContext().registerReceiver(networkChangeReceiver, filter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_profiling, menu);
		return true;
	}

}
