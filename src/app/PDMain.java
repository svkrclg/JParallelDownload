package app;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
class PDMain
{
  static int status;
  static String fileName;
  static URL url;
  static String tempFolder;
  public static void main(String args[])
  {
    long startTime=System.currentTimeMillis();
    try{
    url = new URL("Download Link");
    HttpURLConnection httpConnection =(HttpURLConnection) url.openConnection();
    httpConnection.setRequestMethod("HEAD");
    Long fileSize=httpConnection.getContentLengthLong();
    String isResumable= httpConnection.getHeaderField("Accept-Ranges");
    fileName= httpConnection.getHeaderField("Content-Disposition"); 
    tempFolder=System.currentTimeMillis()+"";
    if(fileName==null)
    {
       int slashIndex=url.toString().lastIndexOf("/");
       fileName=url.toString().substring(slashIndex+1, url.toString().length());
    } 
    System.out.println(fileSize+" "+isResumable+" "+fileName);
    Long t1From, t1To, t2From, t2To, t3From, t3To, t4From, t4To,
         t5From, t5To, t6From, t6To, t7From, t7To, t8From, t8To=000L;
    if(fileSize%8==0)
    {
      Long multiple=fileSize/8;
      t1From=0L;
      t1To=(1*multiple) - 1;

      t2From=t1To+1;
      t2To=2*multiple - 1;

      t3From=t2To+1;
      t3To=3*multiple-1;

      t4From=t3To+1;
      t4To=4*multiple-1;
      
      t5From=t4To+1;
      t5To=5*multiple-1;

      t6From=t5To+1;
      t6To=6*multiple-1;

      t7From=t6To+1;
      t7To=7*multiple-1;

      t8From=t7To+1;
      t8To=8*multiple-1; 
    }
    else
    {
      Long multiple=fileSize/8;
      t1From=000L;
      t1To=(1*multiple) - 1;

      t2From=t1To+1;
      t2To=2*multiple - 1;

      t3From=t2To+1;
      t3To=3*multiple-1;

      t4From=t3To+1;
      t4To=4*multiple-1;
      
      t5From=t4To+1;
      t5To=5*multiple-1;

      t6From=t5To+1;
      t6To=6*multiple-1;

      t7From=t6To+1;
      t7To=7*multiple-1;

      t8From=t7To+1;
      t8To=fileSize-1; 
    }
    //System.out.println(t1From +" - "+t1To+"\n"+ t2From +" - "+t2To+"\n"+ t3From +" - "+t3To+"\n"+t4From +" - "+t4To+"\n"+t5From +" - "+t5To+"\n"+t6From +" - "+t6To+"\n"+t7From +" - "+t7To+"\n"+ t8From +" - "+t8To+"\n");
    createFolder();
    PDMain pdobj=new PDMain();
    Runnable r1=pdobj.new DownloadPart(fileName+"$1", t1From, t1To);
    Runnable r2=pdobj.new DownloadPart(fileName+"$2", t2From, t2To);
    Runnable r3=pdobj.new DownloadPart(fileName+"$3", t3From, t3To);
    Runnable r4=pdobj.new DownloadPart(fileName+"$4", t4From, t4To);
    Runnable r5=pdobj.new DownloadPart(fileName+"$5", t5From, t5To);
    Runnable r6=pdobj.new DownloadPart(fileName+"$6", t6From, t6To); 
    Runnable r7=pdobj.new DownloadPart(fileName+"$7", t7From, t7To);
    Runnable r8=pdobj.new DownloadPart(fileName+"$8", t8From, t8To);
    ExecutorService downloader=Executors.newFixedThreadPool(8);
    downloader.execute(r1);
    downloader.execute(r2);
    downloader.execute(r3);
    downloader.execute(r4);
    downloader.execute(r5);
    downloader.execute(r6);
    downloader.execute(r7);
    downloader.execute(r8);
    downloader.shutdown();
  //  while(ThreadProgress.Thread1!=100 && ThreadProgress.Thread2!=100 && ThreadProgress.Thread3!=100 && ThreadProgress.Thread4!=100 && ThreadProgress.Thread5!=100 && ThreadProgress.Thread6!=100 && ThreadProgress.Thread7!=100 && ThreadProgress.Thread8!=100)
 //   {
 //   	System.out.print("Thread1: "+ThreadProgress.Thread1+", " + "Thread2: "+ThreadProgress.Thread2+", "+ "Thread3: "+ThreadProgress.Thread3+", "+"Thread4: "+ThreadProgress.Thread4+", "+ "Thread5: "+ThreadProgress.Thread5+", "+"Thread6: "+ThreadProgress.Thread6+", "+ "Thread7: "+ThreadProgress.Thread7+", " + "Thread8: "+ThreadProgress.Thread8+"\r");
 //   }
    boolean result=downloader.awaitTermination(4, TimeUnit.HOURS);
    //Begin Joining
    System.out.println("Combining Downloads");
    Join j=new Join(tempFolder, fileName);
    j.startJoin();
    long endTime=System.currentTimeMillis();
    System.out.println(endTime-startTime+"");
  }
    catch(IOException e)
    {
      e.printStackTrace();
    }
    catch(InterruptedException ie)
    {
      System.out.println("Interruption: "+ ie.getMessage());
    }
  }
  public static void createFolder()
  {
    File file = new File(tempFolder);
    if (!file.exists()) {
        if (file.mkdirs()) {
            System.out.println("Directory is created!");
        } else {
            System.out.println("Failed to create directory!");
        }
    }
  }
  class DownloadPart implements Runnable
  {
    String subFileName;
    Long to, from;
    public DownloadPart(String subFileName, Long from, Long to)
    {
           this.subFileName=subFileName;
           this.to=to;
           this.from=from;
    }
    public void run()
    {
      FileOutputStream fos=null;
      BufferedInputStream br=null;
      Long size=to-from+1;
      try
      {
      HttpURLConnection httpConnection =(HttpURLConnection) url.openConnection();
      httpConnection.setRequestProperty("Range", "bytes="+from+"-"+to);
     // System.out.println(Thread.currentThread().getName()+" : "+"Response code:"+httpConnection.getResponseCode());
      long fileSize=httpConnection.getContentLengthLong();
     // System.out.println(Thread.currentThread().getName()+" : FileSize: "+fileSize);
      br= new BufferedInputStream(httpConnection.getInputStream());
      fos = new FileOutputStream(tempFolder+"/"+subFileName);
      byte[] buffer = new byte[204800];
      int bytesRead;
      double totalRead=0;
      while( (bytesRead= br.read(buffer, 0, 204800))!=-1)
      {
        //System.out.println(Thread.currentThread().getName()+" : Read : "+bytesRead+"\r");
        totalRead+=bytesRead;
        int progress=(int) (((double)totalRead/(double)size)*100);
        ThreadProgress.setProgress(Thread.currentThread().getName().substring(7), progress);
        fos.write(buffer, 0, bytesRead);
      }
    }
    catch(IOException e)
      {
       e.printStackTrace();
       Thread.currentThread().start();
      }
    finally
      {
        try
        {
        if(fos!=null && br!=null)
         {
           fos.close();
           br.close();
         }
        }
        catch(IOException e)
        {
          e.printStackTrace();
        }
      }
  }

  }
}
