package net.ciklum.tutorial.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashScreenActivity extends Activity {
   
	long m_dwSplashTime = 3000;
	boolean m_bPaused = false;
	boolean m_bSplashActive = true;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
      setContentView(R.layout.splash);
      
      //Very simple timer thread
        Thread splashTimer = new Thread()
        {
	        public void run()
	        {
		        try
		        {
			        //Wait loop
			        long ms = 0;
			        while(m_bSplashActive && ms < m_dwSplashTime)
			        {
				        sleep(100);
				        //Advance the timer only if we're running.
				        if(!m_bPaused)
				        ms += 100;
			        }
			        //Advance to the next screen.
			        startActivity(new Intent(
			        "com.google.app.splashy.CLEARSPLASH"));
		        }
		        catch(Exception e)
		        {
		        	Log.e("Splash", e.toString());
		        }
		        finally
		        {
		        	finish();
		        }
	        }
        };
        splashTimer.start();
        
    }
}