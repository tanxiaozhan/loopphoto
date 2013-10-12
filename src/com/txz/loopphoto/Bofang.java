package com.txz.loopphoto;


import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;


public class Bofang extends View { 
	  

	int COMPONENT_WIDTH;//控件的宽度  
	int COMPONENT_HEIGHT;//控件的高度  
	boolean initflag = false;//是否已经初始化图片  
    Bitmap bmp=null;//用来存放图片  
    int currPicIndex = 0;//当前播放图片的ID  
//    final int[] bitmapId ={R.drawable.ic_launcher, R.drawable.ic_action_search, R.drawable.feedicons};  

    float photoWidthHeightRate=1;  //图片宽高比
    int DEFAULTWIDTH=100, DEFAULTHEIGHT=100;  //缺省的图片控件的宽和高
	RectF photoRect=new RectF(0,0,DEFAULTWIDTH,DEFAULTHEIGHT); 
	ScheduledFuture<?> future;

    
    int photoCount=0;  //图片总数
    String[] strFileName;
    
    String photoInfo="";


    
	public Bofang(Context context,AttributeSet attr) {
		super(context,attr);
		// TODO Auto-generated constructor stub
		
		//获得图片目录下的文件名，并存放到字符数组
		File file=new File(Environment.getExternalStorageDirectory().getPath() + context.getString(R.string.photo_dir));
    	strFileName=file.list();

		photoCount=strFileName.length;
		for(int i=0;i<photoCount;i++)
			strFileName[i]=Environment.getExternalStorageDirectory().getPath() + context.getString(R.string.photo_dir) + strFileName[i]; 

	}  
 
	public void cancelRun(){
		future.cancel(true);
	}
	
	/* **********************
	/  开始执行定时程序
	/  延迟时间单位：毫秒
	////////////////////*/
	public void start(int delayTimeMS){
		//当前控件的宽和高
		COMPONENT_WIDTH = this.getWidth();  
        COMPONENT_HEIGHT = this.getHeight()-200;
        

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        future =scheduler.scheduleAtFixedRate(new runner(), 0, delayTimeMS, TimeUnit.MILLISECONDS);
		
	}
	
	public class runner implements Runnable
    {
        public void run()
        {
            // TODO Auto-generated method stub
        	currPicIndex = currPicIndex % photoCount;
        	bmp=decodeBitmapFromFile(strFileName[currPicIndex]);
        	//photoInfo=String.valueOf(currPicIndex) + strFileName[currPicIndex];
        	initflag=true;
        	Bofang.this.postInvalidate();//刷新屏幕
        	currPicIndex++;
       }
    }

    
	public int calculateInSampleSize(BitmapFactory.Options options,  
	        int reqWidth, int reqHeight) {  
	    // 源图片的高度和宽度  
	    final int height = options.outHeight;  
	    final int width = options.outWidth;  
	    int inSampleSize = 1;  
	    if (height > reqHeight || width > reqWidth) {  
	        // 计算出实际宽高和目标宽高的比率  
	        final int heightRatio = Math.round((float) height / (float) reqHeight);  
	        final int widthRatio = Math.round((float) width / (float) reqWidth);  
	        // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高  
	        // 一定都会大于等于目标的宽和高。  
	        inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
	    }  

	    return inSampleSize;  
	}
	
	//** 加载本地图片   
	public Bitmap decodeBitmapFromFile(String url) {  
	    // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小  
	    final BitmapFactory.Options options = new BitmapFactory.Options();  
	    options.inJustDecodeBounds = true;  
	    try{
	    BitmapFactory.decodeFile(url,options);  
	    }catch(Exception e){
	    	
	    }
	    
	    // 调用上面定义的方法计算inSampleSize值  
	    options.inSampleSize = calculateInSampleSize(options, COMPONENT_WIDTH, COMPONENT_HEIGHT );  
	    // 使用获取到的inSampleSize值再次解析图片 
	    options.inJustDecodeBounds = false;  
	    return  BitmapFactory.decodeFile(url,options) ;  
	}  
	

    
    //覆写onDraw方法  
    @Override 
    protected void onDraw(Canvas canvas)   
    {
        // TODO Auto-generated method stub  
    	super.onDraw(canvas);  

        
    	
    	
        if(initflag)//检查是否已经获得图片
        {  
        	Paint paint=new Paint();
        	photoWidthHeightRate=(float)bmp.getScaledWidth(canvas) / (float)bmp.getScaledHeight(canvas);
            photoRect=new RectF(0,0,COMPONENT_WIDTH,(int)((float)COMPONENT_WIDTH/photoWidthHeightRate));

            canvas.drawBitmap(bmp,null,photoRect,null);
    		paint.setARGB(200,255,0,0);
    		paint.setTextSize(50);
    		canvas.drawText(photoInfo,10,50,paint);
    		initflag=false;

        }  
        
                
    }  
 
} 
