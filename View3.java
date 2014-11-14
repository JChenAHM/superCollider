package com.czj.collider;

import java.util.Vector;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

import com.wzq.have.BaseView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
  
  
public class View3 extends BaseView {  
  
	public  int  canvasWidth =480;
	public  int  canvasHeight=800;//屏幕宽高
	public  float density=1;
	boolean isRun=false;
	public Vector<Body>   bodyVector=new Vector<Body>();
	public  Body  ball;
	public  Body  ball2;
	public  ColliderFinalActivity context;
    public View3(final ColliderFinalActivity c) {  
        super(c); 
        
        //设置游戏背景颜色
        //this.setBackgroundColor(Color.rgb(47, 44, 44));
        //设置游戏背景图片
        this.setBackgroundResource(R.drawable.background);
        
        this.context=c;
        context.getWindow().setFlags(WindowManager.
				LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		 context.getWindowManager().getDefaultDisplay()
		.getMetrics(displayMetrics);
		//获得当前屏幕分辨率密度
		density = displayMetrics.density;
		//屏幕宽度
		canvasWidth = displayMetrics.widthPixels;
	    //屏幕高度
	    canvasHeight = displayMetrics.heightPixels;
        create();  
        world.setContactListener(new myContactListener());
         //开始模拟
        Button menu5=(Button)context.findViewById(R.id.menu5); 
		menu5.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				context.findViewById(R.id.menu4).setClickable(true);//开启重玩按钮
				context.findViewById(R.id.menu5).setClickable(false);//禁止开始按钮
				isRun=true;
				Vec2 position = world2screen2(ball.getPosition());  
				Body body = createCircle(position.x-25f, position.y, 25f, 0.5f, 0.9f, false,R.drawable.ball1,"ball");
				bodyVector.add(body);
				world.destroyBody2(ball);
				bodyVector.removeElement(ball);
				ball=body;
				
				position = world2screen2(ball2.getPosition());  
			    body = createCircle(position.x-25f, position.y, 25f, 0.5f, 0.9f, false,R.drawable.ball1,"ball");
				bodyVector.add(body);
				world.destroyBody2(ball2);
				bodyVector.removeElement(ball2);
				ball2=body;
			}
		});
		
   }  
