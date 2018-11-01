package download;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class csssearch {

	public static void searchimage(String ur) throws IOException {
		if(ur.toLowerCase().contains("bootstarp")) {return;}
		Set<String> imgset=new HashSet<String>();
		//String ur="http://demo.cssmoban.com/cssthemes5/cpts_1019_bpi/css/style.css";
		String http="http";
		String fileurl=ur;
		if(fileurl.startsWith("http"))
		{
			http=fileurl.split("//")[0];//防止https协议
			fileurl=fileurl.split("//")[1];
		}
		fileurl=fileurl.substring(0,fileurl.lastIndexOf("/"));
		//System.out.println(fileurl);//测试
		URL url=new URL(ur);
		  URLConnection conn =  url.openConnection();
	        conn.setConnectTimeout(1000);
	        conn.setReadTimeout(5000);
	        conn.connect();
	        InputStream in= conn.getInputStream();
	        InputStreamReader inp=new InputStreamReader(in);
	        BufferedReader buf=new  BufferedReader(inp);
	        File file=new File("F:\\download\\"+ur.split("//")[1]);
		      if(!file.exists())
		      {
		    	  file.getParentFile().mkdirs();
		    	  file.createNewFile();
		      }
		//      BufferedOutputStream bufout=new BufferedOutputStream(new FileOutputStream(file));
		      String tex="";
		    while((tex=buf.readLine())!=null)
		    {
//		    	System.out.println(tex);
		    	if(tex.contains("url"))
		    	{
		    		String urladress=fileurl;
		    		String imgurl=tex.split("url")[1];
		    		imgurl=imgurl.split("\\(")[1].split("\\)")[0];//转义字符串
		    		if(imgurl.startsWith("'")||imgurl.startsWith("\""))//注意转义字符串
		    		{
		    			imgurl=imgurl.substring(1,imgurl.length()-1);
		    		}
		    		//System.out.println(imgurl);//测试
		    		while(imgurl.startsWith(".."))
		    		{
		    			imgurl=imgurl.substring(imgurl.indexOf("/")+1);		    			
		    			urladress=urladress.substring(0,urladress.lastIndexOf("/"));
		    		}
		    		urladress=http+"//"+urladress+"/"+imgurl;
		    		//System.out.println(urladress);
		    		//down.download(urladress);
		    		imgset.add(urladress);
		    	}
		    }
	//	    bufout.close();
		    buf.close();
		    inp.close();
		    in.close();
		    Iterator<String> it=imgset.iterator();
		    while(it.hasNext())
		    {		    
		    	String team=it.next();
		    	
		    	try {
		    		download down=new download(team);
		    		Thread t1=new Thread(down);
		    		t1.start();System.out.println(team+"下载成功");}
		    	catch(Exception e) {System.out.println("下载失败："+team);}
		    }
		   
	}
}
