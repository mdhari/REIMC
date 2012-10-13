package edu.sjsu.cme.receivers;

import edu.sjsu.cme.GraphActivity;
import edu.sjsu.cme.Logger;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

/**
 * Making a separate receiver for the graph since
 * we want it to be coupled with it to push data.
 * This BroadcastReceiver is set to receive Intent.ACTION_BATTERY_CHANGED
 * 
 * @see Intent.ACTION_BATTERY_CHANGED
 * @author michael
 *
 */
public class BatteryChangeReceiverForGraph extends BroadcastReceiver {

	private static final String TAG = "REIMC::BatteryChangeReceiverForGraph";
	
	private GraphActivity graphActivity;
	
	/**
	 * Constructor that passes a reference of GraphActivity
	 */
	public BatteryChangeReceiverForGraph(GraphActivity graphActivity){
		this.graphActivity = graphActivity;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		int batteryPct = (int) ((level / (float) scale) * 100);
		Logger.log(TAG, "onReceive called with battery level " +(int) ((level / (float) scale) * 100));
		graphActivity.pushData(String.valueOf(System.currentTimeMillis()),String.valueOf(batteryPct));

	}

}
