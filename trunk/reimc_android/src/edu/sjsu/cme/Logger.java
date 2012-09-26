package edu.sjsu.cme;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * Logger class to help with performance 
 * by enabling or disabling with a simple boolean
 * @author michael
 *
 */
@SuppressLint("ParserError")
public class Logger {
	
	private static final boolean enabled = true;
	
	/**
	 * Wraps the logger with an enabled tag to help with performance
	 * @param TAG - Whatever Tag you want to prefix to the log entry (usually
	 * the class name)
	 * @param string - String to log into into Android's Log.d
	 */
	public static void log(String TAG,String string){
		if(enabled){
			Log.d(TAG,string);
		}
	}

}
