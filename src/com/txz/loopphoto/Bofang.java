package com.txz.loopphoto;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
    Bitmap bmp;//用来存放图片  
    int currPicIndex = 0;//当前播放图片的ID  
//    final int[] bitmapId ={R.drawable.ic_launcher, R.drawable.ic_action_search, R.drawable.feedicons};  

    float photoWidthHeightRate=1;  //图片宽高比
    int DEFAULTWIDTH=100, DEFAULTHEIGHT=100;  //缺省的图片控件的宽和高
	RectF photoRect=new RectF(0,0,DEFAULTWIDTH,DEFAULTHEIGHT); 
    

    
    int photoCount=0;  //图片总数
    String[] strFileName;
    
    String photoInfo="";


    
	public Bofang(Context context,AttributeSet attr) {
		super(context,attr);
		// TODO Auto-generated constructor stub
    	File file=new File(Environment.getExternalStorageDirectory().getPath() + context.getString(R.string.photo_dir));
    	File[] files=file.listFiles();
		
//        final int[] bitmapId ={R.drawable.ic_launcher, R.drawable.ic_action_search, R.drawable.feedicons};  

    	strFileName=new String[10];
        for(File currentFile:files){
       		strFileName[photoCount++]=Environment.getExternalStorageDirectory().getPath() + context.getString(R.string.photo_dir) + currentFile.getName();
        		
        }
        
        //控件的宽，高
        COMPONENT_WIDTH = this.getWidth();  
        COMPONENT_HEIGHT = this.getHeight();  

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(new runner(), 0, 500, TimeUnit.MILLISECONDS);

	}  
 
	
	public class runner implements Runnable
    {
        public void run()
        {
            // TODO Auto-generated method stub
        	currPicIndex = (currPicIndex+1) % photoCount;
        	bmp=getLoacalBitmap(strFileName[currPicIndex]);

        	initflag=true;
        	Bofang.this.postInvalidate();//刷新屏幕
       }
    }

	
	
    //初始化图片  
 
  /*  public void initBitmap()  
    {  
        //获取资源图片  
        Resources res = this.getResources();  

        for(int i=0;i<bitmapId.length;i++)  
        {  
            bmp[i] = BitmapFactory.decodeResource(res, bitmapId[i]);  
        }
	
    }  
    */
    
    
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
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	        photoInfo=String.valueOf(inSampleSize);
	    }  
	    return inSampleSize;  
	}
	
	public Bitmap decodeSampledBitmapFromFile(String url) {  
	    // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小  
	    final BitmapFactory.Options options = new BitmapFactory.Options();  
	    options.inJustDecodeBounds = true;  
	    BitmapFactory.decodeFile(url);  
	    // 调用上面定义的方法计算inSampleSize值  
	    options.inSampleSize = calculateInSampleSize(options, COMPONENT_WIDTH, COMPONENT_HEIGHT );  
	    // 使用获取到的inSampleSize值再次解析图片  
	    options.inJustDecodeBounds = false;  
	    return BitmapFactory.decodeFile(url) ;  
	}  
	
	//** 加载本地图片   
    public Bitmap getLoacalBitmap(String url) {
    		//FileInputStream fis = new FileInputStream(url);
    		//return BitmapFactory.decodeStream(fis);
    		return decodeSampledBitmapFromFile(url); 
    	 
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
        	COMPONENT_WIDTH = this.getWidth();  
            COMPONENT_HEIGHT = this.getHeight();  
        	photoWidthHeightRate=bmp.getScaledWidth(canvas) / bmp.getScaledHeight(canvas);
            photoRect=new RectF(0,0,COMPONENT_WIDTH,(int)(COMPONENT_WIDTH/photoWidthHeightRate));
            //canvas.drawBitmap(bmp, 0, 0, null);//绘制图片
    		canvas.drawBitmap(bmp,null,photoRect,null);
    		canvas.drawText(photoInfo,10,50,paint);
    		initflag=false;

        }  
        
                
    }  
 
} 
