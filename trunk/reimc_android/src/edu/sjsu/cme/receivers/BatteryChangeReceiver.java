package edu.sjsu.cme.receivers;

import edu.sjsu.cme.Logger;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

/**
 * Responsible for getting a change in BatteryState.
 * @author michael
 *
 */
public class BatteryChangeReceiver extends BroadcastReceiver {

	private static final String TAG = "REIMC::BatteryChangeReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//Logger.log(TAG, "onReceive called");
		int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		Logger.log(TAG, "onReceive called with battery level " +(int) ((level / (float) scale) * 100));

	}

}
