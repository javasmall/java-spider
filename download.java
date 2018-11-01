package download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class download implements Runnable{
	
	public String ur;
	public download() {}
	public download(String ur)
	{
		this.ur=ur;
	}
	public static void download(String ur) throws IOException	
	{		 
		 //String ur="http://www.17sucai.com/preview/1266961/2018-06-22/wrj/index.html";
		String fileplace=ur;
		
		if(fileplace.contains("http"))
		{
			
			fileplace=fileplace.split("//")[1];
		}
	        URL url = new URL(ur);
	       URLConnection conn =  url.openConnection();
	        conn.setConnectTimeout(4000);
	        conn.setReadTimeout(5000);
	        conn.connect();
	        InputStream in= conn.getInputStream();
	        
	        BufferedInputStream buf=new BufferedInputStream(in);
	      File file=new File("F:\\download\\"+fileplace);
	      if(!file.exists())
	      {
	    	  file.getParentFile().mkdirs();
	    	  file.createNewFile();
	      }
	       //System.out.print(file.getAbsolutePath()); 
	        BufferedOutputStream bufout=new BufferedOutputStream(new FileOutputStream(file)); 
//	        int b=0;
//	        while((b=buf.read())!=-1)
//	        {
//	        	 bufout.write(b);
//	        	 //System.out.println(b+"");
//	        }
	        byte b[]=new byte[1024];
	        int n=0;
	        while((n=buf.read(b))!=-1)
	        {
	        	bufout.write(b, 0, n);
	        }
	        in.close();
	        buf.close();			
			bufout.close();
			
			//fullFileName.close();
	    }
//    public static void main(String[] args) throws IOException
//
//    {
//       download("http://demo.cssmoban.com/cssthemes5/cpts_1019_bpi/images/banner.jpg");	
//
//    }
	@Override
	public void run() {
		try {
			download(ur);
			System.out.println(Thread.currentThread().getName()+" 下载"+ur+"成功");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	
	
}


