package download;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class getmoban {

	public static void main(String[] args) throws IOException
	{
		ExecutorService ex=Executors.newFixedThreadPool(6);
		Scanner sc=new Scanner(System.in);
		System.out.println("请输入网址（别太大否则下载不完）");
		String url=sc.nextLine();
		geturl g=new geturl(url);//
		csssearch cssimage=new csssearch();
		System.out.println(g.file);
		g.judel();		
		Iterator<String> it=g.htmlurlset.iterator();		
		while(it.hasNext())
		{
			String name=it.next();
			try {
				download download=new download(name);
				
			    ex.execute(download);	
			}
			catch(Exception e){}
			//System.out.println("地址为"+name);
		}
		Iterator<String> it2=g.jsset.iterator();
		while(it2.hasNext())
		{
			String name=it2.next();
			try {
				download download=new download(name);
			    ex.execute(download);	
			}
				catch(Exception e){}
			//System.out.println("js地址为"+name);
		}
		Iterator<String> it3=g.cssset.iterator();
		while(it3.hasNext())//css需要过滤其中是否有背景图片
		{
			String name=it3.next();
			try {
				download download=new download(name);
				ex.execute(download);
				cssimage.searchimage(name);
			}
				catch(Exception e){}
			//System.out.println("css地址为"+name);
		}
		Iterator<String> it4=g.imgset.iterator();
		while(it4.hasNext())
		{
			String name=it4.next();
			try {
				download download=new download(name);
			    ex.execute(download);	
			}
				catch(Exception e){}
			//System.out.println("image地址为"+name);
		}
		ex.shutdown();
		//judel();
	}
}
