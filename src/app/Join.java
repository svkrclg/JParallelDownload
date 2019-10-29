package app;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Join {
 private String fileName;
 private String tempFolder;
 public Join(String t, String s)
  {
	 this.tempFolder=t;
	 this.fileName=s;
  }
 public void startJoin()
 {
	 //creating new file
	 try {
	    File f=new File(fileName);
	    if(f.exists())
	    {
	    	System.out.println("already exists");
	    }
	    else
	    {
	    	boolean r=f.createNewFile();
	        System.out.println(r==true?"Success":"fail");
	    }
		FileOutputStream fos= new FileOutputStream(f);
		BufferedOutputStream bos=new BufferedOutputStream(fos);
		for(int i=1;i<=8;i++)
		{
			BufferedInputStream bis=new BufferedInputStream(new FileInputStream(tempFolder+"/"+fileName+"$"+i));
			byte[] buffer= new byte[10*1024*1024];
			int read=-1;
			while( ( read=bis.read(buffer, 0, 10*1024*1024))!=-1)
			{
				fos.write(buffer, 0, read);
			}
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
 }
 
}
