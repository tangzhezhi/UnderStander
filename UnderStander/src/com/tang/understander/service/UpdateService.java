package com.tang.understander.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class UpdateService  extends Service {
	
	private String TAG = UpdateService.class.getName();
	
    private int count;
    private boolean quit;
    
    private Thread thread;
    private MyBinder binder=new MyBinder();
    public class MyBinder extends Binder
    {
        // 声明一个方法，把count暴露给外部程序。
        public int getCount(){
            return count;
        }
    }
	
	
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service is Created");   
        thread=new Thread(new Runnable() {            
            @Override
            public void run() {
                // 每间隔一秒count加1 ，直到quit为true。
                while(!quit){
                    try{
                        Thread.sleep(1000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    count++;
                }
            }
        });
        thread.start();
        
        
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "Service is Unbinded");
        return true;
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service is started");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service is Destroyed");
        this.quit=true;
        
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
	
	
}
