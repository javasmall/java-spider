package download;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class geturl {
	
	public static String url="http://www.17sucai.com/preview/1324218/2018-07-31/courseware/index.html";
	static String head="http";
	public geturl(String url)
	{
		this.url=url;
	}
	static String file=url;//文件路径
	{
		if(url.contains("http"))
		{
			head=file.split("//")[0];
			file=file.split("//")[1];
		}
		int last=file.lastIndexOf("/");
		file=file.substring(0, last);
	}
	static Set<String> htmlurlset=new HashSet<String>();//html
	static Set<String> jsset=new HashSet<String>();//js
	static Set <String>imgset=new HashSet<String>();//image
	static Set <String>cssset=new HashSet<String>();//css样式
	static Queue<String> queue=new ArrayDeque<String>();
	
//	public geturl() throws IOException 
//	{this.judel();}
	public static void judel() throws IOException 
	{
		queue.add(url);htmlurlset.add(url);
		while(!queue.isEmpty()&&queue!=null)//要防止链接无限扩大
		{
			String teamurl=queue.poll();//弹出头并且删除节点
			System.out.println(teamurl);
			
			if(!teamurl.endsWith(".com"))//有的网站短小，可能识别有错误	
			{
			if(file.indexOf("/")>0)
			{if(teamurl.contains(file.substring(0,file.indexOf("/"))))
			analyze(teamurl);}
			else
				analyze(teamurl);
			}
//			catch(Exception e) {System.out.println("cuo");}			
		}
		
	}
			
	public static void analyze(String URL)
	{
		try {
	  Document doc;
		doc = Jsoup.connect(URL).timeout(20000).header("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36").ignoreContentType(true).get();
		 Elements all=doc.select("[class]");//检查
		  Elements js=doc.getElementsByTag("script");
		  Elements html=doc.select("a[href]");
		  Elements img=doc.select("img");
		  Elements css=doc.select("link[href]");
		  for(Element e:all)
		  {
			  if(e.attr("style")!="")//找到藏在html的css的图片背景
			  { 
				  String tex=e.attr("style");
				  if(tex.contains("url"))
				  {
					  String urladress=file;
			    		String imgurl=tex.split("url")[1];
			    		imgurl=imgurl.split("\\(")[1].split("\\)")[0];//转义字符串
			    		if(imgurl.startsWith("'")||imgurl.startsWith("\""))//注意转义字符串
			    		{
			    			imgurl=imgurl.substring(1,imgurl.length()-1);
			    		} 
			    		while(imgurl.startsWith(".."))
			    		{
			    			imgurl=imgurl.substring(imgurl.indexOf("/")+1);		    			
			    			urladress=urladress.substring(0,urladress.lastIndexOf("/"));
			    		}
			    		urladress=head+"//"+urladress+"/"+imgurl;
			    		imgset.add(urladress);
				  }				  
			  }
		  }
		  for(Element htmlelement:html)
		  {		 		
			  String a=htmlelement.absUrl("href").split("#")[0];
			  
			  if(!a.equals(""))
			  {
				  if(!htmlurlset.contains(a)&&a.contains(file.substring(0,file.indexOf("/"))))//不存在继续遍历
				  { queue.add(a);htmlurlset.add(a); //System.out.println(a);
				  }			 
			  }				  
		  }
		  for(Element jselement:js)//判断JS
		  {
			  String team=jselement.absUrl("src");	
			  if(!team.equals(""))
			  jsset.add(team);//添加

		  }
		  for(Element csselement:css)
		  {
			  String team=csselement.absUrl("href");
			  if(!team.equals(""))//绝对路径
			  cssset.add(team);			
			 // System.out.println(e.attr("href"));
		  }
		  for(Element imageelement:img)
		  {
			  String team=imageelement.absUrl("src");
			  if(!team.equals(""))//绝对路径
			  imgset.add(team);
			 
			  //System.out.println(e.attr("href"));
		  }
		}
		catch(Exception e)
		{
			if(!queue.isEmpty()) {
			URL=queue.poll();
			 analyze(URL);}
		}
	} 				  
//	public static void main(String[] args) throws IOException
//	{
//		ExecutorService ex=Executors.newFixedThreadPool(10);
//		geturl g=new geturl();//
//		csssearch cssimage=new csssearch();
//		System.out.println(g.file);
//		g.judel();		
//		Iterator<String> it=g.htmlurlset.iterator();		
//		while(it.hasNext())
//		{
//			String name=it.next();
//			try {
//				download download=new download(name);
//			    ex.execute(download);	
//			}
//			catch(Exception e){}
//			System.out.println("地址为"+name);
//		}
//		Iterator<String> it2=g.jsset.iterator();
//		while(it2.hasNext())
//		{
//			String name=it2.next();
//			try {
//				download download=new download(name);
//			    ex.execute(download);	
//			}
//				catch(Exception e){}
//			System.out.println("js地址为"+name);
//		}
//		Iterator<String> it3=g.cssset.iterator();
//		while(it3.hasNext())//css需要过滤其中是否有背景图片
//		{
//			String name=it3.next();
//			try {
//				download download=new download(name);
//				ex.execute(download);
//				cssimage.searchimage(name);
//			}
//				catch(Exception e){}
//			System.out.println("css地址为"+name);
//		}
//		Iterator<String> it4=g.imgset.iterator();
//		while(it4.hasNext())
//		{
//			String name=it4.next();
//			try {
//				download download=new download(name);
//			    ex.execute(download);	
//			}
//				catch(Exception e){}
//			System.out.println("image地址为"+name);
//		}
//		ex.shutdown();
//		//judel();
//	}
	}

