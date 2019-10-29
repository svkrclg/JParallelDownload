package app;

public class ThreadProgress {
    public static  int Thread1=0;
    public static int Thread2=0;
    public static int Thread3=0;
    public static int Thread4=0;
    public static int Thread5=0;
    public static int Thread6=0;
    public static int Thread7=0;
    public static int Thread8=0;
    public static void setProgress(String s, int p)
    {
    	System.out.println(s);
    	switch(s.charAt(6))
    	{
    	case 1: Thread1=p;
    	break;
    	case 2: Thread2=p;
    	break;
    	case 3: Thread3=p;
    	break;
    	case 4: Thread4=p;
    	break;
    	case 5: Thread5=p;
    	break;
    	case 6: Thread6=p;
    	break;
    	case 7: Thread7=p;
    	break;
    	case 8: Thread8=p;
    	break;
    	}
    }
    
}
