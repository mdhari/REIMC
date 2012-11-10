package edu.sjsu.cme.mobileclient;

import java.io.IOException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.widget.Toast;

public class MobileLogger {
	
	public static void log(String serverEndpoint,String jsonStr){
		new LogTask().execute(serverEndpoint,jsonStr);
	}
	
	static class LogTask extends AsyncTask<String, Void, Void> {


	    protected Void doInBackground(String... args) {
	    	AndroidHttpClient aHttpClient = AndroidHttpClient.newInstance("Android");
			
			//HttpPost httpPost = new HttpPost("http://localhost/");
//			HttpPost httpPost = new HttpPost("http://192.168.1.68:8080/reimc_rest/bookreader/br/log");
//			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
//			nameValuePairs.add(new BasicNameValuePair("phoneNum", Secure.ANDROID_ID));
//			nameValuePairs.add(new BasicNameValuePair("logType", "MOBILE"));
//			nameValuePairs.add(new BasicNameValuePair("logValue", "Some value"));
			
	    	HttpPost httpPost = new HttpPost(args[0]);
	    	
			try {
				//httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				httpPost.setEntity(new StringEntity(args[1]));
				aHttpClient.execute(httpPost);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
	    }

	 }

}
