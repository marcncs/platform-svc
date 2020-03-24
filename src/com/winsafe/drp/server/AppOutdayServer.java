package com.winsafe.drp.server;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.Organ;

public class AppOutdayServer {
	    private final Timer timer = new Timer(); 
	    AppReceivableObject appro = new AppReceivableObject();
	    OrganService os = new OrganService();
	    AppCustomer ac = new AppCustomer();

	    public AppOutdayServer() { 
	    } 

		public void start() { 
			
	        timer.schedule(new TimerTask() { 
	            public void run() { 
	            //    addMsgDeal(); 
	                
	            } 
	            private void addMsgDeal() { 
	            	try{
	            		//System.out.println("=========start run msg deal==========>");
	            		List list = appro.getOutday();
	            		for ( int i=0; i<list.size(); i++ ){
	            			Map obj = (Map)list.get(i);
	            			String oid = String.valueOf(obj.get("oid"));
	            			int objectsort = Integer.valueOf((String)obj.get("objectsort"));
	            			String outday = String.valueOf(obj.get("outday"));
	            			String name = "";
	            			String mobile = "";
	            			if ( objectsort == 0 ){
	            				Organ o = os.getOrganByID(oid);
	            				name = o.getOrganname();
	            				mobile = o.getOmobile();
	            			}else if ( objectsort == 1 ){
	            				Customer c = ac.getCustomer(oid);
	            				name = c.getCname();
	            				mobile = c.getMobile();
	            			}
	            			MsgService ms = new MsgService(new String[]{"name","day"}, 
	            					new String[]{name,outday}, null, 17);
	            			ms.addmag(1,mobile);
	            		}
	            	}catch ( Exception e ){
	            		e.printStackTrace();
	            	}
	            } 
	        }, getTime(), 24*60*60*1000L); 
	        
	    } 
		
		private Date getTime(){
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 9);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			return calendar.getTime();

		}

	   


}

