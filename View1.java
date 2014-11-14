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
  
  
public class View1 extends BaseView {  
  
	public  int  canvasWidth =480;
	public  int  canvasHeight=800;//屏幕宽高
	public  float density=1;
	boolean isRun=false;
	public Vector<Body>   bodyVector=new Vector<Body>();
	public  Body  ball;
	public  ColliderFinalActivity context;
    public View1(final ColliderFinalActivity c) {  
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
	    
	    //创建游戏布局
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
				world.destroyBody(ball);
				bodyVector.removeElement(ball);
				ball=body;
				
			}
		});
		
   }  
   //创建游戏布局 
   private void create() { 
    	float  x,y,w,h;
        Body body = null;  
    	// 地板   ===============================================================//
        //左斜边
        x=canvasWidth/9;
        y=canvasHeight-70;
        w=(canvasWidth/5)*2;
        h=10f;
        body = createPolygon(x, y,w, h,0.3f,0.5f, true,R.drawable.bottom,"bottom");  
        body.setTransform(new Vec2(x/RATE, y/RATE),-85/RATE);
        bodyVector.add(body);
        //右斜边
        x=canvasWidth*8/9;
        y=canvasHeight-70;
        w=(canvasWidth/5)*2;
        h=10f;
        body = createPolygon(x, y,w, h,0.3f,0.5f, true,R.drawable.bottom,"bottom");  
        body.setTransform(new Vec2(x/RATE, y/RATE),85/RATE);
        bodyVector.add(body);
        //左
        x=1;
        y=canvasHeight/2;
        w=1;
        h=canvasHeight;
        body = createPolygon(x, y,w, h,0.3f,0.5f, true,-1,"bottom");  
        bodyVector.add(body);
        //右
        x=canvasWidth-1;
        y=canvasHeight/2;
        w=1;
        h=canvasHeight;
        body = createPolygon(x, y,w, h,0.3f,0.5f, true,-1,"bottom");  
        bodyVector.add(body);
        //底部
        x=canvasWidth/2;
        y=canvasHeight-5;
        w=canvasWidth;
        h=20f;
        body = createPolygon(x, y,w, h,0.3f,0.5f, true,R.drawable.bottom,"bottom");  
        bodyVector.add(body);
        //柱子===============================================================//
        x=canvasWidth/3;
        y=canvasHeight-10-200;
        w=50f;
        h=200f;
        body = createPolygon(x, y, w, h,0.3f,0.5f, false,R.drawable.pillar1,"pillar1");  
        bodyVector.add(body);
        
        body =createCircle(canvasWidth/3-30, canvasHeight-10-200, 30f, 0.3f, 0.3f, false,R.drawable.ball2,"ball2"); 
        bodyVector.add(body);
        
        x=canvasWidth*3/5;
        y=canvasHeight-10-120;
        w=50f;
        h=120f;
        body = createPolygon(x, y, w, h,0.3f,0.5f, false,R.drawable.pillar1,"pillar2");  
        bodyVector.add(body);
        
        body = createCircle(canvasWidth*3/5-20,canvasHeight-10-120, 20f, 0.3f, 0.3f, false,R.drawable.ball3,"ball3"); 
        bodyVector.add(body);
   
        
        //可控制球===============================================================//   
        body = createCircle(20, 80f, 25f, 0.3f, 0.9f, true,R.drawable.ball1,"ball");  
        bodyVector.add(body);
        ball=body;
        

    }  
    //画游戏界面
    protected void onDraw(Canvas canvas) {  
        // TODO Auto-generated method stub  
        //canvas.drawColor(0xff000000);  
        //paint.setColor(0xff00ff00);  
        paint.setAntiAlias(false); //消除画笔锯齿  
        paint.setStyle(Paint.Style.STROKE);  
        //得到body链表表头   
        for(int i=0; i<bodyVector.size(); i++){  
        	  
        	Body  body=bodyVector.elementAt(i);
            if(body.isCircle)
            {
                   drawCircle(canvas, body);  
            }else if(body.isPolygon)
            {
            	drawPolygon(canvas, body);  
            }
        }  
        super.onDraw(canvas);  
    }  
  
    
    boolean isMove=false;//判断可控小球是否可以移动
    Toast  toast;//提示控件
    
    //触磨可控小球移动判断
    public boolean onMyTouchEvent(MotionEvent event) {
    	if(!isRun)
    	{
    		
    		
    		 if(event.getAction()==MotionEvent.ACTION_MOVE)
    		 {
    			if(isMove)
    			{
	    			float x=event.getX();
	 	    		float y=event.getY();
	 	    		if(check())
	 	    		{
	 	    			ball.m_xf.position.set(new Vec2(x/RATE, y/RATE));
	 	    			if(toast!=null)
	 	    				  toast.cancel();
	 	    		}else
	 	    		{
	 	    			
	 	    		    Vec2 position = world2screen2(ball.getPosition());  
	 	    			ball.m_xf.position.set(new Vec2(position.x/RATE, (position.y-30)/RATE));
	 	    		    if(toast==null)
	 	    		    toast=Toast.makeText(context, "这里不能摆放小球！", Toast.LENGTH_SHORT);
	 	    		    toast.cancel();
	 	    		    toast.show();
	 	    		}
    			}
    		 }else
    		 {
    			    Vec2 position = world2screen2(ball.getPosition());  
    	    		float x=event.getX();
    	    		float y=event.getY();
    	    		if(position.x-ball.r<x&&position.x+ball.r>x
    	    		  &&position.y-ball.r<y&&position.y+ball.r>y)
    	    		{
    	    			isMove=true;
    	    		}else
    	    		{
    	    			isMove=false;
    	    		}
    		 }
    	}
    	return true;
    }
    
   //碰撞监听，我们为这个世界增加碰撞监听，当刚体发生碰撞时，会回调这个方法
    public class   myContactListener implements ContactListener
    {

		
		public void beginContact(Contact contact) {
			// TODO Auto-generated method stub
			
		}

		
		public void endContact(Contact contact) {
			// TODO Auto-generated method stub
			
		}
		
		public void postSolve(Contact contact, ContactImpulse impulse) {
			// TODO Auto-generated method stub
		}
		
		public void preSolve(Contact contact, Manifold oldManifold) {
			// TODO Auto-generated method stub
			//目标球相碰
			if(isRun)
			{
				if(contact.m_fixtureA.m_body.bodyName.endsWith("ball2")
				  &&contact.m_fixtureB.m_body.bodyName.endsWith("ball3"))
				{
					        //contact.m_fixtureA.m_body.m_type= BodyType.STATIC; 
					        //contact.m_fixtureB.m_body.m_type= BodyType.STATIC; 
					       // contact.m_fixtureB.m_body.m_xf.position.set(new Vec2(0/RATE, 0/RATE));
					       // contact.m_fixtureA.m_body.m_xf.position.set(new Vec2(0/RATE, 0/RATE));
							world.destroyBody2(contact.m_fixtureA.m_body);
							world.destroyBody2(contact.m_fixtureB.m_body);
							bodyVector.removeElement(contact.m_fixtureA.m_body);
							bodyVector.removeElement(contact.m_fixtureB.m_body);
						
							isRun=false;
							 Message message = new Message();      
					    	 message.what = 1;
					    	 //发生成功闯过消息
					    	 context.handler.sendMessage(message);
					    	 Toast.makeText(context, "good！", Toast.LENGTH_SHORT).show();
				}
			}
		}
    }
    
    //坚持位置是否可以摆放小球
    boolean check()
    {
    	for(int i=0; i<bodyVector.size(); i++){  
        	    Body  body=bodyVector.elementAt(i);
        	    if(!body.bodyName.equals(ball.bodyName))
        	    {
        	      Vec2 position = world2screen2(body.getPosition());
        	      Vec2 position2 = world2screen2(ball.getPosition()); 
        	      float x=position2.x;
        	      float y=position2.y;
        	      float tt=ball.r;
        	      if(body.isCircle)
        	      {
        	    	  if(position.x-ball.r-tt<x&&position.x+ball.r+tt>x
            	    		  &&position.y-ball.r-tt<y&&position.y+ball.r+tt>y)
            	      {
        	    		  return false;
            	      }
        	      }else if(body.isPolygon)
        	      {
        	    	  if(position.x-ball.w/2-tt<x&&position.x+ball.w/2+tt>x
            	    		  &&position.y-ball.h/2+tt<y&&position.y+ball.h/2+tt>y)
            	      {
        	    		  return false;
            	      }
        	      }
        	    }
        }  
    	return true;
    }
    //退出游戏
    public  void   exit()
    {
    	try
    	{
    		isRun=false;
    		super.isRun=false;
	    	for(int i=0; i<bodyVector.size(); i++){  
	    	    Body  body=bodyVector.elementAt(i);
	    	    if(body.bitmap!=null)
	    	    body.bitmap.recycle();
	    	    world.destroyBody2(body);
	    	}
	    	bodyVector=null;
	    	world=null;
	    	System.gc();
    	}catch(Exception e){e.printStackTrace();}
    }
    
}   

