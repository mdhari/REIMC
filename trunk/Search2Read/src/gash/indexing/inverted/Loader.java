package gash.indexing.inverted;

import gash.indexing.Doc;
import gash.indexing.stopwords.StopWords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Loader {
	private StopWords ignore;
	private List<File> list = new ArrayList<File>();

	//**************************
	//Load stopWords
	public Loader(StopWords stopWords) throws Exception {
		System.out.println("Loader.java>new Load and load stopwords");
		ignore = stopWords;
	}

	//1 Create list test files
	public int load(File f) {
		if (f == null)
			return 0;

		if (f.isFile())
		{
			list.add(f);
		}else {
			discoverFiles(f);
		}

		System.out.println("Loader.java>Loading test files  > # of file loaded > " + list.size());
		
		return list.size();
	}

	public List<File> files() {
		return list;
	}

	public List<Doc> searchTextBackUp(String[] searchWords) {
		
		System.out.println("Loader.java>Searching files for list of words:> words=" + searchWords.length);
	
		
		HashSet<String> listOfSearchWord = new HashSet<String>();
		ArrayList<Doc> docList = new ArrayList<Doc>(list.size());
		
		for(String p: searchWords)
			listOfSearchWord.add(p.toLowerCase());
		
		for (File f : list) {
			
			System.out.println("Loader.java>searchText>Searching file:>" + f.getName());
			
			BufferedReader rdr = null;
			
			Doc d1 = new Doc(f);
			
			try {
				
				rdr = new BufferedReader(new FileReader(f));
				
				//int relPosition = 0;
				int totalWordsInCurrentLine = 0;
				int numOfOccInLine = 0;
				
				String raw = rdr.readLine(); //Read first line
				int lineNum = 1;
				
				//Go through each line
				while (raw != null) 
				{
					//Get parts of a line
					String[] parts = raw.trim().split("[\\s,\\.:;\\-#~\\(\\)\\?\\!\\&\\*\\\"\\/\\'\\`]");
					totalWordsInCurrentLine += parts.length;
					boolean found = false;
					//Loop through each part and compare
					for (String p : parts)
					{
						if (!ignore.contains(p.toLowerCase()))
						{
							if(listOfSearchWord.contains(p.toLowerCase()))
							{
								System.out.println("Found search word and adding to doc keyword" + p.toString());
								found = true;
								numOfOccInLine ++;
								
							}
							
						}
					}

					if(found)
						d1.SpecifyLifeWithSearch(lineNum, raw.toString(), numOfOccInLine);
					
					numOfOccInLine = 0;
					lineNum ++;
					raw = rdr.readLine();
				}

				docList.add(d1);
				
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
		return docList;
	}

	private void discoverFiles(File dir) {
		System.out.println("Loader.java>discoverFiles");
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
