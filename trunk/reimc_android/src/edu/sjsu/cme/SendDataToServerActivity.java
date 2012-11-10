package edu.sjsu.cme;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.SharedPreferences;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class SendDataToServerActivity extends Activity {

	private static final String TAG = "SendDataToServerActivity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data_to_server);
        
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        ((EditText)findViewById(R.id.server_edittext)).setText(sharedPreferences.getString("server_endpoint", getString(R.string.default_jsonendpoint)));
        
        findViewById(R.id.send_data_submit_btn).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// MH: Don't always want to type the url
				SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
			    SharedPreferences.Editor editor = sharedPreferences.edit();
			    editor.putString("server_endpoint", ((EditText)findViewById(R.id.server_edittext)).getText().toString());
			    editor.commit();
				new LogTask().execute(((EditText)findViewById(R.id.server_edittext)).getText().toString(),getIntent().getStringExtra(ProfilingActivity.JSON_RESULTS));
				
			}
        	
        });
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_send_data_to_server, menu);
        return true;
    }
    
    private void showDialog(boolean success){
    	
    	Builder aDialog = new AlertDialog.Builder(this);
    	aDialog.setNeutralButton("OK", null);
    	if(success){
    		aDialog.setTitle("Success!");
    		aDialog.setMessage("Data logged on server!");
    	}else{
    		aDialog.setTitle("Unable to send data");
    		aDialog.setMessage("Transmission failure, check connection");
    	}
    	aDialog.create().show();
    	//finish();
    }
    
    class LogTask extends AsyncTask<String, Void, Boolean> {


	    protected Boolean doInBackground(String... args) {
	    	AndroidHttpClient aHttpClient = AndroidHttpClient.newInstance("Android");
			
			//HttpPost httpPost = new HttpPost("http://localhost/");
//			HttpPost httpPost = new HttpPost("http://192.168.1.68:8080/reimc_rest/bookreader/br/log");
//			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
//			nameValuePairs.add(new BasicNameValuePair("phoneNum", Secure.ANDROID_ID));
//			nameValuePairs.add(new BasicNameValuePair("logType", "MOBILE"));
//			nameValuePairs.add(new BasicNameValuePair("logValue", "Some value"));
			
	    	HttpPost httpPost = new HttpPost(args[0]);
	    	httpPost.setHeader("Content-Type", "application/json");
	    	
			try {
				//httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				httpPost.setEntity(new StringEntity(args[1]));
				aHttpClient.execute(httpPost);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Boolean.FALSE;
				//Toast.makeText(getApplicationContext(), "Error sending to server", Toast.LENGTH_SHORT).show();
			} finally {
				aHttpClient.close();
			}
			
			//Toast.makeText(getApplicationContext(), "Successful Transfer", Toast.LENGTH_SHORT).show();
			return Boolean.TRUE;
	    }
	    
	    protected void onPostExecute(Boolean transmissionSuccess) {
	    	showDialog(transmissionSuccess.booleanValue());
	     }
    }
    
}
