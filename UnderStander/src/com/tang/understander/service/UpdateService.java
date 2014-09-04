package com.tang.understander.service;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.joda.time.DateTime;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class UpdateService  extends Service {
	
	private String TAG = UpdateService.class.getName();
    Timer timer    = null;  
    private ExecutorService service= Executors.newFixedThreadPool(1);  
    
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service is Created");   
        timer    = new Timer();  
        timer.schedule(task,0, 3000);//开启定时器，delay 1s后执行task 
    }
    
    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "Service is Unbinded");
        return true;
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service is started");
        //如果被系统关闭将自动重启
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "服务已完成任务中止了！");
        timer.cancel();
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
	
    
    TimerTask task = new TimerTask(){    
    	public void run(){
    		Message message = new Message(); 
    		message.what = 1; 
    		handler.sendMessage(message); 
    	}    
    };
    
    
    
    Handler handler = new Handler() {  
        public void handleMessage(Message msg) {   
             switch (msg.what) {   
                  case 1:   
                	  service.submit(new MyThread());
                  break;   
             }   
        }   
   };  
   
   
   public class MyThread implements Runnable {

       @Override
       public void run() {
    	   try {
			Thread.sleep(10000);
			Log.d(TAG, "我在做事中"+new DateTime().toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }

   }
   
   
	
}
