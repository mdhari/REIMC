package gash.indexing;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Doc {
	private String name;
	private String location; // file,url
	private String description;
	private Date date;
	private long numWords;
	private long numOfOcc;

	// may want to count the words to know the strength of a word within a
	// document. Also, separation (distance between words) can give us other
	// metrics on the keywords
	//private HashMap<String, KeyWord> keywords = new HashMap<String, KeyWord>();

	private HashMap<Integer, String> textOfLineMap = new HashMap<Integer, String>();
	
	private HashMap<Integer, Integer> OccInLine = new HashMap<Integer, Integer>();
	
	
	public HashMap<Integer, String> getTextOfLineMap() {
		return textOfLineMap;
	}

	public void setTextOfLineMap(HashMap<Integer, String> textOfLineMap) {
		this.textOfLineMap = textOfLineMap;
	}

	public HashMap<Integer, Integer> getOccInLine() {
		return OccInLine;
	}

	public void setOccInLine(HashMap<Integer, Integer> occInLine) {
		OccInLine = occInLine;
	}

	public void SpecifyLifeWithSearch (int lineNum, String lineText, int numOfOccInLine)
	{
		System.out.println("SpecifyLifeWithSearch: Storing " + lineNum + "," + lineText + ",#" + numOfOccInLine);
		
		numOfOcc =+ numOfOccInLine;
		textOfLineMap.put(lineNum, lineText);
		
		OccInLine.put(lineNum, numOfOccInLine);
	}

	public Doc(File f) {
		System.out.println("Document.java>Document");
		
		if (f == null)
			return;

		location = f.getAbsolutePath();
		name = f.getName();
		date = new Date(f.lastModified());
	}

	/**
	 * collect keywords by frequency
	 * 
	 * TODO use a euclidean distance for weighting word importance => clustering
	 * 
	 * @param w
	 */

	/*public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: ");
		sb.append(name);
		sb.append("\nFile: ");
		sb.append(location);
		sb.append("\nNum words: ");
		sb.append(numWords);
		sb.append("\nKeywords:\n");

		DecimalFormat fmt = new DecimalFormat("#.##");

		for (KeyWord kw : keywords()) {
			sb.append(kw.word);
			sb.append(" (n = ");
			sb.append(kw.position.size());
			sb.append(", pos = [");

			double ave = 0.0f;
			for (Integer p : kw.position) {
				ave += p;
				sb.append(p);
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("], ");

			ave /= kw.position.size();
			sb.append("mean = ");
			sb.append(fmt.format(ave));
			sb.append(", stdev = ");
			sb.append(fmt.format(kw.stdDev()));
			sb.append(")\n");

		}
		return sb.toString();
	}*/

	public String csvHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("name,location,numWords\n");
		sb.append("\"");
		sb.append(name);
		sb.append("\",\"");
		sb.append(location);
		sb.append("\",");
		sb.append(numWords);
		sb.append("\n");

		return sb.toString();
	}

	/*public String csvData() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n\n");
		sb.append("word, freq, pos (mean), pos (stdev)\n");

		DecimalFormat fmt = new DecimalFormat("#.##");

		for (KeyWord kw : keywords()) {
			double ave = 0.0f;
			for (Integer p : kw.position) {
				ave += p;
			}
			ave /= kw.position.size();

			sb.append("\"");
			sb.append(kw.word);
			sb.append("\",");
			sb.append(kw.position.size());
			sb.append(",");
			sb.append(fmt.format(ave));
			sb.append(",");
			sb.append(fmt.format(kw.stdDev()));
			sb.append("\n");

		}
		return sb.toString();
	}*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getNumWords() {
		return numWords;
	}

	public void setNumWords(long numWords) {
		this.numWords = numWords;
	}
}
