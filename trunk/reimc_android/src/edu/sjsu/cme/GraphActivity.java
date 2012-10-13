package edu.sjsu.cme;

import java.util.Timer;
import java.util.TimerTask;

import edu.sjsu.cme.receivers.BatteryChangeReceiverForGraph;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.view.Menu;
import android.webkit.WebView;

/**
 * Class responsible for graphing battery data over time
 * @author michael
 *
 */
public class GraphActivity extends Activity {
	public static final String TAG = "REIMC::GraphActivity";

	WebView mWebView;
	//Context context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        
        mWebView = (WebView) findViewById(R.id.graph_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/webapp/graph.html");
        
        // MH: Decided it was easier to let the Android platform broadcast battery changes
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getApplicationContext().registerReceiver(new BatteryChangeReceiverForGraph(this),ifilter);
        
        // MH: Setup a timer to fire every 30 seconds
//        Timer timer = new Timer();
//        timer.schedule(task, 500, 30000);
        
    }

    // MH: The task is to get the current time plus batterylevel
    // and graph it. Because Android warns that WebView methods
    // must run on the Main UI Thread. Hence, send a message to a
    // Handler on the main thread with the data
//    TimerTask task = new TimerTask() {
//        public void run() {
//        	Logger.log(TAG, "" + System.currentTimeMillis() + "," + getBatteryLevel());
//        	Message msg = new Message();
//        	msg.arg1 = (int) System.currentTimeMillis();
// 		   	msg.arg2 = getBatteryLevel();
// 		   	msg.what = 0;
// 		   	mHandler.sendMessageAtFrontOfQueue(msg);
//        }
//    };
    
    
//    private int getBatteryLevel(){
//    	IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//		Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);
//
//		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//		int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//
//		int batteryPct = (int) ((level / (float) scale) * 100);
//
//		return batteryPct;
//    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_graph, menu);
        return true;
    }
    
    // TODO: Fix broken bootstrap for highstocks
    public void pushData(String x, String y){
    	//Logger.log(TAG, "trying javascript:addDataPoint("+x+","+y+");");
    	if(mWebView != null){
    		Logger.log(TAG, "running javascript:addDataPoint("+x+","+y+");");
    		mWebView.loadUrl("javascript:addDataPoint("+x+","+y+");");
    		
    	}
    }
    
//    /**
//     * Our Handler used to execute operations on the main thread.  This is used
//     * to schedule increments of our value.
//     */
//    private final Handler mHandler = new Handler() {
//        @Override public void handleMessage(Message msg) {
//            switch (msg.what) {
//                
//                // It is time to bump the value!
//                case 0: 
//                	pushData(String.valueOf(msg.arg1),String.valueOf(msg.arg2));
//                	break; 
//                 
//                
//            }
//        }
//    };

    
}
