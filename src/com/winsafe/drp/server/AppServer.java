package com.winsafe.drp.server;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.hbm.util.DateUtil;

public class AppServer {
	    private final Timer timer = new Timer(); 
	    private final int minutes;
	    OrganSafety ws = new OrganSafety();

	    public AppServer(int minutes) { 
	        this.minutes = minutes; 
	    } 

		public void start() { 
	        timer.schedule(new TimerTask() { 
	            public void run() { 
	                playSound(); 
	                //timer.cancel(); 
	            } 
	            private void playSound() { 
	                //得到当前时间
	                Date currDatetime = DateUtil.getCurrentTimestamp();
	                if(Integer.valueOf(currDatetime.getHours())==2){
	                	
	                	try {
							ws.createWarehouseSafety();
						} catch (Exception e) {
							e.printStackTrace();
						}
	                }
	                
	                // Start a new thread to play a sound... 
	            } 
	        }, 18000, 18000); //30分钟
	        
	    } 

	    public static void main(String[] args) { 
	    	AppServer eggTimer = new AppServer(2); 
	        eggTimer.start(); 
	    } 


}

