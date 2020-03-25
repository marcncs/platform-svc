package com.winsafe.drp.action.self;

import com.winsafe.drp.dao.AppAwake;
import com.winsafe.drp.dao.CalendarAwake;


public class ListAwakeDWR {

		
	public CalendarAwake getAwake(Integer userid)throws Exception{
		//System.out.println("===========into ======into========");
		CalendarAwake ca = null;
		AppAwake appAwoke = new AppAwake();
		ca=appAwoke.findByID(userid);
		//if(ca.getAwakecontent().length()>10)
		//	ca.setAwakecontent(ca.getAwakecontent().substring(0,9)+"...");
		//System.out.println("===========into =======into======="+ca);
		return ca;
	}
	
	public void affrieAwake(Integer id)throws Exception{
		
		//Session 
	    ////Connection 
	    

	    AppAwake appAwake=new AppAwake();
	    try {
	      appAwake.affrieGetAwake(id);
	      
	    }
	    catch (Exception e) {
	      
	      e.printStackTrace();
	    }
	    finally
	      {
	          //
	      }
	}


}
