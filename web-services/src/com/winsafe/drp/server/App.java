package com.winsafe.drp.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.winsafe.hbm.entity.HibernateUtil;

public class App extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7279979516130113097L;
	private static AppServer appServer = null;
	private static AppMsgDeal appMsg = null;
	private static Logger logger = Logger.getLogger(App.class);
	public void init() throws ServletException {
		logger.debug("--------start-----------");
		//loadCache();
//		appServer = new AppServer(1);
//		appServer.start();

//		AppOutdayServer appos = new AppOutdayServer();
//		appos.start();
		// appMsg = new AppMsgDeal(2);
		// appMsg.start();
	}

	private void loadCache() throws ServletException {
		// System.out.println("--------cache loading... -----------");
		BaseResourceService brs = new BaseResourceService();
		OrganService os = new OrganService();
		DeptService ds = new DeptService();
		WarehouseService ws = new WarehouseService();
		UsersService us = new UsersService();
		try {
			brs.getAllBaseResource();
			os.getAllOrgan();
			ds.getAllDept();
			ws.getAllWarehousebean();
			us.getAllUsers();
			// System.out.println("--------cache loaded success-----------");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			// throw new
			// ServletException("============load cache error:"+e1.getMessage());
			throw new ServletException("load cache error:" + e1.getMessage());
		}
	}
	//
	// public void destroy(){
	// //shutdown message server .
	// }

	// public AppServer getAppServer()
	// {
	// return this.appServer;
	// }

}
