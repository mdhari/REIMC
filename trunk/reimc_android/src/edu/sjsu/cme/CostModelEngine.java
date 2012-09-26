package edu.sjsu.cme;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * The CostModelEngine class is responsible for making decisions on whether or
 * not functions/methods are sent remotely to the server for processing.
 * 
 * @author michael
 * 
 */
public class CostModelEngine {

	public static final String TAG = "CostModelEngine";

	// Create static variables to describe the state of the device
	public static final String CPU_METRIC = "CPU";
	public static final int CPU_LOW = 0;
	public static final int CPU_NORMAL = 1;
	public static final int CPU_HIGH = 2;
	public static final int CPU_ANY = 4;

	public static final String BATTERY_METRIC = "BATTERY";
	public static final int BATTERY_LOW = 5;
	public static final int BATTERY_AVERAGE = 6;
	public static final int BATTERY_HIGH = 7;
	public static final int BATTERY_ANY = 8;

	public static final String NETWORK_METRIC = "NETWORK";
	public static final int NETWORK_QUALITY_LOW = 9;
	public static final int NETWORK_QUALITY_AVERAGE = 10;
	public static final int NETWORK_QUALITY_HIGH = 11;
	public static final int NETWORK_QUALILY_ANY = 12;

	// cost objective models - defaults
	// when the developer doesn't want to fine tune details
	public static final String GOAL_COST = "COST";
	public static final Map<String, Integer> costMap;

	public static final String GOAL_PERFORMANCE = "PERFORMANCE";
	public static final Map<String, Integer> performanceMap;

	public static final String GOAL_POWER = "POWER";
	public static final Map<String, Integer> powerMap;

	static {
		Map<String, Integer> m = new HashMap<String, Integer>();

		m.put(CPU_METRIC, CPU_LOW);
		m.put(BATTERY_METRIC, BATTERY_LOW);
		m.put(NETWORK_METRIC, NETWORK_QUALITY_LOW);
		costMap = Collections.unmodifiableMap(m);

		m.clear();

		m.put(CPU_METRIC, CPU_LOW);
		m.put(BATTERY_METRIC, BATTERY_ANY);
		m.put(NETWORK_METRIC, NETWORK_QUALITY_HIGH);
		performanceMap = Collections.unmodifiableMap(m);

		m.clear();

		m.put(CPU_METRIC, CPU_ANY);
		m.put(BATTERY_METRIC, BATTERY_LOW);
		m.put(NETWORK_METRIC, NETWORK_QUALITY_AVERAGE);
		powerMap = Collections.unmodifiableMap(m);
	}

	// Android context to get services
	private Context context;

	//
	private Map<String, Integer> deviceStatusMap = new HashMap<String, Integer>();
	private Map<String, Integer> costObjectiveMap = new HashMap<String, Integer>();

	/**
	 * Prevent default
	 */
	private CostModelEngine() {
	}

	/**
	 * Constructor that allows a developer to chose preset cost objective models
	 * which include GOAL_COST, GOAL_PERFORMANCE, and GOAL_POWER
	 * 
	 * @param context
	 *            - The Android context
	 * @param costModelObjective
	 *            - Name of the Cost Model Objective to use
	 */
	public CostModelEngine(Context context, String costModelObjective) {

		this.context = context; // set the Android context

		// set the cost objective goal
		if (costModelObjective.toUpperCase() == GOAL_COST) {
			costObjectiveMap = costMap;
		} else if (costModelObjective.toUpperCase() == GOAL_PERFORMANCE) {
			costObjectiveMap = performanceMap;
		} else if (costModelObjective.toUpperCase() == GOAL_POWER) {
			costObjectiveMap = powerMap;
		}

	}

	/**
	 * Will run algorithm to determine if locally run or remotely run based on
	 * developer's preferences
	 * 
	 * @param currDeviceStatusMap
	 * @param currCostObjectiveMap
	 */
	public void runCalc(Map<String, Integer> currDeviceStatusMap,
			Map<String, Integer> currCostObjectiveMap) {

		// if(currDeviceStatusMap.get(CostModelEngine.))

	}

	/**
	 * Where all user-defined metrics are taken and a decision whether remote
	 * processing would be more energy efficient
	 * 
	 * @return True if its more efficient and conforms to cost objectives <br/>
	 *         False otherwise
	 */
	public boolean shouldRunRemote() {

		updateDeviceStatusMap();
		// don't access the Map directly in case in the future we have
		// concurrent programming
		// Map<String,Integer> currDeviceStatusMap = getCurrrentDeviceStatus();
		runCalc(getCurrrentDeviceStatus(), costObjectiveMap);

		return true;
	}

