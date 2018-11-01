package download;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class testtest {

	public static void main(String[] args) throws IOException {
		// TODO 自动生成的方法存根
		 String url="http://www.17sucai.com/preview/140327/2014-06-05/azmind_3_xd/index.html";
		  Document doc;
			doc = Jsoup.connect(url).timeout(20000).ignoreContentType(true).get();
		 Elements img=doc.select("img");
		 for(Element imageelement:img)
		  {
			  String team=imageelement.absUrl("src");
			  
			 
			  System.out.println(team);
		  }
	}

}
