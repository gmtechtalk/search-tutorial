/**
 * S. Chatchawal 
 * May 20, 2561 BE 1:58:19 AM
 * http://www.conan.in.th
 * chatchawal.sangkeettrakarn@nectec.or.th, conan@conan.in.th
 */
package search;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.th.ThaiAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import util.DidYouMean;


/**
 * @author conan
 *
 */
public class MySimpleSearcher {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub

		
		Path path = FileSystems.getDefault().getPath("index-news");
		Directory dir = FSDirectory.open(path);
		IndexReader r = DirectoryReader.open(dir);
		
		
		IndexSearcher searcher = new IndexSearcher(r);

		 
		Analyzer analyzer = new ThaiAnalyzer();
		
		String keyword ="อาการ";
		
		String[] wlist = new  DidYouMean().get(keyword, 3);
		Builder bqall = new BooleanQuery.Builder();

		for(String k : wlist) {
		QueryParser qp1 = new QueryParser("content",analyzer);
		qp1.setDefaultOperator(Operator.AND);
		Query query1 = qp1.parse(k);

		QueryParser qp2 = new QueryParser("title",analyzer);
		qp2.setDefaultOperator(Operator.AND);
		Query query2 = qp2.parse(k);
		
		TermQuery tq = new TermQuery(new Term("publisher", k));

		Builder bq = new BooleanQuery.Builder();
		bq.add(query1, Occur.MUST);
		bq.add(query2,Occur.SHOULD);
		bq.add(tq,Occur.SHOULD);
		
		bqall.add(bq.build(), Occur.SHOULD);
		
		System.out.println(bq.build().toString());
		
		}
		System.out.println(bqall.build());
		TopDocs tops = searcher.search(bqall.build(), 5);
		ScoreDoc[] sd = tops.scoreDocs;

		for(ScoreDoc s : sd){
				Document d = searcher.doc(s.doc);
				String title = d.get("title");
				String content = d.get("content");
				String url = d.get("url");
				String date = d.get("datetime");
				System.out.println("\n"+title+" "+date+" "+url);
		}
			
		r.close();
		
		
		
		
		
		
	}
	
	

}
