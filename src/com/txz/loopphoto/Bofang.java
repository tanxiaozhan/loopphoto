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
	  

	int COMPONENT_WIDTH;//�ؼ��Ŀ��  
	int COMPONENT_HEIGHT;//�ؼ��ĸ߶�  
	boolean initflag = false;//�Ƿ��Ѿ���ʼ��ͼƬ  
    Bitmap bmp=null;//�������ͼƬ  
    int currPicIndex = 0;//��ǰ����ͼƬ��ID  
//    final int[] bitmapId ={R.drawable.ic_launcher, R.drawable.ic_action_search, R.drawable.feedicons};  

    float photoWidthHeightRate=1;  //ͼƬ��߱�
    int DEFAULTWIDTH=100, DEFAULTHEIGHT=100;  //ȱʡ��ͼƬ�ؼ��Ŀ�͸�
	RectF photoRect=new RectF(0,0,DEFAULTWIDTH,DEFAULTHEIGHT); 
	ScheduledFuture<?> future;

    
    int photoCount=0;  //ͼƬ����
    String[] strFileName;
    
    String photoInfo="";


    
	public Bofang(Context context,AttributeSet attr) {
		super(context,attr);
		// TODO Auto-generated constructor stub
		
		//���ͼƬĿ¼�µ��ļ���������ŵ��ַ�����
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
	/  ��ʼִ�ж�ʱ����
	/  �ӳ�ʱ�䵥λ������
	////////////////////*/
	public void start(int delayTimeMS){
		//��ǰ�ؼ��Ŀ�͸�
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
        	Bofang.this.postInvalidate();//ˢ����Ļ
        	currPicIndex++;
       }
    }

    
	public int calculateInSampleSize(BitmapFactory.Options options,  
	        int reqWidth, int reqHeight) {  
	    // ԴͼƬ�ĸ߶ȺͿ��  
	    final int height = options.outHeight;  
	    final int width = options.outWidth;  
	    int inSampleSize = 1;  
	    if (height > reqHeight || width > reqWidth) {  
	        // �����ʵ�ʿ�ߺ�Ŀ���ߵı���  
	        final int heightRatio = Math.round((float) height / (float) reqHeight);  
	        final int widthRatio = Math.round((float) width / (float) reqWidth);  
	        // ѡ���͸�����С�ı�����ΪinSampleSize��ֵ���������Ա�֤����ͼƬ�Ŀ�͸�  
	        // һ��������ڵ���Ŀ��Ŀ�͸ߡ�  
	        inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
	    }  

	    return inSampleSize;  
	}
	
	//** ���ر���ͼƬ   
	public Bitmap decodeBitmapFromFile(String url) {  
	    // ��һ�ν�����inJustDecodeBounds����Ϊtrue������ȡͼƬ��С  
	    final BitmapFactory.Options options = new BitmapFactory.Options();  
	    options.inJustDecodeBounds = true;  
	    try{
	    BitmapFactory.decodeFile(url,options);  
	    }catch(Exception e){
	    	
	    }
	    
	    // �������涨��ķ�������inSampleSizeֵ  
	    options.inSampleSize = calculateInSampleSize(options, COMPONENT_WIDTH, COMPONENT_HEIGHT );  
	    // ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ 
	    options.inJustDecodeBounds = false;  
	    return  BitmapFactory.decodeFile(url,options) ;  
	}  
	

    
    //��дonDraw����  
    @Override 
    protected void onDraw(Canvas canvas)   
    {
        // TODO Auto-generated method stub  
    	super.onDraw(canvas);  

        
    	
    	
        if(initflag)//����Ƿ��Ѿ����ͼƬ
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
