package net.ciklum.tutorial.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HelloDialog extends Activity 
implements OnClickListener {
   
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btn = (Button) findViewById(R.id.alertdlg_simple);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.alertdlg_list);
        btn.setOnClickListener(this);

        ((Button) findViewById(R.id.alertdlg_list_Radio)).setOnClickListener(this);
        ((Button) findViewById(R.id.alertdlg_list_Check)).setOnClickListener(this);
        ((Button) findViewById(R.id.progressdlg_ring)).setOnClickListener(this);
        ((Button) findViewById(R.id.progressdlg_bar)).setOnClickListener(this);
        ((Button) findViewById(R.id.customdlg_xml)).setOnClickListener(this);
             
    }
    @Override
	public void onClick(View v) {
	    switch(v.getId())
	    {
	    case R.id.alertdlg_simple:
	    	ShowAlertDialogSimple();
	    	break;
	    case R.id.alertdlg_list:
	    	ShowAlertDialogList();
	    	break;
	    case R.id.alertdlg_list_Radio:
	    	ShowAlertDialogList_Radio();
	    	break;
	    case R.id.alertdlg_list_Check:
	    	ShowAlertDialogList_Check();
	    	break;
	    case R.id.progressdlg_ring:
	    	ShowProgressDialog_Round();
	    	break;
	    case R.id.progressdlg_bar:
	    	ShowProgressDialog_Bar();
	    	break;
	    case R.id.customdlg_xml:
	    	ShowCustomDialog_Xml();
	    	break;
	     case R.id.popup:
	    	 /** Start a new Activity MyCards.java */
	    	//Intent intent = new Intent(this, MyCards.class);
	    	 // this.startActivity(intent);

	    	break;
	    }
	  }

    private void ShowAlertDialogSimple()
    {
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure you want to exit?")
    		   .setTitle("User Confirmation")
    		   .setIcon(R.drawable.icon)
    	       .setCancelable(false)
    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	               OnOK();
    	        	   dialog.dismiss();
    	           }
    	       })
    	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	        	   OnCancel();
    	                dialog.cancel();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
    }

	protected void OnCancel() {
		// TODO Auto-generated method stub
		TextView txt = (TextView) findViewById(R.id.title);
		txt.setText("Cancel pressed");
	}

	protected void OnOK() {
		// TODO Auto-generated method stub
		TextView txt = (TextView) findViewById(R.id.title);
		txt.setText("OK pressed");
	}
	
	private void ShowAlertDialogList()
    {
	   final CharSequence[] items = {"Red", "Green", "Blue"};
	   AlertDialog.Builder builder = new AlertDialog.Builder(this);
	   builder.setTitle("Pick a color");
	   builder.setItems(items, new DialogInterface.OnClickListener() {
	       public void onClick(DialogInterface dialog, int item) {
	           Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_LONG).show();
	       }
	   });
	   AlertDialog alert = builder.create();
	   alert.show();
    }
	
	private void ShowAlertDialogList_Radio() {
		// TODO Auto-generated method stub
		final CharSequence[] items = {"Red", "Green", "Blue"};
		   AlertDialog.Builder builder = new AlertDialog.Builder(this);
		   builder.setTitle("Pick a color");
		   builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
			    }
			});
		   AlertDialog alert = builder.create();
		   alert.show();
	}
	
	private void ShowAlertDialogList_Check() {
		// TODO Auto-generated method stub
	/*final CharSequence[] items = {"Red", "Green", "Blue"};
	   AlertDialog.Builder builder = new AlertDialog.Builder(this);
	   builder.setTitle("Pick a color");
	   builder.setMultiChoiceItems(items, -1, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
		    }
		});
	   AlertDialog alert = builder.create();
	   alert.show();*/
	}

	private void ShowProgressDialog_Round() {
		ProgressDialog dialog = ProgressDialog.show(this, "", 
                "Loading. Please wait...", true);
		dialog.show();
	}
	
	//PROGRESS BAR THREAD CODE STARTS HERE
	//**************************************
	ProgressDialog progressDialog;
	ProgressThread progressThread;
	private void ShowProgressDialog_Bar() {
		 
		 progressDialog = new ProgressDialog(this);
		  
         progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
         progressDialog.setMessage("Loading...");
         progressDialog.setCancelable(false);
         progressThread = new ProgressThread(progressHandler);
         progressThread.start();
         
         progressDialog.show();

         
	}
	
	 // Define the Handler that receives messages from the thread and update the progress
    final Handler progressHandler = new Handler() {
        public void handleMessage(Message msg) {
            int total = msg.getData().getInt("total");
            progressDialog.setProgress(total);
            if (total >= 100){
            	progressDialog.dismiss();
                //dismissDialog(PROGRESS_DIALOG);
                progressThread.setState(ProgressThread.STATE_DONE);
            }
        }
    };
    
    /** Nested class that performs progress calculations (counting) */
    private class ProgressThread extends Thread {
        Handler mHandler;
        final static int STATE_DONE = 0;
        final static int STATE_RUNNING = 1;
        int mState;
        int total;
       
        ProgressThread(Handler h) {
            mHandler = h;
        }
       
        public void run() {
            mState = STATE_RUNNING;   
            total = 0;
            while (mState == STATE_RUNNING) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Log.e("ERROR", "Thread Interrupted");
                }
                Message msg = mHandler.obtainMessage();
                Bundle b = new Bundle();
                b.putInt("total", total);
                msg.setData(b);
                mHandler.sendMessage(msg);
                total++;
            }
        }
        
        /* sets the current state for the thread,
         * used to stop the thread */
        public void setState(int state) {
            mState = state;
        }
    }
	//PROGRESS BAR THREAD CODE STARTS HERE
	//**************************************
    
	private void ShowCustomDialog_Xml() {
		Context mContext = getApplicationContext();
		Dialog dialog = new Dialog(mContext);

		dialog.setContentView(R.layout.custom_dialog);
		dialog.setTitle("Custom Dialog");

		TextView text = (TextView) dialog.findViewById(R.id.custdlg_text);
		text.setText("Hello, this is a custom dialog!");
		ImageView image = (ImageView) dialog.findViewById(R.id.custdlg_image);
		image.setImageResource(R.drawable.icon);
		dialog.show();
	}
}