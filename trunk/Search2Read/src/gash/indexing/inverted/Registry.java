package gash.indexing.inverted;

import gash.indexing.Doc;
import gash.indexing.stopwords.StopWords;
import gash.indexing.stopwords.StopWordsFile;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Registry {
	public static final String sStopWords = "stop.words.file";

	// TODO abstract/isolate storage. oh yeah and persist it too
	private HashMap<String, ArrayList<Doc>> index = new HashMap<String, ArrayList<Doc>>();

	private Loader loader;
	
	

	//************************************************
	public Registry(String filename) {
		System.out.println("New Registry>" + filename);
		loadStopWords(filename);
	}
	private void loadStopWords(String filename)
	{
		System.out.println("loadStopWords");
		try {

			//Read StopWord file then Load stopWords
			File swf = new File(filename);
			StopWords swords = new StopWordsFile(swf);   //This will read file, load all words in a list
			loader = new Loader(swords); //Sets variable in loader class
			
		} catch (Exception e) {
			throw new RuntimeException("Failed to setup registry.", e);
		}
	}
	

	//************************************************
	public void query(String searchText) {
		
		System.out.println("Query" + searchText);
		
		String[] searchWord = searchText.trim().split("[\\s,\\.:;\\-#~\\(\\)\\?\\!\\&\\*\\\"\\/\\'\\`\\ ]");
		System.out.println("searchWords length>" + searchWord.length);
		
		List<Doc> docs = loader.searchTextBackUp(searchWord);
		
		//Go through list of doc and read 
		for (Doc d : docs) {
			
			System.out.println("Doc # " + d.getName());
			
			HashMap<Integer, Integer> OccInLine = d.getOccInLine();
			HashMap<Integer, String>  textOfLine = d.getTextOfLineMap();
			
			Set<Integer> lines = OccInLine.keySet();
			
		
			for (Integer key :  OccInLine.keySet()) {
				System.out.println("Line # = " + key);
				System.out.println("# of Search Words = " + OccInLine.get(key) );
				System.out.println("Line Text = " + textOfLine.get(key) );
			}

		}
		
	}

	//**************************************************
	public void loadTestData(File dir) {
		System.out.println("Registry.java>loadTestData>" + dir.getName());
		if (dir == null)
			return;

		// TODO prevent double registration

		//Gets list of files from Dir
		//List<Doc> docs = loader.load(dir);
		int i = loader.load(dir);
		
		if(i <= 0)
			System.out.println("Could not load files");
		
	}


}
