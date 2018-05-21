/**
 * S. Chatchawal 
 * May 20, 2561 BE 11:29:58 AM
 * http://www.conan.in.th
 * chatchawal.sangkeettrakarn@nectec.or.th, conan@conan.in.th
 */
package collect;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author conan
 *
 */
public class WebCollector {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String useragent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:42.0) Gecko/20100101 Firefox/42.0";

		String url = "http://www.boomerangshop.com/web/index.php/app/product/cate/bluray/category/new_release/page/1";
		
		Document doc = Jsoup.connect(url)
				.timeout(10000)
				.userAgent(useragent)
				.get();
		
		
		Elements es = doc.select("li"); 
		
		for(Element e : es) {
			Element h = e.select("div h3 a").first();
			if(h!=null) {
				System.out.println(h.text());
				System.out.println(h.absUrl("href"));
			}
			Element p = e.select("span[class=green font14]").first();
			if(p!=null) {
				System.out.println(p.text());
				System.out.println();
			}

		}

		

	}

}
