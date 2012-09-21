package gash.indexing.inverted;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class Search {

	static Registry data;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		//Load Stop words
		String stopWordFileName = "resources/stopwords-long.txt";
		data = new Registry(stopWordFileName);

		
		//Load Test data file
		File dir = new File("testdata/lyrics");
		data.loadTestData(dir);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		data = null;
	}

	@Test
	public void testQuery() {

		String searchText = "pride of the man of washed betrayed";

		long st = System.nanoTime();
		data.query(searchText);
		long et = System.nanoTime();
	}
	
}
