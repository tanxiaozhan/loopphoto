package com.txz.loopphoto;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Context;


public class MainActivity extends Activity {

	Button buttonStart=null;
	Button buttonStop=null;
	Button buttonQuit=null;
	
	Bofang loopImage=null;
	
	TextView textViewDisplayTime=null;
	SeekBar seekBarChangeTime=null;
	
	Context context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        loopImage=(Bofang)findViewById(R.id.bofang1);

        seekBarChangeTime=(SeekBar)findViewById(R.id.seekBarTime);
       	textViewDisplayTime=(TextView)findViewById(R.id.textViewTime);
        
        buttonStart=(Button)findViewById(R.id.buttonStart);
        buttonStop=(Button)findViewById(R.id.buttonStop);
        
        buttonStop.setEnabled(false);
        
       	buttonStop.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            // Do something in response to button click
	        	buttonStart.setEnabled(true);
	        	buttonStop.setEnabled(false);
	        	seekBarChangeTime.setEnabled(true);
	        	
	        	loopImage.cancelRun();

	        }
    	});        
        
        buttonQuit=(Button)findViewById(R.id.buttonQuit);
       	buttonQuit.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            // Do something in response to button click
	        	System.exit(0);

	        }
    	});        

       	context=this;
       	textViewDisplayTime.setText(this.getString(R.string.displayInterval) + String.valueOf((float)seekBarChangeTime.getProgress()/(float)10)+ "√Î");
       	
        
       	seekBarChangeTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

       		@Override
       		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
       				// TODO Auto-generated method stub
       				textViewDisplayTime.setText(context.getString(R.string.displayInterval) + String.valueOf((float)progress/(float)10)+ "√Î");
       		}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
       	});
       	
       	buttonStart.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            // Do something in response to button click
	        	buttonStop.setEnabled(true);
	        	buttonStart.setEnabled(false);
	        	seekBarChangeTime.setEnabled(false);
	        	
	        	loopImage.start(seekBarChangeTime.getProgress()*100+1);
	        }
    	});        
        
       	

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    
    protected void onPause(){
    	System.exit(0);
    }

    
    
}
