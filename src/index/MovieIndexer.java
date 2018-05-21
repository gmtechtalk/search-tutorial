/**
 * S. Chatchawal 
 * May 21, 2561 BE 12:28:56 AM
 * http://www.conan.in.th
 * chatchawal.sangkeettrakarn@nectec.or.th, conan@conan.in.th
 */
package index;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.th.ThaiAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.json.JSONObject;

/**
 * @author conan
 *
 */
public class MovieIndexer {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
		 java.nio.file.Path path = FileSystems.getDefault().getPath("index-movie");
		 Directory dir = FSDirectory.open(path);
		 
		 /* set analyzer to analyze contents */
		 Analyzer analyzer = new ThaiAnalyzer();

		 IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
		 
		 /* always replace old index */
		 iwc.setOpenMode(OpenMode.CREATE); 
		 
		 IndexWriter writer = new IndexWriter(dir,iwc);
		
		
		
		Scanner sc = new Scanner(new File("files/movie.json"));
		
		String line = "";
		
		while(sc.hasNext()) {
			line = sc.nextLine();
			JSONObject m = new JSONObject(line);
			String title = m.getString("title");
			String url = m.getString("url");
			String synopsis = m.getString("synopsis");
			String format = m.getString("format");
			String genres = m.getString("genres").equals("null")?"":m.getString("genres");
			
			
			Document doc = new Document();
			doc.add(new Field("title",title,TextField.TYPE_STORED));
			doc.add(new Field("synopsis",synopsis,TextField.TYPE_STORED));
			doc.add(new Field("format",format,TextField.TYPE_STORED));
			doc.add(new Field("genres",genres,TextField.TYPE_STORED));
			doc.add(new Field("url",url,StringField.TYPE_STORED));
			
			
			writer.addDocument(doc);
			System.out.println(title+" "+format+" "+genres+" "+url);
		}
		sc.close();
		writer.close();
		

	}

}
