package edu.sjsu.cme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.sjsu.cme.mobileclient.MobileLogger;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class ResultsActivity extends Activity {

	static final String TAG = "REIMC::ResultsActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        
        //Logger.log(TAG, ""+getIntent().getStringExtra(ProfilingActivity.JSON_RESULTS));
        findViewById(R.id.store_locally_btn).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_HH.mm.ss");
					Date date = new Date();
					// We can read and write the media
					writeToExternalFile("REIMC_"+dateFormat.format(date)+".json",getIntent().getStringExtra(ProfilingActivity.JSON_RESULTS));
					
				} else{
					Toast.makeText(getApplicationContext(), "REIMC::External Storage Unavalible", Toast.LENGTH_SHORT).show();
				}
				
			}
        	
        });
        
        findViewById(R.id.view_data_btn).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),ViewDataActivity.class);
				intent.putExtra(ProfilingActivity.JSON_RESULTS,getIntent().getStringExtra(ProfilingActivity.JSON_RESULTS));
				startActivity(intent);
			}
        	
        });
        
        findViewById(R.id.store_remotely_btn).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),SendDataToServerActivity.class);
				intent.putExtra(ProfilingActivity.JSON_RESULTS,getIntent().getStringExtra(ProfilingActivity.JSON_RESULTS));
				startActivity(intent);
				
			}
        	
        });
    }
    
    private void writeToExternalFile(String filename,String data){
	    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
	    PrintWriter pw = null;
	    try {
	        // Very simple code to copy a picture from the application's
	        // resource into the external file.  Note that this code does
	        // no error checking, and assumes the picture is small (does not
	        // try to copy it in chunks).  Note that if external storage is
	        // not currently mounted this will silently fail.
//	        InputStream is = graphActivity.getResources().openRawResource(R.drawable.balloons);
	    	pw = new PrintWriter(new FileOutputStream(file,true),true);
	    	pw.println(data);
	    
	    	Logger.log("REIMC:::::::::", "write to file " + filename + " successful");
	    	Toast.makeText(getApplicationContext(), "Logged in your Downloads directory under filename: "+ filename, Toast.LENGTH_LONG).show();
	    	findViewById(R.id.store_locally_btn).setEnabled(false);
	    } catch (IOException e) {
	        // Unable to create file, likely because external storage is
	        // not currently mounted.
	        Log.w("ExternalStorage", "Error writing " + file, e);
	    }finally{
	    	pw.close();
	    }
	    
	    
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_results, menu);
        return true;
    }

    
}
