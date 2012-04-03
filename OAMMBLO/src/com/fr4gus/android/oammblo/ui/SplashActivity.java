package com.fr4gus.android.oammblo.ui;

import com.fr4gus.android.oammblo.R;

import android.content.Intent;
import android.os.Bundle;

/**
 * Shows App logo for few seconds.
 * 
 * @author Franklin Garcia
 * Created Mar 25, 2012
 */
public class SplashActivity extends OammbloActivity {

	private int splashTime = 1000;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        thread.start();
        
    }
    
    Thread thread = new Thread(){
    	@Override
    	public void run() {
    		try{
    			int waited = 0;
        		while(waited <= splashTime){
        			Thread.sleep(100);
        			waited += 100;
        		}
    		}catch (Exception e) {
				
			}finally{
				finish();
				Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
				startActivity(intent);
				
			}
    	};
    };

}
