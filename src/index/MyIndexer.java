/**
 * S. Chatchawal 
 * May 20, 2561 BE 1:39:40 AM
 * http://www.conan.in.th
 * chatchawal.sangkeettrakarn@nectec.or.th, conan@conan.in.th
 */
package index;

import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.th.ThaiAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.IndexableFieldType;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import model.News;
import parse.ThairathParser;


/**
 * @author conan
 *
 */
public class MyIndexer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		 java.nio.file.Path path = FileSystems.getDefault().getPath("index-news");
		 Directory dir = FSDirectory.open(path);
		 
		 /* set analyzer to analyze contents */
		 Analyzer analyzer = new ThaiAnalyzer();

				
		 
		 IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
		 
		 /* always replace old index */
		 iwc.setOpenMode(OpenMode.CREATE); 
		 
		 
		 IndexWriter writer = new IndexWriter(dir,iwc);
		
		
		 String[] ulist = {
		 			"https://www.thairath.co.th/lifestyle/tech",
		 			"https://www.thairath.co.th/lifestyle/auto",
		 			"https://www.thairath.co.th/lifestyle/woman",
		 			"https://www.thairath.co.th/lifestyle/tech/technology",
		 			"https://www.thairath.co.th/lifestyle/tech/review",
		 			"https://www.thairath.co.th/wongnai",
		 			"https://www.thairath.co.th/ent",

		 					};
		 
		 
		 for(String u : ulist) {
			 ArrayList<News> nlist = new ThairathParser().getAllNews(u);
			 for(News n : nlist) {
				 
				 Document d = new Document();
				 
				 d.add(new Field("title", n.getTitle(), TextField.TYPE_STORED));
				 d.add(new Field("content", n.getContent(), TextField.TYPE_STORED));
				 d.add(new Field("category", n.getCategory(), StringField.TYPE_STORED));
				 d.add(new Field("url", n.getUrl(), StringField.TYPE_STORED));
				 d.add(new Field("publisher", n.getPublisher(), StringField.TYPE_STORED));
				 d.add(new Field("datetime", n.getDatetime()+"", TextField.TYPE_STORED));
				 d.add(new Field("dtime", new SimpleDateFormat("yyyyMMddHHmm").format(n.getDatetime()), StringField.TYPE_STORED));
				 
				 writer.addDocument(d);
				 
			 }
		 }	
		 
		 writer.close();
	}

}