	/**
	 * Getter for deviceStatusMap
	 * 
	 * @return A HashMap of the key being a device status metric and the value
	 *         being a indication of the state of the current metric in terms of
	 *         "low", "average" or "high". Use the CostModelEngine's static
	 *         variables for comparisons
	 */
	public Map<String, Integer> getCurrrentDeviceStatus() {
		return deviceStatusMap;
	}

	private void updateDeviceStatusMap() {

		// Battery
		updateBatteryState();
		updateNetworkState();

		// End Battery

	}

	/**
	 * Update the metrics in the deviceStatusMap for Battery
	 */
	private void updateBatteryState() {

		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = context.registerReceiver(null, ifilter);

		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		int batteryPct = (int) ((level / (float) scale) * 100);

		Logger.log(TAG, "BatteryLevel: " + batteryPct);

		deviceStatusMap.put(BATTERY_METRIC, batteryPct);
	}

	/**
	 * Update the metrics in the deviceStatusMap for Network
	 */
	private void updateNetworkState() {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// TelephonyManager tm =
		// (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

		switch (activeNetwork.getType()) {
		case (ConnectivityManager.TYPE_WIFI): // WIFI, NETWORK IS ASSUMED HIGH
			Logger.log(TAG, "Network: WIFI");
			deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_HIGH);
			break;
		case (ConnectivityManager.TYPE_MOBILE): { // Need to sniff out type
													// (2G,3G,4G)
			switch (activeNetwork.getSubtype()) {
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				Logger.log(TAG, "Network: 1xRTT ~ 50-100 kbps"); // ~ 50-100 kbps
				deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_LOW);
				break;
			case TelephonyManager.NETWORK_TYPE_CDMA:
				Logger.log(TAG, "Network: CDMA ~ 14-64 kbps"); // ~ 14-64 kbps
				deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_LOW);
				break;
			case TelephonyManager.NETWORK_TYPE_EDGE:
				Logger.log(TAG, "Network: EDGE ~ 50-100 kbps"); // ~ 50-100 kbps
				deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_LOW);
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				Logger.log(TAG, "Network: EVDO_0 ~ 400-1000 kbps"); // ~ 400-1000
																// kbps
				deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_AVERAGE);
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				Logger.log(TAG, "Network: EVDO_A ~ 600-1400 kbps"); // ~ 600-1400
																// kbps
				deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_AVERAGE);
				break;
			case TelephonyManager.NETWORK_TYPE_GPRS:
				Logger.log(TAG, "Network: GPRS ~ 100 kbps"); // ~ 100 kbps
				deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_LOW);
				break;
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				Logger.log(TAG, "Network: HSDPA ~ 2-14 Mbps"); // ~ 2-14 Mbps
				deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_HIGH);
				break;
			case TelephonyManager.NETWORK_TYPE_HSPA:
				Logger.log(TAG, "Network: HSPA ~ 700-1700 kbps"); // ~ 700-1700 kbps
				deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_HIGH);
				break;
			case TelephonyManager.NETWORK_TYPE_HSUPA:
				Logger.log(TAG, "Network: HSUPA ~ 1-23 Mbps"); // ~ 1-23 Mbps
				deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_HIGH);
				break;
			case TelephonyManager.NETWORK_TYPE_UMTS:
				Logger.log(TAG, "Network: UMTS ~ 400-7000 kbps"); // ~ 400-7000 kbps
				deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_HIGH);
				break;
			// NOT AVAILABLE YET IN API LEVEL 7
			case TelephonyManager.NETWORK_TYPE_EHRPD:
				Logger.log(TAG, "Network: EHRPD ~ 1-2 Mbps"); // ~ 1-2 Mbps
				deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_HIGH);
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_B:
				Logger.log(TAG, "Network: EVDO_B ~ 5 kbps"); // ~ 5 Mbps
				deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_HIGH);
				break;
			case TelephonyManager.NETWORK_TYPE_HSPAP:
				Logger.log(TAG, "Network: HSPAP ~ 10-20 Mbps"); // ~ 10-20 Mbps
				deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_HIGH);
				break;
			case TelephonyManager.NETWORK_TYPE_IDEN:
				Logger.log(TAG, "Network: IDEN ~ 25 kbps"); // ~25 kbps
				deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_LOW);
				break;
			case TelephonyManager.NETWORK_TYPE_LTE:
				Logger.log(TAG, "Network: LTE ~ 10 Mbps"); // ~ 10+ Mbps
				deviceStatusMap.put(NETWORK_METRIC, NETWORK_QUALITY_HIGH);
				break;
			default:
				Logger.log(TAG, "Could not detect network");
				break;
			}
			break;
		}
		default:
			break;
		} // end main switch statement

		Logger.log(TAG,
				"NETWORK STATE CHANGED TO: "
						+ deviceStatusMap.get(NETWORK_METRIC));

	}

}
