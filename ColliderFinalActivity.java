package com.czj.collider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wzq.have.BaseView;

public class ColliderFinalActivity extends Activity {
	
	
	private BaseView  view;   
	LinearLayout body;
    LayoutParams params;
    int   allowka=1; //allow game set
    int   currentka=1;//current set
    private  MediaPlayer sound; //play sound

    @SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
        View locla=LayoutInflater.from(this).inflate(R.layout.main, null);
		setContentView(locla);
		body=(LinearLayout)findViewById(R.id.body);
	    params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		//set 1 is default
	    view=new View1(this);
		body.addView(view, params);
		//button 
		Button menu1=(Button)findViewById(R.id.menu1);
		menu1.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				AlertDialog alert = new AlertDialog.Builder(ColliderFinalActivity.this).create();
	            alert.setTitle("about");
	            alert.setMessage("game---SuperCollider    deveTime---@2012.7—2012.8        school---SJTU-SE            programmer---陈中杰                           id---5100379056 ");
	            alert.setButton("confirm", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                  
	                }
	            });
		        alert.show();
			}
		});
		//sound
		Button menu2=(Button)findViewById(R.id.menu2);
		menu2.setOnClickListener(new OnClickListener() {
			
		
			public void onClick(View v) {
				playSound();
			}
		});
		//selection button
		Button menu3=(Button)findViewById(R.id.menu3);
		menu3.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				
				guanka();
			}
		});
		//restart button
		Button menu4=(Button)findViewById(R.id.menu4);
		menu4.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				body.removeAllViews();
				view.exit();
				if(currentka==1)
				{
			       view=new View1(ColliderFinalActivity.this);
				}else if(currentka==2)
				{
					view=new View2(ColliderFinalActivity.this);
				}else if(currentka==3)
				{
					view=new View3(ColliderFinalActivity.this);
				}else if(currentka==4)
				{
					view=new View4(ColliderFinalActivity.this);
				}else if(currentka==5)
				{
					view=new View5(ColliderFinalActivity.this);
				}
			    body.addView(view, params);
			    ColliderFinalActivity.this.findViewById(R.id.menu4).setClickable(false);//close the restart
			    ColliderFinalActivity.this.findViewById(R.id.menu5).setClickable(true);//display the restart
			}
		});
		findViewById(R.id.menu4).setClickable(false);
		//set
		guanka();
		
		
		//whether this phone fits for this game
		getWindow().setFlags(WindowManager.
				LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay()
		.getMetrics(displayMetrics);
		//screen width
		int canvasWidth = displayMetrics.widthPixels;
	    //screen height
	    int canvasHeight = displayMetrics.heightPixels;
		if(canvasWidth<480||canvasHeight<800)
		{
			AlertDialog alert = new AlertDialog.Builder(ColliderFinalActivity.this).create();

            alert.setTitle("game info");
            alert.setMessage("this phone can not play this game");
            alert.setButton("confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                          finish();
                }
            });
	        alert.show();
		}
		
		//playSound();
    }
    //set
    public  void guanka()
    {
    	View help=LayoutInflater.from(this).inflate(R.layout.menu, null);
		LayoutParams params2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		final Dialog dialog = new Dialog(this,R.style.FullHeightDialog);
		dialog.setTitle("game set");
		dialog.addContentView(help, params2);
		dialog.show();
		
		final TextView  guanka1=(TextView)help.findViewById(R.id.guanka1);
		final TextView  guanka2=(TextView)help.findViewById(R.id.guanka2);
		final TextView  guanka3=(TextView)help.findViewById(R.id.guanka3);
		final TextView  guanka4=(TextView)help.findViewById(R.id.guanka4);
		final TextView  guanka5=(TextView)help.findViewById(R.id.guanka5);
		guanka1.setTextColor(Color.YELLOW);
		SharedPreferences sp = getSharedPreferences("Guanka",Context.MODE_PRIVATE);  
		allowka=sp.getInt("ka", 1);
		if(allowka>1)
		{
			guanka2.setTextColor(Color.YELLOW);
		}else
		{
			guanka2.setEnabled(false);
		}
		if(allowka>2)
		{
			guanka3.setTextColor(Color.YELLOW);
		}else
		{
			guanka3.setEnabled(false);
		}
		if(allowka>3)
		{
			guanka4.setTextColor(Color.YELLOW);
		}else
		{
			guanka4.setEnabled(false);
		}
		if(allowka>4)
		{
			guanka5.setTextColor(Color.YELLOW);
		}else
		{
			guanka5.setEnabled(false);
		}
	    guanka1.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					dialog.cancel();
					body.removeAllViews();
					view.exit();
				    view=new View1(ColliderFinalActivity.this);
				    body.addView(view, params);
				    ColliderFinalActivity.this.findViewById(R.id.menu4).setClickable(false);
				    ColliderFinalActivity.this.findViewById(R.id.menu5).setClickable(true);
				    currentka=1;
				}
	    });
        guanka2.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				dialog.cancel();
				body.removeAllViews();
				view.exit();
			    view=new View2(ColliderFinalActivity.this);
			    body.addView(view, params);
			    ColliderFinalActivity.this.findViewById(R.id.menu4).setClickable(false);
			    ColliderFinalActivity.this.findViewById(R.id.menu5).setClickable(true);
			    currentka=2;
			}
		});
        guanka3.setOnClickListener(new OnClickListener() {
			
		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				dialog.cancel();
				body.removeAllViews();
				view.exit();
			    view=new View3(ColliderFinalActivity.this);
			    body.addView(view, params);
			    ColliderFinalActivity.this.findViewById(R.id.menu4).setClickable(false);
			    ColliderFinalActivity.this.findViewById(R.id.menu5).setClickable(true);
			    currentka=3;
			}
		});
        guanka4.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				dialog.cancel();
				body.removeAllViews();
				view.exit();
			    view=new View4(ColliderFinalActivity.this);
			    body.addView(view, params);
			    ColliderFinalActivity.this.findViewById(R.id.menu4).setClickable(false);
			    ColliderFinalActivity.this.findViewById(R.id.menu5).setClickable(true);
			    currentka=4;
			}
		});
        guanka5.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				dialog.cancel();
				body.removeAllViews();
				view.exit();
			    view=new View5(ColliderFinalActivity.this);
			    body.addView(view, params);
			    ColliderFinalActivity.this.findViewById(R.id.menu4).setClickable(false);
			    ColliderFinalActivity.this.findViewById(R.id.menu5).setClickable(true);
			    currentka=5;
			}
		 });
    }
    
    public boolean onTouchEvent(MotionEvent event) {
    	 view.onMyTouchEvent(event);
    	 return super.onTouchEvent(event);  
    }
    public  Handler   handler=new Handler()
	{

		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==1)
			{
				AlertDialog alert = new AlertDialog.Builder(ColliderFinalActivity.this).create();

	            alert.setTitle("game info");
	            alert.setMessage("Congratulations");
	            alert.setButton("confirm", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                  
	                	 SharedPreferences sp = getSharedPreferences("Guanka",Context.MODE_PRIVATE);  
	                	 if(allowka==currentka)
	                	 {
	                		 allowka=allowka+1;
	                		 sp.edit().putInt("ka", allowka).commit();
	                	 }
	                	 guanka();
	                	
	                }
	            });
		        alert.show();
				ColliderFinalActivity.this.findViewById(R.id.menu4).setClickable(true);
			    ColliderFinalActivity.this.findViewById(R.id.menu5).setClickable(false);
			    
			}else if(msg.what==2)
			{
				Toast.makeText(ColliderFinalActivity.this, "good！", Toast.LENGTH_SHORT).show();
			}
		    super.handleMessage(msg);
		}
		
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		view.exit();
		if(sound!=null&&sound.isPlaying())
        {
			sound.stop();
        }
		System.exit(0);
	}
    //play sound
	public  void playSound()
	{
		if(sound==null)
			sound=MediaPlayer.create(ColliderFinalActivity.this,R.drawable.sound);
		    try
            {   if(!sound.isPlaying())
                {
            	    sound.release();
					sound=MediaPlayer.create(ColliderFinalActivity.this,R.drawable.sound);
			        sound.start();
			        sound.setLooping(true); 
			        Toast.makeText(ColliderFinalActivity.this, "start sound！", Toast.LENGTH_SHORT).show();
                }else 
                {
                	sound.setLooping(false); 
                	sound.stop();
                	Toast.makeText(ColliderFinalActivity.this, "close sound！", Toast.LENGTH_SHORT).show();
                }
         }catch (Exception me)
         {
                me.printStackTrace();
         }
	}
}
