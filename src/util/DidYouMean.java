/**
 * S. Chatchawal 
 * May 20, 2561 BE 2:00:36 AM
 * http://www.conan.in.th
 * chatchawal.sangkeettrakarn@nectec.or.th, conan@conan.in.th
 */
package util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.lucene.analysis.th.ThaiAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexReaderContext;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * @author conan
 *
 */
public class DidYouMean {

	SpellChecker sp;
	
	public DidYouMean() {
		try {
		Path path = FileSystems.getDefault().getPath("index-news");
		Directory dir = FSDirectory.open(path);
		
		sp = new SpellChecker(dir);

		IndexWriterConfig config = new IndexWriterConfig(new ThaiAnalyzer());
		/* use terms from index */
		sp.indexDictionary(new LuceneDictionary(DirectoryReader.open(dir), "content"),config,true);
		
		/* user terms from textfile */
		//	sp.indexDictionary(new PlainTextDictionary(FileSystems.getDefault().getPath("files/word.list")) , config, true);
		
		
		sp.setAccuracy(0.6f);
		
		}catch(Exception er) {}
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		DidYouMean dym = new DidYouMean();
		while(sc.hasNext()) {
			String line = sc.nextLine();
			
			String[] sglist = dym.get(line,3);
			System.out.println(line);
			for(String s : sglist)
				System.out.println(" - "+s);
		}
		sc.close();

	}
	
	
	public String[] get(String key,int lim){
		try {
			
			return sp.suggestSimilar(key, lim);

		}catch(Exception er) {}
		return null;
	}

}
