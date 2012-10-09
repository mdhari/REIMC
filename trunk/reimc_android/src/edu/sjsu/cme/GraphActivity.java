package edu.sjsu.cme;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;

/**
 * Class responsible for graphing battery data over time
 * @author michael
 *
 */
public class GraphActivity extends Activity {
	public static final String TAG = "GraphActivity";

	WebView mWebView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        
        mWebView = (WebView) findViewById(R.id.graph_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/webapp/graph.html");
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_graph, menu);
        return true;
    }
    
    public void pushData(String x, String y){
    	Logger.log(TAG, "trying javascript:addDataPoint("+x+","+y+");");
    	if(mWebView != null){
    		Logger.log(TAG, "running javascript:addDataPoint("+x+","+y+");");
    		mWebView.loadUrl("javascript:addDataPoint("+x+","+y+");");
    	}
    }

    
}
