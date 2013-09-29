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
import android.graphics.RectF;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;


public class Bofang extends View { 
	  

	int COMPONENT_WIDTH;//�ؼ��Ŀ��  
	int COMPONENT_HEIGHT;//�ؼ��ĸ߶�  
    boolean initflag = false;//�Ƿ��Ѿ���ʼ��ͼƬ  
    Bitmap bmp;//�������ͼƬ  
    int currPicIndex = 0;//��ǰ����ͼƬ��ID  
//    final int[] bitmapId ={R.drawable.ic_launcher, R.drawable.ic_action_search, R.drawable.feedicons};  

    float photoWidthHeightRate=1;  //ͼƬ��߱�
    int DEFAULTWIDTH=100, DEFAULTHEIGHT=100;  //ȱʡ��ͼƬ�ؼ��Ŀ�͸�
	RectF photoRect=new RectF(0,0,DEFAULTWIDTH,DEFAULTHEIGHT); 
    

    
    int photoCount=0;  //ͼƬ����
    String[] strFileName;


    
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
        	Bofang.this.postInvalidate();//ˢ����Ļ
       }
    }

	
	
    //��ʼ��ͼƬ  
 
  /*  public void initBitmap()  
    {  
        //��ȡ��ԴͼƬ  
        Resources res = this.getResources();  

        for(int i=0;i<bitmapId.length;i++)  
        {  
            bmp[i] = BitmapFactory.decodeResource(res, bitmapId[i]);  
        }
	
    }  
    */
    
    //** ���ر���ͼƬ   
    public Bitmap getLoacalBitmap(String url) {
    	try {
    		FileInputStream fis = new FileInputStream(url);
    		return BitmapFactory.decodeStream(fis);
    	} 
    	catch (FileNotFoundException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
 
    //��дonDraw����  
    @Override 
    protected void onDraw(Canvas canvas)   
    {
        // TODO Auto-generated method stub  
    	super.onDraw(canvas);  

        
    	
    	
        if(initflag)//����Ƿ��Ѿ����ͼƬ
        {  
            COMPONENT_WIDTH = this.getWidth();  
            COMPONENT_HEIGHT = this.getHeight();  
        	photoWidthHeightRate=bmp.getScaledWidth(canvas) / bmp.getScaledHeight(canvas);
            photoRect=new RectF(0,0,COMPONENT_WIDTH,(int)(COMPONENT_WIDTH/photoWidthHeightRate));
            //canvas.drawBitmap(bmp, 0, 0, null);//����ͼƬ
    		canvas.drawBitmap(bmp,null,photoRect,null);
            //canvas.drawText("width",10,50,null);
    		initflag=false;

        }  
        
                
    }  
 
} 
