package edu.sjsu.cme;

import org.json.JSONArray;

import edu.sjsu.cme.models.LogData;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class LandingActivity extends Activity {

	static final String TAG = "REIMC::LandingActivity";
	static final int PROFILE_REQUEST = 0;
	
	protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == PROFILE_REQUEST) {
            if (resultCode == RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.
            	//Logger.log(TAG, data.getStringExtra(ProfilingActivity.JSON_RESULTS));
            	data.setClassName(getApplicationContext(), "edu.sjsu.cme.ResultsActivity");
                startActivity(data);
            }
        }
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);
		findViewById(R.id.start_profiling_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (validate()) {
							Intent intent = new Intent(getApplicationContext(),
									ProfilingActivity.class);
							
							if(((CheckBox) findViewById(R.id.battery_checkbox)).isChecked())
							intent.putExtra(ProfilingActivity.LOG_BATTERY, true);
							
							if(((CheckBox) findViewById(R.id.network_checkbox)).isChecked())
							intent.putExtra(ProfilingActivity.LOG_NETWORK, true);
							
							LogData.jsonDataArray = new JSONArray(); // reset the log data
							
							startActivityForResult(intent,PROFILE_REQUEST);
						}

					}

				});

	}

	private boolean validate() {
		if (((CheckBox) findViewById(R.id.battery_checkbox)).isChecked()
				|| ((CheckBox) findViewById(R.id.network_checkbox)).isChecked())
			return true;

		Toast.makeText(getApplicationContext(), "You must select one metric to log", Toast.LENGTH_SHORT).show();
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_landing, menu);
		return true;
	}

}
