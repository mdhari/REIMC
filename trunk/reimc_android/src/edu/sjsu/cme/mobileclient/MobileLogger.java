package edu.sjsu.cme.mobileclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.provider.Settings.Secure;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

import edu.sjsu.cme.Logger;

public class MobileLogger {
	
	public static void log(String deviceId, String timestamp, String logDesc){
		
		new LogTask().execute();
	}
	
	static class LogTask extends AsyncTask<String, Void, Void> {


	    protected Void doInBackground(String... urls) {
	    	AndroidHttpClient aHttpClient = AndroidHttpClient.newInstance("Android");
			
			//HttpPost httpPost = new HttpPost("http://localhost/");
			HttpPost httpPost = new HttpPost("http://192.168.1.68:8080/reimc_rest/bookreader/br/log");
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			nameValuePairs.add(new BasicNameValuePair("phoneNum", Secure.ANDROID_ID));
			nameValuePairs.add(new BasicNameValuePair("logType", "MOBILE"));
			nameValuePairs.add(new BasicNameValuePair("logValue", "Some value"));
			
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				aHttpClient.execute(httpPost);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
	    }

	    protected void onPostExecute(Void feed) {
	        // TODO: check this.exception 
	        // TODO: do something with the feed
	    }
	 }

}
