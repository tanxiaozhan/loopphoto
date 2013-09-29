package com.txz.loopphoto;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

	Button buttonStart=null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        buttonStart=(Button)findViewById(R.id.buttonStart);
       	buttonStart.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            // Do something in response to button click
	        	
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
