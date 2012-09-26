package gash.indexing.stopwords;

import java.io.File;
import java.io.InputStream;

public interface StopWords {

	boolean contains(String word);

	void init(File swf) throws Exception;
	void init(InputStream is) throws Exception;

}