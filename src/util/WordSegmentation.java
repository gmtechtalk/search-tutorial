/**
 * S. Chatchawal 
 * May 21, 2561 BE 6:34:59 PM
 * http://www.conan.in.th
 * chatchawal.sangkeettrakarn@nectec.or.th, conan@conan.in.th
 */
package util;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.th.ThaiAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * @author conan
 *
 */
public class WordSegmentation {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
			Analyzer analyzer = new ThaiAnalyzer(null); //ignore stopwords
		
			
			String text = "กว่าพันล้าน เปิดสำนักงานใหม่ 'เฟซบุ๊ก' สุดอลังนึกว่าฝังเพชรไว้ที่พื้น";
			TokenStream stream  = analyzer.tokenStream("", text);

	        stream.reset();
	        ArrayList<String> result = new ArrayList<String>();
	            
	        while(stream.incrementToken()) {
	                String term = stream.getAttribute(CharTermAttribute.class).toString();
	                result.add(term);
	        }
	        
	         stream.close();
	         analyzer.close();
	         
	        	 System.out.println(result);

	}

}
