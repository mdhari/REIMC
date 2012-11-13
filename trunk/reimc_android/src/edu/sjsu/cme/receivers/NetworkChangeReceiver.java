package edu.sjsu.cme.receivers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.sjsu.cme.models.LogData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;

public class NetworkChangeReceiver extends BroadcastReceiver {

	private static final String TAG = "REIMC::NetworkChangeReceiver";
	private static final String logType = "Network";
	private JSONArray dataJsonArray;
	
	public NetworkChangeReceiver(){
		dataJsonArray = new JSONArray();
	}
	
	public JSONArray getJSONArray(){
		return dataJsonArray;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//Logger.log(TAG, "onReceive called");
		JSONObject jsonObject = new JSONObject();
		String networkType = null;
		ConnectivityManager connMgr = (ConnectivityManager) 
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            if(activeInfo.getType() == ConnectivityManager.TYPE_WIFI){
            	networkType = "WIFI";
            }else if(activeInfo.getType() == ConnectivityManager.TYPE_MOBILE){
            	networkType = "MOBILE";
            }
            try {
    			jsonObject.put("timestamp",System.currentTimeMillis());
    			jsonObject.put("logType",logType);
    			jsonObject.put("logData",networkType);
    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
		
		
		
		//dataJsonArray.put(jsonObject);
        LogData.jsonDataArray.put(jsonObject);
        
		//Logger.log(TAG, dataJsonArray.toString());

		//Logger.log(TAG, "onReceive called with battery level " +(int) ((level / (float) scale) * 100));

	}

}
