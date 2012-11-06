package edu.sjsu.cme.receivers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	private static final String logType = "Battery";
	private JSONArray dataJsonArray;
	
	public BatteryChangeReceiver(){
		dataJsonArray = new JSONArray();
	}
	
	public JSONArray getJSONArray(){
		return dataJsonArray;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//Logger.log(TAG, "onReceive called");
		int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("timestamp",System.currentTimeMillis());
			jsonObject.put("logType",logType);
			jsonObject.put("logData",(int) ((level / (float) scale) * 100));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dataJsonArray.put(jsonObject);
		
		//Logger.log(TAG, dataJsonArray.toString());

		//Logger.log(TAG, "onReceive called with battery level " +(int) ((level / (float) scale) * 100));

	}

}
