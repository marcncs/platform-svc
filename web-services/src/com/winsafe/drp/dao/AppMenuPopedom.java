package com.winsafe.drp.dao;

import com.winsafe.drp.entity.EntityManager;

public class AppMenuPopedom {
	public void setMenuPopedom(Long userid,Long roleid)throws Exception{
		
		String sql="insert into menu_popedom(userid,menuurl) "
			+"select ur.userid,rm.menuurl from user_role as ur,role_menu as rm " 
			+"where ur.roleid=rm.roleid and ur.userid="+userid+" and rm.roleid="+roleid+" and rm.ispopedom=1 and rm.menuurl "
			+"not in(select mp.menuurl from menu_popedom as mp,role_menu as rm where mp.menuurl=rm.menuurl and mp.userid="+userid+") ";

		EntityManager.updateOrdelete(sql);
				
	}
}
