package gash.indexing.inverted;

import edu.sjsu.cme.Logger;
import gash.indexing.Document;
import gash.indexing.stopwords.StopWords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class Loader {
	private static final String TAG = "Loader";
	
	private StopWords ignore;
	private Context context;
	private List<File> list = new ArrayList<File>();
	private List<String> pathToFileList = new ArrayList<String>();

	public Loader(StopWords stopWords) throws Exception {
		ignore = stopWords;
	}

	public Loader(StopWords stopWords, Context context) throws Exception {
		ignore = stopWords;
		this.context = context;
	}
	
	public List<Document> load(File f) {
		if (f == null)
			return null;

		if (f.isFile())
			list.add(f);
		else {
			discoverFiles(f);
		}

//		System.out.println("---> " + list);
		Logger.log(TAG, "load list: " + list);
		return gatherData();
	}
	
	public List<Document> load(String f) {
		if (f == null)
			return null;

		try {
			for(String str:context.getAssets().list(f)){
				Logger.log(TAG, str);
				pathToFileList.add(f + "/" + str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		if (f.isFile())
//			list.add(f);
//		else {
//			discoverFiles(f);
//		}

//		System.out.println("---> " + list);
		Logger.log(TAG, "load list: " + list);
		return gatherDataForAndroid();
	}

	public List<File> files() {
		return list;
	}
	
	private List<Document> gatherDataForAndroid() {
		// TODO this should be ran in parallel
		ArrayList<Document> r = new ArrayList<Document>(list.size());
		for (String f : pathToFileList) {
			BufferedReader rdr = null;
			Document d = new Document(f,"some name");
			
			try {
				rdr = new BufferedReader(new InputStreamReader(context.getAssets().open(f)));
				String raw = rdr.readLine();
				int relPosition = 0;
				long numWords = 0;
				while (raw != null) {
					String[] parts = raw.trim().split(
							"[\\s,\\.:;\\-#~\\(\\)\\?\\!\\&\\*\\\"\\/\\'\\`]");

					numWords += parts.length;

					for (String p : parts) {
						if (!ignore.contains(p))
							d.addKeyword(p, relPosition);

						// location (word position) in document allows use to
						// calculate strength by relative location and frequency
						relPosition++;
					}

					raw = rdr.readLine();
				}
				
				d.setNumWords(numWords);
				r.add(d);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (rdr != null)
						rdr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return r;
	}

	private List<Document> gatherData() {
		// TODO this should be ran in parallel
		ArrayList<Document> r = new ArrayList<Document>(list.size());
		for (File f : list) {
			BufferedReader rdr = null;
			Document d = new Document(f);
			
			try {
				rdr = new BufferedReader(new FileReader(f));
				String raw = rdr.readLine();
				int relPosition = 0;
				long numWords = 0;
				while (raw != null) {
					String[] parts = raw.trim().split(
							"[\\s,\\.:;\\-#~\\(\\)\\?\\!\\&\\*\\\"\\/\\'\\`]");

					numWords += parts.length;

					for (String p : parts) {
						if (!ignore.contains(p))
							d.addKeyword(p, relPosition);

						// location (word position) in document allows use to
						// calculate strength by relative location and frequency
						relPosition++;
					}

					raw = rdr.readLine();
				}
				
				d.setNumWords(numWords);
				r.add(d);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (rdr != null)
						rdr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return r;
	}

	/**
	 * depth search
	 * 
	 * @param dir
	 */
	private void discoverFiles(File dir) {
		if (dir == null || dir.isFile())
			return;

		File[] dirs = dir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isFile())
					list.add(f);
				else if (f.getName().startsWith("."))
					; // ignore
				else if (f.isDirectory()) {
					discoverFiles(f);
				}

				return false;
			}
		});
	}
}
