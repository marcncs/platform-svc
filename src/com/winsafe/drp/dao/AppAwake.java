package com.winsafe.drp.dao;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;

/**
 * <p>Title: 城网技术</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: 城网计算机</p>
 *
 * @author jelli
 * @version 1.0
 */
public class AppAwake {
	
	public CalendarAwake findByID(Integer id) throws Exception {//AwakeAction用
		CalendarAwake ca = null;
		String sql = "from CalendarAwake as ca where  ca.awakedatetime< to_date('"
				+ DateUtil.getCurrentDateTime()
				+ "','yyyy-mm-dd hh24:mi:ss') and ca.isawake=0 and ca.isdel=0 and ca.id=" + id+ " order by id desc";
		ca = (CalendarAwake) EntityManager.find(sql);
		return ca;
	}

	public CalendarAwake findByUserID(Integer userid) throws Exception {//DWR用
		CalendarAwake ca = null;
		String sql = "from CalendarAwake as ca where  ca.awakedatetime< '"
				+ DateUtil.getCurrentDate()
				+ "' and ca.isawake=0 and ca.isdel=0 and ca.userid=" + userid+ " order by id desc";
		ca = (CalendarAwake) EntityManager.find(sql);
		return ca;
	}

	public void affrieGetAwake(Integer id) throws Exception {
		String sql = " update calendar_awake set isawake = 1 where id =" + id; // and userid=+userid;
		//System.out.println("sql--------=" + sql);
		EntityManager.updateOrdelete(sql);
	}

}
